package com.rocklin.offer.service;

import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.request.PayOrderPageRequest;
import com.rocklin.offer.model.entity.PayOrder;

public interface PayOrderService {

    /**
     * 创建订单
     */
    PayOrder createOrder(Boolean isUser, Long userId, String name, String money, String type, String param);

    /**
     * 支付成功回调
     */
    boolean markOrderPaid(String outTradeNo, String tradeNo);

    PageResponse<PayOrder> listPayOrderByPageWithFilter(PayOrderPageRequest req);

    boolean deleteOrder(Long id);
}
