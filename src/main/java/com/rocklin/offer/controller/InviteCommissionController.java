package com.rocklin.offer.controller;

import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.model.dto.request.InviteCommissionPageQueryRequest;
import com.rocklin.offer.model.entity.InviteCommission;
import com.rocklin.offer.service.InviteCommissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * 邀请佣金控制器
 */
@Tag(name = "邀请返利信息", description = "邀请返利信息相关接口")
@RestController
@RequestMapping("/invite/commission")
@RequiredArgsConstructor
public class InviteCommissionController {

    private final InviteCommissionService inviteCommissionService;

    /**
     * 根据用户ID查询佣金记录
     */
    @Operation(summary = "根据用户ID查询佣金记录", description = "根据用户ID查询佣金记录")
    @GetMapping("/getByUserId")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<InviteCommission> getByUserId(@RequestParam Long userId) {
        InviteCommission commission = inviteCommissionService.getByUserId(userId);
        return BaseResponse.success(commission);
    }

    @PostMapping("/page")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @Operation(summary = "分页查询邀请佣金列表", description = "分页查询邀请佣金列表")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<PageResponse<InviteCommission>> listCommissionByPage(@RequestBody @Validated InviteCommissionPageQueryRequest request) {
        PageResponse<InviteCommission> response = inviteCommissionService.listCommissionByPage(request);
        return BaseResponse.success(response);
    }

    @PostMapping("/confirm/{id}")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @Operation(summary = "确认佣金", description = "确认佣金")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Void> confirmCommission(@PathVariable Long id, @RequestParam BigDecimal amount) {
        inviteCommissionService.confirmCommission(id, amount);
        return BaseResponse.success();
    }

    @PostMapping("/reject/{id}")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @Operation(summary = "拒绝佣金", description = "拒绝佣金")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Void> rejectCommission(@PathVariable Long id) {
        inviteCommissionService.rejectCommission(id);
        return BaseResponse.success();
    }

    @PostMapping("/redeemMember")
    @Operation(summary = "兑换会员", description = "兑换会员")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 3)
    public BaseResponse<Void> redeemMember(@RequestParam Long userId, @RequestParam Integer planType) {
        inviteCommissionService.redeemMember(userId, planType);
        return BaseResponse.success();
    }
}