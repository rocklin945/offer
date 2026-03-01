package com.rocklin.offer.service;

import com.rocklin.offer.entity.JobInfo;
import com.rocklin.offer.util.JobInfoParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

/**
 *招聘数据获取服务
 * 用于从外部网站获取数据并导入数据库
 * 
 * @author rocklin
 * @date 2026-03-01
 */
@Service
public class JobDataFetchService {

    private static final Logger logger = LoggerFactory.getLogger(JobDataFetchService.class);

    @Autowired
    private JobInfoService jobInfoService;
    
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * 从目标网站获取数据并导入数据库
     * 
     * @return导入结果信息
     */
    public String fetchDataAndImport() {
        try {
            logger.info("开始从目标网站获取数据并导入数据库");
            
            // 从目标网站获取HTML内容
            String htmlContent = restTemplate.getForObject("https://www.givemeoc.com/", String.class);
            
            if (htmlContent == null || htmlContent.isEmpty()) {
                throw new RuntimeException("无法从目标网站获取数据");
            }
            
            // 使用JobInfoParser解析HTML内容
            List<JobInfo> jobInfoList = JobInfoParser.parseJobInfoFromHtml(htmlContent);
            
            if (jobInfoList.isEmpty()) {
                return "解析失败：未找到有效的招聘信息";
            }
            
            logger.info("解析到 {}条", jobInfoList.size());
            
            //过滤有效数据
            List<JobInfo> validJobInfoList = jobInfoList.stream()
                    .filter(JobInfoParser::isValidJobInfo)
                    .toList();
            
            logger.info("过滤后有 {}条有效招聘信息", validJobInfoList.size());
            
            if (validJobInfoList.isEmpty()) {
                return "导入失败：没有有效的招聘信息数据";
            }
            
            // 获取数据库中所有现有的招聘信息用于去重
            List<com.rocklin.offer.model.entity.JobInfo> existingJobInfos = jobInfoService.getAllJobInfo();
            
            //构建去重的标识集合（公司名+招聘类型+招聘对象）
            Set<String> existingKeys = new HashSet<>();
            for (com.rocklin.offer.model.entity.JobInfo jobInfo : existingJobInfos) {
                String key = generateDeduplicationKey(jobInfo.getCompanyName(), 
                        jobInfo.getRecruitType(), jobInfo.getRecruitTarget());
                existingKeys.add(key);
            }
            
            //过重复数据并转换为数据库实体
            List<com.rocklin.offer.model.entity.JobInfo> newJobInfos = new ArrayList<>();
            int duplicateCount = 0;
            
            for (com.rocklin.offer.entity.JobInfo parsedJobInfo : validJobInfoList) {
                String key = generateDeduplicationKey(parsedJobInfo.getCompanyName(), 
                        parsedJobInfo.getRecruitmentType(), parsedJobInfo.getTargetGroup());
                
                if (!existingKeys.contains(key)) {
                    //将解析的JobInfo转换为数据库的JobInfo实体
                    com.rocklin.offer.model.entity.JobInfo dbJobInfo = convertToDbEntity(parsedJobInfo);
                    newJobInfos.add(dbJobInfo);
                    existingKeys.add(key); //防止同一批次内重复
                } else {
                    duplicateCount++;
                }
            }
            
            logger.info("去重后有 {}条数据，重复数据 {}条", newJobInfos.size(), duplicateCount);
            
            if (newJobInfos.isEmpty()) {
                return String.format("导入完成！总数据：%d条，重复跳过：%d条，无新增数据", 
                        validJobInfoList.size(), duplicateCount);
            }
            
            //批量导入到数据库
            int successCount = 0;
            int errorCount = 0;
            
            for (com.rocklin.offer.model.entity.JobInfo dbJobInfo : newJobInfos) {
                try {
                    if (jobInfoService.addJobInfo(dbJobInfo)) {
                        successCount++;
                    } else {
                        errorCount++;
                    }
                } catch (Exception e) {
                    logger.error("导入单条数据失败: {}", e.getMessage(), e);
                    errorCount++;
                }
            }
            
            String result = String.format("数据导入完成！总数据：%d条，成功导入：%d条，重复跳过：%d条，导入失败：%d条", 
                    validJobInfoList.size(), successCount, duplicateCount, errorCount);
            
            logger.info(result);
            return result;
            
        } catch (Exception e) {
            String errorMsg = "数据获取和导入失败：" + e.getMessage();
            logger.error(errorMsg, e);
            return errorMsg;
        }
    }
    
    /**
     *将解析的JobInfo转换为数据库的JobInfo实体
     * 
     * @param parsedJobInfo 解析的JobInfo
     * @return 数据库的JobInfo实体
     */
    private com.rocklin.offer.model.entity.JobInfo convertToDbEntity(com.rocklin.offer.entity.JobInfo parsedJobInfo) {
        com.rocklin.offer.model.entity.JobInfo dbJobInfo = new com.rocklin.offer.model.entity.JobInfo();
        
        //基本字段映射
        dbJobInfo.setCompanyName(parsedJobInfo.getCompanyName());
        dbJobInfo.setCompanyType(parsedJobInfo.getCompanyType());
        dbJobInfo.setIndustry(parsedJobInfo.getIndustry());
        dbJobInfo.setPositionName(parsedJobInfo.getPositions());
        dbJobInfo.setWorkLocation(parsedJobInfo.getLocation());
        dbJobInfo.setRecruitTarget(parsedJobInfo.getTargetGroup());
        dbJobInfo.setRecruitType(parsedJobInfo.getRecruitmentType());
        dbJobInfo.setDeadline(parsedJobInfo.getDeadline());
        dbJobInfo.setRelatedLink(parsedJobInfo.getApplicationLink());
        dbJobInfo.setAnnouncement(parsedJobInfo.getNoticeLink());
        dbJobInfo.setReferralCode(parsedJobInfo.getReferral());
        dbJobInfo.setRemark(parsedJobInfo.getNotes());
        
        return dbJobInfo;
    }
    
    /**
     * 生成去重标识key
     * 
     * @param companyName公名称
     * @param recruitType招类型
     * @param recruitTarget招对象
     * @return标识key
     */
    private String generateDeduplicationKey(String companyName, String recruitType, String recruitTarget) {
        return String.format("%s_%s_%s", 
                companyName != null ? companyName : "",
                recruitType != null ? recruitType : "",
                recruitTarget != null ? recruitTarget : "");
    }
}