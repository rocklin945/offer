package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.config.PayProperties;
import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.common.exception.BusinessException;
import com.rocklin.offer.common.utils.CardKeyGenerator;
import com.rocklin.offer.common.utils.EncryptPasswordUtil;
import com.rocklin.offer.common.utils.SignUtil;
import com.rocklin.offer.mapper.CodeMapper;
import com.rocklin.offer.mapper.MerchantMapper;
import com.rocklin.offer.mapper.PayOrderMapper;
import com.rocklin.offer.mapper.UserMapper;
import com.rocklin.offer.model.dto.request.CodePurchaseRequest;
import com.rocklin.offer.model.dto.request.CodeRedeemRequest;
import com.rocklin.offer.model.dto.request.UserUpdateRequest;
import com.rocklin.offer.model.dto.response.CodePriceResponse;
import com.rocklin.offer.model.entity.*;
import com.rocklin.offer.service.CodeService;
import com.rocklin.offer.service.PayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.rocklin.offer.common.constants.Constants.*;

/**
 * @ClassName CodeServiceImpl
 * @Description 卡密服务实现类
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final UserMapper userMapper;
    private final CodeMapper codeMapper;
    private final MerchantMapper merchantMapper;
    private final EncryptPasswordUtil encryptPasswordUtil;
    private final PayOrderService payOrderService;
    private final PayOrderMapper payOrderMapper;
    private final PayProperties payProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void redeemCode(CodeRedeemRequest req) {
        User user = new User();
        user.setUserAccount(req.getAccount());
        user.setUserPassword(encryptPasswordUtil.getEncryptPassword(req.getPassword()));
        User queryUser = userMapper.queryByPassword(user);
        Assert.notNull(queryUser, ErrorCode.OPERATION_ERROR, "账号不存在或密码错误");

        // 查询卡密是否存在且未被使用
        Code code = codeMapper.selectByCodeAndNotUsed(req.getCode());
        Assert.notNull(code, ErrorCode.OPERATION_ERROR,"卡密不存在或已被使用");

        //检查卡密价格
        Long merchantId = code.getMerchantId();
        Merchant merchant = merchantMapper.selectById(merchantId);
        Assert.notNull(merchant, ErrorCode.OPERATION_ERROR, "卡密商家不存在");
        BigDecimal autumnPrice = merchant.getAutumnPrice();
        BigDecimal campusPrice = merchant.getCampusPrice();
        BigDecimal price = code.getPrice();
        if(price.compareTo(autumnPrice) == 0){
            becomeMember(queryUser, 120);
        }else if(price.compareTo(campusPrice) == 0){
            becomeMember(queryUser, 365);
        }else{
            Assert.isTrue(false, ErrorCode.OPERATION_ERROR, "卡密价格错误");
        }

        // 更新卡密状态为已使用
        code.setIsUsed(ONE);
        code.setUserAccount(queryUser.getUserAccount());
        code.setUsedTime(new Date());
        codeMapper.updateById(code);
        log.info("卡密兑换成功: userId={}, code={}", queryUser.getId(), req.getCode());
    }

    private void becomeMember(User user, int days) {
        // 计算新的会员过期时间
        LocalDateTime newExpireTime;
        if (user.getMemberExpireTime() == null || user.getMemberExpireTime().isBefore(LocalDateTime.now())) {
            // 如果用户不是会员或会员已过期，则从现在开始计算
            newExpireTime = LocalDateTime.now().plusDays(days);
        } else {
            // 如果用户仍是会员，则在现有过期时间基础上增加天数
            newExpireTime = user.getMemberExpireTime().plusDays(days);
        }

        // 更新用户会员信息
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        userUpdateRequest.setId(user.getId());
        userUpdateRequest.setMemberExpireTime(newExpireTime);
        userUpdateRequest.setUserRole(UserRoleEnum.VIP.getValue());
        Long result = userMapper.updateById(userUpdateRequest);
        Assert.isTrue(result > 0, ErrorCode.OPERATION_ERROR, "更新用户会员信息失败");
    }


    @Override
    public CodePriceResponse getCodePrice(String account, String password) {
        Merchant merchant = merchantMapper.selectByAccount(account, password);
        Assert.notNull(merchant, ErrorCode.OPERATION_ERROR, "商家不存在");
        CodePriceResponse response = new CodePriceResponse();
        response.setAutumnPrice(merchant.getAutumnPrice());
        response.setCampusPrice(merchant.getCampusPrice());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String purchaseCode(CodePurchaseRequest req, String account, String password) {
        Merchant merchant = merchantMapper.selectByAccount(account, password);
        Assert.notNull(merchant, ErrorCode.OPERATION_ERROR, "商家不存在");

        BigDecimal autumnPrice = merchant.getAutumnPrice();
        BigDecimal campusPrice = merchant.getCampusPrice();
        BigDecimal price = new BigDecimal(req.getPrice());
        List<String> codes;
        String html;
        if(price.compareTo(autumnPrice) == 0){
            codes = generateCode(req.getQuantity(), autumnPrice, merchant.getId());
            BigDecimal total = autumnPrice.multiply(BigDecimal.valueOf(req.getQuantity()));
            html = createOrder(merchant.getId(), "秋招会员",
                    total.setScale(TWO, RoundingMode.HALF_UP).toPlainString(), "alipay", codes);
        }else if(price.compareTo(campusPrice) == 0){
            codes = generateCode(req.getQuantity(), campusPrice, merchant.getId());
            BigDecimal total = campusPrice.multiply(BigDecimal.valueOf(req.getQuantity()));
            html = createOrder(merchant.getId(), "校招会员",
                    total.setScale(TWO, RoundingMode.HALF_UP).toPlainString(), "alipay", codes);
        }else{
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "卡密价格错误");
        }
        return html;
    }

    @Override
    public List<Code> listCode(String account, String password) {
        Merchant merchant = merchantMapper.selectByAccount(account, password);
        Assert.notNull(merchant, ErrorCode.OPERATION_ERROR, "商家不存在");
        List<Code> codeList= codeMapper.selectListByMerchantId(merchant.getId());
        return codeList;
    }

    @Override
    public boolean markOrderPaid(Map<String, String> params) {
        // 1. 验签
        String sign = params.get(SIGN);
        Map<String, String> verifyParams = new HashMap<>(params);
        verifyParams.remove(SIGN);
        verifyParams.remove(SIGN_TYPE);

        String localSign = SignUtil.getSign(verifyParams, payProperties.getKey());
        if (!localSign.equalsIgnoreCase(sign)) {
            log.error("支付平台异步回调签名验证失败,平台签名:{},本地签名:{}", sign, localSign);
            return false; // 签名不一致
        }

        // 2. 处理订单逻辑
        String outTradeNo = params.get(OUT_TRADE_NO);
        String tradeNo = params.get(TRADE_NO);          // 平台订单号
        String tradeStatus = params.get(TRADE_STATUS);  // "TRADE_SUCCESS" 表示成功

        // 获取卡密
        String codes = params.get(PARAM).trim();
        if (codes.startsWith(LEFT_BRACKET) && codes.endsWith(RIGHT_BRACKET)) {
            codes = codes.substring(1, codes.length() - 1);
        }

        // 按逗号分割，并去掉多余空格
        List<String> codeList = Arrays.stream(codes.split(COMMA))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        if (TRADE_SUCCESS.equals(tradeStatus)) {
            boolean updated = updateOrder(outTradeNo, tradeNo, codeList);
            log.info("更新订单状态：{}", updated ? "成功" : "失败");
            return updated ? true : false;
        }
        return false;
    }

    private boolean updateOrder(String outTradeNo, String tradeNo, List<String> codeList) {
        PayOrder payOrder = payOrderMapper.selectByOutTradeNo(outTradeNo);
        Assert.notNull(payOrder, ErrorCode.OPERATION_ERROR, "订单不存在");
        //更新卡密逻辑删除
        try {
            for (String code : codeList) {
                codeMapper.updateCodeIsDelete(code);
            }
        } catch (Exception e) {
            log.error("更新卡密逻辑删除失败:{}", e.getMessage());
            return false;
        }
        int rows = payOrderMapper.updateStatusAndTradeNo(outTradeNo, tradeNo, 1);
        return rows > 0;
    }

    private String createOrder(Long userId, String name, String money, String type, List<String> param) {
        // 1. 创建本地订单
        PayOrder order = payOrderService.createOrder(false, userId, name, money, type, null);

        log.info("商家:{}创建订单:{}", userId, order.getOutTradeNo());

        // 2. 组装参数
        Map<String, String> params = new HashMap<>();
        params.put(PID, payProperties.getPid());
        params.put(TYPE, type);
        params.put(OUT_TRADE_NO, order.getOutTradeNo());
        params.put(NOTIFY_URL, CODE_NOTIFY_URL);
        params.put(RETURN_URL, CODE_RETURN_URL);
        params.put(NAME, name);
        params.put(MONEY, money);
        params.put(PARAM, String.valueOf(param));

        // 3. 生成签名
        String sign = SignUtil.getSign(params, payProperties.getKey());
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


    private List<String> generateCode(Integer quantity, BigDecimal autumnPrice, Long id) {
        List<String> responses = new ArrayList<>();
        // 批次号默认用年月
        String batch = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String[] codes = CardKeyGenerator.generateBatch("VIP", batch, 10, quantity);
        for (int i = 0; i < quantity; i++) {
            Code code = new Code();
            code.setCode(codes[i]);
            code.setPrice(autumnPrice);
            code.setMerchantId(id);
            code.setIsDelete(1);
            codeMapper.insert(code);
            responses.add(code.getCode());
        }
        return responses;
    }
}