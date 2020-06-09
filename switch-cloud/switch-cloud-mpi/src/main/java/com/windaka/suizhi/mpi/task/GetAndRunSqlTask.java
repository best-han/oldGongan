package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.config.DataConnectProperties;
import com.windaka.suizhi.common.constants.FtpConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;


/**
 * 公安-从ftp下载sql并运行插入到数据库中
 */
@Slf4j
@Service
public class GetAndRunSqlTask {

    @Autowired
    private DataConnectProperties dataConnectProperties;

    @Autowired
    private DataSource dataSource;


    /**
     * 运行状态
     */
    private boolean running = false;

    /**
     * 功能描述: 下载并执行sql文件
     *
     * @auther: lixianhua
     * @date: 2019/12/10 8:43
     * @param:
     * @return:
     */
    public void executeInternal() throws Exception {
        //如果任务不在执行中
        if (!running) {
            running = true;
           log.info("************SQL文件执行开始，开始时间为" + TimesUtil.getServerDateTime(9, new Date()));
            try {
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
                }else{
                    log.error("下载sql文件失败*********************************************");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                /*conn = null;*/
            }
            running = false;
        }
    }

    public static synchronized void runSql(File file) throws FileNotFoundException{

        Connection conn = null;
        // mysql驱动
        try{
            //log.info("************sql执行0，文件为："+file.getName());
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://56.35.130.2/switch_cloud?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull",
                    "root", "root");
            ScriptRunner run = new ScriptRunner(conn);
            run.setAutoCommit(false);
            Reader reader = new FileReader(file);
            run.runScript(reader);
            run.closeConnection();
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
            reader.close();
            file.delete();//正式测试环境，方便看sql下载下来没有
        }catch (Exception e){
            e.printStackTrace();
            log.error("************sql执行失败，文件为："+file.getName());
        }finally {
            conn=null;
        }
    }

  /*  public static void main(String[] args){
        Connection conn = null;
        try {
            int n=0;
            while(true){
                // 下载sql文件到本地
                log.info("****************下载SQL文件，开始时间为" + TimesUtil.getServerDateTime(9, new Date()));
                List<String> list=FtpUtil.downloadFileFromFtp(FtpConstants.FTP_BASEPATH, "", "F:\\switch_cloud_file","sjwl","log","GetAndRunSqlTask");
                //boolean result=FtpUtil.downloadFileFromFtp("/", "", "f:\\switch_cloud_file_download");
                log.info("****************下载SQL文件的list：" + list);
                if(null!=list && list.size()>0){
                    for(String fileName:list){
                        log.info("****************下载SQL文件名为" + fileName);
                        //File file = new File( CommonConstants.GONGAN_SQL_PATH +File.separator + fileName);
                        File file = new File( "f:\\switch_cloud_file"  +File.separator + fileName);
                    *//*Reader reader = new FileReader(file);
                    run.runScript(reader);*//*
                        //file.delete();//正式测试环境，方便看sql下载下来没有
                        //    FtpUtil.deleteFile(CommonConstants.BASE_PATH, fileName);
                    }
                    //   run.closeConnection();
       *//*         if (conn != null && !conn.isClosed()) {
                    conn.close();
                }*//*
                    // System.out.println("SQL文件执行结束，结束时间为" + TimesUtil.getServerDateTime(9, new Date()));
                    //Thread.sleep(5000);
                    n++;
                    log.info("连接次数:"+n);
                }else{
                    log.error("下载sql文件失败*********************************************");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args){
        log.info("SQL文件执行开始，开始时间为" + TimesUtil.getServerDateTime(9, new Date()));
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // mysql驱动
            con = (Connection) DriverManager.getConnection("jdbc:mysql://47.103.36.248/switch_cloud_test?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull",
                    "root", "root");

            ScriptRunner run = new ScriptRunner(con);
            run.setAutoCommit(true);

            //File file = new File( CommonConstants.GONGAN_SQL_PATH +File.separator + fileName);
            File file = new File( "C:\\Users\\Lenovo\\Desktop\\视界物联\\gongan_jar_正式\\" + "switch_cloud_4.sql");
            Reader reader = new FileReader(file);
            run.runScript(reader);
            //file.delete();

            if (con != null && !con.isClosed()) {
                con.close();
            }
            System.out.println("SQL文件执行结束，结束时间为" + TimesUtil.getServerDateTime(9, new Date()));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con = null;
        }

    }
  /*  public static List<String> downloadFileFromFtp(String basePath, String ftpPath, String targetPath, String prefix, String suffix) throws OssRenderException {
       *//* List<String> list = null;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        int reply;//定义返回状态码
        if (!ftp.isConnected()) {
            ftp.connect("10.10.5.122", 21);
        }
        ftp.enterLocalPassiveMode();
        ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
        boolean loginResult = ftp.login("kakaxi", "666");

        String replyString = ftp.getReplyString();//获取服务器执行命令的响应字符串
        log.info(replyString);
        // 登录
        if (FtpUtil.ftpClient != null) {
            try {
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(basePath + ftpPath)) {
                    log.error(basePath + ftpPath + FtpUtil.DIR_NOT_EXIST);
                    return list;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    log.error(basePath + ftpPath + "没有路径");
                    return list;
                }
                list = new ArrayList<String>();
                File localPath = new File(targetPath);
                // 判断是否存在本地路径
                if (!localPath.exists()) {
                    localPath.mkdirs();
                }
                for (String ff : fs) {
                    String ftpName = new String(ff.getBytes(serverCharset), localCharset);
                    if (StringUtils.isNotBlank(prefix) && !ftpName.startsWith(prefix)) {
                        continue;
                    }
                    if (StringUtils.isNotBlank(suffix) && !ftpName.endsWith(suffix)) {
                        continue;
                    }
                    File file = new File(targetPath + File.separator + ftpName);
                    try (OutputStream os = new FileOutputStream(file)) {
                        ftpClient.retrieveFile(ff, os);
                        list.add(ftpName);
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            } catch (IOException e) {
                log.error("下载文件失败", e);
            } finally {
                closeConnect();
            }
        }
        return list;*//*
    }*/

}
