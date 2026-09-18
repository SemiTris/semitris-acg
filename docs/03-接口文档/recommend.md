# 接口文档：recommend 推荐安利

基础路径：`/recommend` ｜ 全部返回 `R<T>`

## 接口总表

| 接口 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 推荐一部番 | POST | `/recommend/add` | body: Recommend |
| 修改 | PUT | `/recommend/update` | body: Recommend（含 id） |
| 取消推荐 | DELETE | `/recommend/delete/{id}` | |
| 安利墙列表 | GET | `/recommend/list` | 连表返回番信息 |

## 设计要点

- 前端"选番"下拉用 `GET /anime/list`。
- `/recommend/list` 连表 `anime`，返回番标题/封面。

## 请求 / 响应示例

### POST /recommend/add

```json
{ "animeId": 2, "reason": "三集定律都撑不住，剧情紧凑到不敢呼吸", "tags": "神作,悬疑,2025" }
```

```json
{ "code": 200, "message": "推荐成功", "data": null }
```

### GET /recommend/list

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "animeId": 2,
      "title": "某某番",
      "cover": "https://semitris-acg.oss-cn-hangzhou.aliyuncs.com/cover/xxx.jpg",
      "reason": "三集定律都撑不住",
      "tags": "神作,悬疑,2025",
      "createdAt": "2026-08-27 12:00:00"
    }
  ]
}
```

## Recommend 字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | Long | 主键 |
| animeId | Long | 番剧 ID |
| reason | String | 推荐理由 |
| tags | String | 推荐标签，逗号分隔 |
| createdAt | Date | 推荐时间 |
| **title** | String | （连表）番剧标题 |
| **cover** | String | （连表）番剧封面 |
