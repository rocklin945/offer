package com.rocklin.offer.controller;

import cn.hutool.core.util.StrUtil;
import com.rocklin.offer.common.config.PayProperties;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.model.entity.PayOrder;
import com.rocklin.offer.service.PayOrderService;
import com.rocklin.offer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Tag(name = "支付接口", description = "支付接口相关接口")
@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/pay")
public class PayController {

    private final PayProperties payProperties;
    private final PayOrderService payOrderService;
    private final UserService userService;

    /**
     * 创建支付订单（表单跳转）
     */
    @Operation(summary = "创建支付订单", description = "创建支付订单")
    @GetMapping("/createOrder")
    @ResponseBody
    public String createOrder(
            @RequestParam String type,        // 支付方式 (alipay / wxpay)
            @RequestParam String money,       // 金额
            @RequestParam String name,        // 商品名
            @RequestParam(required = false) String param, // 附加参数，可选
            @RequestParam(required = false) String cid    // 支付渠道，可选
    ) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        Long userId = Long.valueOf(strUserId);
        // 1. 创建本地订单
        PayOrder order = payOrderService.createOrder(userId, name, money, type, param);

        // 2. 组装参数
        Map<String, String> params = new HashMap<>();
        params.put("pid", payProperties.getPid());
        params.put("type", type);
        params.put("out_trade_no", order.getOutTradeNo());
        params.put("notify_url", payProperties.getNotifyUrl());
        params.put("return_url", payProperties.getReturnUrl());
        params.put("name", name);
        params.put("money", money);
        if (param != null) params.put("param", param);
        if (cid != null) params.put("cid", cid);

        // 3. 生成签名
        String sign = getSign(params);
        params.put("sign_type", "MD5");
        params.put("sign", sign);

        // 4. 返回 HTML 表单（前端访问后自动跳转 ZPAY）
        StringBuilder html = new StringBuilder();
        html.append("<form id='paying' action='").append(payProperties.getUrl()).append("' method='post'>");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            html.append("<input type='hidden' name='")
                    .append(entry.getKey())
                    .append("' value='")
                    .append(entry.getValue())
                    .append("'/>");
        }
        html.append("<input type='submit' value='正在跳转...'>");
        html.append("</form>");
        html.append("<script>document.forms['paying'].submit();</script>");

        return html.toString();
    }

    /**
     * 支付平台异步回调（notify_url）
     */
    @Operation(summary = "支付平台异步回调", description = "支付平台异步回调")
    @PostMapping("/notify")
    @ResponseBody
    public String notifyCallback(@RequestParam Map<String, String> params) {
        // 1. 验签
        String sign = params.get("sign");
        Map<String, String> verifyParams = new HashMap<>(params);
        verifyParams.remove("sign");
        verifyParams.remove("sign_type");

        String localSign = getSign(verifyParams);
        if (!localSign.equalsIgnoreCase(sign)) {
            return "fail"; // 签名不一致
        }

        // 2. 处理订单逻辑
        String outTradeNo = params.get("out_trade_no");
        String tradeNo = params.get("trade_no");       // 平台订单号
        String tradeStatus = params.get("trade_status"); // "TRADE_SUCCESS" 表示成功

        if ("TRADE_SUCCESS".equals(tradeStatus)) {
            boolean updated = payOrderService.markOrderPaid(outTradeNo, tradeNo);
            if (updated) {
                return "success";
            } else {
                return "fail"; // 更新失败（可能订单不存在）
            }
        }
        return "fail";
    }

    /**
     * 支付完成后的同步跳转页面（return_url）
     */
    @Operation(summary = "支付完成后的同步跳转页面", description = "支付完成后的同步跳转页面")
    @GetMapping("/return")
    @ResponseBody
    public BaseResponse<String> returnCallback(@RequestParam Map<String, String> params) {
        // 仅展示给用户，业务处理仍以 notify 为准
        log.info("支付完成，订单号：{}", params.get("out_trade_no"));
        return BaseResponse.success("支付成功");
    }

    /**
     * 生成签名（ZPAY 规则：按 ASCII 升序拼接 + key，MD5 小写）
     */
    private String getSign(Map<String, String> params) {
        params = sortByKey(params);
        String signStr = params.entrySet().stream()
                .filter(e -> e.getValue() != null && !e.getValue().isEmpty())
                .map(e -> e.getKey() + "=" + e.getValue())
                .reduce((a, b) -> a + "&" + b)
                .orElse("");
        signStr += payProperties.getKey();

        return DigestUtils.md5DigestAsHex(signStr.getBytes(StandardCharsets.UTF_8));
    }

    // 工具方法：按 key 升序排序
    private static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }
}
