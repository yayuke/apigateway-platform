package com.apigateway.common.core;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果类
 * 用于返回分页查询的数据
 *
 * @author apigateway
 * @since 1.0.0
 */
@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页码
     */
    private Long pageNum;

    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 数据列表
     */
    private List<T> records;

    public PageResult() {
    }

    public PageResult(Long pageNum, Long pageSize, Long total, List<T> records) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.records = records;
        // 计算总页数
        this.pages = (total + pageSize - 1) / pageSize;
    }

    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> build(Long pageNum, Long pageSize, Long total, List<T> records) {
        return new PageResult<>(pageNum, pageSize, total, records);
    }
}
