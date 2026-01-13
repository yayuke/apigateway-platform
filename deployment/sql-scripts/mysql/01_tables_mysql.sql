-- =============================================
-- 国产化API网关平台 - MySQL数据库初始化脚本
-- 版本: 1.0.0
-- 数据库: MySQL 8.0+
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `apigateway` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `apigateway`;

-- =============================================
-- 1. 系统用户表
-- =============================================
CREATE TABLE `sys_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    `real_name` VARCHAR(50) COMMENT '真实姓名',
    `email` VARCHAR(100) COMMENT '邮箱',
    `phone` VARCHAR(20) COMMENT '手机号',
    `status` INT DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `last_login_time` DATETIME COMMENT '最后登录时间',
    `last_login_ip` VARCHAR(50) COMMENT '最后登录IP',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT COMMENT '创建人ID',
    `update_user_id` BIGINT COMMENT '更新人ID',
    `deleted` INT DEFAULT 0 COMMENT '删除标识:0-未删除,1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_user_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户表';

-- =============================================
-- 2. API信息表
-- =============================================
CREATE TABLE `api_info` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'API ID',
    `api_name` VARCHAR(100) NOT NULL COMMENT 'API名称',
    `api_path` VARCHAR(255) NOT NULL COMMENT 'API路径',
    `api_method` VARCHAR(10) NOT NULL COMMENT '请求方法',
    `datasource_id` BIGINT COMMENT '数据源ID',
    `sql_content` TEXT COMMENT 'SQL内容',
    `need_auth` INT DEFAULT 1 COMMENT '是否需要认证:0-否,1-是',
    `encrypt_type` VARCHAR(20) COMMENT '加密类型:SM4/AES/NONE',
    `status` INT DEFAULT 0 COMMENT '状态:0-草稿,1-发布,2-下线',
    `version` VARCHAR(20) DEFAULT '1.0' COMMENT '版本号',
    `description` VARCHAR(500) COMMENT 'API描述',
    `request_example` TEXT COMMENT '请求参数示例',
    `response_example` TEXT COMMENT '响应示例',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_user_id` BIGINT COMMENT '创建人ID',
    `update_user_id` BIGINT COMMENT '更新人ID',
    `deleted` INT DEFAULT 0 COMMENT '删除标识:0-未删除,1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_api_path` (`api_path`),
    KEY `idx_api_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='API信息表';

-- =============================================
-- 3. 数据源配置表
-- =============================================
CREATE TABLE `datasource_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置ID',
    `ds_name` VARCHAR(100) NOT NULL COMMENT '数据源名称',
    `ds_type` VARCHAR(50) NOT NULL COMMENT '数据库类型:DM/MYSQL/ORACLE/SQLSERVER/POSTGRESQL',
    `jdbc_url` VARCHAR(500) NOT NULL COMMENT 'JDBC连接URL',
    `username` VARCHAR(100) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(加密)',
    `driver_class` VARCHAR(100) COMMENT '驱动类名',
    `initial_size` INT DEFAULT 5 COMMENT '初始连接数',
    `max_active` INT DEFAULT 20 COMMENT '最大活跃连接数',
    `min_idle` INT DEFAULT 5 COMMENT '最小空闲连接数',
    `max_wait` INT DEFAULT 60000 COMMENT '最大等待时间(ms)',
    `test_query` VARCHAR(100) COMMENT '测试查询SQL',
    `status` INT DEFAULT 1 COMMENT '状态:0-禁用,1-启用',
    `health_status` INT DEFAULT 0 COMMENT '健康状态:0-异常,1-正常',
    `last_check_time` DATETIME COMMENT '最后检查时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` INT DEFAULT 0 COMMENT '删除标识:0-未删除,1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_ds_name` (`ds_name`),
    KEY `idx_ds_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据源配置表';

-- =============================================
-- 4. 系统日志表
-- =============================================
CREATE TABLE `sys_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `log_type` INT NOT NULL COMMENT '日志类型:1-登录日志,2-操作日志,3-异常日志',
    `log_title` VARCHAR(100) COMMENT '日志标题',
    `user_id` BIGINT COMMENT '操作人ID',
    `username` VARCHAR(50) COMMENT '操作人用户名',
    `ip` VARCHAR(50) COMMENT '操作IP',
    `request_url` VARCHAR(500) COMMENT '请求URL',
    `request_method` VARCHAR(10) COMMENT '请求方法',
    `request_params` TEXT COMMENT '请求参数',
    `response_result` TEXT COMMENT '响应结果',
    `execute_time` BIGINT COMMENT '执行时长(ms)',
    `status` INT COMMENT '状态:0-失败,1-成功',
    `exception_msg` TEXT COMMENT '异常信息',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_log_type` (`log_type`),
    KEY `idx_log_user_id` (`user_id`),
    KEY `idx_log_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统日志表';

-- =============================================
-- 5. 初始化数据
-- =============================================

-- 初始化管理员用户 (密码: admin123)
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `status`)
VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '系统管理员', 1);

-- 初始化默认数据源配置
INSERT INTO `datasource_config` (`id`, `ds_name`, `ds_type`, `jdbc_url`, `username`, `password`, `status`, `health_status`)
VALUES (1, '主数据源', 'MYSQL', 'jdbc:mysql://localhost:3306/apigateway?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai', 'root', '123456', 1, 1);
