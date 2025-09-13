package com.rocklin.offer.controller;

import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.model.dto.response.WebInfoResponse;
import com.rocklin.offer.model.dto.response.WebPriceResponse;
import com.rocklin.offer.service.WebInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName WebInfoController
 * @Description 网站信息相关接口
 * @Author: rocklin
 * @Date 2025/8/24 10:49
 * @Version 1.0
 */
@Tag(name = "网站信息", description = "网站信息相关接口")
@RestController
@RequestMapping("/webInfo")
@RequiredArgsConstructor
public class WebInfoController {

    private final WebInfoService webInfoService;

    /**
     * 获取网站信息
     */
    @Operation(summary = "获取网站信息", description = "获取网站概览信息")
    @GetMapping("/get")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<WebInfoResponse> getWebInfo() {
        WebInfoResponse webInfo = webInfoService.getWebInfo();
        return BaseResponse.success(webInfo);
    }

    /**
     * 获取会员图片URL
     */
    @Operation(summary = "获取会员图片URL", description = "获取会员二维码图片URL，无需管理员权限")
    @GetMapping("/member-image")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<String> getMemberImageUrl() {
        String imageUrl = webInfoService.getMemberImageUrl();
        return BaseResponse.success(imageUrl);
    }

    /**
     * 获取价格
     */
    @Operation(summary = "获取价格", description = "获取价格，无需管理员权限")
    @GetMapping("/price")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<WebPriceResponse> getPrice() {
        WebPriceResponse price = webInfoService.getPrice();
        return BaseResponse.success(price);
    }

    /**
     * 获取佣金比例
     */
    @Operation(summary = "获取佣金比例", description = "获取佣金比例，无需管理员权限")
    @GetMapping("/commission-rate")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<String> getCommissionRate() {
        String price = webInfoService.getCommissionRate();
        return BaseResponse.success(price);
    }
}
