package com.rocklin.offer.service;

import com.rocklin.offer.model.dto.request.CodePurchaseRequest;
import com.rocklin.offer.model.dto.request.CodeRedeemRequest;
import com.rocklin.offer.model.dto.response.CodePriceResponse;
import com.rocklin.offer.model.entity.Code;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CodeService
 * @Description 卡密服务接口
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
public interface CodeService {

    /**
     * 卡密兑换
     */
    void redeemCode(CodeRedeemRequest req);

    /**
     * 根据商家账号获取卡密商品列表
     */
    CodePriceResponse getCodePrice(String account, String password);

    /**
     * 购买卡密
     */
    String purchaseCode(CodePurchaseRequest req, String account, String password);

    /**
     * 获取卡密列表
     */
    List<Code> listCode(String account, String password);

    boolean markOrderPaid(Map<String, String> params);
}