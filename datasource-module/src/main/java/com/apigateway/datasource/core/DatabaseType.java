package com.apigateway.datasource.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型枚举
 *
 * @author apigateway
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum DatabaseType {

    /**
     * 达梦数据库
     */
    DM("DM", "达梦数据库", "dm.jdbc.driver.DmDriver"),

    /**
     * MySQL数据库
     */
    MYSQL("MYSQL", "MySQL数据库", "com.mysql.cj.jdbc.Driver"),

    /**
     * Oracle数据库
     */
    ORACLE("ORACLE", "Oracle数据库", "oracle.jdbc.OracleDriver"),

    /**
     * SQL Server数据库
     */
    SQLSERVER("SQLSERVER", "SQL Server数据库", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    /**
     * PostgreSQL数据库
     */
    POSTGRESQL("POSTGRESQL", "PostgreSQL数据库", "org.postgresql.Driver");

    /**
     * 数据库类型代码
     */
    private final String code;

    /**
     * 数据库类型名称
     */
    private final String name;

    /**
     * 驱动类名
     */
    private final String driverClassName;

    /**
     * 根据代码获取数据库类型
     *
     * @param code 代码
     * @return 数据库类型
     */
    public static DatabaseType fromCode(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (DatabaseType type : values()) {
            if (type.getCode().equalsIgnoreCase(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据驱动类名获取数据库类型
     *
     * @param driverClassName 驱动类名
     * @return 数据库类型
     */
    public static DatabaseType fromDriverClassName(String driverClassName) {
        if (driverClassName == null || driverClassName.isEmpty()) {
            return null;
        }
        for (DatabaseType type : values()) {
            if (type.getDriverClassName().equals(driverClassName)) {
                return type;
            }
        }
        return null;
    }
}
