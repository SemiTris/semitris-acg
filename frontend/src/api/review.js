import request from '@/utils/request'

// 某番评价列表（按 id 倒序）
export function listReviewByAnime(animeId) {
  return request.get(`/review/listByAnime/${animeId}`)
}

// 写评价（rating 必填 1~10 + content；userId 后端填充；写后后端同事务重算 anime.rating）
export function addReview(data) {
  return request.post('/review/add', data)
}

// 修改评价（只传变更字段 + id；anime_id 不可变更）
export function updateReview(data) {
  return request.put('/review/update', data)
}

// 删除评价（后端删除后重算 anime.rating）
export function deleteReview(id) {
  return request.delete(`/review/delete/${id}`)
}
