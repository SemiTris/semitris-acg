package com.semitris.acg.util;

import lombok.Data;

/**
 * 统一响应结果封装类，所有接口统一返回该格式
 *
 * @param <T> 携带的数据类型
 */
@Data
public class R<T> {

    // 状态码，200 表示成功，500 表示失败
    private Integer code;

    // 提示信息
    private String message;

    // 携带的数据
    private T data;

    public R() {
    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 操作成功，不带数据
     */
    public static <T> R<T> success() {
        return new R<>(200, "操作成功", null);
    }

    /**
     * 操作成功，携带数据
     */
    public static <T> R<T> success(T data) {
        return new R<>(200, "操作成功", data);
    }

    /**
     * 操作成功，自定义提示信息和数据
     */
    public static <T> R<T> success(String message, T data) {
        return new R<>(200, message, data);
    }

    /**
     * 操作失败，携带失败原因
     */
    public static <T> R<T> error(String message) {
        return new R<>(500, message, null);
    }
}
