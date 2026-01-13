package com.apigateway.datasource.manager;

import com.apigateway.datasource.core.DataSourceContextHolder;
import com.apigateway.datasource.entity.DataSourceConfig;
import com.apigateway.datasource.factory.DataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

/**
 * 数据源管理器
 * 管理动态数据源的创建、切换和健康检查
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@Component
public class DataSourceManager {

    @Autowired
    private DataSourceFactory dataSourceFactory;

    /**
     * 添加数据源
     *
     * @param config 数据源配置
     * @return 数据源
     */
    public DataSource addDataSource(DataSourceConfig config) {
        log.info("添加数据源：{}", config.getDsName());
        return dataSourceFactory.createDataSource(config);
    }

    /**
     * 切换数据源
     *
     * @param dataSourceName 数据源名称
     */
    public void switchDataSource(String dataSourceName) {
        log.info("切换数据源：{}", dataSourceName);
        DataSourceContextHolder.setDataSource(dataSourceName);
    }

    /**
     * 清除数据源（恢复到默认数据源）
     */
    public void clearDataSource() {
        log.info("清除数据源，恢复到默认数据源");
        DataSourceContextHolder.clearDataSource();
    }

    /**
     * 检查数据源健康状态
     *
     * @param config 数据源配置
     * @return 是否健康
     */
    public boolean checkHealth(DataSourceConfig config) {
        DataSource dataSource = dataSourceFactory.createDataSource(config);
        try (Connection connection = dataSource.getConnection()) {
            boolean valid = connection.isValid(5);
            log.info("数据源健康检查：{}，状态：{}", config.getDsName(), valid ? "正常" : "异常");
            return valid;
        } catch (Exception e) {
            log.error("数据源健康检查失败：{}", config.getDsName(), e);
            return false;
        }
    }

    /**
     * 测试数据源连接
     *
     * @param config 数据源配置
     * @return 是否连接成功
     */
    public boolean testConnection(DataSourceConfig config) {
        try {
            DataSource dataSource = dataSourceFactory.createDataSource(config);
            try (Connection connection = dataSource.getConnection()) {
                return connection.isValid(5);
            }
        } catch (Exception e) {
            log.error("测试数据源连接失败：{}", config.getDsName(), e);
            return false;
        }
    }

    /**
     * 获取所有数据源
     *
     * @return 数据源Map
     */
    public Map<String, DataSource> getAllDataSources() {
        return dataSourceFactory.getDataSourceCache();
    }
}
