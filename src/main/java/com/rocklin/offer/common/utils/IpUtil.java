package com.rocklin.offer.common.utils;

import jakarta.servlet.http.HttpServletRequest;

import static com.rocklin.offer.common.constants.Constants.*;

public class IpUtil {

    /**
     * 获取客户端真实 IP
     */
    public static String getIp(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN;
        }

        String ip = request.getHeader(X_FORWARDED_FOR);
        if (ip != null && ip.length() != 0 && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多个 IP 用逗号分隔，第一个才是真实 IP
            if (ip.contains(COMMA)) {
                ip = ip.split(COMMA)[0].trim();
            }
            return ip;
        }

        ip = request.getHeader(X_REAL_IP);
        if (isEmptyOrUnknown(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (isEmptyOrUnknown(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (isEmptyOrUnknown(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static boolean isEmptyOrUnknown(String ip) {
        return ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip);
    }
}
