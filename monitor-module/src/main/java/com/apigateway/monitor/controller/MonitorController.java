package com.apigateway.monitor.controller;

import com.apigateway.common.core.Result;
import com.apigateway.monitor.entity.SystemMonitor;
import com.apigateway.monitor.service.SystemMonitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统监控控制器
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/monitor")
@Api(tags = "系统监控")
public class MonitorController {

    @Autowired
    private SystemMonitorService systemMonitorService;

    /**
     * 获取系统监控信息
     */
    @GetMapping("/system")
    @ApiOperation("获取系统监控信息")
    public Result<SystemMonitor> getSystemMonitor() {
        SystemMonitor monitor = systemMonitorService.getSystemMonitor();
        return Result.success(monitor);
    }
}
