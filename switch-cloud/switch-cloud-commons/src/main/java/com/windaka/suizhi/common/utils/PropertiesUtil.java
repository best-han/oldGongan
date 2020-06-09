package com.windaka.suizhi.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName PropertiesUtil
 * @Description 配置工具类
 * @Author lixianhua
 * @Date 2019/12/9 19:02
 * @Version 1.0
 */
@Slf4j
public class PropertiesUtil {

    /**
     * 系统资源文件
     */
    protected static final String SYSTEM_PROPERTIES_FILE_NAME = "properties.properties";

    public static final String CAPTURE_DEVICE_URI = "http://56.35.136.172:10000/";//gongan的流媒体服务器地址


    /**
     * 获取资源文件的共通方法
     *
     * @param propertiesFileName
     * @return
     * @throws IOException
     */
    protected static Properties getProperties(String propertiesFileName) throws IOException {
        Properties prop = new Properties();
        InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesFileName);

        try {
            prop.load(is);
        } catch (IOException e) {
            throw e;
        }

        return prop;
    }
    /**
     * 功能描述: 根据参数获取配置值
     * @auther: lixianhua
     * @date: 2019/12/10 8:11
     * @param:
     * @return:
     */
    public static String getPertiesValue(String key) {
        Properties properties = getProperties();
        return properties == null ? "" : properties.getProperty(key);
    }

    /**
     * 功能描述: 获取配置实体类
     * @auther: lixianhua
     * @date: 2019/12/10 8:12
     * @param:
     * @return:
     */
    public static Properties getProperties() {
        try {
            return getProperties(SYSTEM_PROPERTIES_FILE_NAME);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }


    /**
     * 功能描述: 读取properties的全部信息
     * @auther: lixianhua
     * @date: 2019/12/10 8:14
     * @param:
     * @return:
     */
    public Map<String, String> readAllProperties() throws FileNotFoundException, IOException {
        Properties props = getProperties();
        //保存所有的键值
        Map<String, String> map = new HashMap<String, String>();
        Enumeration en = props.propertyNames();
        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            String Property = props.getProperty(key);
            map.put(key, Property);
        }
        return map;
    }

    /**
     * 获取本机图片根目录  hjt
     */
    public static String getLocalTomcatImageIp() {
       // TODO Auto-generated method stub
    /*   InetAddress ia=null;
       try {
          ia=ia.getLocalHost();
          //String localname=ia.getHostName();
          String localip=ia.getHostAddress();
          //String localTomcatImageIp="http://"+localip+":8080/img/image/";//公安内网解压后会多一个image文件夹
           String localTomcatImageIp="http://"+localip+":8080/img/";//6.54测试环境
          log.info("本机图片访问根路径是 ："+localTomcatImageIp);
          return localTomcatImageIp;
       } catch (Exception e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }*/
      return "http://47.103.36.248:8080/img/";
      // return "http://56.35.130.2:8080/img/image/";//gongan
    }



}
