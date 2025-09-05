package com.rocklin.offer.service;

import com.rocklin.offer.model.entity.MembershipPlan;

import java.util.List;

/**
 * 会员套餐服务接口
 */
public interface MembershipPlanService {
    /**
     * 获取所有上架的会员套餐
     */
    List<MembershipPlan> getAllActivePlans();

    /**
     * 根据套餐类型查询套餐(1:周卡, 2:月卡, 3:季卡, 4:年卡)
     */
    MembershipPlan getPlanByPlanType(Integer planType);

    /**
     * 获取所有套餐
     */
    List<MembershipPlan> getAllPlans();
}