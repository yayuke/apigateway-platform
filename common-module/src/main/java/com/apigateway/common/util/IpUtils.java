package com.apigateway.common.util;

import cn.hutool.core.net.NetUtil;
import javax.servlet.http.HttpServletRequest;

/**
 * IP工具类
 * 提供IP相关的常用操作
 *
 * @author apigateway
 * @since 1.0.0
 */
public class IpUtils {

    private static final String UNKNOWN = "unknown";
    private static final String LOCALHOST_IP = "127.0.0.1";
    private static final String LOCALHOST_IP_16 = "0:0:0:0:0:0:0:1";
    private static final int IP_LEN = 15;

    /**
     * 获取客户端IP地址
     *
     * @param request 请求
     * @return IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 对于通过多个代理的情况，第一个IP为客户端真实IP
        if (ip != null && ip.length() > IP_LEN) {
            int index = ip.indexOf(",");
            if (index > 0) {
                ip = ip.substring(0, index);
            }
        }

        if (LOCALHOST_IP_16.equals(ip)) {
            ip = LOCALHOST_IP;
        }

        return ip;
    }

    /**
     * 判断是否为内网IP
     *
     * @param ip IP地址
     * @return 是否为内网IP
     */
    public static boolean isInnerIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        return NetUtil.isInnerIP(ip);
    }

    /**
     * 验证IP地址格式
     *
     * @param ip IP地址
     * @return 是否有效
     */
    public static boolean isValidIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        return NetUtil.isIpv4(ip);
    }
}
