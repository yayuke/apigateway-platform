package com.apigateway.datasource.util;

import com.apigateway.datasource.core.DatabaseType;

/**
 * 数据库工具类
 * 提供数据库相关的工具方法
 *
 * @author apigateway
 * @since 1.0.0
 */
public class DatabaseUtils {

    /**
     * 获取数据库类型对应的包装符号
     *
     * @param databaseType 数据库类型
     * @return 包装符号
     */
    public static String getWrapSymbol(DatabaseType databaseType) {
        if (databaseType == null) {
            return "\"";
        }
        switch (databaseType) {
            case DM:
            case POSTGRESQL:
                return "\"";
            case MYSQL:
                return "`";
            case ORACLE:
            case SQLSERVER:
                return "\"";
            default:
                return "\"";
        }
    }

    /**
     * 包装字段名（处理关键字）
     *
     * @param fieldName 字段名
     * @param databaseType 数据库类型
     * @return 包装后的字段名
     */
    public static String wrapFieldName(String fieldName, DatabaseType databaseType) {
        String symbol = getWrapSymbol(databaseType);
        return symbol + fieldName + symbol;
    }

    /**
     * 将下划线命名转换为驼峰命名
     *
     * @param name 下划线命名
     * @return 驼峰命名
     */
    public static String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (c == '_') {
                capitalizeNext = true;
            } else {
                if (capitalizeNext) {
                    result.append(Character.toUpperCase(c));
                    capitalizeNext = false;
                } else {
                    result.append(Character.toLowerCase(c));
                }
            }
        }
        return result.toString();
    }

    /**
     * 将驼峰命名转换为下划线命名
     *
     * @param name 驼峰命名
     * @return 下划线命名
     */
    public static String toUnderlineCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
