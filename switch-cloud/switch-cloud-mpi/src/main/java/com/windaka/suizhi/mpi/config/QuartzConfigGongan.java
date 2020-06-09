package com.windaka.suizhi.mpi.config;

import com.windaka.suizhi.mpi.task.*;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description 公安内网定时任务类
 * @Author hjt
 * @Date
 */
/*@Configuration
@EnableScheduling // 启用定时任务
@EnableAsync*/
public class QuartzConfigGongan {

    @Resource
    GetAndRunSqlTask getAndRunSqlTask;

    @Resource
    GetImageFileUnzipTask getImageFileUnzipTask;

    @Resource
    QueueMsgTask queueMsgTask;

    @Resource
    PersonInfoIsCrimeTask personInfoIsCrimeTask;

    @Resource
    HomeCarTask homeCarTask;

    @Resource
    CaputreFaceToytlfTask caputreFaceToytlfTask;

    @Resource
    ZipFileToYisaTask zipFileToYisaTask;

    @Resource
    CaptureCarTohbTask captureCarTohbTask;

    @Resource
    CaptureCarToyisaTask captureCarToyisaTask;

    @Resource
    SuspectedAddMsgTask suspectedAddMsgTask;

    @Resource
    SuspectedQuitMsgTask suspectedQuitMsgTask;

    @Resource
    CaptureFaceToyisaTask captureFaceToyisaTask;

    @Resource
    GetAlarmFromHb getAlarmFromHb;

    //下载sql任务并运行-公安
    @Scheduled(cron = "*/10 * * * * ?")
    @Async
    public void getAndRunSqlQuartz() {
        try {
            getAndRunSqlTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //下载imageZip并解压任务-公安
    @Scheduled(cron = "*/10 * * * * ?")
    @Async
    public void getImageUnZipFileQuartz() {
        try {
            getImageFileUnzipTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //执行推送队列里的定时任务-公安网
    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void QueueMsgTaskQuartz() {
        try {
            queueMsgTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 1 * * ?")//每天1点触发
    @Async
    public void PersonInfoIsCrimeTaskQuartz() {
        try {
            personInfoIsCrimeTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 推送前端车辆报警任务-公安网
     * @auther: lixianhua
     * @date: 2020/1/7 14:33
     * @param:
     * @return:
     */
    @Scheduled(cron = "0/10 * * * * ?")
    @Async
    public void MsgPushCar() {
        try {
            homeCarTask.run();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 */1 * * * ?")
    @Async
    public void CaputreFaceToytlfTask() {
        try {
            caputreFaceToytlfTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "*/10 * * * * ?")
    @Async
    public void ZipFileToYisaTask() {
        try {
            zipFileToYisaTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 功能描述: 给海博传图片和数据到ftp(10分钟执行一次)
     *
     * @auther: lixianhua
     * @date: 2019/12/30 19:45
     * @param:
     * @return:
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @Async
    public void hkCarTask() {
        captureCarTohbTask.executeInternal();
    }

    /**
     * 功能描述: 给以萨数据到ftp
     *
     * @date: 2019/12/30 19:45
     * @param:
     * @return:
     */
    @Scheduled(cron = "0 0/20 * * * ?")
    @Async
    public void ysCarTask() {
        captureCarToyisaTask.runTask();
    }

    /**
     * 功能描述: 给以萨发送人脸信息
     *
     * @auther: lixianhua
     * @date: 2020/1/14 15:38
     * @param:
     * @return:
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @Async
    public void ysFaceTask() {
        captureFaceToyisaTask.run();
    }

    /**
     * 功能描述: 从海博数据字典中获取数据
     * @auther: lixianhua
     * @date: 2020/1/15 15:14
     * @param:
     * @return:
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    @Async
    public void hbViewTask() {
        getAlarmFromHb.run();
    }

    //疑似新增人/车短信发送 hjt
   // @Scheduled(cron = "0 0/2 * * * ?")
    @Scheduled(cron = "0 0 8 * * ?")//8点
    @Async
    public void suspectedAddMsgTask(){
        try {
            suspectedAddMsgTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //疑似迁出人短信发送 hjt
    @Scheduled(cron = "0 0 8 * * ?")//8点
 //   @Scheduled(cron = "0 0/2 * * * ?")
    @Async
    public void suspectedQuitMsgTask(){
        try {
            suspectedQuitMsgTask.executeInternal();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}

