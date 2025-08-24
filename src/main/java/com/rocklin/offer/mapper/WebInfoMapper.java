package com.rocklin.offer.mapper;

import com.rocklin.offer.model.entity.WebInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName WebInfoMapper
 * @Description 网站信息数据库操作
 * @Author: rocklin
 * @Date 2025/8/24 
 * @Version 1.0
 */
@Mapper
public interface WebInfoMapper {

    /**
     * 查询网站信息
     * @return 网站信息
     */
    WebInfo selectWebInfo();

    /**
     * 更新网站信息
     * @param webInfo 网站信息
     * @return 影响行数
     */
    int updateWebInfo(WebInfo webInfo);

    /**
     * 增加PV访问量
     * @return 影响行数
     */
    int incrementPv();

    /**
     * 获取用户总数
     * @return 用户总数
     */
    int getUserCount();

    /**
     * 获取会员总数（userRole=2）
     * @return 会员总数
     */
    int getMemberCount();

    /**
     * 获取招聘信息总数
     * @return 招聘信息总数
     */
    int getJobCount();
}