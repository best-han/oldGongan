package com.windaka.suizhi.common.constants;

/**
 * @ClassName CommonConstants
 * @Description 通用常量类
 * @Author lixianhua
 * @Date 2019/12/8 19:10
 * @Version 1.0
 */
public class CommonConstants {

    //   基本路径
    public final static String BASE_PATH = "/";
    public final static String LOCAL_SQL_PATH = "/home/windaka/switch_cloud_sql_file";
    public final static String BACKUP_LOCAL_SQL_PATH = "/home/windaka/switch_cloud_sql_file/backup";
    //  本地项目图片文件架（直接访问路径）
    public final static String LOCAL_PROJECT_IMAGE_PATH = "/opt/apache-tomcat-8.5.43/webapps/img";//
    //本地图片文件架
    public final static String LOCAL_IMAGE_FILE_BASE_PATH = "/home/windaka/switch_cloud_image_file";

    public final static String WENYUAN_ZIP_PATH = "/home/windaka/wenyuan_zip/";
    //本地图片文件基本路径(需要压缩的文件)
    //public final static String LOCAL_IMAGE_FILE_PATH = "f:\\switch_cloud_image_file";
    public final static String LOCAL_IMAGE_FILE_PATH = "/home/windaka/switch_cloud_image_file/image";
    //本地图片文件备份路径
    public final static String BACKUP_LOCAL_IMAGE_FILE_PATH = "/home/windaka/switch_cloud_image_file/backup";


    //  公安网sql路径
    public final static String GONGAN_SQL_PATH = "/home/windaka/switch_cloud_sql_file";//
    //  公安网tomcat本地图片路径
    public final static String GONGAN_IMAGE_PATH = "/opt/apache-tomcat-8.5.43/webapps/img";//


    //图片文件压缩包名
    public static final String ZIP_IMAGE_FILE_NAME = "switch_cloud_image.zip";//压缩包名
    //图片文件压缩包名前缀
    public static final String ZIP_IMAGE_FILE_NAME_PREFIX = "switch_cloud_image";

    // 以萨文远数据存放ftp路径
    public static final String GONGAN_YISA_FTP_PATH = "/cardata/wenyuan/";

    // 以萨车辆图片存放ftp路径
    public static final String GONGAN_YISA_FTP_PIC_PATH = "/cardata/picture/";

    // 以萨车辆图片存放ftp路径
    public static final String GONGAN_YISA_FTP_FACE_PATH = "/cardata/face/";

    // 以萨服务器文远压缩包临时目录
    public static final String FTP_ZIP_TEMP = "/temp/zip/";

    // 以萨服务器文远压缩包临时目录
    public static final String FTP_DAT_TEMP = "/temp/dat/";

    // 海博ftp图片路径（
    public static final String HB_FTP_PATH_PIC = "/picture/fenghuangcheng/";
    // 海博ftp的文件txt路径
    public static final String HB_FTP_PATH_TXT = "/data/";

    // 为海博临时存储数据的服务器路径（
    public static final String HB_FTP_PATH_TMP = "/hbtemp/";
}
