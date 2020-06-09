package com.windaka.suizhi.manageport.util;

import com.windaka.suizhi.common.constants.FastdfsConstants;

/**
 * @ClassName CommonUtil
 * @Description 公用工具类
 * @Author lixianhua
 * @Date 2019/12/10 17:11
 * @Version 1.0
 */
public class CommonUtil {

    // 封装图片完整URL地址
    public static String getResAccessUrl(String fullPath) {
        String fileUrl = FastdfsConstants.HTTP_PRODOCOL + "://" + FastdfsConstants.RES_HOST + "/" + fullPath;
        return fileUrl;
    }
}
