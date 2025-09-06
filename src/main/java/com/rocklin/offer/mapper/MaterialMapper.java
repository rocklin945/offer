package com.rocklin.offer.mapper;

import com.rocklin.offer.model.dto.request.MaterialPageQueryRequest;
import com.rocklin.offer.model.entity.Material;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterialMapper {

    /**
     * 根据ID查询资料
     */
    Material selectById(@Param("id") Long id);

    /**
     * 根据条件查询资料列表
     */
    List<Material> selectByCondition(@Param("query") MaterialPageQueryRequest queryRequest, @Param("offset") int offset) ;

    /**
     * 根据条件查询资料总数
     */
    long countByCondition(@Param("query") MaterialPageQueryRequest queryRequest);

    /**
     * 获取所有分类
     */
    List<String> selectAllCategories();

    /**
     * 增加查看次数
     */
    int incrementViewCount(@Param("uuid") String uuid);

    /**
     * 插入资料
     */
    int insert(@Param("material") Material material);
}