package com.windaka.suizhi.common.utils;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.constants.FtpConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Component;
import sun.net.ftp.FtpClient;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ftp上传  hjt
 * basePath：ftp上传的根目录 图片为"/image"，
 * 图片的fileName需按照需求文档的来对应表名__对应记录字段名__对应记录主键__对应记录主键值_文件名称.jpg
 */
@Slf4j
@Component
public class FtpUtil {


    /**
     * 该目录不存在
     */
    public static final String DIR_NOT_EXIST = "该目录不存在";

    /**
     * "该目录下没有文件
     */
    public static final String DIR_CONTAINS_NO_FILE = "该目录下没有文件";

    /**
     * 本地字符编码
     **/
    private static String localCharset = "GBK";

    /**
     * FTP协议里面，规定文件名编码为iso-8859-1
     **/
    private static String serverCharset = "ISO-8859-1";

    /**
     * 设置缓冲区大小4M
     **/
    private static final int BUFFER_SIZE = 1024 * 1024 * 4;


    public static Boolean uploadFile(String host, int port, String username, String password, String basePath,
                                     String filePath, String fileName, InputStream inputStream) {
        boolean f = true;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            int reply;//定义返回状态码
            if (!ftp.isConnected()) {
                ftp.connect(host, port);
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            boolean loginResult = ftp.login(username, password);
            String replyString = ftp.getReplyString();//获取服务器执行命令的响应字符串
            log.info(replyString);
      /*  reply=ftp.getReply();//接受状态码(如果成功，返回230，如果失败返回503)
        if(!FTPReply.isPositiveCompletion(reply)){
            ftp.disconnect();//连接失败，断开连接
            return false;
        }*/
            //检测所传入的目录是否存在，如果存在返回true，否则返回false//汉字文件目录不识别
            if (!ftp.changeWorkingDirectory(basePath)) {
                ftp.makeDirectory(basePath);
                log.info("*********************************在ftp" + host + "上创建目录：" + basePath);
            }
     /*       if(StringUtils.isNotBlank(filePath)){
                String[] dirs = filePath.split("/");//直有linux系统目录可以这样（/home/./..）,windows有盘符（F:）
                tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || dir.equals(""))
                        continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {//目录不存在，创建，创建失败返回false
                            return false;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }*/
            ftp.setFileType(FTP.BINARY_FILE_TYPE);//把文件转换为二进制字符流的形式进行上传
            ftp.setBufferSize(BUFFER_SIZE);
            log.info("*********************************在ftp" + host + "上开始上传");
            log.info(ftp.getPassiveHost() + "******" + ftp.getStatus() + "********" + ftp.getDataConnectionMode());
            if (!ftp.storeFile(fileName, inputStream)) {
                log.info("*********************************在ftp" + host + "上上传失败！！");
                f = false;
            }
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
            log.info(e.getMessage(), "上传文件异常,操作失败");
            f = false;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    f = false;
                }
            }
        }
        return f;
    }

    /**
     * 功能描述: ftp上传文件
     *
     * @auther: lixianhua
     * @date: 2019/12/26 11:01
     * @param: basePath:上传路径
     * @return:
     */
    public static Boolean uploadSimpleFile(String host, int port, String username, String password, String basePath,
                                           String fileName, InputStream inputStream) {
        log.info(host + "*******************测试开始11111");
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.setControlEncoding("UTF-8");

            int reply;//定义返回状态码
            if (!ftpClient.isConnected()) {
                ftpClient.connect(host, port);
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            boolean loginResult = ftpClient.login(username, password);
            String replyString = ftpClient.getReplyString();//获取服务器执行命令的响应字符串
            log.info(host + "*******************获取服务器执行命令的响应字符串:" + replyString);


//            ftpClient.enterLocalPassiveMode();
         /*   log.info(host+"*******************测试开始");
            boolean flag = login(ftpClient,host, String.valueOf(port), username, password);
            log.info(host+"*******************测试结束"+flag);*/
            if (loginResult) {
                /*该部分为逐级创建*/
                String[] split = basePath.split("/");
                for (String str : split) {
                    if (StringUtils.isBlank(str)) {
                        continue;
                    }
                    if (!ftpClient.changeWorkingDirectory(str)) {
                        boolean makeDirectory = ftpClient.makeDirectory(str);
                        boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(str);
                    }
                }
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//把文件转换为二进制字符流的形式进行上传
                ftpClient.setBufferSize(BUFFER_SIZE);
                ftpClient.enterLocalPassiveMode();
                if (ftpClient.storeFile(fileName, inputStream)) {
                    log.info("**************************uploadSimpleFile" + fileName + "上传成功");
                    return true;
                } else {
                    log.info("**************************uploadSimpleFile" + fileName + "上传失败");
                    return false;
                }
            }
        } catch (Exception e) {
            if (fileName.endsWith(".txt")) {
                log.info("海博数据异常：" + e.toString());
            } else {
                log.info(e.getMessage(), "上传文件异常,操作失败");
            }
        } finally {
            closeConnect(ftpClient);
        }
        if (fileName.endsWith(".txt")) {
            log.error("**************************uploadSimpleFile" + fileName + "海博数据上传失败");
        } else {
            log.error("**************************uploadSimpleFile" + fileName + "上传失败");
        }
        return false;
    }
    /**
     * 功能描述: 切换目录
     * @auther: lixianhua
     * @date: 2020/1/18 16:05
     * @param:
     * @return:
     */
    public static void switchPath(FTPClient ftpClient,String path) throws IOException {
        /*该部分为逐级创建*/
        String[] split = path.split("/");
        for (String str : split) {
            if (StringUtils.isBlank(str)) {
                continue;
            }
            if (!ftpClient.changeWorkingDirectory(str)) {
                boolean makeDirectory = ftpClient.makeDirectory(str);
                boolean changeWorkingDirectory = ftpClient.changeWorkingDirectory(str);
            }
        }
    }


    /**
     * 功能描述: 上传ftp
     * @auther: lixianhua
     * @date: 2020/1/16 22:43
     * @param:
     * @return:
     */
    public static Boolean uploadFtpFile(FTPClient ftpClient,  String fileName, InputStream inputStream) throws IOException {
        try {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//把文件转换为二进制字符流的形式进行上传
            ftpClient.setBufferSize(BUFFER_SIZE);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
//            ftpClient.enterLocalActiveMode();
            if (ftpClient.storeFile(new String(fileName.getBytes(localCharset),
                    serverCharset), inputStream)) {
                log.info("****FTP上传成功****");
                return true;
            } else {
                log.info("****FTP上传失败****");
                return false;
            }
        } catch (IOException e) {
            log.info("****上传异常****");
            throw e;
        }
    }


    /**
     * 功能描述: 从ftp下载文件到本地服务器
     *
     * @auther: lixianhua
     * @date: 2019/12/9 15:14
     * @param: basePath:ftp基础路径
     * @param: ftpPath:ftp子路径
     * @param: target:本地目标路径
     * @param: prefix:下载文件前缀
     * @param: suffix:下载文件后缀
     * @return:
     */
    public static synchronized List<String> downloadFileFromFtp(String basePath, String ftpPath, String targetPath, String prefix, String suffix, String task) throws OssRenderException {
        List<String> list = null;
        // 登录
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        FTPClient ftpClient = new FTPClient();
        log.info("登录时间*****");
        log.info(sdf.format(new Date()));
        boolean loginFtpRsult = login(ftpClient, PropertiesUtil.getPertiesValue(FtpConstants.FTP_HOST), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PASSWORD));
        log.info(sdf.format(new Date()));
        if (loginFtpRsult) {
            //if (ftpClient != null) {
            try {
                // 判断是否存在该目录
                if (!ftpClient.changeWorkingDirectory(basePath + ftpPath)) {
                    log.error(basePath + ftpPath + DIR_NOT_EXIST);
                    return list;
                }
                ftpClient.enterLocalPassiveMode();  // 设置被动模式，开通一个端口来传输数据
                String[] fs = ftpClient.listNames();
                // 判断该目录下是否有文件
                if (fs == null || fs.length == 0) {
                    log.error(task + "***************:" + basePath + ftpPath + DIR_CONTAINS_NO_FILE);
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
                        log.info(task + "*****************downloading***********:" + file.getName());
                        boolean result = ftpClient.retrieveFile(ff, os);
                        if (result) {
                            log.info(task + "*****************download success:" + file.getName());
                            list.add(ftpName);
                        } else {
                            log.error(task + "*****************download false!!!:" + file.getName());
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            } catch (IOException e) {
                log.error(task + "***************:" + "下载文件失败", e);
            } finally {
                closeConnect(ftpClient);
            }
            // }
        } else {
            log.error(task + "********登陆失败*******");
        }
        return list;
    }

    /**
     * 删除文件下所有文件或删除单个文件 *
     *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return
     */
    public static boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        try {
            login(ftpClient, PropertiesUtil.getPertiesValue(FtpConstants.FTP_HOST), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PASSWORD));
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            int deleteNum = 0;
            if (StringUtils.isNotBlank(filename)) {
                ftpClient.dele(filename);
                deleteNum++;
            } else {
                String[] fileArr = ftpClient.listNames();
                for (int i = 0; i < fileArr.length; i++) {
                    ftpClient.dele(fileArr[i]);
                    deleteNum++;
                }
            }
            ftpClient.logout();
            flag = true;
            log.info("删除文件成功,一共删除" + deleteNum + "个文件");
        } catch (Exception e) {
            log.info("删除文件失败");
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 连接FTP服务器
     *
     * @param address  地址，如：127.0.0.1
     * @param port     端口，如：21
     * @param username 用户名，如：root
     * @param password 密码，如：root
     */
    private static boolean login(FTPClient ftpClient, String address, String port, String username, String password) throws OssRenderException {
        //ftpClient = new FTPClient();
        try {
            ftpClient.setControlEncoding("UTF-8");
            if (!ftpClient.isConnected()) {
                ftpClient.connect(address, Integer.parseInt(port));
            }
            //ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            //限制缓冲区大小
            ftpClient.setBufferSize(BUFFER_SIZE);
            String replyString = ftpClient.getReplyString();//获取服务器执行命令的响应字符串
            log.info("获取ftp服务器执行命令的响应字符串:" + replyString);
            return true;
        } catch (Exception e) {
            log.error("**********************登录异常*****************");
            log.error(e.getMessage());
        }
        return false;
    }

    /**
     * 功能描述: 登录ftp，获取登录实例
     *
     * @auther: lixianhua
     * @date: 2020/1/16 22:33
     * @param:
     * @return:
     */
    public static FTPClient loginFtp(String address, String port, String username, String password) throws IOException {
        FTPClient ftpClient = new FTPClient();
        try {
            if (!ftpClient.isConnected()) {
                ftpClient.connect(address, Integer.parseInt(port));
            }
            //ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            boolean flag = ftpClient.login(username, password);
            if (!flag) {
                return null;
            }
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            //限制缓冲区大小
            ftpClient.setBufferSize(BUFFER_SIZE);
            return ftpClient;
        } catch (Exception e) {
            log.error("****连接异常****");
            throw e;
        }
    }

    /**
     * 功能描述: ftp文件转换成流
     *
     * @auther: lixianhua
     * @date: 2019/12/10 9:26
     * @param:
     * @return:
     */
    public static InputStream getInputStreamFromFtp(String dir, String filename) throws Exception {
        InputStream in = null;
        FTPClient ftpClient = new FTPClient();
        login(ftpClient, PropertiesUtil.getPertiesValue(FtpConstants.FTP_HOST), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PASSWORD));
        // 判断是否存在该目录
        if (!ftpClient.changeWorkingDirectory(dir)) {
            log.error(dir + DIR_NOT_EXIST);
            return null;
        }
        // 转到指定下载目录
        if (dir != null) {//验证是否有该文件夹，有就转到，没有创建后转到该目录下
            ftpClient.changeWorkingDirectory(dir);//转到指定目录下
        }
        //2015/4/28 不需要遍历，改为直接用文件名取
        String remoteAbsoluteFile = FileUploadUtil.toFtpFilename(dir + "/" + filename);
        // 下载文件
        ftpClient.setBufferSize(1024);
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
        in = ftpClient.retrieveFileStream(remoteAbsoluteFile);
        return in;
    }

    /**
     * 关闭FTP连接
     */
    public static void closeConnect(FTPClient ftpClient) {
        if (ftpClient != null&&  ftpClient.isConnected()) {
            try {
                ftpClient.logout();
            } catch (IOException e) {
                log.error("退出FTP登录失败",e);
            } finally {
                if (ftpClient.isConnected()) {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException ioe) {
                        log.error("关闭FTP连接失败",ioe);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//        Boolean result=FtpUtil.uploadFile("10.10.5.122",21,"kakaxi",
//                "666","/33", CommonConstants.DATA_FILE_PATH,SqlFileUtil.fileName,null);
//        FtpUtil ftp = new FtpUtil();
//        List<String> list = new ArrayList<>();
//        list.add("111");
//        list.add("222");
//        list.add("333");
//        list.add("444");
//        list.remove(3);
//        list.add("555");
//        list.remove(3);
//        list.add("666");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
//        System.out.println(list);
        try {
            FTPClient client = loginFtp("10.10.6.131", "21",
                            "han","windaka0532");
            switchPath(client,"20191226");
                String [] name = client.listNames();
                for(int i=0;i<name.length;i++){
                    switchPath(client,name[i]);
                    InputStream is =  new FileInputStream(new File("d:/1.txt"));;
                    uploadFtpFile(client,name[i] + "qqq.txt",is);
                    switchPath(client,"..");
                    is.close();

                }
                closeConnect(client);
            // 上传sql文件
//            InputStream inputStream = new FileInputStream(new File("d:\\002.jpg"));
//            String base64 = FileUploadUtil.getImageStr("d:\\11.jpg");
//            System.out.println(base64);
//            InputStream inputStream = new ByteArrayInputStream(PicUtil.stringToInputStream(base64));
//            BufferedImage image = ImageIO.read(inputStream);
//            int srcWidth = image.getWidth();      // 源图宽度
//            int srcHeight = image.getHeight();    // 源图高度
//            System.out.println("宽度：" + srcWidth);
//            System.out.println("高度：" + srcHeight);
//            inputStream.close();
//            Boolean result = FtpUtil.uploadFile("10.10.5.122", 21, "kakaxi",
//                    "666", "/switch_cloud_file", "", SqlFileUtil.fileName, inputStream);
//            ftp.deleteFile("33", "face_person_attr__base64Img__id__46__20191208165902.jpg");
//            ftp.downloadFileFromFtp("33", "", "D:/test");
//            if (result) {
//////                System.out.println("上传sql成功");
//////            } else {
//////                System.out.println("上传sql失败");
//////            }
            // 上传图片
//            InputStream inputStreamPic = new FileInputStream(new File("D:/mmm.jpg"));
//            Boolean resultPic = FtpUtil.uploadSimpleFile("10.10.6.131", 21, "han",
//                    "windaka0532", "/root-dir/20190319/123456/", "nnn.jpg", inputStream);
       /*     Boolean res = FtpUtil.uploadFile("10.10.5.122", 21, "kakaxi",
                    "666", CommonConstants.BASE_PATH, "", "wenyuan_ddd.zip", inputStream);
            Boolean a = FtpUtil.uploadFile("10.10.5.122", 21, "kakaxi",
                    "666", CommonConstants.BASE_PATH, "", "carsql333.sql", inputStream);
            Boolean b = FtpUtil.uploadFile("10.10.5.122", 21, "kakaxi",
                    "666", CommonConstants.BASE_PATH, "", "carsql444.sql", inputStream);*/
//            ftp.deleteFile("33", "face_person_attr__base64Img__id__46__20191208165902.jpg");
//            ftp.downloadFileFromFtp("33", "", "D:/test");
      /*      if (resultPic&&res&&a&&b) {
                System.out.println("上传图片成功");
            } else {
                System.out.println("上传图片失败");
            }*/
//            login(PropertiesUtil.getPertiesValue(FtpConstants.FTP_HOST), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.FTP_PASSWORD));
            //切换FTP目录
//            ftpClient.changeWorkingDirectory("/switch_cloud_file/image");
//            ftpClient.deleteFile("111");
            System.out.println("执行完成");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("存在异常，执行失败");
        }
    }
}
