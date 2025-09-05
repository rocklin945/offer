package com.rocklin.offer.controller;

import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.model.entity.MembershipPlan;
import com.rocklin.offer.service.impl.MembershipPlanServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会员套餐控制器
 */
@Tag(name = "会员套餐", description = "会员套餐相关接口")
@RestController
@RequestMapping("/membership/plan")
@RequiredArgsConstructor
public class MembershipPlanController {

    private final MembershipPlanServiceImpl membershipPlanService;

    /**
     * 获取所有上架的会员套餐
     */
    @Operation(summary = "获取所有上架的会员套餐", description = "获取所有上架的会员套餐")
    @GetMapping("/list")
    @SlidingWindowRateLimit(windowInSeconds = 60, maxCount = 50)
    public BaseResponse<List<MembershipPlan>> listAllActivePlans() {
        List<MembershipPlan> plans = membershipPlanService.getAllActivePlans();
        return BaseResponse.success(plans);
    }
}