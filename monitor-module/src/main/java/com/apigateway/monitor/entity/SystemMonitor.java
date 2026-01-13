package com.apigateway.monitor.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统监控信息实体
 *
 * @author apigateway
 * @since 1.0.0
 */
@Data
public class SystemMonitor {

    /**
     * JVM内存信息
     */
    private JvmMemory jvmMemory;

    /**
     * 系统信息
     */
    private SystemInfo systemInfo;

    /**
     * 数据库连接池信息
     */
    private DataSourceInfo dataSourceInfo;

    /**
     * Redis信息
     */
    private RedisInfo redisInfo;

    /**
     * 采集时间
     */
    private LocalDateTime timestamp;

    @Data
    public static class JvmMemory {
        /**
         * JVM最大内存(MB)
         */
        private Double max;

        /**
         * JVM已分配内存(MB)
         */
        private Double total;

        /**
         * JVM空闲内存(MB)
         */
        private Double free;

        /**
         * JVM使用率(%)
         */
        private Double used;
    }

    @Data
    public static class SystemInfo {
        /**
         * CPU核心数
         */
        private Integer cpuCores;

        /**
         * CPU使用率(%)
         */
        private Double cpuUsage;

        /**
         * 系统总内存(MB)
         */
        private Double totalMemory;

        /**
         * 系统可用内存(MB)
         */
        private Double freeMemory;

        /**
         * JVM运行时间(ms)
         */
        private Long uptime;
    }

    @Data
    public static class DataSourceInfo {
        /**
         * 活跃连接数
         */
        private Integer active;

        /**
         * 空闲连接数
         */
        private Integer idle;

        /**
         * 总连接数
         */
        private Integer total;

        /**
         * 最大连接数
         */
        private Integer maxActive;

        /**
         * 使用率(%)
         */
        private Double usage;
    }

    @Data
    public static class RedisInfo {
        /**
         * Redis版本
         */
        private String version;

        /**
         * 已使用内存(MB)
         */
        private Double usedMemory;

        /**
         * 内存使用率(%)
         */
        private Double memoryUsage;

        /**
         * 连接数
         */
        private Integer connections;

        /**
         * 运行天数
         */
        private Long uptimeInDays;

        /**
         * 状态
         */
        private String status;
    }
}
