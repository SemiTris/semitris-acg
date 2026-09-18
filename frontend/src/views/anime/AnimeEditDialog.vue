<template>
  <el-dialog
    v-model="visible"
    :title="form.id ? '编辑番剧' : '新增番剧'"
    width="560px"
    @closed="resetForm"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="标题" prop="title">
        <el-input v-model="form.title" placeholder="中文标题（必填）" />
      </el-form-item>
      <el-form-item label="原名">
        <el-input v-model="form.originalTitle" />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-radio-group v-model="form.type">
          <el-radio :label="1">动画</el-radio>
          <el-radio :label="2">漫画</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="form.status">
          <el-radio :label="0">连载中</el-radio>
          <el-radio :label="1">已完结</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="集数">
        <el-input-number v-model="form.episodes" :min="0" />
      </el-form-item>
      <el-form-item label="年份">
        <el-input-number v-model="form.year" :min="1980" :max="2099" />
      </el-form-item>
      <el-form-item label="标签">
        <el-input v-model="form.genre" placeholder="逗号分隔，如：奇幻,治愈" />
      </el-form-item>
      <el-form-item label="评分">
        <el-input-number v-model="form.rating" :min="0" :max="10" :step="0.5" />
      </el-form-item>
      <el-form-item label="简介">
        <el-input type="textarea" v-model="form.synopsis" :rows="3" />
      </el-form-item>
      <el-form-item label="封面">
        <el-upload
          :auto-upload="false"
          :show-file-list="false"
          accept="image/*"
          :on-change="onFileChange"
        >
          <el-button>{{ form.cover ? '重新上传' : '上传封面' }}</el-button>
        </el-upload>
        <CoverImage v-if="form.cover" :src="form.cover" class="cover-preview" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="saving" @click="submit">保存</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { addAnime, updateAnime } from '@/api/anime'
import { uploadCover } from '@/api/file'
import CoverImage from '@/components/CoverImage.vue'

const props = defineProps({
  visible: { type: Boolean, default: false },
  anime: { type: Object, default: null }
})
const emit = defineEmits(['update:visible', 'saved'])

const visible = computed({
  get: () => props.visible,
  set: (v) => emit('update:visible', v)
})

const formRef = ref()
const saving = ref(false)
const form = reactive({
  id: null, title: '', originalTitle: '', type: 1, status: 0,
  episodes: 0, year: null, genre: '', rating: null, synopsis: '', cover: ''
})
const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }]
}

function resetForm() {
  Object.assign(form, {
    id: props.anime?.id ?? null,
    title: props.anime?.title ?? '',
    originalTitle: props.anime?.originalTitle ?? '',
    type: props.anime?.type ?? 1,
    status: props.anime?.status ?? 0,
    episodes: props.anime?.episodes ?? 0,
    year: props.anime?.year ?? null,
    genre: props.anime?.genre ?? '',
    rating: props.anime?.rating ?? null,
    synopsis: props.anime?.synopsis ?? '',
    cover: props.anime?.cover ?? ''
  })
}

async function onFileChange(file) {
  if (!file.raw) return
  const url = await uploadCover(file.raw)  // OSS 直链回显
  form.cover = url
}

async function submit() {
  await formRef.value.validate()
  saving.value = true
  try {
    if (form.id) {
      // 编辑：只传变更字段 + id（不含 userId/时间）
      await updateAnime({
        id: form.id, title: form.title, originalTitle: form.originalTitle,
        type: form.type, status: form.status, episodes: form.episodes,
        year: form.year, genre: form.genre, rating: form.rating,
        synopsis: form.synopsis, cover: form.cover
      })
    } else {
      // 新增：显式字段，不含 id/时间/userId（后端填充）
      await addAnime({
        title: form.title, originalTitle: form.originalTitle,
        type: form.type, status: form.status, episodes: form.episodes,
        year: form.year, genre: form.genre, rating: form.rating,
        synopsis: form.synopsis, cover: form.cover
      })
    }
    ElMessage.success(form.id ? '修改成功' : '新增成功')
    emit('saved')
  } finally {
    saving.value = false
  }
}
</script>

<style scoped>
.cover-preview {
  width: 140px;
  height: 90px;
  margin-top: 8px;
  border-radius: 6px;
  overflow: hidden;
  display: block;
}
</style>
