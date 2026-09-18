# 接口文档：user 用户

基础路径：`/user` ｜ 全部返回 `R<T>`

> 单人站点，**本模块可后置**到第二迭代。

## 接口总表

| 接口 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 注册 | POST | `/user/add` | body: User |
| 登录 | POST | `/user/login` | body: { username, password }，成功返回用户信息 |
| 删除 | DELETE | `/user/delete/{id}` | |
| 修改 | PUT | `/user/update` | body: User（含 id） |
| 详情 | GET | `/user/findById/{id}` | |
| 分页 | GET | `/user/page` | 多条件（username/nickname 模糊） |

## 约定

- 密码存 **BCrypt**（数据库设计规范禁止 MD5；加密/比对用 `util/PasswordUtil`，注册时加密、登录时 `matches` 比对）。
- 登录成功返回用户信息（不含密码）；失败返回 `R.error("账号或密码错误")`。

## 请求 / 响应示例

### POST /user/login

```json
{ "username": "semitris", "password": "123456" }
```

```json
{ "code": 200, "message": "登录成功", "data": { "id": 1, "username": "semitris", "nickname": "番之主人", "avatar": null, "registerTime": "2026-08-27 00:00:00" } }
```

## User 字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | Long | 主键 |
| username | String | 账号（唯一） |
| password | String | 密码（BCrypt，响应中不回传） |
| nickname | String | 昵称 |
| avatar | String | 头像 URL |
| registerTime | Date | 注册时间 |
