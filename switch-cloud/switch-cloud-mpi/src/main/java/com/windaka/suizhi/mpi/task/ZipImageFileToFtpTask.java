package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.common.utils.ZipUtil;
import com.windaka.suizhi.mpi.config.FtpProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 将本地图片文件一次打成zip发送ftp
 */
@Slf4j
@Service
public class ZipImageFileToFtpTask {

    public static String SRC_ZIP_IMAGE_FILE_PATH="f:\\switch_cloud_image_file";//需要打包的本地源文件，本地测试

    public static String DEST_ZIP_FILE_PATH="f:\\zip\\";//打包后所在的文件路径，本地测试


    @Autowired
    FtpProperties ftpProperties;


    public void executeInternal() throws Exception {
    	log.info("task....zip发送ftp任务");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String zipFileName=CommonConstants.ZIP_IMAGE_FILE_NAME_PREFIX + sdf.format(new Date())+".zip";
        String absolutelyPath= ZipUtil.zip(CommonConstants.LOCAL_IMAGE_FILE_PATH, CommonConstants.LOCAL_IMAGE_FILE_BASE_PATH +File.separator + zipFileName, null);
        log.info(absolutelyPath);
        if(StringUtils.isBlank(absolutelyPath)){
            log.error("压缩失败~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }
        log.info("压缩成功~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        File f=new File(absolutelyPath);
        //File f=new File(DEST_ZIP_FILE_PATH+"/"+ CommonConstants.ZIP_IMAGE_FILE_NAME);
        InputStream inputStream=null;
        try{
            inputStream=new FileInputStream(f);
            Boolean result= FtpUtil.uploadFile(ftpProperties.getHost(),ftpProperties.getPort(),ftpProperties.getUsername(),
                    ftpProperties.getPassword(),ftpProperties.getBasePath(),"",zipFileName,inputStream);
           /* Boolean result= FtpUtil.uploadFile("10.10.5.122",21,"kakaxi",
                    "666","/",CommonConstants.LOCAL_IMAGE_FILE_PATH,CommonConstants.ZIP_IMAGE_FILE_NAME,inputStream);*/
            if(result){
                inputStream.close();
                File ff=new File(CommonConstants.LOCAL_IMAGE_FILE_PATH);
                removeDir(ff);//删除打包上传的原图片
                log.info("上传图片压缩文件至ftp成功");
                File backupFile=new File(CommonConstants.BACKUP_LOCAL_IMAGE_FILE_PATH );
                FileUtils.copyFileToDirectory(f,backupFile);//备份
                f.delete();//删除原文件，若读写流没有关闭则删除不了
            }else{
                log.error("上传图片压缩文件至ftp失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("上传图片压缩文件至ftp失败",e);
        }finally {
            try{
                if(null!=inputStream){
                    inputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void upSqlFile(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        log.info("UpFileTask----" + sdf.format(new Date()));
        Calendar c=new GregorianCalendar();
        c.setTime(new Date());
        c.add(Calendar.SECOND, -10);
        int i= TimesUtil.getSecond(c.getTime());
        i=i+10;
        String s=String.valueOf(i).substring(0,1);//取上一个文件上传
        //File f=new File(CommonConstants.LOCAL_SQL_PATH + File.separator +SqlFileUtil.fileName);
        File f=new File(CommonConstants.LOCAL_SQL_PATH +File.separator+ SqlFileUtil.fileNamePrefix+"_"+s+".sql");
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

    /**
     * 删除目录下的所有文件，不删除目录，子目录也不删除
     * @param dir
     */
    private static void removeDir(File dir) {
        File[] files=dir.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                removeDir(file);
            }else{
                file.delete();
                log.info("删除"+file.getName());
            }
        }
    }


   public static void main(String[] args) throws IOException{
       File dir=new File("f:\\aa");
       removeDir(dir);

        /* SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String absolutelyPath= ZipUtil.zip(SRC_ZIP_IMAGE_FILE_PATH, DEST_ZIP_FILE_PATH+CommonConstants.ZIP_IMAGE_FILE_NAME, null);
        log.info(absolutelyPath);
        if(StringUtils.isBlank(absolutelyPath)){
            log.error("压缩失败~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            return;
        }
        log.info("压缩成功~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        File f=new File(DEST_ZIP_FILE_PATH+"/"+ CommonConstants.ZIP_IMAGE_FILE_NAME);
        InputStream inputStream=null;
        try{
            inputStream=new FileInputStream(f);
            *//*Boolean result= FtpUtil.uploadFile(ftpProperties.getHost(),ftpProperties.getPort(),ftpProperties.getUsername(),
                    ftpProperties.getPassword(),ftpProperties.getBasePath(),"",CommonConstants.ZIP_IMAGE_FILE_NAME,inputStream);*//*
            Boolean result= FtpUtil.uploadFile("10.10.5.122",21,"kakaxi",
                    "666","/",CommonConstants.LOCAL_IMAGE_FILE_PATH,CommonConstants.ZIP_IMAGE_FILE_NAME,inputStream);
            if(result){
                inputStream.close();
                log.info("上传图片压缩文件至ftp成功");
             *//*   File backupFile=new File(CommonConstants.BACKUP_DATA_FILE_PATH+"\\"+sdf.format(new Date())+"_"+CommonConstants.ZIP_IMAGE_FILE_NAME);
                FileUtils.copyFileToDirectory(f,backupFile);//备份
                FileUtils.deleteDirectory(f);//删除原文件，若读写流没有关闭则删除不了*//*
            }else{
                log.error("上传图片压缩文件至ftp失败");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("上传图片压缩文件至ftp失败",e);
        }finally {
            try{
                if(null!=inputStream){
                    inputStream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }*/
    }

}
