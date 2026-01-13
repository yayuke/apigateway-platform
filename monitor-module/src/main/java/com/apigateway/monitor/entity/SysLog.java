package com.apigateway.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统日志实体
 *
 * @author apigateway
 * @since 1.0.0
 */
@Data
@TableName("sys_log")
public class SysLog {

    /**
     * 日志ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 日志类型：1-登录日志，2-操作日志，3-异常日志
     */
    @TableField("log_type")
    private Integer logType;

    /**
     * 日志标题
     */
    @TableField("log_title")
    private String logTitle;

    /**
     * 操作人ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 操作人用户名
     */
    @TableField("username")
    private String username;

    /**
     * 操作IP
     */
    @TableField("ip")
    private String ip;

    /**
     * 请求URL
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 请求方法
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 响应结果
     */
    @TableField("response_result")
    private String responseResult;

    /**
     * 执行时长(ms)
     */
    @TableField("execute_time")
    private Long executeTime;

    /**
     * 状态：0-失败，1-成功
     */
    @TableField("status")
    private Integer status;

    /**
     * 异常信息
     */
    @TableField("exception_msg")
    private String exceptionMsg;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
