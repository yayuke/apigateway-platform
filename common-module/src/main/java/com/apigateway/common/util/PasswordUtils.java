package com.apigateway.common.util;

import cn.dev33.satoken.secure.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;
import org.springframework.stereotype.Component;

/**
 * 密码工具类
 * 支持多种密码加密方式
 *
 * @author apigateway
 * @since 1.0.0
 */
@Component
public class PasswordUtils {

    /**
     * 加密方式：BCrypt
     */
    public static final String ENCRYPT_TYPE_BCRYPT = "BCrypt";

    /**
     * 加密方式：MD5
     */
    public static final String ENCRYPT_TYPE_MD5 = "MD5";

    /**
     * 默认加密方式
     */
    public static final String DEFAULT_ENCRYPT_TYPE = ENCRYPT_TYPE_BCRYPT;

    /**
     * 加密密码（使用默认方式BCrypt）
     *
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String encrypt(String password) {
        return encrypt(password, DEFAULT_ENCRYPT_TYPE);
    }

    /**
     * 加密密码
     *
     * @param password 明文密码
     * @param encryptType 加密方式（BCrypt/MD5）
     * @return 加密后的密码
     */
    public static String encrypt(String password, String encryptType) {
        if (password == null || password.isEmpty()) {
            return "";
        }

        if (ENCRYPT_TYPE_MD5.equals(encryptType)) {
            return DigestUtil.md5Hex(password);
        } else {
            // 默认使用BCrypt
            return BCrypt.hashpw(password);
        }
    }

    /**
     * 验证密码
     *
     * @param password 明文密码
     * @param hashedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean verify(String password, String hashedPassword) {
        if (password == null || hashedPassword == null) {
            return false;
        }

        // 尝试BCrypt验证
        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (Exception e) {
            // BCrypt验证失败，尝试MD5验证
            String md5Password = DigestUtil.md5Hex(password);
            return md5Password.equals(hashedPassword);
        }
    }

    /**
     * 验证密码（指定加密方式）
     *
     * @param password 明文密码
     * @param hashedPassword 加密后的密码
     * @param encryptType 加密方式（BCrypt/MD5）
     * @return 是否匹配
     */
    public static boolean verify(String password, String hashedPassword, String encryptType) {
        if (password == null || hashedPassword == null) {
            return false;
        }

        if (ENCRYPT_TYPE_MD5.equals(encryptType)) {
            String md5Password = DigestUtil.md5Hex(password);
            return md5Password.equals(hashedPassword);
        } else {
            return BCrypt.checkpw(password, hashedPassword);
        }
    }

    /**
     * 生成随机密码
     *
     * @param length 密码长度
     * @return 随机密码
     */
    public static String generateRandomPassword(int length) {
        return DigestUtil.md5Hex(String.valueOf(System.currentTimeMillis())).substring(0, length);
    }

    /**
     * 判断密码是否为BCrypt加密
     *
     * @param hashedPassword 加密后的密码
     * @return 是否为BCrypt加密
     */
    public static boolean isBCrypt(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }
        return hashedPassword.startsWith("$2a$") || hashedPassword.startsWith("$2b$");
    }
}
