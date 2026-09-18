<template>
  <el-pagination
    background
    layout="total, prev, pager, next"
    :total="total"
    :page-size="pageSize"
    :current-page="pageNum"
    @current-change="onChange"
  />
</template>

<script setup>
const props = defineProps({
  total: { type: Number, default: 0 },
  pageNum: { type: Number, default: 1 },
  pageSize: { type: Number, default: 8 }
})
const emit = defineEmits(['change'])

const onChange = (page) => emit('change', page)
</script>
