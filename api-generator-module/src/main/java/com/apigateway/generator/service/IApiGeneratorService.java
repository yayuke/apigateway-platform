package com.apigateway.generator.service;

import com.apigateway.generator.entity.ApiExecuteResult;
import com.apigateway.generator.entity.ApiInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * API生成服务接口
 *
 * @author apigateway
 * @since 1.0.0
 */
public interface IApiGeneratorService {

    /**
     * 根据路径和方法获取API
     */
    ApiInfo getApiByPathAndMethod(String path, String method);

    /**
     * 执行API
     */
    ApiExecuteResult executeApi(String path, String method, Map<String, Object> params);

    /**
     * 分页查询API列表
     */
    IPage<ApiInfo> pageApis(Integer pageNum, Integer pageSize, Integer status, String apiName);

    /**
     * 创建API
     */
    boolean createApi(ApiInfo apiInfo);

    /**
     * 更新API
     */
    boolean updateApi(ApiInfo apiInfo);

    /**
     * 删除API
     */
    boolean deleteApi(Long id);

    /**
     * 发布API
     */
    boolean publishApi(Long id);

    /**
     * 下线API
     */
    boolean offlineApi(Long id);
}
