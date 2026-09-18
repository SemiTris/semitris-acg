<template>
  <div class="collection-list">
    <div class="tabs">
      <el-radio-group v-model="listType" @change="load">
        <el-radio-button :label="0">全部</el-radio-button>
        <el-radio-button v-for="n in 5" :key="n" :label="n">{{ listTypeText(n) }}</el-radio-button>
      </el-radio-group>
    </div>

    <div class="cards">
      <el-card v-for="c in list" :key="c.id" class="coll-card" shadow="never">
        <div class="coll-row">
          <div class="thumb"><CoverImage :src="c.cover" /></div>
          <div class="coll-info">
            <div class="ctitle">{{ c.title }}</div>
            <div class="cmeta">
              <StatusTag kind="list" :value="c.listType" />
              <span>进度 {{ c.progress }}</span>
              <span>{{ c.favorite ? '★加精' : '未加精' }}</span>
            </div>
            <div class="cbtns">
              <el-button size="small" @click="openEdit(c)">更新</el-button>
            </div>
          </div>
        </div>
      </el-card>
    </div>
    <el-empty v-if="!list.length" description="暂无收藏" />

    <el-dialog v-model="dialog" title="更新收藏" width="380px">
      <el-form label-width="70px">
        <el-form-item label="类型">
          <el-select v-model="editForm.listType">
            <el-option v-for="n in 5" :key="n" :label="listTypeText(n)" :value="n" />
          </el-select>
        </el-form-item>
        <el-form-item label="进度">
          <el-input-number v-model="editForm.progress" :min="0" />
        </el-form-item>
        <el-form-item label="加精">
          <el-switch v-model="editForm.favorite" :active-value="1" :inactive-value="0" />
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
import { listCollection, updateCollection } from '@/api/collection'
import CoverImage from '@/components/CoverImage.vue'
import StatusTag from '@/components/StatusTag.vue'

const listType = ref(0)
const list = ref([])
const dialog = ref(false)
const editForm = reactive({ id: null, listType: 1, progress: 0, favorite: 0 })

const listTypeText = (n) =>
  ({ 1: '想看', 2: '在看', 3: '已看完', 4: '搁置', 5: '最爱' })[n] || String(n)

async function load() {
  // listType 0=全部（不传）/ n = 过滤
  const data = await listCollection(listType.value === 0 ? undefined : listType.value)
  list.value = data || []
}

function openEdit(c) {
  Object.assign(editForm, {
    id: c.id,
    listType: c.listType,
    progress: c.progress,
    favorite: c.favorite
  })
  dialog.value = true
}

async function submit() {
  // update：只传变更字段 + id（userId/时间不传）
  await updateCollection({
    id: editForm.id,
    listType: editForm.listType,
    progress: editForm.progress,
    favorite: editForm.favorite
  })
  ElMessage.success('已更新')
  dialog.value = false
  load()
}

onMounted(load)
</script>

<style scoped>
.tabs {
  margin-bottom: 16px;
}
.cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 14px;
}
.coll-row {
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
.ctitle {
  font-weight: 600;
  color: var(--text-primary);
}
.cmeta {
  display: flex;
  align-items: center;
  gap: 10px;
  color: var(--text-secondary);
  font-size: 12px;
  margin: 6px 0;
}
.cbtns {
  margin-top: 8px;
}
</style>
