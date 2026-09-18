import request from '@/utils/request'

// 登录（{ username, password } → password 不返回；失败由 request 拦截弹「账号或密码错误」）
export function login(data) {
  return request.post('/user/login', data)
}
