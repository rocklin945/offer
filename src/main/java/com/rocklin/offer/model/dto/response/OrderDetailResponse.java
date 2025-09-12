package com.rocklin.offer.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderDetailResponse {
    private String code;   // "1" 成功，"error" 失败
    private String msg;    // 返回信息
    private String status; // 支付状态（可能为空）
    private String name;
    private BigDecimal money;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("trade_no")
    private String tradeNo;

    private String type;
    private String param;

    @JsonProperty("addtime")
    private String addTime;

    @JsonProperty("endtime")
    private String endTime;

    private String pid;
    private String buyer;
}
