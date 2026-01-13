package com.apigateway.datasource.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.apigateway.datasource.core.DynamicDataSource;
import com.apigateway.datasource.factory.DataSourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 动态数据源配置类
 * 配置多数据源支持和动态切换
 *
 * @author apigateway
 * @since 1.0.0
 */
@Configuration
public class DynamicDataSourceConfig {

    /**
     * 主数据源配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源
     */
    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(DataSourceFactory dataSourceFactory) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        // 设置默认数据源
        DataSource masterDataSource = masterDataSource();
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);

        // 设置数据源Map（包含主数据源）
        java.util.Map<Object, Object> dataSourceMap = new java.util.HashMap<>();
        dataSourceMap.put("master", masterDataSource);
        dynamicDataSource.setTargetDataSources(dataSourceMap);

        return dynamicDataSource;
    }
}
