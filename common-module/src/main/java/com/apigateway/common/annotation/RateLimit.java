package com.apigateway.common.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 * 标注需要限流的API方法
 *
 * @author apigateway
 * @since 1.0.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    /**
     * 限流key
     */
    String key() default "";

    /**
     * 时间窗口（秒）
     */
    int time() default 60;

    /**
     * 时间窗口内最大请求次数
     */
    int count() default 100;
}
