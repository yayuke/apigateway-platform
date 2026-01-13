package com.apigateway.common.util;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

/**
 * 验证工具类
 * 提供数据验证相关的常用操作
 *
 * @author apigateway
 * @since 1.0.0
 */
public class ValidatorUtils {

    /**
     * 手机号正则表达式
     */
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 邮箱正则表达式
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    /**
     * 用户名正则表达式（字母开头，允许字母数字下划线，4-16位）
     */
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{3,15}$");

    /**
     * 密码正则表达式（至少包含数字和字母，6-20位）
     */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");

    /**
     * 验证手机号
     *
     * @param mobile 手机号
     * @return 是否有效
     */
    public static boolean isMobile(String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return false;
        }
        return MOBILE_PATTERN.matcher(mobile).matches();
    }

    /**
     * 验证邮箱
     *
     * @param email 邮箱
     * @return 是否有效
     */
    public static boolean isEmail(String email) {
        if (StrUtil.isBlank(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 验证用户名
     *
     * @param username 用户名
     * @return 是否有效
     */
    public static boolean isUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * 验证密码
     *
     * @param password 密码
     * @return 是否有效
     */
    public static boolean isPassword(String password) {
        if (StrUtil.isBlank(password)) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * 验证是否为空
     *
     * @param value 值
     * @return 是否为空
     */
    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).trim().isEmpty();
        }
        return false;
    }

    /**
     * 验证字符串长度
     *
     * @param str 字符串
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @return 是否符合
     */
    public static boolean isLength(String str, int minLength, int maxLength) {
        if (str == null) {
            return false;
        }
        int len = str.length();
        return len >= minLength && len <= maxLength;
    }
}
