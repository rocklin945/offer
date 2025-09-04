package com.rocklin.offer.service;

import com.rocklin.offer.model.entity.InviteCommission;

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
}