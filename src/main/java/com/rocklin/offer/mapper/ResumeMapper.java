package com.rocklin.offer.mapper;

import com.rocklin.offer.model.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName ResumeMapper
 * @Description 简历数据库操作
 * @Author: rocklin
 * @Date 2025/8/31
 * @Version 1.0
 */
@Mapper
public interface ResumeMapper {

    /**
     * 插入简历
     */
    Long insert(Resume resume);

    /**
     * 根据ID查询简历
     */
    Resume selectById(@Param("id") Long id);

    /**
     * 根据用户ID查询简历
     */
    Resume selectByUserId(@Param("userId") Long userId);

    /**
     * 更新简历
     */
    Long updateById(Resume resume);

    /**
     * 根据ID删除简历
     */
    Long deleteById(@Param("id") Long id);

    /**
     * 根据用户ID删除简历
     */
    Long deleteByUserId(@Param("userId") Long userId);
}