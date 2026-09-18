import request from '@/utils/request'

// 分页查询番剧（pageNum/pageSize 必传；title/type/status 可选）
export function pageAnime(params) {
  return request.get('/anime/page', { params })
}

// 全量列表（下拉框用，按 id 倒序）
export function listAnime() {
  return request.get('/anime/list')
}

// 按 id 查详情
export function getAnimeById(id) {
  return request.get(`/anime/findById/${id}`)
}

// 新增（title 必填、type 1/2、status 0/1；userId/时间字段不传，后端填充）
export function addAnime(data) {
  return request.post('/anime/add', data)
}

// 修改（只传变更字段 + id；不传 userId/时间）
export function updateAnime(data) {
  return request.put('/anime/update', data)
}

// 删除（子表由外键级联清理）
export function deleteAnime(id) {
  return request.delete(`/anime/delete/${id}`)
}
