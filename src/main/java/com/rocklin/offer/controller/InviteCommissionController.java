package com.rocklin.offer.controller;

import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.model.entity.InviteCommission;
import com.rocklin.offer.service.InviteCommissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}