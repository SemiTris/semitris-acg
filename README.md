# SemiTris-ACG ｜ SemiTris 的 ACG 私宅

> 只属于我的番剧小窝 —— 个人二次元（动漫/漫画）的观看记录、收藏、推荐与评价网站。

## 项目身份

| 项 | 值 |
|---|---|
| 项目名 | SemiTris-ACG |
| 中文名 | SemiTris 的 ACG 私宅 |
| 口号 | 只属于我的番剧小窝 |
| 定位 | 单人个人网站（使用者仅 SemiTris） |
| 后端 package | com.semitris.acg |
| 数据库 | semitris_acg（MySQL 8.x, utf8mb4） |
| 前端工程 | semitris-acg-web |
| 技术栈 | Spring Boot 3.5 + MyBatis(XML) + PageHelper + MySQL + Vue（跟随老师） |

## 文档索引

| 目录 | 说明 |
|---|---|
| [00-项目定义](00-项目定义/00-命名与品牌.md) | 命名与品牌、项目范围 |
| [01-需求](01-需求/PRD.md) | PRD、用户故事与验收标准 |
| [02-技术设计](02-技术设计/01-架构设计.md) | 架构、数据库、接口规范 |
| [03-接口文档](03-接口文档/anime.md) | 六大模块接口明细 |
| [04-计划](04-计划/01-项目计划书.md) | 计划书、学习同步对照表 |
| [05-规范](05-规范/01-编码规范.md) | 编码、Git、素材规范 |

## 快速导航

- [项目范围](00-项目定义/01-项目范围.md)
- [PRD](01-需求/PRD.md)
- [数据库设计（含 DDL）](02-技术设计/02-数据库设计.md)
- [接口文档入口](03-接口文档/anime.md)
- [项目计划书](04-计划/01-项目计划书.md)
- [学习同步对照表](04-计划/02-学习同步对照表.md)

## 约定速览

- 所有接口统一返回 `R<T>`：`{ code: 200/500, message, data }`
- 分页统一用 PageHelper，参数 `pageNum` / `pageSize`，返回 `PageInfo`
- 前后端分离，后端默认 `8080` 端口，CORS 全开
- 封面/头像上传到 `D:/upload/`，访问路径由 `/file/upload`、`/file/download` 提供
- 数据库字段 snake_case，代码字段 camelCase（`map-underscore-to-camel-case: true`）

## 说明

本文档包是**产品经理（Claude）交付给码农（SemiTris）的需求与设计文档**，只包含范围、需求、设计、计划与验收标准，**不包含任何 Java / Vue 代码实现**。所有代码由 SemiTris 本人按文档编写，以巩固 Spring Boot + MyBatis + Vue 的学习成果。
