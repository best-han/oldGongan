package com.windaka.suizhi.api.common;

/**
 * 运营管理端常量类
 * @author: hjt
 * @Date: 2019/1/9 15:35
 * @Version 1.0
 */
public class OssConstants {
    public final static String OSS_REDIS_PREFIX = "oss:"; //OSSredis存储前缀

    public final static String FILE_SAVEPATH="/home/windaka/project/upload";//人脸图片上传文件保存路径（服务器）
    public final static int HALF_TIME_VEDIO=15;//视频播放时长的一半（秒）
    /**
     * 缓存华为手机的redis 目标设备Token，这里是hash结构存储
     */
    public static final String MOBILE_USERNAME_HW = "mobile:username:hv";
    public static final String  MOBILE_USERNAME_XM = "mobile:username:xiaomi";//小米
    public static final String  MOBILE_USERNAME_VIVO = "mobile:username:vivo";//vivo
    public static final String  MOBILE__USERNAME_OPPO = "mobile:username:oppo";//oppo

    /**
     * 手机型号
     */
    public static final String  MOBILE_HW = "huawei";//华为
    public static final String  MOBILE_XM = "xiaomi";//小米
    public static final String  MOBILE_VIVO = "vivo";//vivo
    public static final String  MOBILE_OPPO = "oppo";//oppo

}
