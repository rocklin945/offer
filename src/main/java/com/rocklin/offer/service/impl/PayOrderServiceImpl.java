package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.utils.OrderNoGenerator;
import com.rocklin.offer.mapper.PayOrderMapper;
import com.rocklin.offer.mapper.UserMapper;
import com.rocklin.offer.model.entity.PayOrder;
import com.rocklin.offer.model.entity.User;
import com.rocklin.offer.service.PayOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl implements PayOrderService {

    private final PayOrderMapper payOrderMapper;
    private final UserMapper userMapper;

    @Override
    public PayOrder createOrder(Long userId, String name, String money, String type, String param) {
        User user = userMapper.selectById(userId);
        Assert.notNull(user, ErrorCode.OPERATION_ERROR, "用户不存在");

        PayOrder order = new PayOrder();
        order.setOutTradeNo(OrderNoGenerator.generate());
        order.setUserId(userId);
        order.setName(name);
        order.setMoney(new BigDecimal(money));
        order.setType(type);
        order.setStatus(0); // 未支付
        order.setParam(param);

        payOrderMapper.insert(order);
        return order;
    }

    @Override
    public boolean markOrderPaid(String outTradeNo, String tradeNo) {
        int rows = payOrderMapper.updateStatusAndTradeNo(outTradeNo, tradeNo, 1);
        return rows > 0;
    }

    @Override
    public PayOrder getOrderByOutTradeNo(String outTradeNo) {
        return payOrderMapper.selectByOutTradeNo(outTradeNo);
    }
}
