package com.semitris.acg.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt 密码加密工具类
 * <p>数据库设计规范（02-数据库设计_优化版）明确：密码禁止 MD5，统一使用 BCrypt。</p>
 */
public class PasswordUtil {

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    /**
     * 对明文密码加密，返回 60 位 BCrypt 哈希（库列宽 VARCHAR(64) 足够存放）
     *
     * @param rawPassword 明文密码
     * @return BCrypt 哈希串
     */
    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    /**
     * 校验明文密码与哈希是否匹配（第二迭代登录模块使用）
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 库中存储的哈希
     * @return 是否匹配
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
