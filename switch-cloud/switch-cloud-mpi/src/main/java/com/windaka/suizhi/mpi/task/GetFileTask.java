package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.config.FtpProperties;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class GetFileTask extends QuartzJobBean {

    @Autowired
    FtpProperties ftpProperties;

    /**
     * 运行状态
     */
    private boolean running = false;

    /**
     * 功能描述: 从ftp上下载图片到本地
     *
     * @auther: lixianhua
     * @date: 2019/12/10 8:54
     * @param:
     * @return:
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        //如果任务不在执行中
   /*     if (!running) {
            running = true;
            System.out.println("从ftp上下载文件到本地任务执行开始" + TimesUtil.getServerDateTime(9, new Date()));
            try {
                // 下载图片文件
                FtpUtil.downloadFileFromFtp(CommonConstants.DATA_FILE_PATH,CommonConstants.IMAGE_PATH,CommonConstants.LOCAL_DATA_FILE_PATH+CommonConstants.LOCAL_IMAGE_PATH);
                // 删除ftp上图片文件
                FtpUtil.deleteFile(ftpProperties.getBasePath()+CommonConstants.LOCAL_IMAGE_PATH,null);
                System.out.println("任务执行成功" );
            } catch (OssRenderException e) {
                e.printStackTrace();
                System.out.println("任务执行失败" );
            }

            System.out.println("从ftp上下载文件到本地任务执行结束，结束时间为" + TimesUtil.getServerDateTime(9, new Date()));
            running = false;
        }*/
    }
}
