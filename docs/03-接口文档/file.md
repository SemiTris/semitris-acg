# 接口文档：file 封面文件上传（阿里云 OSS 云存储）

基础路径：`/file` ｜ 全部返回 `R<T>`

## 接口总表

| 接口 | 方法 | 路径 | 说明 |
|---|---|---|---|
| 上传 | POST | `/file/upload` | multipart 表单，参数名 `file`，上传至阿里云 OSS |

> `/file/download/{filename}` 已**弃用**：OSS 直链即公开 URL，无需后端回显接口。封面展示统一使用 `anime.cover` 的 OSS 直链。

## 约定

- 存储：阿里云 OSS（配置键 `oss.endpoint / access-key-id / access-key-secret / bucket-name`）。
- 文件路径：object 命名 `cover/{时间戳}.{小写扩展名}`，如 `cover/1690000000000.jpg`（无中文/空格）。
- 上传成功返回 **OSS 公开直链 URL**，例如：
  `https://{bucket}.{endpoint}/cover/1690000000000.jpg`
- 前端把该 URL 存入 `anime.cover` 即可，可直接作 `<img src>`。

## 配置安全

- 仓库只提交 `application.example.yaml`（模板，`oss.*` 键留空）。
- 真实 `application.yaml` 由开发者复制模板后填写真实密钥；该文件已被 `.gitignore` 排除，密钥不入库。

## 请求 / 响应示例

### POST /file/upload（multipart/form-data）

```
file: <二进制图片>
```

```json
{ "code": 200, "message": "上传成功", "data": "https://semitris-acg.oss-cn-hangzhou.aliyuncs.com/cover/1690000000000.jpg" }
```

### 异常

- 空文件：`R.error("文件为空")`
- 未配置 OSS 密钥：`R.error("上传服务未配置")`
