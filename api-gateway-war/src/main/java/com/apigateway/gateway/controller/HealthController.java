package com.apigateway.gateway.controller;

import com.apigateway.common.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 网关健康检查控制器
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/gateway")
public class HealthController {

    /**
     * 健康检查接口
     */
    @GetMapping("/health")
    public Result<Map<String, Object>> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "API Gateway");
        return Result.success(health);
    }

    /**
     * 网关信息接口
     */
    @GetMapping("/info")
    public Result<Map<String, Object>> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "API Gateway");
        info.put("version", "1.0.0");
        info.put("description", "国产化API网关平台");
        return Result.success(info);
    }
}
