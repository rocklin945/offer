package com.rocklin.offer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.model.dto.request.CodePurchaseRequest;
import com.rocklin.offer.model.dto.request.CodeRedeemRequest;
import com.rocklin.offer.model.dto.response.CodePriceResponse;
import com.rocklin.offer.model.entity.Code;
import com.rocklin.offer.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.rocklin.offer.common.constants.Constants.PARAM;

/**
 * @ClassName CodeController
 * @Description 卡密相关接口
 * @Author: rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Tag(name = "卡密", description = "卡密相关接口")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {

    private final CodeService codeService;

    /**
     * 卡密兑换
     */
    @Operation(summary = "卡密兑换", description = "用户使用卡密兑换会员权益")
    @PostMapping("/redeem")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<String> redeemCode(@RequestBody @Valid CodeRedeemRequest req) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR, "参数为空");
        codeService.redeemCode(req);
        return BaseResponse.success();
    }

    /**
     * 根据商家账号获取卡密商品价格
     */
    @Operation(summary = "获取卡密商品价格", description = "根据商家账号获取卡密商品价格")
    @GetMapping("/price")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<CodePriceResponse> getCodePrice(@RequestParam("account") String account,
                                                                       @RequestParam("password") String password) {
        CodePriceResponse response = codeService.getCodePrice(account, password);
        return BaseResponse.success(response);
    }

    /**
     * 购买卡密
     */
    @Operation(summary = "购买卡密", description = "商家购买卡密商品")
    @PostMapping("/purchase")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<String> purchaseCode(@RequestBody @Valid CodePurchaseRequest req,
                                                           @RequestParam("account") String account,
                                                           @RequestParam("password") String password) {
        String html = codeService.purchaseCode(req, account, password);
        return BaseResponse.success(html);
    }

    /**
     * 卡密返回接口
     */
    @Operation(summary = "卡密返回接口", description = "卡密返回接口")
    @GetMapping("/return")
    public List<String> returnCode(@RequestParam Map<String, String> params) throws JsonProcessingException {
        log.info("returnCode params: {}", params);
        String codesJson = params.get(PARAM);
        if (codesJson == null) {
            return Collections.emptyList();
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(codesJson, new TypeReference<List<String>>() {});
    }

    /**
     * 商家获取卡密列表
     */
    @Operation(summary = "获取卡密列表", description = "商家获取卡密列表")
    @GetMapping("/list")
    public BaseResponse<List<Code>> listCode(@RequestParam("account") String account,
                                             @RequestParam("password") String password) {
        List<Code> codes = codeService.listCode(account, password);
        return BaseResponse.success(codes);
    }
}