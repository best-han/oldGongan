package com.windaka.suizhi.mpi.config;

import com.windaka.suizhi.mpi.task.*;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description 公用定时任务
 * @Author hjt
 * @Date
 */
@Configuration
@EnableScheduling // 启用定时任务
@EnableAsync
public class QuartzConfig {

	@Resource
	HomeAbnormalTask homeAbnormalTask;

	@Resource
	PersonInOutTask personInOutTask;

	@Resource
	CarInOutTask carInOutTask;
	
    //图片用于外网发送
	//推送前端websocket任务-公安网
	@Scheduled(cron="0/10 * * * * ?")
	@Async
	public void MsgPushTaskQuartz() {
		try {
			homeAbnormalTask.executeInternal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Scheduled(cron="0/15 * * * * ?")
	@Async
	public void PersonInOutTaskQuartz() {
		try {
			personInOutTask.executeInternal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Scheduled(cron="0/10 * * * * ?")
	@Async
	public void CarInOutTaskQuartz() {
		try {
			carInOutTask.executeInternal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}


