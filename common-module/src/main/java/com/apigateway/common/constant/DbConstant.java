package com.apigateway.common.constant;

/**
 * 数据库常量
 * 定义数据库相关的常量
 *
 * @author apigateway
 * @since 1.0.0
 */
public interface DbConstant {

    /**
     * 达梦数据库
     */
    String DB_DM = "DM";

    /**
     * MySQL数据库
     */
    String DB_MYSQL = "MYSQL";

    /**
     * Oracle数据库
     */
    String DB_ORACLE = "ORACLE";

    /**
     * SQL Server数据库
     */
    String DB_SQLSERVER = "SQLSERVER";

    /**
     * PostgreSQL数据库
     */
    String DB_POSTGRESQL = "POSTGRESQL";

    /**
     * 主数据源
     */
    String DS_PRIMARY = "master";

    /**
     * 从数据源
     */
    String DS_SLAVE = "slave";
}
