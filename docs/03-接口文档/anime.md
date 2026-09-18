# 接口文档：anime 番剧/漫画

基础路径：`/anime` ｜ 全部返回 `R<T>`

## 接口总表

| 接口 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 新增 | POST | `/anime/add` | body: Anime |
| 删除 | DELETE | `/anime/delete/{id}` | 级联清理 collection/review/recommend |
| 修改 | PUT | `/anime/update` | body: Anime（含 id），只更新非空字段 |
| 详情 | GET | `/anime/findById/{id}` | 返回单条 Anime |
| 全量列表 | GET | `/anime/list` | 下拉框用，按 id 倒序 |
| 分页搜索 | GET | `/anime/page` | 多条件分页，见下 |

## 分页搜索参数

| 参数 | 必填 | 说明 |
|---|---|---|
| pageNum | 否 | 页码，默认 1 |
| pageSize | 否 | 每页条数，默认 5 |
| title | 否 | 标题模糊 LIKE |
| type | 否 | 类型等值：1=动画 2=漫画 |
| status | 否 | 状态等值：0=连载 1=完结 |

## 请求 / 响应示例

### POST /anime/add

```json
{
  "title": "葬送的芙莉莲",
  "originalTitle": "葬送のフリーレン",
  "cover": "https://semitris-acg.oss-cn-hangzhou.aliyuncs.com/cover/1690000000000.jpg",
  "type": 1,
  "genre": "奇幻,治愈,冒险",
  "episodes": 28,
  "status": 1,
  "year": 2023,
  "rating": 9.5,
  "synopsis": "勇者一行的魔法使芙莉莲，在旅程结束后重新踏上旅途，去理解人类的感情。"
}
```

```json
{ "code": 200, "message": "新增番剧成功", "data": null }
```

### GET /anime/page?pageNum=1&pageSize=5&title=芙莉莲

`data` 为 `PageInfo<Anime>`：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 1,
    "list": [ { "id": 1, "title": "葬送的芙莉莲", "type": 1, "status": 1, "rating": 9.5, "...": "..." } ],
    "pageNum": 1,
    "pageSize": 5,
    "pages": 1
  }
}
```

## Anime 字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | Long | 主键 |
| title | String | 中文标题（必填） |
| originalTitle | String | 原名 |
| cover | String | 封面 URL |
| type | Integer | 1=动画 2=漫画 |
| genre | String | 类型标签，逗号分隔 |
| episodes | Integer | 集数/话数 |
| status | Integer | 0=连载中 1=已完结 |
| year | Integer | 年份 |
| rating | BigDecimal | 我的平均评分 0.0~10.0 |
| synopsis | String | 简介 |
| createdAt | Date | 录入时间 |
