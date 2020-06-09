package com.windaka.suizhi.common.utils;

import cn.hutool.core.codec.Base64Decoder;
import com.windaka.suizhi.api.common.OssConstants;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.FastdfsConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.*;

@Slf4j
public class FileUploadUtil {

    /**
     *
     * <p>Description: 生成文件名字，时间+文件名命名</p>
     * @author hjt
     * @Date 2019年1月28日 下午5:06:59
     */
    /*  public String mkFileName(String fileName){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	return sdf.format(new Date())+fileName;
    }*/
    /**
     *
     * <p>Description: 生成文件的保存目录(hashcode生成)</p>
     * @author hjt
     * @Date 2019年1月28日 下午5:07:44
     */
    /*  public String mkFilePath(String savePath,String fileName){
    	//得到文件名的hashCode值，得到的就是fileName这个字符串对象在内存中的地址
    	int hashCode=fileName.hashCode();
    	int dir1=hashCode&0xf;
    	int dir2=(hashCode&0xf0)>>4;
    	//构造新的保存目录
    	String dir=savePath+"\\"+dir1+"\\"+dir2;
    	//file既可以代表文件，也可以代表目录
    	File file=new File(dir);
    	if(!file.exists()){
    		file.mkdirs();
    	}
    	return dir;
    }*/

    /**
     * <p>Description: 生成文件的保存目录</p>
     *
     * @author hjt
     * @Date 2019年5月8日
     */
    public static String mkFilePath(String savePath) {
      /*  int end=ClassLoader.getSystemResource("").toString().indexOf("/windaka-sjwl-commons");
        savePath=ClassLoader.getSystemResource("").toString().substring(0,end)+savePath;*/
        //file既可以代表文件，也可以代表目录
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return savePath;
    }

    /**
     * <p>Description: 上传文件到服务器，返回文件保存路径</p>
     * map中必须带的参数：personCode，xqCode，fileName（.jpg或者.png），uploadFile（base64编码的字符串格式）
     *
     * @author hjt
     * @Date 2019年5月8日
     */
    public static String uploadFile(Map<String, Object> map) throws OssRenderException, FileUploadException, IOException {
        if (map.get("personCode") == null || map.get("personCode") == ""
                || map.get("xqCode") == null || map.get("xqCode") == ""
                //|| map.get("wyCode")==null || map.get("wyCode")==""
                || map.get("uploadFile") == null || map.get("uploadFile") == ""
                || map.get("fileName") == null || map.get("fileName") == "") {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "请求参数为空");
        }
        String personCode = map.get("personCode").toString();
        String xqCode = map.get("xqCode").toString();
        // String wyCode=map.get("wyCode").toString();
        String uploadFile = map.get("uploadFile").toString();
        String fileName = map.get("fileName").toString();
        //得到上传文件的保存目录
        String savePath = OssConstants.FILE_SAVEPATH;
        if (StringUtils.isBlank(fileName)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "上传的文件无文件名");
        }
        //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
        //处理掉获取到的上传文件的文件名的路径部分，只保留文件名部分
        fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
        //得到上传文件的扩展名
        String fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!"jpg".equals(fileExtName) && !"png".equals(fileExtName)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "上传的文件格式不正确");
        }
        if (StringUtils.isBlank(uploadFile)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "上传的文件为空");
        }
        Base64Decoder decoder = new Base64Decoder();
        //得到文件保存路径
        String savePathStr = mkFilePath(savePath + File.separator + xqCode);
        //创建一个文件输出流
        FileOutputStream fos = new FileOutputStream(savePathStr + File.separator + personCode + "." + fileExtName);

        byte[] b = decoder.decode(uploadFile);
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 1024;
            }
        }
        fos.write(b);
        fos.flush();
        //关闭输出流
        fos.close();
        return savePathStr + File.separator + fileName;//返回
    }


    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }



    /**
     * 功能描述: 生成文件到本地
     * @auther: lixianhua
     * @date: 2019/12/8 18:37
     * @param:
     * @return:
     */
    public static boolean inputStreamToLocalFile(byte [] byteArr , String path,String fileName) {
        OutputStream outStream=null;
        InputStream inputStream = null;
        try {
             inputStream = new ByteArrayInputStream(byteArr);
            String filePath = (path.endsWith("/")?path:path+"/" )+ fileName;
            log.info("***************************************filePath************************"+filePath);
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.setWritable(true, false);//linux其他目录写权限
            file.createNewFile();
            outStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                inputStream.close();
                outStream.close();
                return true;
            }catch (IOException ee){
                ee.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 功能描述: 转化输出的编码
     * @auther: lixianhua
     * @date: 2019/12/10 9:24
     * @param:
     * @return:
     */
    public static String toFtpFilename(String fileName) throws Exception {
        return new String(fileName.getBytes("GBK"),"ISO8859-1");
    }

    /**
     * 删除服务上的文件
     * @author Master.Pan
     * @date 2017年11月20日 上午11:06:48
     * @param filePath 路径
     * @param fileName 文件名
     * @return
     */
    public static boolean deleteServerFile(String filePath,String fileName){
        boolean delete_flag = false;
        File file = new File(filePath+fileName);
        if (file.exists() && file.isFile() && file.delete())
            delete_flag = true;
        else
            delete_flag = false;
        return delete_flag;
    }
    /**
     * 功能描述: 删除目录下所有文件
     * @auther: lixianhua
     * @date: 2020/1/18 7:50
     * @param:
     * @return:
     */
    public static void deleteAllFile(String filePath){
        File path = new File(filePath);
        File [] files = path.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                deleteAllFile(file.getPath());
                file.delete();
            }else{
                file.delete();
            }
        }
    }

    /**
     * 功能描述: 将文件转换成byte数组
     * @auther: lixianhua
     * @date: 2019/12/26 18:25
     * @param:
     * @return:
     */
    public static byte[] FileTobyte(File tradeFile){
        byte[] buffer = null;
        try
        {
            FileInputStream fis = new FileInputStream(tradeFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 功能描述: 字符串写入本地文件
     * @auther: lixianhua
     * @date: 2019/12/26 21:12
     * @param:
     * @return:
     */
    public static void writeStringtoFile(String result, String outPath, String outFileName) throws Exception {
        File dir = new File(outPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File txt = new File(outPath + "/" + outFileName);
        if (!txt.exists()) {
            txt.createNewFile();
        }
        FileWriter writer = new FileWriter(outPath + "/" + outFileName, true);
        writer.write(result);
        writer.close();
    }
    /**
     * 功能描述: 获取路径中的文件名
     * @auther: lixianhua
     * @date: 2020/1/4 8:41
     * @param:
     * @return:
     */
    public static String getFileNameFromPath(String pathName){

        String fName = pathName.trim();
        if(fName.contains("\\")){
            fName =  fName.substring(fName.lastIndexOf("\\")+1);
        }else if(fName.contains("/")){
            fName =   fName.substring(fName.lastIndexOf("/")+1);
        }
         return fName;

    }



    public static void main(String[] args) throws Exception {
//        System.out.println(getFileNameFromPath("a/b/car_access_record__base64Img__id__19796__20191230123505.jpg"));
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("aa",11);
//        map.remove("aa");
//        map.put("aa",22);
//        System.out.println(map.get("aa"));
        // 编码
//        String imgStr = imageToBase64Str("d:/002.jpg");

//        writeLocalStrOne(FtpUtil.stringToInputStream(imgStr),"D:/mmm.jpg");
        deleteAllFile("d:/home");
        System.out.println("执行完成");
//        String strImg = getImageStr("C:/Users/Administrator/Desktop/sao.jpg");
//        //System.out.println(strImg);
//        Map map = new HashMap();
//        map.put("personCode", "person");
//        map.put("xqCode", "xq");
//        map.put("wyCode", "wy");
//        map.put("fileName", "hjt.jpg");
//        map.put("uploadFile", strImg);
//        String filepath = uploadFile(map);
//        System.out.println(filepath);
    }
}
