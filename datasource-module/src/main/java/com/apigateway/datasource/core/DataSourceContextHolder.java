package com.apigateway.datasource.core;

/**
 * 数据源上下文持有者
 * 用于存储和获取当前线程使用的数据源
 *
 * @author apigateway
 * @since 1.0.0
 */
public class DataSourceContextHolder {

    /**
     * 数据源名称的线程本地变量
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 主数据源名称
     */
    public static final String DEFAULT_DATASOURCE = "master";

    /**
     * 设置当前线程使用的数据源
     *
     * @param dataSourceName 数据源名称
     */
    public static void setDataSource(String dataSourceName) {
        if (dataSourceName == null || dataSourceName.isEmpty()) {
            dataSourceName = DEFAULT_DATASOURCE;
        }
        CONTEXT_HOLDER.set(dataSourceName);
    }

    /**
     * 获取当前线程使用的数据源
     *
     * @return 数据源名称
     */
    public static String getDataSource() {
        String dataSourceName = CONTEXT_HOLDER.get();
        return dataSourceName != null ? dataSourceName : DEFAULT_DATASOURCE;
    }

    /**
     * 清除当前线程的数据源
     */
    public static void clearDataSource() {
        CONTEXT_HOLDER.remove();
    }
}
