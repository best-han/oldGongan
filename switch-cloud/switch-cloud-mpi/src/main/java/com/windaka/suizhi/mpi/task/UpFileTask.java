package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.mpi.config.FtpProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.windaka.suizhi.common.utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 向ftp服务器上传sql文件
 */
@Slf4j
@Service
public class UpFileTask{

    @Autowired
    FtpProperties ftpProperties;

    public void executeInternal() throws Exception {
    	log.info("task....ftp服务器上传sql任务");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        log.info("UpFileTask----" + sdf.format(new Date()));
        Calendar c=new GregorianCalendar();
        c.setTime(new Date());
        c.add(Calendar.SECOND, -10);
        int i=TimesUtil.getSecond(c.getTime());
        i=i+10;
        String s=String.valueOf(i).substring(0,1);//取上一个文件上传
        //File f=new File(CommonConstants.LOCAL_SQL_PATH + File.separator +SqlFileUtil.fileName);
        File f=new File(CommonConstants.LOCAL_SQL_PATH +File.separator+SqlFileUtil.fileNamePrefix+"_"+s+".sql");
        //备份文件地址
        File backup=new File(CommonConstants.BACKUP_LOCAL_SQL_PATH);
        if(!backup.exists()){
            backup.mkdir();
        }
        File backupFile=new File(CommonConstants.BACKUP_LOCAL_SQL_PATH +File.separator+sdf.format(new Date()));
        InputStream inputStream=null;
        try{
            inputStream=new FileInputStream(f);
            Boolean result=FtpUtil.uploadFile(ftpProperties.getHost(),ftpProperties.getPort(),ftpProperties.getUsername(),
                    ftpProperties.getPassword(),ftpProperties.getBasePath(),CommonConstants.LOCAL_SQL_PATH,
                    SqlFileUtil.fileNamePrefix+sdf.format(new Date())+".sql",inputStream);
            /*Boolean result=FtpUtil.uploadFile("10.10.5.122",21,"kakaxi",
                    "666","/33",CommonConstants.DATA_FILE_PATH,SqlFileUtil.fileName,inputStream);*/
            if(result){
                log.info("上传sql文件成功");
                inputStream.close();
                FileUtils.copyFileToDirectory(f,backupFile);//备份
                f.delete();//删除原文件，若读写流没有关闭则删除不了
                return;
            }
        }catch( Exception e){
            e.printStackTrace();
            log.error("上传sql文件失败",e);
        }finally {
            try{
                if(null!=inputStream){
                    inputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        log.error("上传sql文件失败");
    }

  /*  public static void main(String[] args){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        File f=new File(CommonConstants.DATA_FILE_PATH+"/"+SqlFileUtil.fileName);
        try{
            InputStream inputStream=new FileInputStream(f);
            File backupFile=new File(CommonConstants.BACKUP_DATA_FILE_PATH+"\\"+sdf.format(new Date()));
            Boolean result=FtpUtil.uploadFile("10.10.5.122",21,"kakaxi",
                    "666","/33",CommonConstants.DATA_FILE_PATH,SqlFileUtil.fileName,inputStream);
            if(result){
                log.info("上传文件成功");
                inputStream.close();
                FileUtils.copyFileToDirectory(f,backupFile);//备份
                f.delete();//删除原文件，若读写流没有关闭则删除不了
                return;
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("上传文件失败",e);
        }
        log.error("上传文件失败");

    }*/
}
