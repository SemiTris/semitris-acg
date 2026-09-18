<template>
  <el-card class="anime-card" shadow="never">
    <div class="card-cover">
      <CoverImage :src="anime.cover" />
      <div class="card-actions">
        <el-button type="primary" size="small" @click="onEdit">编辑</el-button>
        <el-popconfirm
          title="删除该番剧？关联的收藏/评价/推荐将一并删除"
          confirm-button-text="删除"
          cancel-button-text="取消"
          @confirm="onDelete"
        >
          <template #reference>
            <el-button type="danger" size="small">删除</el-button>
          </template>
        </el-popconfirm>
      </div>
    </div>
    <div class="card-info">
      <div class="card-title" :title="anime.title">{{ anime.title }}</div>
      <div class="card-meta">
        <StatusTag kind="type" :value="anime.type" />
        <StatusTag kind="status" :value="anime.status" />
      </div>
      <div class="card-rating" v-if="anime.rating != null">
        ★ {{ Number(anime.rating).toFixed(1) }}
      </div>
      <div class="card-sub" v-if="anime.episodes">{{ anime.episodes }} 话</div>
    </div>
  </el-card>
</template>

<script setup>
import CoverImage from './CoverImage.vue'
import StatusTag from './StatusTag.vue'

const props = defineProps({
  anime: { type: Object, required: true }
})
const emit = defineEmits(['edit', 'delete'])

const onEdit = () => emit('edit', props.anime)
const onDelete = () => emit('delete', props.anime)
</script>

<style scoped>
.card-cover {
  position: relative;
  height: 180px;
  overflow: hidden;
}
.card-actions {
  position: absolute;
  right: 8px;
  bottom: 8px;
  display: flex;
  gap: 6px;
  opacity: 0;
  transition: opacity 0.2s ease;
}
.anime-card:hover .card-actions {
  opacity: 1;
}
.card-info {
  padding: 10px 12px;
}
.card-title {
  font-weight: 600;
  color: var(--text-primary);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.card-meta {
  display: flex;
  gap: 6px;
  margin-top: 6px;
}
.card-rating {
  margin-top: 6px;
  color: var(--primary);
  font-weight: 600;
}
.card-sub {
  color: var(--text-secondary);
  font-size: 12px;
  margin-top: 2px;
}
</style>
