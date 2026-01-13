package com.apigateway.common.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean工具类
 * 提供对象拷贝相关的常用操作
 *
 * @author apigateway
 * @since 1.0.0
 */
public class BeanUtils {

    /**
     * 对象属性拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyProperties(Object source, Object target) {
        BeanUtil.copyProperties(source, target);
    }

    /**
     * 对象属性拷贝（忽略空值）
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtil.copyProperties(source, target, cn.hutool.core.bean.CopyOptions.create().ignoreNullValue());
    }

    /**
     * 对象转换
     *
     * @param source 源对象
     * @param clazz 目标类
     * @param <T> 目标类型
     * @return 目标对象
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        return BeanUtil.copyProperties(source, clazz);
    }

    /**
     * 列表转换
     *
     * @param sourceList 源列表
     * @param clazz 目标类
     * @param <T> 目标类型
     * @param <S> 源类型
     * @return 目标列表
     */
    public static <T, S> List<T> copyList(List<S> sourceList, Class<T> clazz) {
        if (CollUtil.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S source : sourceList) {
            T target = BeanUtil.copyProperties(source, clazz);
            targetList.add(target);
        }
        return targetList;
    }

    /**
     * 获取对象属性值
     *
     * @param obj 对象
     * @param propertyName 属性名
     * @return 属性值
     */
    public static Object getProperty(Object obj, String propertyName) {
        return BeanUtil.getProperty(obj, propertyName);
    }

    /**
     * 设置对象属性值
     *
     * @param obj 对象
     * @param propertyName 属性名
     * @param value 属性值
     */
    public static void setProperty(Object obj, String propertyName, Object value) {
        BeanUtil.setProperty(obj, propertyName, value);
    }
}
