import axios from 'axios'
import { ElMessage } from 'element-plus'

// 统一请求实例：baseURL=/api（vite dev proxy → http://localhost:8080）
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 响应拦截：code===200 返回 data（剥掉 R 包装，页面直接拿数据）；
// 业务失败统一弹 message 并 reject——页面不写死 code=500 分支，统一走这里提示。
request.interceptors.response.use(
  (res) => {
    const body = res.data
    if (body && body.code === 200) {
      return body.data
    }
    const msg = body && body.message ? body.message : '操作失败'
    ElMessage.error(msg)
    return Promise.reject(new Error(msg))
  },
  (err) => {
    const msg = err.response?.data?.message || err.message || '请求失败'
    ElMessage.error(msg)
    return Promise.reject(err)
  }
)

export default request
