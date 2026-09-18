<template>
  <el-image
    class="cover-image"
    :src="src || ''"
    fit="cover"
  >
    <template #error>
      <div class="cover-placeholder"><span>无封面</span></div>
    </template>
    <template #placeholder>
      <div class="cover-placeholder">加载中</div>
    </template>
  </el-image>
</template>

<script setup>
defineProps({
  src: { type: String, default: '' }
})
</script>

<style scoped>
.cover-image {
  width: 100%;
  height: 100%;
  display: block;
  background: var(--bg-card);
}
.cover-placeholder {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--gradient-brand);
  opacity: 0.85;
  color: #fff;
  font-size: 13px;
  letter-spacing: 1px;
}
</style>
