# CLAUDE.md —— SemiTris-ACG（本仓库 Claude Code 工作指引）

> 本文件是**项目级、本仓库专属**的 Claude Code（实现工程师）初始化与工作指引。
> 由 Claude Code 维护；与 OpenCode 维护的 `AGENTS.md`（全局协作契约）分开并**互引**。

## 1. 谁是谁（本项目）——引用 AGENTS.md

- **Human（SemiTris）**：产品负责人、最终决策者。
- **OpenCode**：架构师 / 评审者。出设计包（唯一事实源）、评审、验收标准、ADR。
- **Claude Code（本仓库开工者）**：实现工程师。按设计包/接口契约写业务代码、回报结果。

> 完整角色分工、工作流程、技术契约、安全红线以根目录 [AGENTS.md](AGENTS.md) 为**唯一权威**。
> **生效优先级**：人类指令 > 安全红线 > AGENTS.md > 设计包 > CLAUDE.md。

## 2. 我已初始化（进入本项目开发）

- 已通读 `AGENTS.md`、`docs/README.md`、文档目录规范、User 模块全链路样板。
- 已确认协作流程：OpenCode 出设计包 → 人类确认 → 我实现 → 我回报 → OpenCode 评审 → 人类验收。
- 记忆：不做跨项目残留；本项目相关状态只写在本仓库内（本文件 / docs / 代码注释）。

## 3. 当前仓库状态速览

- 后端 `backend/` **仅 User 模块完整**（Controller→Service→Impl→Mapper+XML 全链路，BCrypt、PageHelper、R 统一响应）——是复刻新模块的**样板模板**。
- 其余模块（Anime / Review / Recommend / Collection）为**空骨架**，尚未开工。
- 前端 Vue 3 + Element Plus + Vite 待建。

## 4. 开工必读清单（实现新模块前）

1. 读 `docs/agent/<feature>/` 设计包（若无，先提问，**不自行补设计**）。
2. 技术契约见 `docs/02-技术设计/` 与 `docs/03-接口文档/`；接口明细各模块在 `docs/03-接口文档/`。
3. 编码规范见 `docs/05-规范/01-编码规范.md`；Git 规范见 `docs/05-规范/02-Git与工程规范.md`。
4. 以 User 模块为模板复刻（照抄清单已记于本会话；要点：显式 `@Mapper`、时间列用 `LocalDateTime`、分页 `PageHelper.startPage`+`new PageInfo<>`、五端点 `/add /delete/{id} /update /findById/{id} /page`、统一返回 `R<...>`、复用 `util/R`+`util/PasswordUtil`+`util/CorsConfig`）。

## 5. 我的边界（简版，权威在 AGENTS.md）

- **无权私改**：顶层架构、模块边界、表结构、接口契约、错误码、配置格式、公共类型。
- **不写跨项目记忆**；本项目细节只在仓库内落盘。
- 跨模块 / 改 schema / 改公共 API / >5 文件 / >1 天：先走设计包 + ADR。
- 安全红线不可被本文件覆盖。
