package com.rocklin.offer.model.dto.request;

import com.rocklin.offer.common.request.PageRequest;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName PayOrderPageRequest
 * @Description 支付订单分页查询参数
 * @Author: rocklin
 * @Date 2025/9/12 10:32
 * @Version 1.0
 */
@Data
public class PayOrderPageRequest extends PageRequest {
    private String id; // 改为String类型以避免大数字精度丢失
    private String outTradeNo;
    private String tradeNo;
    private Long userId;
    private BigDecimal money;
    private Integer status;
}