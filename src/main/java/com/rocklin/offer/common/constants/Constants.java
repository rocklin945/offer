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
    public static final double ZERO_POINT_ONE = 0.1;
    public static final double ZERO_POINT_TWO = 0.2;
    public static final int TWO =2;

    /**
     * pdf
     */
    public static final String PDF_PAGE_PREFIX ="page_";
    public static final String PDF_PAGE_SUFFIX =".jpg";
    public static final String JPG_SUFFIX ="jpg";
    public static final String CONTENT_DISPOSITION ="inline; filename=";
    public static final String PDF_SUFFIX =".pdf";
    public static final String FILE ="file";
    public static final String ORIGIN_PDF ="origin.pdf";
    public static final String USER_DIR ="user.dir";
    public static final String DOT =".";

    /**
     * 支付相关常量
     */
    // 支付参数
    public static final String PID = "pid";
    public static final String TYPE = "type";
    public static final String OUT_TRADE_NO = "out_trade_no";
    public static final String NOTIFY_URL = "notify_url";
    public static final String RETURN_URL = "return_url";
    public static final String NAME = "name";
    public static final String MONEY = "money";
    public static final String PARAM = "param";
    public static final String CID = "cid";
    public static final String SIGN = "sign";
    public static final String SIGN_TYPE = "sign_type";
    public static final String TRADE_NO = "trade_no";
    public static final String TRADE_STATUS = "trade_status";

    // 固定值
    public static final String MD5 = "MD5";
    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String FAIL = "fail";

    // HTML 表单
    public static final String FORM_NAME = "paying";
    public static final String FORM_METHOD = "post";
    public static final String FORM_SUBMIT_VALUE = "正在跳转...";

    // HTML 标签模板
    public static final String TAG_FORM_OPEN = "<form id='%s' action='%s' method='%s'>";
    public static final String TAG_FORM_CLOSE = "</form>";
    public static final String TAG_INPUT_HIDDEN = "<input type='hidden' name='%s' value='%s'/>";
    public static final String TAG_INPUT_SUBMIT = "<input type='submit' value='%s'>";
    public static final String TAG_SCRIPT_AUTO_SUBMIT = "<script>document.forms['%s'].submit();</script>";

    // 符号
    public static final String AMPERSAND = "&";
    public static final String EQUAL = "=";
}
