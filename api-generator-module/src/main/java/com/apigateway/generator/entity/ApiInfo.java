package com.apigateway.generator.entity;

import com.apigateway.common.core.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * API信息实体
 *
 * @author apigateway
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("api_info")
public class ApiInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * API ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * API名称
     */
    @TableField("api_name")
    private String apiName;

    /**
     * API路径
     */
    @TableField("api_path")
    private String apiPath;

    /**
     * 请求方法（GET/POST/PUT/DELETE）
     */
    @TableField("api_method")
    private String apiMethod;

    /**
     * 数据源ID
     */
    @TableField("datasource_id")
    private Long datasourceId;

    /**
     * SQL内容
     */
    @TableField("sql_content")
    private String sqlContent;

    /**
     * 是否需要认证：0-否，1-是
     */
    @TableField("need_auth")
    private Integer needAuth;

    /**
     * 加密类型（SM4/AES/NONE）
     */
    @TableField("encrypt_type")
    private String encryptType;

    /**
     * 状态：0-草稿，1-发布，2-下线
     */
    @TableField("status")
    private Integer status;

    /**
     * 版本号
     */
    @TableField("version")
    private String version;

    /**
     * API描述
     */
    @TableField("description")
    private String description;

    /**
     * 请求参数示例（JSON格式）
     */
    @TableField("request_example")
    private String requestExample;

    /**
     * 响应示例（JSON格式）
     */
    @TableField("response_example")
    private String responseExample;
}
