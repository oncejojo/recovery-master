package com.jojo.recovery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 定时任务调控制
 * @Author: jojo
 * @Date 2022-7-17 11:41
 */
@Configuration
@EnableScheduling
public class DispatchConfig {
    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
        taskExecutor.setThreadNamePrefix("jojo-Scheduler-");
        taskExecutor.setPoolSize(10);
        return taskExecutor;
    }
}
