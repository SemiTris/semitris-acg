<template>
  <div class="anime-wall">
    <div class="toolbar">
      <el-input
        v-model="query.title"
        placeholder="搜索标题"
        clearable
        style="width: 220px"
        @keyup.enter="load(1)"
        @clear="load(1)"
      >
        <template #append>
          <el-button @click="load(1)">搜索</el-button>
        </template>
      </el-input>
      <el-radio-group v-model="query.type" @change="load(1)">
        <el-radio-button :label="0">全部</el-radio-button>
        <el-radio-button :label="1">动画</el-radio-button>
        <el-radio-button :label="2">漫画</el-radio-button>
      </el-radio-group>
      <el-radio-group v-model="query.status" @change="load(1)">
        <el-radio-button :label="-1">全部状态</el-radio-button>
        <el-radio-button :label="0">连载中</el-radio-button>
        <el-radio-button :label="1">已完结</el-radio-button>
      </el-radio-group>
      <el-button type="primary" @click="openAdd">新增番剧</el-button>
    </div>

    <div class="card-grid">
      <AnimeCard
        v-for="a in list"
        :key="a.id"
        :anime="a"
        @edit="openEdit"
        @delete="onDelete"
      />
    </div>
    <div class="pager">
      <Pagination
        :total="total"
        :page-num="query.pageNum"
        :page-size="query.pageSize"
        @change="load"
      />
    </div>

    <AnimeEditDialog
      v-model:visible="dialogVisible"
      :anime="editing"
      @saved="onSaved"
    />
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { pageAnime, deleteAnime } from '@/api/anime'
import { ElMessage, ElMessageBox } from 'element-plus'
import AnimeCard from '@/components/AnimeCard.vue'
import Pagination from '@/components/Pagination.vue'
import AnimeEditDialog from './AnimeEditDialog.vue'

// 筛选状态：type 0=全部（不传），status -1=全部（不传）
const query = reactive({ pageNum: 1, pageSize: 8, title: '', type: 0, status: -1 })
const list = ref([])
const total = ref(0)
const dialogVisible = ref(false)
const editing = ref(null)

async function load(pageNum = query.pageNum) {
  query.pageNum = pageNum
  const params = { pageNum, pageSize: query.pageSize }
  if (query.title) params.title = query.title
  if (query.type !== 0) params.type = query.type
  if (query.status !== -1) params.status = query.status
  const data = await pageAnime(params)
  list.value = data.list || []
  total.value = data.total || 0
}

const openAdd = () => {
  editing.value = null
  dialogVisible.value = true
}
const openEdit = (anime) => {
  editing.value = anime
  dialogVisible.value = true
}
const onSaved = () => {
  dialogVisible.value = false
  load()
}

async function onDelete(anime) {
  await ElMessageBox.confirm(
    `确定删除「${anime.title}」？关联的收藏/评价/推荐将一并删除`,
    '删除确认',
    { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' }
  )
  await deleteAnime(anime.id)
  ElMessage.success('删除成功')
  load()
}

onMounted(() => load())
</script>

<style scoped>
.toolbar {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  align-items: center;
  margin-bottom: 16px;
}
.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 16px;
}
.pager {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
