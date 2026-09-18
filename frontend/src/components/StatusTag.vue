<template>
  <el-tag :type="tag.type" size="small" effect="dark" class="status-tag">
    {{ tag.text }}
  </el-tag>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  // kind: 'type'(1动画/2漫画) | 'status'(0连载/1完结) | 'list'(listType 1~5)
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
const listMap = {
  1: { text: '想看', type: 'primary' },
  2: { text: '在看', type: 'success' },
  3: { text: '已看完', type: 'info' },
  4: { text: '搁置', type: 'warning' },
  5: { text: '最爱', type: 'danger' }
}

const tag = computed(() => {
  const map = props.kind === 'type' ? typeMap
    : props.kind === 'status' ? statusMap
      : listMap
  return map[props.value] || { text: String(props.value), type: 'info' }
})
</script>

<style scoped>
.status-tag {
  margin-bottom: 4px;
}
</style>
