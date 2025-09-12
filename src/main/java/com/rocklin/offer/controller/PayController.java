package com.rocklin.offer.controller;

import cn.hutool.core.util.StrUtil;
import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.config.PayProperties;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.common.request.DeleteRequest;
import com.rocklin.offer.common.response.BaseResponse;
import com.rocklin.offer.common.response.PageResponse;
import com.rocklin.offer.common.utils.OrderParser;
import com.rocklin.offer.model.dto.request.CreateOrderRequest;
import com.rocklin.offer.model.dto.request.PayOrderPageRequest;
import com.rocklin.offer.model.dto.request.PayOrderQueryRequest;
import com.rocklin.offer.model.dto.response.OrderDetailResponse;
import com.rocklin.offer.model.entity.PayOrder;
import com.rocklin.offer.service.PayOrderService;
import com.rocklin.offer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.rocklin.offer.common.constants.Constants.*;


@Tag(name = "订单支付", description = "订单支付相关接口")
@RestController
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
    @PostMapping("/createOrder")
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public String createOrder(@RequestBody CreateOrderRequest req) {
        String strUserId = userService.getUserIdFromRequest();
        Assert.isTrue(!StrUtil.isBlank(strUserId), ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        Long userId = Long.valueOf(strUserId);

        // 1. 创建本地订单
        PayOrder order = payOrderService.createOrder(userId, req.getName(), req.getMoney(), req.getType(), req.getParam());

        log.info("用户:{}创建订单:{}", userId, order.getOutTradeNo());

        // 2. 组装参数
        Map<String, String> params = new HashMap<>();
        params.put(PID, payProperties.getPid());
        params.put(TYPE, req.getType());
        params.put(OUT_TRADE_NO, order.getOutTradeNo());
        params.put(NOTIFY_URL, payProperties.getNotifyUrl());
        params.put(RETURN_URL, payProperties.getReturnUrl());
        params.put(NAME, req.getName());
        params.put(MONEY, req.getMoney());
        if (req.getParam() != null) params.put(PARAM, req.getParam());
        if (req.getCid() != null) params.put(CID, req.getCid());

        // 3. 生成签名
        String sign = getSign(params);
        params.put(SIGN_TYPE, MD5);
        params.put(SIGN, sign);

        // 4. 返回 HTML 表单（前端访问后自动跳转 ZPAY）
        StringBuilder html = new StringBuilder();
        html.append(String.format(TAG_FORM_OPEN, FORM_NAME, payProperties.getUrl(), FORM_METHOD));
        for (Map.Entry<String, String> entry : params.entrySet()) {
            html.append(String.format(TAG_INPUT_HIDDEN, entry.getKey(), entry.getValue()));
        }
        html.append(String.format(TAG_INPUT_SUBMIT, FORM_SUBMIT_VALUE));
        html.append(TAG_FORM_CLOSE);
        html.append(String.format(TAG_SCRIPT_AUTO_SUBMIT, FORM_NAME));

        return html.toString();
    }

    /**
     * 支付平台异步回调（notify_url）
     */
    @Operation(summary = "支付平台异步回调", description = "支付平台异步回调")
    @GetMapping("/notify")
    public String notifyCallback(@RequestParam Map<String, String> params) {
        log.info("支付平台异步回调：{}", params);

        // 1. 验签
        String sign = params.get(SIGN);
        Map<String, String> verifyParams = new HashMap<>(params);
        verifyParams.remove(SIGN);
        verifyParams.remove(SIGN_TYPE);

        String localSign = getSign(verifyParams);
        if (!localSign.equalsIgnoreCase(sign)) {
            log.error("支付平台异步回调签名验证失败,平台签名:{},本地签名:{}", sign, localSign);
            return FAIL; // 签名不一致
        }

        // 2. 处理订单逻辑
        String outTradeNo = params.get(OUT_TRADE_NO);
        String tradeNo = params.get(TRADE_NO);          // 平台订单号
        String tradeStatus = params.get(TRADE_STATUS);  // "TRADE_SUCCESS" 表示成功

        if (TRADE_SUCCESS.equals(tradeStatus)) {
            boolean updated = payOrderService.markOrderPaid(outTradeNo, tradeNo);
            log.info("更新订单状态：{}", updated ? "成功" : "失败");
            return updated ? SUCCESS : FAIL;
        }
        return FAIL;
    }

    /**
     * 管理员获取订单信息
     */
    @Operation(summary = "管理员获取订单信息", description = "管理员获取订单信息")
    @PostMapping("/listOrder")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<PageResponse<PayOrder>> adminGetOrderInfo(@RequestBody @Valid PayOrderPageRequest req) {
        PageResponse<PayOrder> result = payOrderService.listPayOrderByPageWithFilter(req);
        return BaseResponse.success(result);
    }

    /**
     * 管理员查询第三方支付订单信息
     */
    @Operation(summary = "管理员查询第三方支付订单信息", description = "管理员查询第三方支付订单信息")
    @PostMapping("/getOrderDetail")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<OrderDetailResponse> adminGetOrderInfo(@RequestBody PayOrderQueryRequest req) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR, "查询参数不能为空");
        if (req.getOutTradeNo() == null && req.getTradeNo() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "out_trade_no 或 trade_no 至少一个不能为空");
        }

        // 第三方接口参数
        String pid = payProperties.getPid();
        String key = payProperties.getKey();

        // 拼接URL
        StringBuilder url = new StringBuilder(ORDER_DETAIL_URL)
                .append(URL_PARAM_START).append(PARAM_ACT)
                .append(URL_PARAM_JOIN).append(PARAM_PID).append(pid)
                .append(URL_PARAM_JOIN).append(PARAM_KEY).append(key);

        if (req.getOutTradeNo() != null) {
            url.append(URL_PARAM_JOIN).append(PARAM_OUT_TRADE_NO).append(req.getOutTradeNo());
        }
        if (req.getTradeNo() != null) {
            url.append(URL_PARAM_JOIN).append(PARAM_TRADE_NO).append(req.getTradeNo());
        }

        try {
            // 用 RestTemplate 调用
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url.toString(), String.class);

            // 解析返回 JSON
            OrderDetailResponse orderDetail = OrderParser.parse(result);

            // 返回封装后的对象
            if(ONE_STRING.equals(orderDetail.getCode())){
                return BaseResponse.success(orderDetail);
            }else{
                return BaseResponse.error(666501, orderDetail.getMsg());
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "调用支付平台失败: " + e.getMessage());
        }
    }

    /**
     * 管理员删除订单信息
     */
    @Operation(summary = "管理员删除订单信息", description = "管理员删除订单信息")
    @PostMapping("/deleteOrder")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 5, maxCount = 10)
    public BaseResponse<Boolean> deleteOrder(@RequestBody DeleteRequest req) {
        Assert.notNull(req, ErrorCode.PARAMS_ERROR, "查询参数不能为空");
        boolean result = payOrderService.deleteOrder(req.getId());
        return BaseResponse.success(result);
    }

    /**
     * 生成签名（ZPAY 规则：按 ASCII 升序拼接 + key，MD5 小写）
     */
    private String getSign(Map<String, String> params) {
        params = sortByKey(params);
        String signStr = params.entrySet().stream()
                .filter(e -> e.getValue() != null && !e.getValue().isEmpty())
                .map(e -> e.getKey() + EQUAL + e.getValue())
                .reduce((a, b) -> a + AMPERSAND + b)
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
