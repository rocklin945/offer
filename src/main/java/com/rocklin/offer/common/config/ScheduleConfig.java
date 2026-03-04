package com.rocklin.offer.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 定时任务调度器配置
 * 配置独立的线程池，避免单线程阻塞导致任务无法执行
 * 
 * @author rocklin
 * @date 2026-03-04
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
    }

    /**
     * 配置定时任务线程池
     * 
     * @return ThreadPoolTaskScheduler
     */
    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        // 线程池大小，建议设置为 5-10
        scheduler.setPoolSize(5);
        // 线程名前缀
        scheduler.setThreadNamePrefix("scheduled-task-");
        // 是否等待任务执行完再关闭线程池
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        // 关闭时等待的时间（秒）
        scheduler.setAwaitTerminationSeconds(60);
        // 错误处理
        scheduler.setErrorHandler(t -> {
            System.err.println("定时任务执行异常：" + t.getMessage());
            t.printStackTrace();
        });
        // 初始化
        scheduler.initialize();
        return scheduler;
    }
}
