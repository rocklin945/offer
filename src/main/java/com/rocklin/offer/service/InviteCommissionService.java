package com.rocklin.offer.service;

import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.request.InviteCommissionPageQueryRequest;
import com.rocklin.offer.model.entity.InviteCommission;

import java.math.BigDecimal;

/**
 * 邀请佣金服务
 */
public interface InviteCommissionService {
    /**
     * 处理用户邀请
     */
    void handleUserInvitation(Long inviterId, Long inviteeId);

    /**
     * 处理用户成为会员
     */
    void handleUserBecomeMember(Long userId);

    /**
     * 根据用户ID查询佣金记录
     */
    InviteCommission getByUserId(Long userId);

    /**
     * 管理员接口：分页查询佣金记录
     */
    PageResponse<InviteCommission> listCommissionByPage(InviteCommissionPageQueryRequest request);

    /**
     * 管理员接口：确认佣金
     */
    void confirmCommission(Long id, BigDecimal amount);

    /**
     * 管理员接口：拒绝佣金
     */
    void rejectCommission(Long id, BigDecimal amount);

    /**
     * 兑换会员
     */
    void redeemMember(Long userId, Integer planType);

    /**
     * 提现
     */
    void withdraw(Long id, BigDecimal amount);
}