package com.apigateway.common.constant;

/**
 * 系统常量
 * 定义系统中使用的常量
 *
 * @author apigateway
 * @since 1.0.0
 */
public interface SystemConstant {

    /**
     * UTF-8编码
     */
    String UTF8 = "UTF-8";

    /**
     * 默认分页大小
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大分页大小
     */
    Integer MAX_PAGE_SIZE = 100;

    /**
     * 启用状态
     */
    Integer STATUS_ENABLED = 1;

    /**
     * 禁用状态
     */
    Integer STATUS_DISABLED = 0;

    /**
     * 删除状态：未删除
     */
    Integer DELETED_NO = 0;

    /**
     * 删除状态：已删除
     */
    Integer DELETED_YES = 1;

    /**
     * API状态：草稿
     */
    Integer API_STATUS_DRAFT = 0;

    /**
     * API状态：已发布
     */
    Integer API_STATUS_PUBLISHED = 1;

    /**
     * API状态：已下线
     */
    Integer API_STATUS_OFFLINE = 2;

    /**
     * 需要认证
     */
    Integer NEED_AUTH_YES = 1;

    /**
     * 不需要认证
     */
    Integer NEED_AUTH_NO = 0;
}
