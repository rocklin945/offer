package com.rocklin.offer.model.dto.response;

import lombok.Data;

/**
 * @ClassName CountResponse
 * @Description 统计响应
 * @Author: rocklin
 * @Date 2025/9/18 17:24
 * @Version 1.0
 */
@Data
public class CountResponse {
    /**
     * 26 届校招结束时间（时间戳，毫秒）
     * 前端倒计时组件可通过 (endTime - 当前系统时间) 计算剩余时长。
     * 例如：Vue/React 倒计时组件可直接传入 (endTime - Date.now())。
     */
    private Long endTime;

    /**
     * 26 届校招累计新开数量
     */
    private Long totalOpenCount;

    /**
     * 本月新开校招数量
     */
    private Long monthOpenCount;

    /**
     * 近三天新开校招数量
     */
    private Long lastThreeDaysOpenCount;

    /**
     * 内推企业总量
     */
    private Long referralCompanyCount;
}
