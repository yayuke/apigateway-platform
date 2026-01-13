package com.apigateway.generator.controller;

import com.apigateway.common.core.PageResult;
import com.apigateway.common.core.Result;
import com.apigateway.generator.entity.ApiExecuteResult;
import com.apigateway.generator.entity.ApiInfo;
import com.apigateway.generator.service.IApiGeneratorService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * API生成管理控制器
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/apis")
@Api(tags = "API管理")
public class ApiController {

    @Autowired
    private IApiGeneratorService apiGeneratorService;

    /**
     * 执行API（网关转发）
     */
    @PostMapping("/execute/**")
    @ApiOperation("执行API")
    public Result<ApiExecuteResult> executeApi(
            @RequestParam Map<String, Object> params) {
        // 提取路径
        String path = extractPath();

        // 默认POST方法
        ApiExecuteResult result = apiGeneratorService.executeApi(path, "POST", params);

        if (result.getSuccess()) {
            return Result.success(result);
        } else {
            return Result.error(result.getErrorMessage());
        }
    }

    /**
     * 分页查询API列表
     */
    @GetMapping("/page")
    @ApiOperation("分页查询API列表")
    public Result<PageResult<ApiInfo>> pageApis(
            @ApiParam("页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam("每页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam("状态") @RequestParam(required = false) Integer status,
            @ApiParam("API名称") @RequestParam(required = false) String apiName) {

        IPage<ApiInfo> page = apiGeneratorService.pageApis(pageNum, pageSize, status, apiName);
        PageResult<ApiInfo> result = PageResult.build(
                page.getCurrent(),
                page.getSize(),
                page.getTotal(),
                page.getRecords()
        );
        return Result.success(result);
    }

    /**
     * 根据ID查询API
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询API")
    public Result<ApiInfo> getApiById(@ApiParam("API ID") @PathVariable Long id) {
        ApiInfo apiInfo = apiGeneratorService.getById(id);
        if (apiInfo == null) {
            return Result.error("API不存在");
        }
        return Result.success(apiInfo);
    }

    /**
     * 创建API
     */
    @PostMapping
    @ApiOperation("创建API")
    public Result<Void> createApi(@RequestBody ApiInfo apiInfo) {
        boolean success = apiGeneratorService.createApi(apiInfo);
        return success ? Result.success("创建成功") : Result.error("创建失败");
    }

    /**
     * 更新API
     */
    @PutMapping
    @ApiOperation("更新API")
    public Result<Void> updateApi(@RequestBody ApiInfo apiInfo) {
        boolean success = apiGeneratorService.updateApi(apiInfo);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }

    /**
     * 删除API
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除API")
    public Result<Void> deleteApi(@ApiParam("API ID") @PathVariable Long id) {
        boolean success = apiGeneratorService.deleteApi(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }

    /**
     * 发布API
     */
    @PutMapping("/{id}/publish")
    @ApiOperation("发布API")
    public Result<Void> publishApi(@ApiParam("API ID") @PathVariable Long id) {
        boolean success = apiGeneratorService.publishApi(id);
        return success ? Result.success("发布成功") : Result.error("发布失败");
    }

    /**
     * 下线API
     */
    @PutMapping("/{id}/offline")
    @ApiOperation("下线API")
    public Result<Void> offlineApi(@ApiParam("API ID") @PathVariable Long id) {
        boolean success = apiGeneratorService.offlineApi(id);
        return success ? Result.success("下线成功") : Result.error("下线失败");
    }

    /**
     * 提取请求路径
     */
    private String extractPath() {
        // 从RequestContextHolder获取请求路径
        org.springframework.web.context.request.RequestAttributes attrs =
            org.springframework.web.context.request.RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            javax.servlet.http.HttpServletRequest request =
                ((org.springframework.web.context.request.ServletRequestAttributes) attrs).getRequest();
            String uri = request.getRequestURI();
            // 移除 /api/apis/execute 前缀
            return uri.substring("/api/apis/execute".length());
        }
        return "/";
    }
}
