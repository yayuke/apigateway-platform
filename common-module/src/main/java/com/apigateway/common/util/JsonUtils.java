package com.apigateway.common.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

/**
 * JSON工具类
 * 提供JSON相关的常用操作
 *
 * @author apigateway
 * @since 1.0.0
 */
public class JsonUtils {

    /**
     * 对象转JSON字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        }
        return JSON.toJSONString(obj);
    }

    /**
     * JSON字符串转对象
     *
     * @param json JSON字符串
     * @param clazz 对象类型
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    /**
     * JSON字符串转JSONObject
     *
     * @param json JSON字符串
     * @return JSONObject
     */
    public static JSONObject parseObject(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json);
    }

    /**
     * 格式化JSON字符串
     *
     * @param json JSON字符串
     * @return 格式化后的字符串
     */
    public static String format(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(json);
        return JSON.toJSONString(jsonObject, true);
    }
}
