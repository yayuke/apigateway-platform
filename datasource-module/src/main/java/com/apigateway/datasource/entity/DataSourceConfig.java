package com.apigateway.datasource.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据源配置实体类
 *
 * @author apigateway
 * @since 1.0.0
 */
@Data
@TableName("datasource_config")
public class DataSourceConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 数据源名称
     */
    @TableField("ds_name")
    private String dsName;

    /**
     * 数据库类型（DM/MYSQL/ORACLE/SQLSERVER/POSTGRESQL）
     */
    @TableField("ds_type")
    private String dsType;

    /**
     * JDBC连接URL
     */
    @TableField("jdbc_url")
    private String jdbcUrl;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码（加密）
     */
    @TableField("password")
    private String password;

    /**
     * 驱动类名
     */
    @TableField("driver_class")
    private String driverClass;

    /**
     * 初始连接数
     */
    @TableField("initial_size")
    private Integer initialSize;

    /**
     * 最大活跃连接数
     */
    @TableField("max_active")
    private Integer maxActive;

    /**
     * 最小空闲连接数
     */
    @TableField("min_idle")
    private Integer minIdle;

    /**
     * 最大等待时间(ms)
     */
    @TableField("max_wait")
    private Long maxWait;

    /**
     * 测试查询SQL
     */
    @TableField("test_query")
    private String testQuery;

    /**
     * 状态：0-禁用，1-启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 健康状态：0-异常，1-正常
     */
    @TableField("health_status")
    private Integer healthStatus;

    /**
     * 最后检查时间
     */
    @TableField("last_check_time")
    private LocalDateTime lastCheckTime;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;
}
