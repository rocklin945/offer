package com.rocklin.offer.service.impl;

import com.rocklin.offer.mapper.JobInfoMapper;
import com.rocklin.offer.mapper.WebInfoMapper;
import com.rocklin.offer.model.dto.request.JobInfoQueryRequest;
import com.rocklin.offer.model.entity.JobInfo;
import com.rocklin.offer.model.entity.WebInfo;
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
    private final WebInfoMapper webInfoMapper;

    @Override
    public boolean addJobInfo(JobInfo jobInfo) {
        //更新最新活动
        WebInfo webInfo = webInfoMapper.selectWebInfo();
        webInfo.setActivity5(webInfo.getActivity4());
        webInfo.setActivity4(webInfo.getActivity3());
        webInfo.setActivity3(webInfo.getActivity2());
        webInfo.setActivity2(webInfo.getActivity1());
        webInfo.setActivity1("新发布招聘信息：" + jobInfo.getCompanyName()+jobInfo.getRecruitType());
        webInfoMapper.updateWebInfo(webInfo);
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
        //增加网站pv
        webInfoMapper.incrementPv();
        int offset = (queryRequest.getPageNum() - 1) * queryRequest.getPageSize();
        return jobInfoMapper.selectList(offset, queryRequest);
    }

    @Override
    public int getJobInfoCount(JobInfoQueryRequest queryRequest) {
        return jobInfoMapper.selectCount(queryRequest);
    }
}