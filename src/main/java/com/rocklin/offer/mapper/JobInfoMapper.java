package com.rocklin.offer.mapper;

import com.rocklin.offer.model.dto.request.JobInfoQueryRequest;
import com.rocklin.offer.model.entity.JobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName JobInfoMapper
 * @Description 招聘信息Mapper接口
 * @Author: rocklin
 * @Date 2025/8/19 21:30
 * @Version 1.0
 */
@Mapper
public interface JobInfoMapper {

    /**
     * 插入招聘信息
     * @param jobInfo 招聘信息
     * @return 影响行数
     */
    int insert(JobInfo jobInfo);

    /**
     * 根据ID删除招聘信息
     * @param id 主键ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);

    /**
     * 更新招聘信息
     * @param jobInfo 招聘信息
     * @return 影响行数
     */
    int updateById(JobInfo jobInfo);

    /**
     * 根据ID查询招聘信息
     * @param id 主键ID
     * @return 招聘信息
     */
    JobInfo selectById(@Param("id") Long id);

    /**
     * 查询招聘信息列表
     * @param queryRequest 查询请求参数
     * @return 招聘信息列表
     */
    List<JobInfo> selectList(@Param("offset") int offset, @Param("query") JobInfoQueryRequest queryRequest);

    /**
     * 查询招聘信息总数
     * @param queryRequest 查询请求参数
     * @return 总数
     */
    int selectCount(@Param("query") JobInfoQueryRequest queryRequest);

    /**
     * 获取所有招聘信息
     * @return 所有招聘信息列表
     */
    List<JobInfo> selectAll();

    Long getOpenCountBetween(@Param("startTime") LocalDateTime startTime,@Param("endTime") LocalDateTime endTime);
}