# 接口文档：review 评价

基础路径：`/review` ｜ 全部返回 `R<T>`

## 接口总表

| 接口 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 写评价 | POST | `/review/add` | body: Review |
| 修改 | PUT | `/review/update` | body: Review（含 id） |
| 删除 | DELETE | `/review/delete/{id}` | |
| 按番查 | GET | `/review/listByAnime/{animeId}` | 某番全部评价，按时间倒序 |
| 全部评价分页 | GET | `/review/page` | 可选，安利墙/最近评价用 |

## 请求 / 响应示例

### POST /review/add

```json
{ "animeId": 1, "rating": 10, "content": "神作，每一集都在流泪。战力随心情波动的芙莉莲最有魅力。🥲" }
```

```json
{ "code": 200, "message": "评价成功", "data": null }
```

### GET /review/listByAnime/1

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    { "id": 1, "animeId": 1, "rating": 10, "content": "神作，每一集都在流泪。", "createdAt": "2026-08-27 11:00:00" }
  ]
}
```

## Review 字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | Long | 主键 |
| animeId | Long | 番剧 ID |
| rating | Integer | 评分 1 ~ 10 |
| content | String | 评价内容 |
| createdAt | Date | 评价时间 |
