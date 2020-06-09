package com.windaka.suizhi.manageport.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.windaka.suizhi.common.constants.FastdfsConstants;
import com.windaka.suizhi.manageport.service.FastdfsService;
import com.windaka.suizhi.manageport.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName FastdfsServiceImpl
 * @Description fastdfs实现类
 * @Author lixianhua
 * @Date 2019/12/10 15:41
 * @Version 1.0
 */
@Slf4j
@Service
public class FastdfsServiceImpl implements FastdfsService {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 功能描述: 上传图片
     * @auther: lixianhua
     * @date: 2019/12/10 15:45
     * @param:
     * @return:
     */
    @Override
    public Map<String, Object> uploadFile(InputStream inputStream,String fileType)  {
        Map<String,Object> map = null;
        try {
            map = new HashMap<>(8);
            // 上传并且生成缩略图
            StorePath storePath = this.fastFileStorageClient.uploadImageAndCrtThumbImage(inputStream, inputStream.available(), fileType, null);
            // 带分组的路径
            map.put("filePath",storePath.getFullPath());
            // 不带分组的路径
            map.put("basePath",storePath.getPath());
            // 获取缩略图路径
            map.put("thumbnailPah", thumbImageConfig.getThumbImagePath(storePath.getFullPath()));
            // 获取完整url地址
            map.put("urlPath", CommonUtil.getResAccessUrl(storePath.getFullPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

}
