<template>
  <div class="anime-detail" v-if="anime">
    <el-page-header
      content="番剧详情"
      @back="$router.push('/anime')"
    />

    <div class="detail-top">
      <div class="cover-box"><CoverImage :src="anime.cover" /></div>
      <div class="detail-info">
        <h2 class="title">{{ anime.title }}</h2>
        <div class="original" v-if="anime.originalTitle">{{ anime.originalTitle }}</div>
        <div class="row">
          <StatusTag kind="type" :value="anime.type" />
          <StatusTag kind="status" :value="anime.status" />
        </div>
        <div class="row" v-if="anime.rating != null">评分 <b class="score">{{ anime.rating }}</b></div>
        <div class="row">年份 <b>{{ anime.year ?? '—' }}</b></div>
        <div class="row">集数 <b>{{ anime.episodes ?? 0 }} 话</b></div>
        <div class="row" v-if="anime.genre">标签 <span>{{ anime.genre }}</span></div>

        <!-- 收藏区（路由级判断） -->
        <div class="collect-area">
          <template v-if="collection">
            <el-tag type="info">已收藏（{{ listTypeText(collection.listType) }}）</el-tag>
            <div class="collect-meta">
              进度 {{ collection.progress }} · 加精 {{ collection.favorite ? '是' : '否' }}
            </div>
            <el-button size="small" @click="openUpdateCollect">更新收藏</el-button>
            <el-button size="small" type="danger" @click="cancelCollect">取消收藏</el-button>
          </template>
          <el-button v-else type="primary" @click="openAddCollect">收藏</el-button>
        </div>
      </div>
    </div>

    <div class="synopsis">{{ anime.synopsis || '暂无简介' }}</div>

    <el-divider />

    <!-- 评价区 -->
    <div class="reviews">
      <div class="rev-head">
        <h3>评价</h3>
        <el-button size="small" type="primary" @click="openAddReview">写评价</el-button>
      </div>
      <el-empty v-if="!reviews.length" description="还没有评价" />
      <div v-for="r in reviews" :key="r.id" class="rev-item">
        <div class="rev-rating">★ {{ r.rating }}</div>
        <div class="rev-content">{{ r.content }}</div>
        <div class="rev-foot">
          <span class="rev-time">{{ formatTime(r.createdAt) }}</span>
          <el-button link type="primary" size="small" @click="openEditReview(r)">编辑</el-button>
          <el-popconfirm title="删除该评价？" @confirm="delReview(r)">
            <template #reference>
              <el-button link type="danger" size="small">删除</el-button>
            </template>
          </el-popconfirm>
        </div>
      </div>
    </div>

    <!-- 收藏新增/更新弹窗 -->
    <el-dialog v-model="collectDialog" :title="collection ? '更新收藏' : '收藏番剧'" width="380px">
      <el-form label-width="70px">
        <el-form-item label="类型">
          <el-select v-model="collectForm.listType">
            <el-option v-for="n in 5" :key="n" :label="listTypeText(n)" :value="n" />
          </el-select>
        </el-form-item>
        <el-form-item label="进度">
          <el-input-number v-model="collectForm.progress" :min="0" />
        </el-form-item>
        <el-form-item label="加精">
          <el-switch v-model="collectForm.favorite" :active-value="1" :inactive-value="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="collectDialog = false">取消</el-button>
        <el-button type="primary" @click="submitCollect">保存</el-button>
      </template>
    </el-dialog>

    <!-- 评价写/改弹窗 -->
    <el-dialog v-model="reviewDialog" :title="editingReview ? '编辑评价' : '写评价'" width="380px">
      <el-form label-width="70px">
        <el-form-item label="评分">
          <el-input-number v-model="reviewForm.rating" :min="1" :max="10" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" v-model="reviewForm.content" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="reviewDialog = false">取消</el-button>
        <el-button type="primary" @click="submitReview">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getAnimeById } from '@/api/anime'
import { findByAnime, addCollection, updateCollection, deleteCollection } from '@/api/collection'
import { listReviewByAnime, addReview, updateReview, deleteReview } from '@/api/review'
import CoverImage from '@/components/CoverImage.vue'
import StatusTag from '@/components/StatusTag.vue'

const route = useRoute()
const router = useRouter()

const anime = ref(null)
const collection = ref(null)
const reviews = ref([])

const collectDialog = ref(false)
const collectForm = reactive({ listType: 1, progress: 0, favorite: 0 })
const reviewDialog = ref(false)
const editingReview = ref(null)
const reviewForm = reactive({ rating: 5, content: '' })

const animeId = () => Number(route.params.id)

const listTypeText = (n) =>
  ({ 1: '想看', 2: '在看', 3: '已看完', 4: '搁置', 5: '最爱' })[n] || String(n)
const formatTime = (t) => (t ? String(t).replace('T', ' ').slice(0, 19) : '')

async function loadAnime() {
  const id = animeId()
  anime.value = await getAnimeById(id)
  if (!anime.value) return
  collection.value = await findByAnime(id)
  if (collection.value == null) collection.value = null
  reviews.value = (await listReviewByAnime(id)) || []
}

// 收藏增改删
function openAddCollect() {
  Object.assign(collectForm, { listType: 1, progress: 0, favorite: 0 })
  collectDialog.value = true
}
function openUpdateCollect() {
  Object.assign(collectForm, {
    listType: collection.value.listType,
    progress: collection.value.progress,
    favorite: collection.value.favorite
  })
  collectDialog.value = true
}
async function submitCollect() {
  if (collection.value) {
    // 已收藏：update 只传变更字段 + id
    await updateCollection({
      id: collection.value.id,
      listType: collectForm.listType,
      progress: collectForm.progress,
      favorite: collectForm.favorite
    })
    ElMessage.success('已更新收藏')
  } else {
    // 未收藏：add（userId 后端填充，不传时间）
    await addCollection({
      animeId: anime.value.id,
      listType: collectForm.listType,
      progress: collectForm.progress,
      favorite: collectForm.favorite
    })
    ElMessage.success('收藏成功')
  }
  collectDialog.value = false
  loadAnime()
}
async function cancelCollect() {
  ElMessageBox.confirm('确定取消收藏？', '取消收藏', { type: 'warning' })
    .then(async () => {
      await deleteCollection(collection.value.id)
      ElMessage.success('已取消')
      loadAnime()
    })
    .catch(() => {})
}

// 评价写改删（rating 联动由后端重算，改后 reloadAnime 刷新即可见）
function openAddReview() {
  Object.assign(reviewForm, { rating: 5, content: '' })
  editingReview.value = null
  reviewDialog.value = true
}
function openEditReview(r) {
  Object.assign(reviewForm, { rating: r.rating, content: r.content })
  editingReview.value = r
  reviewDialog.value = true
}
async function submitReview() {
  if (editingReview.value) {
    await updateReview({ id: editingReview.value.id, rating: reviewForm.rating, content: reviewForm.content })
    ElMessage.success('已修改评价')
  } else {
    await addReview({ animeId: anime.value.id, rating: reviewForm.rating, content: reviewForm.content })
    ElMessage.success('已添加评价')
  }
  reviewDialog.value = false
  loadAnime()
}
async function delReview(r) {
  await deleteReview(r.id)
  ElMessage.success('已删除评价')
  loadAnime()
}

onMounted(loadAnime)
// 路由 id 变化时重载（从列表详情切换）
watch(() => route.params.id, loadAnime)
</script>

<style scoped>
.anime-detail {
  max-width: 860px;
  margin: 0 auto;
}
.detail-top {
  display: flex;
  gap: 20px;
  margin-top: 16px;
}
.cover-box {
  width: 200px;
  height: 280px;
  flex-shrink: 0;
  border-radius: var(--radius);
  overflow: hidden;
}
.title {
  color: var(--text-primary);
  margin: 0 0 4px;
}
.original {
  color: var(--text-secondary);
  margin-bottom: 8px;
}
.row {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--text-secondary);
  margin: 6px 0;
}
.row b {
  color: var(--text-primary);
}
.score {
  color: var(--primary);
  font-size: 20px;
}
.collect-area {
  margin-top: 14px;
  display: flex;
  align-items: center;
  gap: 10px;
  flex-wrap: wrap;
}
.collect-meta {
  color: var(--text-secondary);
  font-size: 12px;
}
.synopsis {
  margin-top: 18px;
  color: var(--text-secondary);
  line-height: 1.8;
}
.rev-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.rev-item {
  padding: 10px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.06);
}
.rev-rating {
  color: var(--primary);
  font-weight: 600;
}
.rev-content {
  color: var(--text-primary);
  margin: 4px 0;
}
.rev-foot {
  display: flex;
  align-items: center;
  gap: 8px;
}
.rev-time {
  color: var(--text-secondary);
  font-size: 12px;
  margin-right: auto;
}
</style>
