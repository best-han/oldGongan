package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.common.utils.ZipUtil;
import com.windaka.suizhi.mpi.config.FtpProperties;
import com.windaka.suizhi.common.constants.FtpConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;

/**
 * 公安-从ftp下载图片zip到本地并解压
 */
@Slf4j
@Service
public class GetImageFileUnzipTask {

    @Autowired
    FtpProperties ftpProperties;

    /**
     * 运行状态
     */
    private boolean running = false;

    private boolean running2 = false;

    /**
     * 功能描述: 从ftp上下载图片压缩包并解压到本地指定tomacat目录
     *
     * @auther: hjt
     * @date:
     * @param:
     * @return:
     */
    public void executeInternal() throws  Exception{
        //如果任务不在执行中
        if (!running) {
            running = true;
            log.info("从ftp上下载图片zip文件到本地任务执行开始" + TimesUtil.getServerDateTime(9, new Date()));
            try {
                // 下载图片压缩文件
                List<String> list=FtpUtil.downloadFileFromFtp(FtpConstants.FTP_BASEPATH,"",CommonConstants.GONGAN_IMAGE_PATH,"switch_cloud_image","zip","GetImageFileUnzipTask");
                // 删除ftp上图片文件
                //FtpUtil.deleteFile(ftpProperties.getBasePath()+CommonConstants.GONGAN_LOCAL_IMAGE_PATH,null);
                if(null!=list && list.size()>0){
                    log.info("从ftp上下载图片zip文件到本地任务执行成功image down success" );
                    for(String fileName:list){
                        FtpUtil.deleteFile(CommonConstants.BASE_PATH, fileName);
                        //解压到当前文件夹下
                        File[] files=ZipUtil.unzip(CommonConstants.GONGAN_IMAGE_PATH + File.separator +fileName,null);
                        if(files!=null && files.length>0){
                            log.info("解压图片zip成功，删除tomcat中的zip包");
                            //File ff=new File(CommonConstants.GONGAN_IMAGE_PATH + File.separator +CommonConstants.ZIP_IMAGE_FILE_NAME);
                            File ff=new File(CommonConstants.GONGAN_IMAGE_PATH + File.separator +fileName);

                            ff.delete();
                        }
                    }
                }
               //runsql();
            } catch (Exception e) {
                e.printStackTrace();
                log.error("从ftp上下载图片zip文件到本地解压任务执行失败" );
            }
            log.info("从ftp上下载图片zip到本地解压任务执行结束，结束时间为" + TimesUtil.getServerDateTime(9, new Date()));
            running = false;
        }
    }


    public void runsql(){

        //如果任务不在执行中
        if (!running2) {
        running2 = true;
        log.info("************SQL文件执行开始，开始时间为" + TimesUtil.getServerDateTime(9, new Date()));
        //Connection conn = null;
        try {
            //conn = dataSource.getConnection();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
                /*// mysql驱动
                conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/switch_cloud?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull",
                        "root", "root");
                ScriptRunner run = new ScriptRunner(conn);
                run.setAutoCommit(true);*/
            // 下载sql文件到本地
            log.info("****************下载SQL文件，开始时间为" + TimesUtil.getServerDateTime(9, new Date()));
            List<String> list=FtpUtil.downloadFileFromFtp(FtpConstants.FTP_BASEPATH, "", CommonConstants.GONGAN_SQL_PATH,"switch_cloud","sql","GetAndRunSqlTask");
            //boolean result=FtpUtil.downloadFileFromFtp("/", "", "f:\\switch_cloud_file_download");
            log.info("****************下载SQL文件的list：" + list);
            if(null!=list && list.size()>0){
                for(String fileName:list){
                    log.info("****************下载SQL文件名为" + fileName);
                    FtpUtil.deleteFile(CommonConstants.BASE_PATH, fileName);
                    File file = new File( CommonConstants.GONGAN_SQL_PATH +File.separator + fileName);
                    //File file = new File( "f:\\switch_cloud_file_download\\" + SqlFileUtil.fileName);
                    runSql(file);

                }
                    /*run.closeConnection();
                    if (conn != null && !conn.isClosed()) {
                        conn.close();
                    }*/
                // System.out.println("SQL文件执行结束，结束时间为" + TimesUtil.getServerDateTime(9, new Date()));
            }else{
                log.error("下载sql文件失败*********************************************");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /*conn = null;*/
        }
        running2 = false;
    }
}

    public static synchronized void runSql(File file) throws FileNotFoundException {

        Connection conn = null;
        // mysql驱动
        try{
            //Thread.sleep(5000);
            //log.info("************sql执行0，文件为："+file.getName());
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://56.35.130.2/switch_cloud?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull",
                    "root", "root");
            //log.info("************sql执行1，文件为："+file.getName());
            ScriptRunner run = new ScriptRunner(conn);
            //log.info("************sql执行2，文件为："+file.getName());
            run.setAutoCommit(false);
            //log.info("************sql执行3，文件为："+file.getName());
            Reader reader = new FileReader(file);
            run.runScript(reader);
            //log.info("************sql执行4，文件为："+file.getName());

            run.closeConnection();
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            //log.info("************sql执行5，文件为："+file.getName());
            reader.close();
            //log.info("************sql执行6，文件为："+file.getName());
            file.delete();//正式测试环境，方便看sql下载下来没有
        }catch (Exception e){
            e.printStackTrace();
            log.error("************sql执行失败，文件为："+file.getName());
        }finally {
            conn=null;
        }
    }
}
