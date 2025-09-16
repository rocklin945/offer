package com.rocklin.offer.mapper;

import com.rocklin.offer.model.entity.Code;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName CodeMapper
 * @Description 卡密Mapper接口
 * @Author rocklin
 * @Date 2025/9/15
 * @Version 1.0
 */
@Mapper
public interface CodeMapper {

    /**
     * 根据ID查询卡密
     * 
     * @param id 卡密ID
     * @return 卡密信息
     */
    Code selectById(@Param("id") Long id);

    /**
     * 根据卡密查询未使用的卡密
     * 
     * @param code 卡密
     * @return 卡密信息
     */
    Code selectByCodeAndNotUsed(@Param("code") String code);

    /**
     * 更新卡密信息
     * 
     * @param code 卡密信息
     * @return 更新结果
     */
    int updateById(Code code);

    /**
     * 插入卡密信息
     * 
     * @param code 卡密信息
     * @return 插入结果
     */
    int insert(Code code);

    List<Code> selectListByMerchantId(Long id);
}