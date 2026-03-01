package com.rocklin.offer.task;

import com.rocklin.offer.service.JobDataFetchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *招数据获取定时任务
 *每天晚上9点自动从目标网站获取数据并导入数据库
 * 
 * @author rocklin
 * @date 2026-03-01
 */
@Component
public class JobDataFetchTask {

    private static final Logger logger = LoggerFactory.getLogger(JobDataFetchTask.class);

    @Autowired
    private JobDataFetchService jobDataFetchService;

    @Scheduled(cron = "0 0 * * * ?")
    public void executeDataFetchTask() {
        logger.info("开始执行招聘数据自动获取任务");
        
        try {
            String result = jobDataFetchService.fetchDataAndImport();
            logger.info("招聘数据自动获取任务执行完成: {}", result);
        } catch (Exception e) {
            logger.error("招聘数据自动获取任务执行失败: {}", e.getMessage(), e);
        }
    }
}