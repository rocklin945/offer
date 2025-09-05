package com.rocklin.offer.service.impl;

import com.rocklin.offer.mapper.MembershipPlanMapper;
import com.rocklin.offer.model.entity.MembershipPlan;
import com.rocklin.offer.service.MembershipPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员套餐服务实现类
 */
@Service
@RequiredArgsConstructor
public class MembershipPlanServiceImpl implements MembershipPlanService {

    private final MembershipPlanMapper membershipPlanMapper;

    @Override
    public List<MembershipPlan> getAllActivePlans() {
        return membershipPlanMapper.selectAllActive();
    }

    @Override
    public MembershipPlan getPlanByPlanType(Integer planType) {
        return membershipPlanMapper.selectByPlanType(planType);
    }
    
    @Override
    public List<MembershipPlan> getAllPlans() {
        return membershipPlanMapper.selectAll();
    }
}