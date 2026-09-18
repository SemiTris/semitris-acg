<template>
  <div class="recommend-wall">
    <div class="toolbar">
      <el-button type="primary" @click="openAdd">新增推荐</el-button>
    </div>

    <div class="cards">
      <el-card v-for="r in list" :key="r.id" class="rec-card" shadow="never">
        <div class="rec-row">
          <div class="thumb"><CoverImage :src="r.cover" /></div>
          <div class="rec-info">
            <div class="rtitle">{{ r.title }}</div>
            <div class="reason">{{ r.reason }}</div>
            <div v-if="r.tags" class="rtags">
              <el-tag v-for="t in r.tags.split(',')" :key="t" size="small">{{ t }}</el-tag>
            </div>
            <div class="foot">
              <span class="time">{{ formatTime(r.createdAt) }}</span>
              <el-button link size="small" @click="openEdit(r)">编辑</el-button>
              <el-popconfirm title="删除该推荐？" @confirm="del(r)">
                <template #reference>
                  <el-button link type="danger" size="small">删除</el-button>
                </template>
              </el-popconfirm>
            </div>
          </div>
        </div>
      </el-card>
    </div>
    <el-empty v-if="!list.length" description="还没有推荐" />

    <el-dialog v-model="dialog" :title="editing ? '编辑推荐' : '新增推荐'" width="480px">
      <el-form label-width="70px">
        <!-- 新增可选题番；编辑不传 anime_id（不可变更） -->
        <el-form-item v-if="!editing" label="番剧">
          <el-select v-model="form.animeId" filterable placeholder="选择番剧">
            <el-option v-for="a in animeOptions" :key="a.id" :label="a.title" :value="a.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="理由">
          <el-input type="textarea" v-model="form.reason" :rows="3" />
        </el-form-item>
        <el-form-item label="标签">
          <el-input v-model="form.tags" placeholder="逗号分隔" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialog = false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { listRecommend, addRecommend, updateRecommend, deleteRecommend } from '@/api/recommend'
import { listAnime } from '@/api/anime'
import CoverImage from '@/components/CoverImage.vue'

const list = ref([])
const animeOptions = ref([])
const dialog = ref(false)
const editing = ref(null)
const form = reactive({ animeId: null, reason: '', tags: '' })

const formatTime = (t) => (t ? String(t).replace('T', ' ').slice(0, 19) : '')

async function load() {
  list.value = (await listRecommend()) || []
}

async function loadAnimes() {
  animeOptions.value = (await listAnime()) || []
}

function openAdd() {
  Object.assign(form, { animeId: null, reason: '', tags: '' })
  editing.value = null
  dialog.value = true
}
function openEdit(r) {
  Object.assign(form, { animeId: r.animeId, reason: r.reason, tags: r.tags })
  editing.value = r
  dialog.value = true
}

async function submit() {
  if (editing.value) {
    // 编辑：只传变更字段 + id；anime_id 不可变更
    await updateRecommend({ id: editing.value.id, reason: form.reason, tags: form.tags })
    ElMessage.success('已修改')
  } else {
    await addRecommend({ animeId: form.animeId, reason: form.reason, tags: form.tags })
    ElMessage.success('已推荐')
  }
  dialog.value = false
  load()
}

async function del(r) {
  await deleteRecommend(r.id)
  ElMessage.success('已删除')
  load()
}

onMounted(() => {
  load()
  loadAnimes()
})
</script>

<style scoped>
.toolbar {
  margin-bottom: 16px;
}
.cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 14px;
}
.rec-row {
  display: flex;
  gap: 12px;
}
.thumb {
  width: 100px;
  height: 130px;
  flex-shrink: 0;
  border-radius: 6px;
  overflow: hidden;
}
.rtitle {
  font-weight: 600;
  color: var(--text-primary);
}
.reason {
  color: var(--text-secondary);
  margin: 6px 0;
  line-height: 1.6;
}
.rtags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}
.foot {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 8px;
}
.time {
  color: var(--text-secondary);
  font-size: 12px;
  margin-right: auto;
}
</style>
