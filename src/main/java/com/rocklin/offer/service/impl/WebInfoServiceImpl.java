package com.rocklin.offer.service.impl;

import com.rocklin.offer.common.enums.ErrorCode;
import com.rocklin.offer.common.exception.Assert;
import com.rocklin.offer.mapper.WebInfoMapper;
import com.rocklin.offer.model.dto.response.WebInfoResponse;
import com.rocklin.offer.model.dto.response.WebPriceResponse;
import com.rocklin.offer.model.entity.WebInfo;
import com.rocklin.offer.service.WebInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName WebInfoServiceImpl
 * @Description 网站信息服务实现类
 * @Author: rocklin
 * @Date 2025/8/24 10:51
 * @Version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebInfoServiceImpl implements WebInfoService {

    private final WebInfoMapper webInfoMapper;

    @Override
    public WebInfoResponse getWebInfo() {
        WebInfo webInfo = webInfoMapper.selectWebInfo();

        // 刷新实时统计数据
        if (webInfo != null) {
            int userCount = webInfoMapper.getUserCount();
            int memberCount = webInfoMapper.getMemberCount();
            int jobCount = webInfoMapper.getJobCount();

            // 如果统计数据发生变化，更新数据库
            if (webInfo.getUserCount() != userCount || webInfo.getJobCount() != jobCount) {
                webInfo.setUserCount(userCount);
                webInfo.setJobCount(jobCount);
                webInfoMapper.updateWebInfo(webInfo);
            }

            // 转换为响应 DTO
            WebInfoResponse response = new WebInfoResponse();
            buildWebInfoResponse(webInfo, response);
            response.setMemberCount(memberCount); // 设置会员数量

            return response;
        }

        return null;
    }

    private void buildWebInfoResponse(WebInfo webInfo, WebInfoResponse response) {
        response.setId(webInfo.getId());
        response.setPv(webInfo.getPv());
        response.setImageUrl(webInfo.getImageUrl());
        response.setUserCount(webInfo.getUserCount());
        response.setJobCount(webInfo.getJobCount());
        response.setOriginalPrice(webInfo.getOriginalPrice());
        response.setCurrentPrice(webInfo.getCurrentPrice());
        response.setActivity1(webInfo.getActivity1());
        response.setActivity1Time(webInfo.getActivity1Time());
        response.setActivity2(webInfo.getActivity2());
        response.setActivity2Time(webInfo.getActivity2Time());
        response.setActivity3(webInfo.getActivity3());
        response.setActivity3Time(webInfo.getActivity3Time());
        response.setActivity4(webInfo.getActivity4());
        response.setActivity4Time(webInfo.getActivity4Time());
        response.setActivity5(webInfo.getActivity5());
        response.setActivity5Time(webInfo.getActivity5Time());
        response.setUpdateTime(webInfo.getUpdateTime());
    }

    @Override
    public boolean incrementPv() {
        // 先检查是否存在网站信息
        WebInfo webInfo = webInfoMapper.selectWebInfo();
        Assert.notNull(webInfo, ErrorCode.NOT_FOUND, "网站信息不存在");
        int result = webInfoMapper.incrementPv();
        return result > 0;
    }

    @Override
    public String getMemberImageUrl() {
        WebInfo webInfo = webInfoMapper.selectWebInfo();
        Assert.notNull(webInfo, ErrorCode.NOT_FOUND, "网站信息不存在");
        return webInfo.getImageUrl();
    }

    @Override
    public WebPriceResponse getPrice() {
        WebInfo webInfo = webInfoMapper.selectWebInfo();
        Assert.notNull(webInfo, ErrorCode.NOT_FOUND, "网站信息不存在");
        WebPriceResponse resp = new WebPriceResponse();
        resp.setOriginalPrice(webInfo.getOriginalPrice());
        resp.setCurrentPrice(webInfo.getCurrentPrice());
        return resp;
    }
}
