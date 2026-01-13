package com.apigateway.common.constant;

/**
 * 缓存键常量
 * 定义Redis缓存键的前缀
 *
 * @author apigateway
 * @since 1.0.0
 */
public interface CacheKey {

    /**
     * 缓存键前缀
     */
    String PREFIX = "api:gateway:";

    /**
     * 用户缓存
     */
    String USER = PREFIX + "user:";

    /**
     * 数据源缓存
     */
    String DATASOURCE = PREFIX + "datasource:";

    /**
     * API缓存
     */
    String API = PREFIX + "api:";

    /**
     * Token缓存
     */
    String TOKEN = PREFIX + "token:";

    /**
     * 限流缓存
     */
    String RATE_LIMIT = PREFIX + "ratelimit:";

    /**
     * 配置缓存
     */
    String CONFIG = PREFIX + "config:";
}
