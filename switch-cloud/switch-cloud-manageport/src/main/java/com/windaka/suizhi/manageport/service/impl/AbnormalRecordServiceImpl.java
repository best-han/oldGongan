package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.manageport.config.FtpProperties;
import com.windaka.suizhi.manageport.dao.AbnormalRecordDao;
import com.windaka.suizhi.manageport.service.AbnormalRecordService;
import com.windaka.suizhi.manageport.service.FastdfsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 异常行为 hjt
 */
@Slf4j
@Service
public class AbnormalRecordServiceImpl implements AbnormalRecordService {
    @Autowired
    private AbnormalRecordDao abnormalRecordDao;

    @Autowired
    private FastdfsService fastdfsService;

    @Autowired
    FtpProperties ftpProperties;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateAbnormalRecord(Map<String, Object> params) throws OssRenderException, IOException {
        if (null != params.get("base64Img") && StringUtils.isNotBlank(params.get("base64Img").toString())) {
            byte[] byteArr  = PicUtil.stringToInputStream(params.get("base64Img").toString());
            // 根据manageId主键
            int id = abnormalRecordDao.getIdByManageId(params);
            String fileName = PicUtil.getPicName("record_abnormal", id);
            // 图片放入打包路径
          /*  FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
            // 图片放入访问路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,  CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
            params.put("base64Img", fileName);*/

            //封装访问路径：年/月/日
            Date date=new Date();
            // 图片放入打包路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,
                    CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
            // 图片放入访问路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,
                    CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
            params.put("base64Img", PicUtil.getPicRelativePath(date)+ fileName);
        }
        abnormalRecordDao.updateAbnormalRecord(params);

    }

    @Override
    public Page<Map<String, Object>> queryAbnormalRecordPageList(Map<String, Object> params) throws OssRenderException {
        int totalRows = abnormalRecordDao.queryTotalAbnormalRecord(params);
        List<Map<String, Object>> list = Collections.emptyList();
        if (totalRows > 0) {
            PageUtil.pageParamConver(params, true);
            list = abnormalRecordDao.queryAbnormalRecordPageList(params);
        }
        return new Page<>(totalRows, MapUtils.getInteger(params, PageUtil.PAGE), list);
    }

    @Override
    public List<Map<String, Object>> queryAbnormalRecordList(Map<String, Object> params) throws OssRenderException {
        List<Map<String, Object>> list = abnormalRecordDao.queryAbnormalRecordList(params);
        return list;
    }


}
