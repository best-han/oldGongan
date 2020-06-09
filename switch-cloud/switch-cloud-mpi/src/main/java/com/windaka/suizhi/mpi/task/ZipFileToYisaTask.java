package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.constants.FtpConstants;
import com.windaka.suizhi.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 公安-将本地sql和图片文件一次打成zip发送至以萨ftp
 */
@Slf4j
@Service
public class ZipFileToYisaTask {

    public void executeInternal() {

        log.info("文远zip传给以萨ftp任务开始，开始时间为:" + TimesUtil.getServerDateTime(9, new Date()));
        FTPClient ysClient = null;
        FTPClient myClient = null;
        try {
            // 从ftp下载zip到本地服务器 by lixianhua
            List<String> list = FtpUtil.downloadFileFromFtp(CommonConstants.BASE_PATH, "", CommonConstants.FTP_ZIP_TEMP, "wenyuan", ".zip", "ZipFileToYisaTask");
            System.out.println("文远zip传给以萨一共有" + list.size() + "个文件");
            int num = 0;
            if (null != list && list.size() > 0) {
                // 登陆以萨FTP
                ysClient = FtpUtil.loginFtp(PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_HOST),
                        PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_USERNAME),
                        PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PASSWORD));
                myClient = FtpUtil.loginFtp( PropertiesUtil.getPertiesValue(FtpConstants.FTP_HOST), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PASSWORD));
                // 切换目录
                FtpUtil.switchPath(ysClient, CommonConstants.GONGAN_YISA_FTP_PATH);
                FtpUtil.switchPath(myClient, CommonConstants.BASE_PATH);
                for (String fileName : list) {
                    File f = new File(CommonConstants.FTP_ZIP_TEMP + fileName);
                    InputStream inputStream = new FileInputStream(f);
                    // 上传文件到以萨ftp
                    FtpUtil.uploadFtpFile(ysClient, fileName, inputStream);
//                    Boolean result = FtpUtil.uploadSimpleFile(PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_HOST),
//                            Integer.parseInt(PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PORT)), PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_USERNAME),
//                            PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PASSWORD), CommonConstants.GONGAN_YISA_FTP_PATH, fileName, inputStream);
                    inputStream.close();
                    num++;
                    // 删除ftp上的文件
                    myClient.dele(fileName);
//                    FtpUtil.deleteFile(CommonConstants.BASE_PATH, fileName);
                }
            }
            log.info("文远zip传给以萨ftp成功，一共删除" + num + "个文件");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文远zip传给以萨ftp失败", e);
        }finally {
            // 删除服务器全部临时文件
            FileUploadUtil.deleteAllFile(CommonConstants.FTP_ZIP_TEMP);
            FtpUtil.closeConnect(ysClient);
            FtpUtil.closeConnect(myClient);
        }
        log.info("文远zip传给以萨ftp任务结束，结束时间为:" + TimesUtil.getServerDateTime(9, new Date()));
    }

}
