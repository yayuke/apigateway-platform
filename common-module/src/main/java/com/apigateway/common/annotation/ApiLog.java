package com.apigateway.common.annotation;

import java.lang.annotation.*;

/**
 * API日志注解
 * 标注需要记录日志的API方法
 *
 * @author apigateway
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

    /**
     * API名称
     */
    String value() default "";

    /**
     * 是否记录请求参数
     */
    boolean logParams() default true;

    /**
     * 是否记录响应结果
     */
    boolean logResult() default false;
}
