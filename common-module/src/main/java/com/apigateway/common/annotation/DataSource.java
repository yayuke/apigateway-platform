package com.apigateway.common.annotation;

import java.lang.annotation.*;

/**
 * 数据源注解
 * 用于指定使用的数据源
 *
 * @author apigateway
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 数据源名称
     */
    String value() default "master";
}
