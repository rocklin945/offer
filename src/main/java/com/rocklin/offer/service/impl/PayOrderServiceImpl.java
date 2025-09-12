package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.common.utils.OrderNoGenerator;
import com.rocklin.offer.mapper.PayOrderMapper;
import com.rocklin.offer.mapper.UserMapper;
import com.rocklin.offer.mapper.WebInfoMapper;
import com.rocklin.offer.model.dto.request.PayOrderPageRequest;
import com.rocklin.offer.model.dto.request.UserUpdateRequest;
import com.rocklin.offer.model.entity.Material;
import com.rocklin.offer.model.entity.PayOrder;
import com.rocklin.offer.model.entity.User;
import com.rocklin.offer.model.entity.WebInfo;
import com.rocklin.offer.service.InviteCommissionService;
import com.rocklin.offer.service.PayOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl implements PayOrderService {

    private final PayOrderMapper payOrderMapper;
    private final UserMapper userMapper;
    private final WebInfoMapper webInfoMapper;
    private final InviteCommissionService commissionService;

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
        PayOrder payOrder = payOrderMapper.selectByOutTradeNo(outTradeNo);
        Assert.notNull(payOrder, ErrorCode.OPERATION_ERROR, "订单不存在");
        int rows = payOrderMapper.updateStatusAndTradeNo(outTradeNo, tradeNo, 1);
        try {
            BigDecimal money = payOrder.getMoney();
            WebInfo webInfo = webInfoMapper.selectWebInfo();
            if (money.compareTo(webInfo.getCurrentPrice()) == 0) {
                becomeMember(payOrder.getUserId(), 120);
            }else if (money.compareTo(webInfo.getOriginalPrice()) == 0) {
                becomeMember(payOrder.getUserId(), 365);
            }else {
                throw new Exception("金额错误");
            }
            commissionService.handleUserBecomeMember(payOrder.getUserId(), money);
        }catch (Exception e){
            e.printStackTrace();
        }
        return rows > 0;
    }

    @Override
    public PageResponse<PayOrder> listPayOrderByPageWithFilter(PayOrderPageRequest req) {
        long total = payOrderMapper.countByCondition(req);
        int offset = (req.getPageNum() - 1) * req.getPageSize();
        List<PayOrder> records = payOrderMapper.selectByCondition(req, offset);
        return new PageResponse<>(records, total, req.getPageSize(), req.getPageNum());
    }

    @Override
    public boolean deleteOrder(Long id) {
        return payOrderMapper.deleteById(id) > 0;
    }

    private void becomeMember(Long userId, int days) {
        // 添加用户会员天数
        User user = userMapper.selectById(userId);
        Assert.notNull(user, ErrorCode.NOT_FOUND, "用户不存在");

        // 计算新的会员过期时间
        LocalDateTime newExpireTime;
        if (user.getMemberExpireTime() == null || user.getMemberExpireTime().isBefore(LocalDateTime.now())) {
            // 如果用户不是会员或会员已过期，则从现在开始计算
            newExpireTime = LocalDateTime.now().plusDays(days);
        } else {
            // 如果用户仍是会员，则在现有过期时间基础上增加天数
            newExpireTime = user.getMemberExpireTime().plusDays(days);
        }

        // 更新用户会员信息
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(userId);
        userUpdateRequest.setMemberExpireTime(newExpireTime);
        userUpdateRequest.setUserRole(UserRoleEnum.VIP.getValue());
        userMapper.updateById(userUpdateRequest);
    }
}
