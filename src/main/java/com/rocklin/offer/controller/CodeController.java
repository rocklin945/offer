package com.rocklin.offer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.model.dto.request.CodePurchaseRequest;
import com.rocklin.offer.model.dto.request.CodeRedeemRequest;
import com.rocklin.offer.model.dto.response.CodePriceResponse;
import com.rocklin.offer.model.entity.Code;
import com.rocklin.offer.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.rocklin.offer.common.constants.Constants.*;

/**
 * @ClassName CodeController
 * @Description 卡密相关接口
 * @Author: rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Tag(name = "卡密", description = "卡密相关接口")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/code")
public class CodeController {

    private final CodeService codeService;

    /**
     * 卡密兑换
     */
    @Operation(summary = "卡密兑换", description = "用户兑换卡密")
    @PostMapping("/redeem")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<Void> redeemCode(@RequestBody @Valid CodeRedeemRequest req) {
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
     * 支付平台异步回调（notify_url）
     */
    @Operation(summary = "支付平台异步回调", description = "支付平台异步回调")
    @GetMapping("/notify")
    public String notifyCallback(@RequestParam Map<String, String> params) {
        log.info("支付平台异步回调：{}", params);
        boolean result = codeService.markOrderPaid(params);
        if (result) {
            return SUCCESS;
        }
        return FAIL;
    }

    /**
     * 卡密返回接口
     */
    @Operation(summary = "卡密返回接口", description = "卡密返回接口")
    @GetMapping("/return")
    public String returnCode(@RequestParam Map<String, String> params, HttpServletResponse response)
            throws IOException {
        log.info("returnCode params: {}", params);
        String codes = params.get(PARAM);
        if (codes == null || codes.isBlank()) {
            // 如果没有卡密数据，重定向到卡密购买页面
            response.sendRedirect(CODE_PURCHASE_URL);
            return null;
        }

        // 去掉前后方括号
        codes = codes.trim();
        if (codes.startsWith(LEFT_BRACKET) && codes.endsWith(RIGHT_BRACKET)) {
            codes = codes.substring(1, codes.length() - 1);
        }

        // 按逗号分割，并去掉多余空格
        List<String> codeList = Arrays.stream(codes.split(COMMA))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        // 将卡密列表转换为JSON字符串
        ObjectMapper mapper = new ObjectMapper();
        String codesJson = mapper.writeValueAsString(codeList);

        // URL编码
        String encodedCodes = URLEncoder.encode(codesJson, StandardCharsets.UTF_8);

        // 重定向到卡密购买页面，并传递卡密数据
        response.sendRedirect(CODE_PURCHASE_URL_PREFIX + encodedCodes);
        return null;
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