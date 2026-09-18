<template>
  <div class="placeholder">
    <h2>{{ title }}</h2>
    <p>该页面在本期（批次 A+B）不实现，属后续批次内容。</p>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const title = computed(() => {
  const map = {
    animeDetail: '番剧详情页',
    collection: '追番列表页',
    recommend: '安利墙',
    login: '登录页'
  }
  return map[route.name] || '待实现页面'
})
</script>

<style scoped>
.placeholder {
  text-align: center;
  padding: 60px 20px;
  color: var(--text-secondary);
}
.placeholder h2 {
  color: var(--text-primary);
}
</style>
