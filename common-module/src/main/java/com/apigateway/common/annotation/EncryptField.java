package com.apigateway.common.annotation;

import java.lang.annotation.*;

/**
 * 加密字段注解
 * 标注需要加密/解密的字段
 *
 * @author apigateway
 * @since 1.0.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EncryptField {

    /**
     * 加密算法：SM4, AES
     */
    String algorithm() default "SM4";
}
