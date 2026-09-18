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
| 技术栈 | Spring Boot 3.5.16 + Java 17 + MyBatis(XML) + PageHelper + MySQL 8 + Vue 3 + Element Plus + Vite（权威见 [docs/02-技术设计/00-技术选型.md](docs/02-技术设计/00-技术选型.md)） |

## 目录结构

```
semitris-acg/
├── docs/                # 全部文档（导航入口 docs/README.md）
│   ├── 00-项目定义/ ~ 05-规范/    # 定义/需求/设计/契约/计划/规范
│   ├── 06-记录/         # adr/ 架构决策记录
│   ├── 07-协作/         # AI 设计包机制
│   └── assets/          # 非 Markdown 资产
├── database/            # 数据库脚本（唯一入口 init.sql）
├── backend/             # Spring Boot 后端
└── frontend/            # Vue 前端（semitris-acg-web，待建）
```

## 文档索引

| 目录 | 说明 |
|---|---|
| [docs 导航](docs/README.md) | 文档唯一导航入口 |
| [00-项目定义](docs/00-项目定义/00-命名与品牌.md) | 命名与品牌、项目范围 |
| [01-需求](docs/01-需求/PRD.md) | PRD、用户故事与验收标准 |
| [02-技术设计](docs/02-技术设计/01-架构设计.md) | 架构、数据库、接口规范 |
| [03-接口文档](docs/03-接口文档/anime.md) | 六大模块接口明细 |
| [04-计划](docs/04-计划/01-项目计划书.md) | 项目计划书 |
| [05-规范](docs/05-规范/01-编码规范.md) | 编码、Git、素材、文档目录规范 |
| [06-记录](docs/06-记录/adr/) | ADR 架构决策记录 |
| [07-协作](docs/07-协作/) | AI 协作设计包 |

## 快速导航

- [项目范围](docs/00-项目定义/01-项目范围.md)
- [PRD](docs/01-需求/PRD.md)
- [数据库设计（含 DDL）](docs/02-技术设计/02-数据库设计.md)
- [数据库脚本](database/init.sql)
- [接口文档入口](docs/03-接口文档/anime.md)
- [项目计划书](docs/04-计划/01-项目计划书.md)
- [AI 协作约定](AGENTS.md)

## 约定速览

- 所有接口统一返回 `R<T>`：`{ code: 200/500, message, data }`
- 分页统一用 PageHelper，参数 `pageNum` / `pageSize`，返回 `PageInfo`
- 前后端分离，后端默认 `8080` 端口，CORS 全开
- 封面/头像上传到 `D:/upload/`，访问路径由 `/file/upload`、`/file/download` 提供
- 数据库字段 snake_case，代码字段 camelCase（`map-underscore-to-camel-case: true`）

## AI 协作（OpenCode + Claude Code）

- 协作契约见 [AGENTS.md](AGENTS.md)：OpenCode 设计/评审，Claude Code 实现，人类（SemiTris）最终决策。
- 设计落盘：`docs/07-协作/<功能>/00-需求 ~ 06-变更记录`；关键决策记 `docs/06-记录/adr/NNNN-标题.md`。
- 流程：设计包 → 人类确认 → 实现 → 回报 → 评审 → 验收。

## 说明

本文档只包含范围、需求、设计、计划与验收标准，**不包含任何 Java / Vue 代码实现**。所有代码由实现方（Claude Code 或 owner）按文档契约编写。
