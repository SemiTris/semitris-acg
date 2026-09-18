import request from '@/utils/request'

// 封面上传到 OSS，返回公开直链（formData 字段名 file）
export function uploadCover(file) {
  const fd = new FormData()
  fd.append('file', file)
  return request.post('/file/upload', fd)
}
