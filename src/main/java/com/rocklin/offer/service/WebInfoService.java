package com.rocklin.offer.service;

import com.rocklin.offer.model.dto.response.WebInfoResponse;
import com.rocklin.offer.model.dto.response.WebPriceResponse;

/**
 * @ClassName WebInfoService
 * @Description 网站信息服务接口
 * @Author: rocklin
 * @Date 2025/8/24 10:51
 * @Version 1.0
 */
public interface WebInfoService {

    /**
     * 获取网站信息（包含会员数量统计）
     * 
     * @return 网站信息响应
     */
    WebInfoResponse getWebInfo();

    /**
     * 增加PV访问量
     * 
     * @return 是否增加成功
     */
    boolean incrementPv();

    /**
     * 获取会员图片URL（公开接口）
     * 
     * @return 图片URL
     */
    String getMemberImageUrl();

    /**
     * 获取价格（公开接口）
     *
     * @return 价格信息
     */
    WebPriceResponse getPrice();
}
