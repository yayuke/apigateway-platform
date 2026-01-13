package com.apigateway.monitor.service;

import com.apigateway.monitor.entity.SystemMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

/**
 * 系统监控服务
 *
 * @author apigateway
 * @since 1.0.0
 */
@Slf4j
@Service
public class SystemMonitorService {

    /**
     * 获取系统监控信息
     */
    public SystemMonitor getSystemMonitor() {
        SystemMonitor monitor = new SystemMonitor();
        monitor.setTimestamp(java.time.LocalDateTime.now());

        // 获取JVM内存信息
        monitor.setJvmMemory(getJvmMemoryInfo());

        // 获取系统信息
        monitor.setSystemInfo(getSystemInfo());

        return monitor;
    }

    /**
     * 获取JVM内存信息
     */
    private SystemMonitor.JvmMemory getJvmMemoryInfo() {
        SystemMonitor.JvmMemory jvmMemory = new SystemMonitor.JvmMemory();

        Runtime runtime = Runtime.getRuntime();
        double maxMemory = runtime.max() / 1024.0 / 1024.0;
        double totalMemory = runtime.totalMemory() / 1024.0 / 1024.0;
        double freeMemory = runtime.freeMemory() / 1024.0 / 1024.0;
        double usedMemory = totalMemory - freeMemory;

        jvmMemory.setMax(maxMemory);
        jvmMemory.setTotal(totalMemory);
        jvmMemory.setFree(freeMemory);
        jvmMemory.setUsed((usedMemory / maxMemory) * 100);

        return jvmMemory;
    }

    /**
     * 获取系统信息
     */
    private SystemMonitor.SystemInfo getSystemInfo() {
        SystemMonitor.SystemInfo systemInfo = new SystemMonitor.SystemInfo();

        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();

        systemInfo.setCpuCores(osBean.getAvailableProcessors());
        systemInfo.setUptime(runtimeBean.getUptime());

        // 获取系统内存信息
        com.sun.management.OperatingSystemMXBean sunOsBean =
            (com.sun.management.OperatingSystemMXBean) osBean;
        double totalMemory = sunOsBean.getTotalPhysicalMemorySize() / 1024.0 / 1024.0;
        double freeMemory = sunOsBean.getFreePhysicalMemorySize() / 1024.0 / 1024.0;

        systemInfo.setTotalMemory(totalMemory);
        systemInfo.setFreeMemory(freeMemory);

        return systemInfo;
    }
}
