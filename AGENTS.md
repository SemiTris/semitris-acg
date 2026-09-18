# AGENTS.md —— SemiTris-ACG 项目 AI 协作约定

> 本文件是**项目级 AI 初始化工件**：OpenCode（设计/评审）与 Claude Code（实现）双方在本项目内必须共同遵守的约定。人类（SemiTris）是产品负责人与最终决策者。
> 生效优先级：人类指令 > 安全红线 > 本文件 > 设计包 > 全局 AGENTS.md。

## 1. 项目一句话

单人使用的二次元（动画/漫画）个人网站：番剧资料库 + 收藏/追番 + 评价 + 推荐安利墙 + 封面上传。纯手录数据，前后端分离的全栈项目。

## 2. 角色分工（严格）

| 角色 | 职责 | 不做 |
|---|---|---|
| **OpenCode** | 需求拆解、架构、模块边界、数据模型、接口契约、任务拆解、评审、验收标准、ADR | 大批量业务代码、日常 commit、生产操作 |
| **Claude Code** | 按设计包/接口契约写业务代码、回报实现结果 | 擅自改契约/表结构/架构，未经确认不提交关键决策 |
| **Human** | 产品负责人、最终决策者 | — |

## 3. 工作流程（默认模式）

```
OpenCode 出设计包 → 人类确认 → Claude Code 实现 → Claude 回报 → OpenCode 评审 → 人类验收
```

- **Claude Code 必须先等待 OpenCode 给出必要说明（设计包）**，不得在无契约情况下自行猜测接口/结构。
- Hotfix 例外：Claude 可先修，24h 内补文档，OpenCode 事后评审。
- 跨模块 / 改 schema / 改公共 API / >5 文件 / >1 天的工作：必须先走设计包与 ADR。

## 4. 目录结构（标准化）

```
semitris-acg/
├── AGENTS.md            # 本文件
├── README.md / LICENSE
├── .gitignore
├── docs/                # 全部文档（分层规范见 docs/05-规范/04-文档目录规范.md）
│   ├── 00-项目定义/ ~ 05-规范/    # 定义/需求/设计/契约/计划/规范
│   ├── 06-记录/         # adr/ 架构决策记录（NNNN-标题.md）
│   ├── 07-协作/         # 设计包（<功能>/00-需求 ~ 06-变更记录）
│   └── assets/          # 非 Markdown 资产（HTML/图片等）
├── database/            # 数据库脚本（唯一入口 database/init.sql）
├── backend/             # Spring Boot 后端（com.semitris.acg）
│   └── src/main/java/com/semitris/acg/{controller,service,impl,mapper,entity,util}
└── frontend/            # Vue 前端（semitris-acg-web，待建）
```

## 5. 关键技术契约（后端，详见 docs/02-技术设计/ 与 docs/03-接口文档/）

- **技术栈权威**：`docs/02-技术设计/00-技术选型.md` —— Java 17 / Spring Boot 3.5.16 / MyBatis(XML) + PageHelper / MySQL 8 / Vue 3(Composition API) + Element Plus + Vite。

- 统一响应 `R<T>`：`code=200` 成功 / `500` 失败，`message` + `data`。
- 路径风格固定：`/add` `/delete/{id}` `/update` `/findById/{id}` `/page` `/list`。
- 分页用 PageHelper：参数 `pageNum`（默认1）/ `pageSize`（默认5~10），返回 `PageInfo`。
- 数据库 snake_case ↔ 代码 camelCase，`map-underscore-to-camel-case: true`。
- 密码 **BCrypt**（`util/PasswordUtil`，禁止 MD5）。
- 业务表 `user_id` 由后端常量 `DEFAULT_USER_ID = 1L` 固定填充，不进 API。
- 删除番剧/用户靠外键 `ON DELETE CASCADE`，Service **不手工删子表**。
- review 新增/改/删后，**同一事务**内重算 `anime.rating = AVG(review.rating)`。
- 枚举校验在 Service 层：`type`(1/2)、`status`(0/1)、`list_type`(1~5)、`favorite`(0/1)、`rating`(1~10)、`progress`(0~episodes)。
- 联表补充字段（title/cover）直接加在实体上作非持久化字段，不建额外 DTO。

## 6. 验证与常用命令

- 后端启动：`backend/` 下 `./mvnw spring-boot:run`（依赖本地 MySQL `semitris_acg`）。
- 建库建表：执行 `database/init.sql`（开发期可重复执行，先 DROP 再 CREATE）。
- 接口验收：Postman 逐条跑通，返回结构一致。
- 提交：约定式提交（`feat:` `fix:` `docs:` `refactor:`），一个功能点一次提交。

## 7. 安全红线（项目级）

- 禁止提交 `.env`、密钥、token；禁止 force push `main`/`release`。
- 生产库禁止迁移/删/改；`database/init.sql` 仅限开发环境。
- 破坏性命令 / DB 写操作 / 迁移需人类确认，迁移必须可回滚。
- 允许绕过文档的例外必须由人类确认并记录到变更记录。

## 8. 本文件的维护

- 契约变化（接口、结构、命名、表结构）时，先更新文档包 + ADR，再同步本文件速览。
- 本文件由 OpenCode 维护；发现与现状不符时以文档包为准并报 OpenCode 更新。
