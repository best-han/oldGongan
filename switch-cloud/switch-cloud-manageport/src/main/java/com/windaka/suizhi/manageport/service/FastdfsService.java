package com.windaka.suizhi.manageport.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName FastdfsService
 * @Description fastdfs接口
 * @Author lixianhua
 * @Date 2019/12/10 15:41
 * @Version 1.0
 */
public interface FastdfsService {
    /**
     * 功能描述: 上传
     * @auther: lixianhua
     * @date: 2019/12/10 15:44
     * @param:
     * @return:
     */
    public Map<String ,Object> uploadFile(InputStream inputStream,String fileType);
}
