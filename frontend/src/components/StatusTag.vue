<template>
  <el-tag :type="tag.type" size="small" effect="dark" class="status-tag">
    {{ tag.text }}
  </el-tag>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  // kind: 'type'（1动画/2漫画）| 'status'（0连载中/1已完结）
  kind: { type: String, default: 'type' },
  value: { type: Number, default: 0 }
})

const typeMap = {
  1: { text: '动画', type: 'primary' },
  2: { text: '漫画', type: 'warning' }
}
const statusMap = {
  0: { text: '连载中', type: 'success' },
  1: { text: '已完结', type: 'info' }
}

const tag = computed(() => {
  const map = props.kind === 'type' ? typeMap : statusMap
  return map[props.value] || { text: String(props.value), type: 'info' }
})
</script>

<style scoped>
.status-tag {
  margin-bottom: 4px;
}
</style>
