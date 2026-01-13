package com.apigateway.encrypt.util;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 国密工具类
 * 提供SM2/SM3/SM4加密算法的工具方法
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@Component
public class SmUtils {

    /**
     * 默认SM4密钥（16字节，128位）
     */
    private static final String DEFAULT_SM4_KEY = "1234567890abcdef";

    /**
     * SM4加密
     *
     * @param content 待加密内容
     * @return 加密后的十六进制字符串
     */
    public static String sm4Encrypt(String content) {
        return sm4Encrypt(content, DEFAULT_SM4_KEY);
    }

    /**
     * SM4加密
     *
     * @param content 待加密内容
     * @param key 密钥（16字节）
     * @return 加密后的十六进制字符串
     */
    public static String sm4Encrypt(String content, String key) {
        try {
            SymmetricCrypto sm4 = SmUtil.sm4(key.getBytes(StandardCharsets.UTF_8));
            return sm4.encryptHex(content);
        } catch (Exception e) {
            log.error("SM4加密失败", e);
            throw new RuntimeException("SM4加密失败：" + e.getMessage());
        }
    }

    /**
     * SM4解密
     *
     * @param encrypted 加密的十六进制字符串
     * @return 解密后的内容
     */
    public static String sm4Decrypt(String encrypted) {
        return sm4Decrypt(encrypted, DEFAULT_SM4_KEY);
    }

    /**
     * SM4解密
     *
     * @param encrypted 加密的十六进制字符串
     * @param key 密钥（16字节）
     * @return 解密后的内容
     */
    public static String sm4Decrypt(String encrypted, String key) {
        try {
            SymmetricCrypto sm4 = SmUtil.sm4(key.getBytes(StandardCharsets.UTF_8));
            return sm4.decryptStr(encrypted);
        } catch (Exception e) {
            log.error("SM4解密失败", e);
            throw new RuntimeException("SM4解密失败：" + e.getMessage());
        }
    }

    /**
     * SM3哈希
     *
     * @param content 待哈希内容
     * @return 哈希值（十六进制字符串）
     */
    public static String sm3Hash(String content) {
        try {
            return SmUtil.sm3(content);
        } catch (Exception e) {
            log.error("SM3哈希失败", e);
            throw new RuntimeException("SM3哈希失败：" + e.getMessage());
        }
    }

    /**
     * SM3加密（加盐）
     *
     * @param content 待加密内容
     * @param salt 盐值
     * @return 加密后的哈希值
     */
    public static String sm3HashWithSalt(String content, String salt) {
        return sm3Hash(content + salt);
    }

    /**
     * SM2生成密钥对
     *
     * @return SM2对象
     */
    public static SM2 sm2GenerateKeyPair() {
        try {
            return SmUtil.sm2();
        } catch (Exception e) {
            log.error("SM2生成密钥对失败", e);
            throw new RuntimeException("SM2生成密钥对失败：" + e.getMessage());
        }
    }

    /**
     * SM2加密
     *
     * @param content 待加密内容
     * @param publicKey 公钥（十六进制字符串）
     * @return 加密后的十六进制字符串
     */
    public static String sm2Encrypt(String content, String publicKey) {
        try {
            SM2 sm2 = new SM2(null, publicKey);
            return sm2.encryptBcd(content, KeyType.PublicKey);
        } catch (Exception e) {
            log.error("SM2加密失败", e);
            throw new RuntimeException("SM2加密失败：" + e.getMessage());
        }
    }

    /**
     * SM2解密
     *
     * @param encrypted 加密的十六进制字符串
     * @param privateKey 私钥（十六进制字符串）
     * @return 解密后的内容
     */
    public static String sm2Decrypt(String encrypted, String privateKey) {
        try {
            SM2 sm2 = new SM2(privateKey, null);
            return sm2.decryptStr(encrypted, KeyType.PrivateKey);
        } catch (Exception e) {
            log.error("SM2解密失败", e);
            throw new RuntimeException("SM2解密失败：" + e.getMessage());
        }
    }

    /**
     * SM2签名
     *
     * @param content 待签名内容
     * @param privateKey 私钥（十六进制字符串）
     * @return 签名（十六进制字符串）
     */
    public static String sm2Sign(String content, String privateKey) {
        try {
            SM2 sm2 = new SM2(privateKey, null);
            return sm2.signHex(content).toString();
        } catch (Exception e) {
            log.error("SM2签名失败", e);
            throw new RuntimeException("SM2签名失败：" + e.getMessage());
        }
    }

    /**
     * SM2验签
     *
     * @param content 原始内容
     * @param sign 签名（十六进制字符串）
     * @param publicKey 公钥（十六进制字符串）
     * @return 是否验证通过
     */
    public static boolean sm2Verify(String content, String sign, String publicKey) {
        try {
            SM2 sm2 = new SM2(null, publicKey);
            return sm2.verifyHex(content, sign);
        } catch (Exception e) {
            log.error("SM2验签失败", e);
            return false;
        }
    }

    /**
     * 获取默认SM4密钥
     *
     * @return 默认密钥
     */
    public static String getDefaultSm4Key() {
        return DEFAULT_SM4_KEY;
    }
}
