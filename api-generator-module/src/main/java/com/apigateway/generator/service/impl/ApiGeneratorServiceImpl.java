package com.apigateway.generator.service.impl;

import com.apigateway.common.exception.BusinessException;
import com.apigateway.datasource.manager.DataSourceManager;
import com.apigateway.generator.entity.ApiExecuteResult;
import com.apigateway.generator.entity.ApiInfo;
import com.apigateway.generator.mapper.ApiInfoMapper;
import com.apigateway.generator.service.IApiGeneratorService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * API生成服务实现类
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@Service
public class ApiGeneratorServiceImpl extends ServiceImpl<ApiInfoMapper, ApiInfo> implements IApiGeneratorService {

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    @Autowired
    private DataSourceManager dataSourceManager;

    @Override
    public ApiInfo getApiByPathAndMethod(String path, String method) {
        if (!StringUtils.hasText(path) || !StringUtils.hasText(method)) {
            throw new BusinessException("API路径和方法不能为空");
        }
        return apiInfoMapper.selectByPathAndMethod(path, method);
    }

    @Override
    public ApiExecuteResult executeApi(String path, String method, Map<String, Object> params) {
        // 1. 获取API配置
        ApiInfo apiInfo = getApiByPathAndMethod(path, method);
        if (apiInfo == null) {
            return ApiExecuteResult.error("API不存在或已下线");
        }

        // 2. 验证API状态
        if (apiInfo.getStatus() != 1) {
            return ApiExecuteResult.error("API未发布");
        }

        long startTime = System.currentTimeMillis();

        try {
            // 3. 构建SQL
            String sql = buildSql(apiInfo.getSqlContent(), params);

            // 4. 执行SQL
            String sqlType = getSqlType(sql);
            if ("SELECT".equalsIgnoreCase(sqlType)) {
                return executeQuery(sql, startTime);
            } else {
                return executeUpdate(sql, startTime);
            }
        } catch (Exception e) {
            log.error("执行API失败：{}", apiInfo.getApiPath(), e);
            return ApiExecuteResult.error("API执行失败：" + e.getMessage());
        }
    }

    @Override
    public IPage<ApiInfo> pageApis(Integer pageNum, Integer pageSize, Integer status, String apiName) {
        Page<ApiInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ApiInfo> wrapper = new LambdaQueryWrapper<>();

        if (status != null) {
            wrapper.eq(ApiInfo::getStatus, status);
        }

        if (StringUtils.hasText(apiName)) {
            wrapper.like(ApiInfo::getApiName, apiName);
        }

        wrapper.orderByDesc(ApiInfo::getCreateTime);
        return this.page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createApi(ApiInfo apiInfo) {
        if (!StringUtils.hasText(apiInfo.getApiPath())) {
            throw new BusinessException("API路径不能为空");
        }
        if (!StringUtils.hasText(apiInfo.getSqlContent())) {
            throw new BusinessException("SQL内容不能为空");
        }

        // 检查API路径是否已存在
        ApiInfo existing = getApiByPathAndMethod(apiInfo.getApiPath(), apiInfo.getApiMethod());
        if (existing != null) {
            throw new BusinessException("API路径和方法已存在");
        }

        // 设置默认状态为草稿
        if (apiInfo.getStatus() == null) {
            apiInfo.setStatus(0);
        }

        // 设置默认版本
        if (!StringUtils.hasText(apiInfo.getVersion())) {
            apiInfo.setVersion("1.0");
        }

        return this.save(apiInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateApi(ApiInfo apiInfo) {
        if (apiInfo.getId() == null) {
            throw new BusinessException("API ID不能为空");
        }

        ApiInfo existing = this.getById(apiInfo.getId());
        if (existing == null) {
            throw new BusinessException("API不存在");
        }

        return this.updateById(apiInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteApi(Long id) {
        if (id == null) {
            throw new BusinessException("API ID不能为空");
        }

        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishApi(Long id) {
        if (id == null) {
            throw new BusinessException("API ID不能为空");
        }

        ApiInfo apiInfo = this.getById(id);
        if (apiInfo == null) {
            throw new BusinessException("API不存在");
        }

        apiInfo.setStatus(1); // 发布状态
        return this.updateById(apiInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean offlineApi(Long id) {
        if (id == null) {
            throw new BusinessException("API ID不能为空");
        }

        ApiInfo apiInfo = this.getById(id);
        if (apiInfo == null) {
            throw new BusinessException("API不存在");
        }

        apiInfo.setStatus(2); // 下线状态
        return this.updateById(apiInfo);
    }

    /**
     * 构建SQL（替换参数占位符）
     */
    private String buildSql(String sqlTemplate, Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return sqlTemplate;
        }

        String sql = sqlTemplate;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String placeholder = "${" + entry.getKey() + "}";
            String value = entry.getValue() != null ? entry.getValue().toString() : "";
            // 简单的字符串替换，实际使用时应该使用预编译语句防止SQL注入
            sql = sql.replace(placeholder, value);
        }

        return sql;
    }

    /**
     * 获取SQL类型
     */
    private String getSqlType(String sql) {
        String trimmed = sql.trim().toUpperCase();
        if (trimmed.startsWith("SELECT")) {
            return "SELECT";
        } else if (trimmed.startsWith("INSERT")) {
            return "INSERT";
        } else if (trimmed.startsWith("UPDATE")) {
            return "UPDATE";
        } else if (trimmed.startsWith("DELETE")) {
            return "DELETE";
        }
        return "UNKNOWN";
    }

    /**
     * 执行查询SQL
     */
    private ApiExecuteResult executeQuery(String sql, long startTime) throws SQLException {
        DataSource dataSource = dataSourceManager.getAllDataSources().get("master");
        if (dataSource == null) {
            throw new BusinessException("数据源未配置");
        }

        List<Map<String, Object>> resultList = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new java.util.LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rs.getMetaData().getColumnName(i);
                    Object value = rs.getObject(i);
                    row.put(columnName, value);
                }
                resultList.add(row);
            }
        }

        long executeTime = System.currentTimeMillis() - startTime;
        return ApiExecuteResult.success(resultList, executeTime);
    }

    /**
     * 执行更新SQL
     */
    private ApiExecuteResult executeUpdate(String sql, long startTime) throws SQLException {
        DataSource dataSource = dataSourceManager.getAllDataSources().get("master");
        if (dataSource == null) {
            throw new BusinessException("数据源未配置");
        }

        int affectedRows;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            affectedRows = ps.executeUpdate();
        }

        long executeTime = System.currentTimeMillis() - startTime;
        return ApiExecuteResult.update(affectedRows, executeTime);
    }
}
