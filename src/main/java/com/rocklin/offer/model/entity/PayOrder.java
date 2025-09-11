package com.rocklin.offer.model.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayOrder {
    private Long id;
    private String outTradeNo;
    private String tradeNo;
    private Long userId;
    private String name;
    private BigDecimal money;
    private String type;
    private Integer status;
    private String param;
    private Date notifyTime;
    private Date createTime;
    private Date updateTime;
}
