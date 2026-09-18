# 接口文档：file 文件上传

基础路径：`/file` ｜ 全部返回 `R<T>`

## 接口总表

| 接口 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 上传 | POST | `/file/upload` | multipart 表单，参数名 `file` |
| 回显/下载 | GET | `/file/download/{filename}` | 读取 `D:/upload/` 下文件 |

## 约定

- 保存目录：`D:/upload/`（application.yaml：`semitris.upload.dir`，结尾必须有 `/`）。
- 文件名用时间戳/UUID 重命名，避免中文与空格。
- 上传成功返回可访问的完整 URL，例如：
  `http://localhost:8080/file/download/1690000000000.jpg`
- 前端把该 URL 存入 `anime.cover` 即可。

## 请求 / 响应示例

### POST /file/upload（multipart/form-data）

```
file: <二进制图片>
```

```json
{ "code": 200, "message": "上传成功", "data": "http://localhost:8080/file/download/1690000000000.jpg" }
```

### GET /file/download/1690000000000.jpg

返回图片文件流（浏览器可直接作为 `<img src>` 使用）。
