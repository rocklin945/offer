package com.rocklin.offer.model.dto.request;

import lombok.Data;

/**
 * @ClassName PayOrderQueryRequest
 * @Description TODO
 * @Author: rocklin
 * @Date 2025/9/12 10:33
 * @Version 1.0
 */
@Data
public class PayOrderQueryRequest {
    private String outTradeNo;
    private String tradeNo;
}
