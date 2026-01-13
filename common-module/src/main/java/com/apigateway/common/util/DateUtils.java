package com.apigateway.common.util;

import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 日期工具类
 * 提供日期相关的常用操作
 *
 * @author apigateway
 * @since 1.0.0
 */
public class DateUtils {

    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 默认时间格式
     */
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    /**
     * 格式化日期时间
     *
     * @param dateTime 日期时间
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 格式化日期时间
     *
     * @param dateTime 日期时间
     * @param pattern  格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 获取当前时间戳（秒）
     *
     * @return 时间戳
     */
    public static long currentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间戳（毫秒）
     *
     * @return 时间戳
     */
    public static long currentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 日期转字符串
     *
     * @param date 日期
     * @return 字符串
     */
    public static String formatDate(Date date) {
        return DateUtil.format(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * 日期时间转字符串
     *
     * @param date 日期
     * @return 字符串
     */
    public static String formatDateTime(Date date) {
        return DateUtil.format(date, DEFAULT_DATETIME_FORMAT);
    }
}
