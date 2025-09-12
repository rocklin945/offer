package com.rocklin.offer.model.dto.request;

import lombok.Data;

@Data
public class CreateOrderRequest {
    // 支付方式,默认支付宝
    private String type = "alipay";
    // 金额
    private String money;
    // 商品名,默认VIP
    private String name = "MyOffer VIP";
    // 附加参数，可选
    private String param;
    // 支付渠道，可选
    private String cid;
}
