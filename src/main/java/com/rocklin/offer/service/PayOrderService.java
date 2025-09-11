package com.rocklin.offer.service;

import com.rocklin.offer.model.entity.PayOrder;

public interface PayOrderService {

    /**
     * 创建订单
     */
    PayOrder createOrder(Long userId, String name, String money, String type, String param);

    /**
     * 支付成功回调
     */
    boolean markOrderPaid(String outTradeNo, String tradeNo);

    /**
     * 根据订单号查询
     */
    PayOrder getOrderByOutTradeNo(String outTradeNo);
}
