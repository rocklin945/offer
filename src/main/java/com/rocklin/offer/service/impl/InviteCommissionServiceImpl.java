package com.rocklin.offer.service.impl;

import com.rocklin.offer.mapper.InviteCommissionMapper;
import com.rocklin.offer.mapper.UserMapper;
import com.rocklin.offer.model.entity.InviteCommission;
import com.rocklin.offer.model.entity.User;
import com.rocklin.offer.service.InviteCommissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.rocklin.offer.common.constants.Constants.ONE;
import static com.rocklin.offer.common.constants.Constants.USER_PREFIX;

@Service
@Slf4j
@RequiredArgsConstructor
public class InviteCommissionServiceImpl implements InviteCommissionService {

    private final InviteCommissionMapper commissionMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void handleUserInvitation(Long inviterId, Long inviteeId) {
        // 检查邀请人是否已有佣金记录
        InviteCommission existing = commissionMapper.selectByUserId(inviterId);
        
        if (existing == null) {
            // 创建新的佣金记录
            InviteCommission commission = new InviteCommission();
            commission.setUserId(inviterId);
            commission.setInvitedCount(ONE);
            commission.setTotalCommission(BigDecimal.ZERO);
            commission.setPendingCommission(BigDecimal.ZERO);
            commission.setBalanceCommission(BigDecimal.ZERO);
            commission.setCreateTime(LocalDateTime.now());
            commission.setUpdateTime(LocalDateTime.now());
            commissionMapper.insert(commission);
        } else {
            // 增加邀请用户数
            commissionMapper.increaseInvitedCount(inviterId);
        }
    }

    @Override
    @Transactional
    public void handleUserBecomeMember(Long userId) {
        // 查找用户的邀请人
        User user = userMapper.selectById(userId);
        if (user == null || user.getInviterCode() == null) {
            log.info("用户没有邀请人或用户不存在: userId={}", userId);
            return;
        }

        // 根据邀请码查找邀请人
        User inviter = userMapper.selectByInviteCode(USER_PREFIX + user.getInviterCode());
        if (inviter == null) {
            log.info("邀请人不存在: inviteCode={}", user.getInviterCode());
            return;
        }

        // 增加待结算佣金
        commissionMapper.increasePendingCommission(inviter.getId(), new BigDecimal("0.99"));
        log.info("增加待结算佣金: userId={}, amount=0.99", inviter.getId());
    }

    @Override
    public InviteCommission getByUserId(Long userId) {
        return commissionMapper.selectByUserId(userId);
    }
}