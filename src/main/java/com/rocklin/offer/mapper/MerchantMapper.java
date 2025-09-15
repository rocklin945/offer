package com.rocklin.offer.mapper;

import com.rocklin.offer.model.entity.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName MerchantMapper
 * @Description 卡密商家Mapper接口
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Mapper
public interface MerchantMapper {

    /**
     * 根据ID查询商家
     * 
     * @param id 商家ID
     * @return 商家信息
     */
    Merchant selectById(@Param("id") Long id);

    /**
     * 根据账号查询商家
     * 
     * @param account 商家账号
     * @return 商家信息
     */
    Merchant selectByAccount(@Param("account") String account,@Param("password") String password);

    /**
     * 更新商家信息
     * 
     * @param merchant 商家信息
     * @return 更新结果
     */
    int updateById(Merchant merchant);

    /**
     * 插入商家信息
     * 
     * @param merchant 商家信息
     * @return 插入结果
     */
    int insert(Merchant merchant);
}