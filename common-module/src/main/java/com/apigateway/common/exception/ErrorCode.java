package com.apigateway.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误码枚举
 * 定义系统中使用的错误码
 *
 * @author apigateway
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 通用错误码 (1000-1999)
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),

    // 用户相关错误码 (2000-2999)
    USER_NOT_FOUND(2001, "用户不存在"),
    USER_PASSWORD_ERROR(2002, "密码错误"),
    USER_DISABLED(2003, "用户已被禁用"),
    USER_EXISTS(2004, "用户已存在"),
    TOKEN_EXPIRED(2005, "Token已过期"),
    TOKEN_INVALID(2006, "Token无效"),

    // 数据源相关错误码 (3000-3999)
    DATASOURCE_NOT_FOUND(3001, "数据源不存在"),
    DATASOURCE_CONNECT_FAILED(3002, "数据源连接失败"),
    DATASOURCE_CONFIG_ERROR(3003, "数据源配置错误"),
    DATASOURCE_NOT_SUPPORT(3004, "不支持的数据源类型"),

    // API相关错误码 (4000-4999)
    API_NOT_FOUND(4001, "API不存在"),
    API_DISABLED(4002, "API已禁用"),
    API_EXECUTE_FAILED(4003, "API执行失败"),
    API_SQL_ERROR(4004, "SQL语句错误"),
    API_AUTH_FAILED(4005, "API认证失败"),
    API_RATE_LIMIT(4006, "API请求过于频繁"),

    // 加密相关错误码 (5000-5999)
    ENCRYPT_ERROR(5001, "加密失败"),
    DECRYPT_ERROR(5002, "解密失败"),
    KEY_ERROR(5003, "密钥错误"),
    ALGORITHM_NOT_SUPPORT(5004, "不支持的加密算法");

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    /**
     * 根据错误码获取错误消息
     */
    public static String getMessage(Integer code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode.getMessage();
            }
        }
        return "未知错误";
    }
}
