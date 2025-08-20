package com.rocklin.offer.service.impl;

import com.rocklin.offer.mapper.JobInfoMapper;
import com.rocklin.offer.model.dto.JobInfoQueryRequest;
import com.rocklin.offer.model.entity.JobInfo;
import com.rocklin.offer.service.JobInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName JobInfoServiceImpl
 * @Description 招聘信息服务实现类
 * @Author: rocklin
 * @Date 2025/8/19 21:45
 * @Version 1.0
 */
@Service
@RequiredArgsConstructor
public class JobInfoServiceImpl implements JobInfoService {

    private final JobInfoMapper jobInfoMapper;

    @Override
    public boolean addJobInfo(JobInfo jobInfo) {
        return jobInfoMapper.insert(jobInfo) > 0;
    }

    @Override
    public boolean deleteJobInfo(Long id) {
        return jobInfoMapper.deleteById(id) > 0;
    }

    @Override
    public boolean updateJobInfo(JobInfo jobInfo) {
        return jobInfoMapper.updateById(jobInfo) > 0;
    }

    @Override
    public JobInfo getJobInfoById(Long id) {
        return jobInfoMapper.selectById(id);
    }

    @Override
    public List<JobInfo> getJobInfoList(JobInfoQueryRequest queryRequest) {
        int offset = (queryRequest.getPageNum() - 1) * queryRequest.getPageSize();
        return jobInfoMapper.selectList(offset, queryRequest);
    }

    @Override
    public int getJobInfoCount(JobInfoQueryRequest queryRequest) {
        return jobInfoMapper.selectCount(queryRequest);
    }
}