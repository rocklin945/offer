package com.rocklin.offer.service;

import com.rocklin.offer.model.dto.request.JobInfoQueryRequest;
import com.rocklin.offer.model.entity.JobInfo;

import java.util.List;

/**
 * @ClassName JobInfoService
 * @Description 招聘信息服务接口
 * @Author: rocklin
 * @Date 2025/8/19 21:40
 * @Version 1.0
 */
public interface JobInfoService {

    /**
     * 添加招聘信息
     * @param jobInfo 招聘信息
     * @return 是否成功
     */
    boolean addJobInfo(JobInfo jobInfo);

    /**
     * 删除招聘信息
     * @param id 主键ID
     * @return 是否成功
     */
    boolean deleteJobInfo(Long id);

    /**
     * 更新招聘信息
     * @param jobInfo 招聘信息
     * @return 是否成功
     */
    boolean updateJobInfo(JobInfo jobInfo);

    /**
     * 根据ID查询招聘信息
     * @param id 主键ID
     * @return 招聘信息
     */
    JobInfo getJobInfoById(Long id);

    /**
     * 查询招聘信息列表
     * @param queryRequest 查询请求
     * @return 招聘信息列表
     */
    List<JobInfo> getJobInfoList(JobInfoQueryRequest queryRequest);

    /**
     * 查询招聘信息总数
     * @param queryRequest 查询请求
     * @return 总数
     */
    int getJobInfoCount(JobInfoQueryRequest queryRequest);
}