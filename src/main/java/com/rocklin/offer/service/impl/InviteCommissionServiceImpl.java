package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.mapper.InviteCommissionMapper;
import com.rocklin.offer.mapper.UserMapper;
import com.rocklin.offer.mapper.WebInfoMapper;
import com.rocklin.offer.mapper.MembershipPlanMapper;
import com.rocklin.offer.model.dto.request.InviteCommissionPageQueryRequest;
import com.rocklin.offer.model.dto.request.UserUpdateRequest;
import com.rocklin.offer.model.entity.InviteCommission;
import com.rocklin.offer.model.entity.MembershipPlan;
import com.rocklin.offer.model.entity.User;
import com.rocklin.offer.model.entity.WebInfo;
import com.rocklin.offer.service.InviteCommissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static com.rocklin.offer.common.constants.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class InviteCommissionServiceImpl implements InviteCommissionService {

    private final InviteCommissionMapper commissionMapper;
    private final UserMapper userMapper;
    private final WebInfoMapper webInfoMapper;
    private final MembershipPlanMapper membershipPlanMapper;

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
    public PageResponse<InviteCommission> listCommissionByPage(InviteCommissionPageQueryRequest request) {
        // 计算偏移量
        int offset = (request.getPageNum() - 1) * request.getPageSize();
        // 查询数据
        List<InviteCommission> commissions = commissionMapper.selectCommissionPage(request, offset,
                request.getPageSize());
        // 查询总数
        long total = commissionMapper.selectCommissionCount(request);
        return new PageResponse<>(commissions, total, request.getPageNum(), request.getPageSize());
    }

    @Override
    @Transactional
    public void confirmCommission(Long id, BigDecimal amount) {
        // 查询佣金记录
        InviteCommission commission = commissionMapper.selectById(id);
        Assert.notNull(commission, ErrorCode.NOT_FOUND, "佣金记录不存在");
        // 更新状态
        commissionMapper.confirmCommission(id, amount);
        log.info("确认佣金: userId={}, amount={}", commission.getUserId(), amount);
    }

    @Override
    @Transactional
    public void rejectCommission(Long id, BigDecimal amount) {
        // 查询佣金记录
        InviteCommission commission = commissionMapper.selectById(id);
        Assert.notNull(commission, ErrorCode.NOT_FOUND, "佣金记录不存在");
        // 拒绝佣金
        commissionMapper.rejectCommission(id, amount);
        log.info("拒绝佣金: userId={}", commission.getUserId());
    }

    @Override
    public void redeemMember(Long userId, Integer planType) {
        // 获取用户余额信息
        InviteCommission inviteCommission = commissionMapper.selectByUserId(userId);
        Assert.notNull(inviteCommission, ErrorCode.NOT_FOUND, "用户没有佣金记录");
        BigDecimal balanceCommission = inviteCommission.getBalanceCommission();
        Assert.isTrue(balanceCommission.compareTo(BigDecimal.ZERO) > 0,
                ErrorCode.OPERATION_ERROR, "用户没有余额可兑换");

        // 根据planType获取套餐信息
        MembershipPlan plan = membershipPlanMapper.selectByPlanType(planType);
        Assert.notNull(plan, ErrorCode.NOT_FOUND, "套餐不存在");

        // 检查余额是否足够兑换该套餐
        BigDecimal amount = plan.getPrice();
        Assert.isTrue(amount.compareTo(balanceCommission) <= 0,
                ErrorCode.OPERATION_ERROR, "兑换金额大于余额");

        // 扣除用户余额
        inviteCommission.setBalanceCommission(balanceCommission.subtract(amount));
        commissionMapper.update(inviteCommission);

        // 添加用户会员天数
        User user = userMapper.selectById(userId);
        Assert.notNull(user, ErrorCode.NOT_FOUND, "用户不存在");

        // 计算新的会员过期时间
        LocalDateTime newExpireTime;
        if (user.getMemberExpireTime() == null || user.getMemberExpireTime().isBefore(LocalDateTime.now())) {
            // 如果用户不是会员或会员已过期，则从现在开始计算
            newExpireTime = LocalDateTime.now().plusDays(plan.getDays());
        } else {
            // 如果用户仍是会员，则在现有过期时间基础上增加天数
            newExpireTime = user.getMemberExpireTime().plusDays(plan.getDays());
        }

        // 更新用户会员信息
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(userId);
        userUpdateRequest.setMemberExpireTime(newExpireTime);
        userUpdateRequest.setUserRole(UserRoleEnum.VIP.getValue());
        userMapper.updateById(userUpdateRequest);

        log.info("用户{}成功兑换{}套餐，增加{}天会员，余额剩余：{}",
                userId, plan.getLabel(), plan.getDays(), inviteCommission.getBalanceCommission().subtract(amount));
    }

    @Override
    @Transactional
    public void handleUserBecomeMember(Long userId) {
        // 查找用户的邀请人
        User inviter = getUserAndInviter(userId);
        if (inviter == null) return;

        // 增加待结算佣金
        WebInfo webInfo = webInfoMapper.selectWebInfo();
        String commission = String.valueOf(webInfo.getCurrentPrice().multiply(BigDecimal.valueOf(ZERO_POINT_TWO)));
        increasePendingCommission(commission, inviter);
    }

    private void increasePendingCommission(String commission, User inviter) {
        BigDecimal amount = new BigDecimal(commission).setScale(TWO, RoundingMode.HALF_UP);
        commissionMapper.increasePendingCommission(inviter.getId(), amount);
        log.info("增加待结算佣金: userId={}, amount={}", inviter.getId(), amount);
    }

    @Override
    @Transactional
    public void handleUserBecomeMember(Long userId,BigDecimal price) {
        User inviter = getUserAndInviter(userId);
        if (inviter == null) return;

        // 增加待结算佣金
        String commission = String.valueOf(price.multiply(BigDecimal.valueOf(ZERO_POINT_TWO)));
        increasePendingCommission(commission, inviter);
    }

    private User getUserAndInviter(Long userId) {
        // 查找用户的邀请人
        User user = userMapper.selectById(userId);
        if (user == null || user.getInviterCode() == null) {
            log.info("用户没有邀请人或用户不存在: userId={}", userId);
            return null;
        }

        // 根据邀请码查找邀请人
        User inviter = userMapper.selectByInviteCode(USER_PREFIX + user.getInviterCode());
        if (inviter == null) {
            log.info("邀请人不存在: inviteCode={}", user.getInviterCode());
            return null;
        }
        return inviter;
    }

    @Override
    public InviteCommission getByUserId(Long userId) {
        return commissionMapper.selectByUserId(userId);
    }

    @Override
    @Transactional
    public void withdraw(Long id, BigDecimal amount) {
        // 查询佣金记录
        InviteCommission commission = commissionMapper.selectById(id);
        Assert.notNull(commission, ErrorCode.NOT_FOUND, "佣金记录不存在");

        // 检查余额是否足够
        BigDecimal balanceCommission = commission.getBalanceCommission();
        Assert.isTrue(balanceCommission.compareTo(BigDecimal.ZERO) > 0,
                ErrorCode.OPERATION_ERROR, "用户没有余额可提现");
        Assert.isTrue(amount.compareTo(balanceCommission) <= 0,
                ErrorCode.OPERATION_ERROR, "提现金额大于余额");
        Assert.isTrue(amount.compareTo(BigDecimal.ZERO) > 0,
                ErrorCode.OPERATION_ERROR, "提现金额必须大于0");

        // 扣除用户余额
        commission.setBalanceCommission(balanceCommission.subtract(amount));
        commissionMapper.update(commission);

        log.info("用户{}成功提现{}，余额剩余：{}",
                commission.getUserId(), amount, commission.getBalanceCommission());
    }
}