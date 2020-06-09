package com.windaka.suizhi.webapi.config;

import com.windaka.suizhi.webapi.task.SuspectedAddTask;
import com.windaka.suizhi.webapi.task.SuspectedQuitTask;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * 定时任务 hjt
 * 因短信发送只在公安内网进行，目前只支持内网执行此定时任务
 */
@Configuration
@EnableScheduling
@EnableAsync
public class QuartzConfig {

    @Resource
    SuspectedAddTask suspectedAddTask;

    @Resource
    SuspectedQuitTask suspectedQuitTask;

    @Scheduled(cron = "0 0 1 * * ?")
    @Async
    public void SuspectedAddTask(){
        try {
            suspectedAddTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @Scheduled(cron = "0 0 1 * * ?")
    @Async
    public void SuspectedQuitTask(){
        try {
            suspectedQuitTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
