-- ============================================================
-- SemiTris-ACG 初始化脚本
-- 数据库：semitris_acg（utf8mb4）
-- 用途：建库 + 建表 + 初始用户（多用户预留版）
-- 特性：可重复执行（先 DROP 再 CREATE，开发期安全）
-- ============================================================

CREATE DATABASE IF NOT EXISTS semitris_acg DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE semitris_acg;

-- 先删子表，再删主表（遵循外键依赖顺序）
DROP TABLE IF EXISTS `recommend`;
DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `collection`;
DROP TABLE IF EXISTS `anime`;
DROP TABLE IF EXISTS `user`;

-- ------------------------------------------------------------
-- 1. 用户表（单人站点：初始化插入 id=1，后端 DEFAULT_USER_ID 使用）
-- ------------------------------------------------------------
CREATE TABLE `user` (
    id            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    username      VARCHAR(50)  NOT NULL COMMENT '账号',
    password      VARCHAR(64)  NOT NULL COMMENT '密码（BCrypt，禁止 MD5）',
    nickname      VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
    avatar        VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
    register_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    updated_at    DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '信息更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表（预留多用户，单人站点固定 id=1）';

-- 初始化唯一用户（密码留空，待登录模块实现后用 BCrypt 设置）
INSERT INTO `user` (id, username, password, nickname) VALUES
(1, 'semitris', '', '番之主人');

-- ------------------------------------------------------------
-- 2. 番剧/漫画资料表
-- ------------------------------------------------------------
CREATE TABLE `anime` (
    id             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    title          VARCHAR(100) NOT NULL COMMENT '中文标题',
    original_title VARCHAR(100) DEFAULT NULL COMMENT '原名',
    cover          VARCHAR(255) DEFAULT NULL COMMENT '封面图片 URL',
    type           TINYINT      DEFAULT 1 COMMENT '类型：1=动画 2=漫画',
    genre          VARCHAR(100) DEFAULT NULL COMMENT '类型标签，逗号分隔，如：热血,奇幻',
    episodes       INT          DEFAULT 0 COMMENT '集数/话数（>=0）',
    status         TINYINT      DEFAULT 0 COMMENT '连载状态：0=连载中 1=已完结',
    year           INT          DEFAULT NULL COMMENT '播出/连载年份',
    rating         DECIMAL(3,1) DEFAULT NULL COMMENT '平均评分（0.0~10.0），冗余字段，review 变更时同步 AVG',
    synopsis       TEXT         COMMENT '简介',
    created_at     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
    updated_at     DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id),
    KEY idx_type (type),
    KEY idx_title (title)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='番剧/漫画资料表';

-- ------------------------------------------------------------
-- 3. 收藏/追番表（同一用户对同一番剧仅一条收藏）
-- ------------------------------------------------------------
CREATE TABLE `collection` (
    id         BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id    BIGINT   NOT NULL COMMENT '用户ID（后端默认填充 1）',
    anime_id   BIGINT   NOT NULL COMMENT '番剧 ID',
    list_type  TINYINT  DEFAULT 1 COMMENT '收藏类型：1=想看 2=在看 3=已看完 4=搁置 5=最爱',
    progress   INT      DEFAULT 0 COMMENT '看到第几集/话（0<=progress<=episodes）',
    favorite   TINYINT  DEFAULT 0 COMMENT '是否加精收藏：0=否 1=是',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_anime (user_id, anime_id),
    CONSTRAINT fk_collection_user  FOREIGN KEY (user_id)  REFERENCES `user` (id) ON DELETE CASCADE,
    CONSTRAINT fk_collection_anime FOREIGN KEY (anime_id) REFERENCES anime (id)  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏/追番表';

-- ------------------------------------------------------------
-- 4. 评价表
-- ------------------------------------------------------------
CREATE TABLE `review` (
    id         BIGINT   NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id    BIGINT   NOT NULL COMMENT '用户ID（后端默认填充 1）',
    anime_id   BIGINT   NOT NULL COMMENT '番剧 ID',
    rating     INT      NOT NULL COMMENT '评分（1~10，Service 层校验）',
    content    TEXT     COMMENT '评价内容',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间',
    updated_at DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id),
    KEY idx_anime (anime_id),
    KEY idx_user (user_id),
    CONSTRAINT fk_review_user  FOREIGN KEY (user_id)  REFERENCES `user` (id) ON DELETE CASCADE,
    CONSTRAINT fk_review_anime FOREIGN KEY (anime_id) REFERENCES anime (id)  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- ------------------------------------------------------------
-- 5. 推荐表
-- ------------------------------------------------------------
CREATE TABLE `recommend` (
    id         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
    user_id    BIGINT       NOT NULL COMMENT '用户ID（后端默认填充 1）',
    anime_id   BIGINT       NOT NULL COMMENT '番剧 ID',
    reason     VARCHAR(255) DEFAULT NULL COMMENT '推荐理由',
    tags       VARCHAR(100) DEFAULT NULL COMMENT '推荐标签，逗号分隔',
    created_at DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '推荐时间',
    updated_at DATETIME     DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id),
    KEY idx_anime (anime_id),
    KEY idx_user (user_id),
    CONSTRAINT fk_recommend_user  FOREIGN KEY (user_id)  REFERENCES `user` (id) ON DELETE CASCADE,
    CONSTRAINT fk_recommend_anime FOREIGN KEY (anime_id) REFERENCES anime (id)  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推荐表';

-- 建表完成。
