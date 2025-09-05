package com.rocklin.offer.mapper;

import com.rocklin.offer.model.entity.MembershipPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员套餐Mapper
 */
@Mapper
public interface MembershipPlanMapper {
    /**
     * 根据ID查询套餐
     */
    MembershipPlan selectById(@Param("id") Long id);

    /**
     * 查询所有上架套餐
     */
    List<MembershipPlan> selectAllActive();

    /**
     * 根据套餐类型查询套餐(1:周卡, 2:月卡, 3:季卡, 4:年卡)
     */
    MembershipPlan selectByPlanType(@Param("planType") Integer planType);
}