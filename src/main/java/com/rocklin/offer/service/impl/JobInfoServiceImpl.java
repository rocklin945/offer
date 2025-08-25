package com.rocklin.offer.service.impl;

import com.alibaba.excel.EasyExcel;
import com.rocklin.offer.mapper.JobInfoMapper;
import com.rocklin.offer.mapper.WebInfoMapper;
import com.rocklin.offer.model.dto.JobInfoImportDTO;
import com.rocklin.offer.model.dto.request.JobInfoQueryRequest;
import com.rocklin.offer.model.entity.JobInfo;
import com.rocklin.offer.model.entity.WebInfo;
import com.rocklin.offer.service.JobInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        webInfo.setActivity5Time(webInfo.getActivity4Time());
        webInfo.setActivity4(webInfo.getActivity3());
        webInfo.setActivity4Time(webInfo.getActivity3Time());
        webInfo.setActivity3(webInfo.getActivity2());
        webInfo.setActivity3Time(webInfo.getActivity2Time());
        webInfo.setActivity2(webInfo.getActivity1());
        webInfo.setActivity2Time(webInfo.getActivity1Time());
        webInfo.setActivity1Time(LocalDateTime.now());
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

    @Override
    public List<JobInfo> getAllJobInfo() {
        return jobInfoMapper.selectAll();
    }

    @Override
    public String batchImportJobInfo(MultipartFile file) {
        try {
            // 读取Excel文件数据
            List<JobInfoImportDTO> importData = EasyExcel.read(file.getInputStream())
                    .head(JobInfoImportDTO.class)
                    .sheet()
                    .doReadSync();

            if (importData == null || importData.isEmpty()) {
                return "导入失败：Excel文件为空";
            }

            // 获取数据库中所有现有的招聘信息
            List<JobInfo> existingJobInfos = getAllJobInfo();
            
            // 构建去重的标识集合（公司名+招聘类型+招聘对象）
            Set<String> existingKeys = new HashSet<>();
            for (JobInfo jobInfo : existingJobInfos) {
                String key = generateDeduplicationKey(jobInfo.getCompanyName(), 
                        jobInfo.getRecruitType(), jobInfo.getRecruitTarget());
                existingKeys.add(key);
            }

            // 过滤重复数据并转换为JobInfo实体
            List<JobInfo> newJobInfos = new ArrayList<>();
            int duplicateCount = 0;
            
            for (JobInfoImportDTO importDto : importData) {
                String key = generateDeduplicationKey(importDto.getCompanyName(), 
                        importDto.getRecruitType(), importDto.getRecruitTarget());
                
                if (!existingKeys.contains(key)) {
                    JobInfo jobInfo = new JobInfo();
                    BeanUtils.copyProperties(importDto, jobInfo);
                    newJobInfos.add(jobInfo);
                    existingKeys.add(key); // 防止同一批次内重复
                } else {
                    duplicateCount++;
                }
            }

            // 批量插入新数据
            int insertCount = 0;
            if (!newJobInfos.isEmpty()) {
                for (JobInfo jobInfo : newJobInfos) {
                    if (addJobInfo(jobInfo)) {
                        insertCount++;
                    }
                }
            }

            return String.format("导入完成！总数据：%d条，成功导入：%d条，重复跳过：%d条", 
                    importData.size(), insertCount, duplicateCount);
                    
        } catch (IOException e) {
            return "导入失败：文件读取错误 - " + e.getMessage();
        } catch (Exception e) {
            return "导入失败：" + e.getMessage();
        }
    }

    /**
     * 生成去重标识键
     * @param companyName 公司名称
     * @param recruitType 招聘类型
     * @param recruitTarget 招聘对象
     * @return 去重键
     */
    private String generateDeduplicationKey(String companyName, String recruitType, String recruitTarget) {
        return (companyName != null ? companyName : "") + "_" + 
               (recruitType != null ? recruitType : "") + "_" + 
               (recruitTarget != null ? recruitTarget : "");
    }
}