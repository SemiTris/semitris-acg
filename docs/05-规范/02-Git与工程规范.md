# 02-Git 与工程规范

## 1. 初始化

项目已初始化为 git 仓库（根目录），**无需重复 `git init`**。根级 `.gitignore` 已就位（由 OpenCode 维护），后端目录另有 `backend/.gitignore`。

## 2. .gitignore（根级，已落地）

```gitignore
# Java
target/
*.class
*.jar
*.war
!.mvn/wrapper/maven-wrapper.jar

# IDE
.idea/
*.iml
.vscode/
out/

# 前端
node_modules/
dist/
npm-debug.log*
yarn-error.log*

# 系统
.DS_Store
Thumbs.db

# 云存储密钥（不提交）
**/application.yaml
```

## 3. 提交信息格式（约定式提交）

```
feat: 新增番剧查询接口
fix: 修复分页 total 不准
docs: 更新数据库设计文档
refactor: 抽取统一返回工具类
```

- 每个功能点一次提交，粒度小、信息清晰，方便回看"我每步学了什么"。

## 4. 分支策略

- 个人项目：`master` 主分支即可，功能完成即提交。
- 想尝试的玩法（如换种写法）可开分支，玩明白了再合回，玩坏了直接删。

## 5. 提交节奏建议

- 每天两次提交：后端接口全部跑通 = 一次；前端页面完成 = 一次。
- 提交前用 Postman 把所有当天接口跑一遍，保证是"绿的"再提交。
