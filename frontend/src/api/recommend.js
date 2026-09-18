import request from '@/utils/request'

// 安利墙列表（连表番 title/cover，按 id 倒序）
export function listRecommend() {
  return request.get('/recommend/list')
}

// 新增推荐（animeId 必填 + reason/tags 可选；userId 后端填充）
export function addRecommend(data) {
  return request.post('/recommend/add', data)
}

// 修改推荐（只传变更字段 + id；anime_id 不可变更）
export function updateRecommend(data) {
  return request.put('/recommend/update', data)
}

// 删除推荐
export function deleteRecommend(id) {
  return request.delete(`/recommend/delete/${id}`)
}
