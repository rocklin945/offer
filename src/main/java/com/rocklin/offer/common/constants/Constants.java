package com.rocklin.offer.common.constants;

/**
 * @ClassName Constants
 * @Description 常量类
 * @Author: rocklin
 * @Date 2025/8/19 19:55
 * @Version 1.0
 */
public final class Constants {
    public static final String SUCCESS = "success";

    /**
     * JWT
     */
    public static final String USERACCOUNT = "userAccount";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String USER_ID = "userId";
    public static final int TOKEN_START_INDEX = 7;

    public static final String USER_PREFIX ="用户_";

    /**
     * 滑动窗口限流
     */
    public static final String SLIDING_RATE_LIMIT_KEY_PREFIX ="sliding_rate_limit:";
    public static final String COLON =":";
    public static final long ONE_SECOND =1000L;
    public static final int ZERO =0;
    public static final String X_FORWARDED_FOR ="X-Forwarded-For";
    public static final String COMMA =",";

    /**
     * 会员鉴权
     */
    public static final String PAGE_NUM ="pageNum";
    public static final String PAGE_SIZE ="pageSize";
    public static final String GET ="GET";
    public static final String POST ="POST";

    /**
     * ip
     */
    public static final String UNKNOWN ="unknown";
    public static final String X_REAL_IP ="X-Real-IP";
    public static final String PROXY_CLIENT_IP ="Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP ="WL-Proxy-Client-IP";

    /**
     * 数量
     */
    public static final int ONE = 1;
    public static final double ZERO_POINT_ONE =0.1;
    public static final int TWO =2;
}
