import request from '@/utils/request'

// 追番列表（listType 可选：1~5，不传=全部；连表 title/cover）
export function listCollection(listType) {
  return request.get('/collection/list', { params: { listType: listType || undefined } })
}

// 按番查询收藏状态（详情页判断；未收藏 data=null）
export function findByAnime(animeId) {
  return request.get(`/collection/findByAnime/${animeId}`)
}

// 新增收藏（userId/时间不传，后端填充；listType 1~5、favorite 0/1、progress<=episodes）
export function addCollection(data) {
  return request.post('/collection/add', data)
}

// 更新收藏（只传变更字段 + id；listType/progress/favorite）
export function updateCollection(data) {
  return request.put('/collection/update', data)
}

// 取消收藏
export function deleteCollection(id) {
  return request.delete(`/collection/delete/${id}`)
}
