package com.apigateway.datasource.factory;

import com.alibaba.druid.pool.DruidDataSource;
import com.apigateway.datasource.core.DatabaseType;
import com.apigateway.datasource.entity.DataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源工厂
 * 用于创建和管理数据源
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataSourceFactory {

    /**
     * 数据源缓存
     */
    private final Map<String, DataSource> dataSourceCache = new HashMap<>();

    /**
     * 创建数据源
     *
     * @param config 数据源配置
     * @return 数据源
     */
    public DataSource createDataSource(DataSourceConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("数据源配置不能为空");
        }

        String cacheKey = config.getDsName();

        // 从缓存中获取
        if (dataSourceCache.containsKey(cacheKey)) {
            return dataSourceCache.get(cacheKey);
        }

        // 创建Druid数据源
        DruidDataSource dataSource = new DruidDataSource();

        try {
            // 设置基本信息
            dataSource.setUrl(config.getJdbcUrl());
            dataSource.setUsername(config.getUsername());
            dataSource.setPassword(config.getPassword());

            // 设置驱动类名
            if (config.getDriverClass() != null && !config.getDriverClass().isEmpty()) {
                dataSource.setDriverClassName(config.getDriverClass());
            } else {
                DatabaseType type = DatabaseType.fromCode(config.getDsType());
                if (type != null) {
                    dataSource.setDriverClassName(type.getDriverClassName());
                }
            }

            // 设置连接池参数
            if (config.getInitialSize() != null) {
                dataSource.setInitialSize(config.getInitialSize());
            } else {
                dataSource.setInitialSize(5);
            }

            if (config.getMaxActive() != null) {
                dataSource.setMaxActive(config.getMaxActive());
            } else {
                dataSource.setMaxActive(20);
            }

            if (config.getMinIdle() != null) {
                dataSource.setMinIdle(config.getMinIdle());
            } else {
                dataSource.setMinIdle(5);
            }

            if (config.getMaxWait() != null) {
                dataSource.setMaxWait(config.getMaxWait());
            } else {
                dataSource.setMaxWait(60000L);
            }

            // 设置测试查询SQL
            if (config.getTestQuery() != null && !config.getTestQuery().isEmpty()) {
                dataSource.setValidationQuery(config.getTestQuery());
            } else {
                DatabaseType type = DatabaseType.fromCode(config.getDsType());
                if (type != null) {
                    switch (type) {
                        case DM:
                            dataSource.setValidationQuery("SELECT 1 FROM DUAL");
                            break;
                        case MYSQL:
                            dataSource.setValidationQuery("SELECT 1");
                            break;
                        case ORACLE:
                            dataSource.setValidationQuery("SELECT 1 FROM DUAL");
                            break;
                        default:
                            dataSource.setValidationQuery("SELECT 1");
                    }
                }
            }

            // 设置连接池属性
            dataSource.setTestWhileIdle(true);
            dataSource.setTestOnBorrow(false);
            dataSource.setTestOnReturn(false);
            dataSource.setTimeBetweenEvictionRunsMillis(60000);
            dataSource.setMinEvictableIdleTimeMillis(300000);

            // 初始化数据源
            dataSource.init();

            // 加入缓存
            dataSourceCache.put(cacheKey, dataSource);

            log.info("数据源创建成功：{}", cacheKey);
            return dataSource;

        } catch (SQLException e) {
            log.error("创建数据源失败：{}", cacheKey, e);
            throw new RuntimeException("创建数据源失败：" + e.getMessage());
        }
    }

    /**
     * 获取数据源缓存
     *
     * @return 数据源缓存
     */
    public Map<String, DataSource> getDataSourceCache() {
        return dataSourceCache;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        dataSourceCache.clear();
    }
}
