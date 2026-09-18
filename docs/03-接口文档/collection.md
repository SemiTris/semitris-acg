# 接口文档：collection 收藏/追番

基础路径：`/collection` ｜ 全部返回 `R<T>`

## 接口总表

| 接口 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 加入收藏 | POST | `/collection/add` | body: Collection |
| 取消收藏 | DELETE | `/collection/delete/{id}` | |
| 更新状态/进度 | PUT | `/collection/update` | body: Collection（含 id），只更新非空字段 |
| 按番查 | GET | `/collection/findByAnime/{animeId}` | 详情页判断该番收藏状态 |
| 追番列表 | GET | `/collection/list?listType=` | 连表返回番信息，可按类型筛选 |

## listType 枚举

`1=想看  2=在看  3=已看完  4=搁置  5=最爱`（不传 listType = 查全部）

## 设计要点

- 一部番**最多一条收藏**（数据库唯一约束），重复 add 应返回失败提示。
- `/collection/list` 与 `/collection/findByAnime/{animeId}` 需**连表 `anime`**，返回字段见下。

## 请求 / 响应示例

### POST /collection/add

```json
{ "animeId": 1, "listType": 2, "progress": 12, "favorite": 1 }
```

```json
{ "code": 200, "message": "收藏成功", "data": null }
```

### GET /collection/list?listType=2

```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "animeId": 1,
      "title": "葬送的芙莉莲",
      "cover": "https://semitris-acg.oss-cn-hangzhou.aliyuncs.com/cover/1690000000000.jpg",
      "listType": 2,
      "progress": 12,
      "favorite": 1,
      "createdAt": "2026-08-27 10:00:00"
    }
  ]
}
```

> `title` / `cover` 为非持久化字段，来自连表查询（collection LEFT JOIN anime）。

## Collection 字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | Long | 主键 |
| animeId | Long | 番剧 ID |
| listType | Integer | 1想看 2在看 3看完 4搁置 5最爱 |
| progress | Integer | 看到第几集/话 |
| favorite | Integer | 0 否 / 1 是 |
| createdAt | Date | 收藏时间 |
| **title** | String | （连表）番剧标题 |
| **cover** | String | （连表）番剧封面 |
