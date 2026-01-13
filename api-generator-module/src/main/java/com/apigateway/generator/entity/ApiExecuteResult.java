package com.apigateway.generator.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * API执行结果
 *
 * @author apigateway
 * @since 1.0.0
 */
@Data
public class ApiExecuteResult {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 执行的SQL
     */
    private String sql;

    /**
     * 影响行数
     */
    private Integer affectedRows;

    /**
     * 查询结果列表
     */
    private List<Map<String, Object>> data;

    /**
     * 执行时长(ms)
     */
    private Long executeTime;

    /**
     * 创建成功结果
     */
    public static ApiExecuteResult success(List<Map<String, Object>> data, Long executeTime) {
        ApiExecuteResult result = new ApiExecuteResult();
        result.setSuccess(true);
        result.setData(data);
        result.setExecuteTime(executeTime);
        return result;
    }

    /**
     * 创建失败结果
     */
    public static ApiExecuteResult error(String errorMessage) {
        ApiExecuteResult result = new ApiExecuteResult();
        result.setSuccess(false);
        result.setErrorMessage(errorMessage);
        return result;
    }

    /**
     * 创建更新/删除结果
     */
    public static ApiExecuteResult update(Integer affectedRows, Long executeTime) {
        ApiExecuteResult result = new ApiExecuteResult();
        result.setSuccess(true);
        result.setAffectedRows(affectedRows);
        result.setExecuteTime(executeTime);
        return result;
    }
}
