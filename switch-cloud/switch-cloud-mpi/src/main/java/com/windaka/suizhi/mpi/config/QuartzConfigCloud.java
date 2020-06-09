package com.windaka.suizhi.mpi.config;

import com.windaka.suizhi.mpi.task.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * @Description 用于外网的定时任务
 * @Author hjt
 * @Date
 */
@Configuration
@EnableScheduling // 启用定时任务
@EnableAsync
public class QuartzConfigCloud {
	@Resource
	UpFileTask upFileTask;
	
	@Resource
	ZipImageFileToFtpTask zipImageFileToFtpTask;

   //sql上传任务
	@Scheduled(cron="0/10 * * * * ?")
    @Async
    public void upFileQuartz() {
		try {
			upFileTask.executeInternal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    //sql用于外网发送
    //图片压缩上传任务
	@Scheduled(cron="0/10 * * * * ?")
    @Async
    public void zipImageFileToFtpTaskQuartz() {
		try {
			zipImageFileToFtpTask.executeInternal();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


}


