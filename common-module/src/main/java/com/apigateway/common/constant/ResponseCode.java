package com.apigateway.common.constant;

/**
 * 响应码常量
 * 定义系统中使用的响应码
 *
 * @author apigateway
 * @since 1.0.0
 */
public interface ResponseCode {

    /**
     * 成功
     */
    Integer SUCCESS = 200;

    /**
     * 失败
     */
    Integer ERROR = 500;

    /**
     * 未授权
     */
    Integer UNAUTHORIZED = 401;

    /**
     * 禁止访问
     */
    Integer FORBIDDEN = 403;

    /**
     * 资源不存在
     */
    Integer NOT_FOUND = 404;

    /**
     * 参数错误
     */
    Integer PARAM_ERROR = 400;

    /**
     * 业务异常
     */
    Integer BUSINESS_ERROR = 600;

    /**
     * 数据源异常
     */
    Integer DATASOURCE_ERROR = 700;

    /**
     * 加密异常
     */
    Integer ENCRYPT_ERROR = 800;
}
