package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.FileUploadUtil;
import com.windaka.suizhi.common.utils.PicUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.config.FtpProperties;
import com.windaka.suizhi.manageport.dao.CarAccessRecordDao;
import com.windaka.suizhi.manageport.service.CarAccessRecordService;
import com.windaka.suizhi.manageport.service.FastdfsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 车辆出入场记录
 * @Author wcl
 * @Date 2019/8/22 0022 下午 4:24
 */
@Slf4j
@Service
public class CarAccessRecordServiceImpl implements CarAccessRecordService {

    @Autowired
    private CarAccessRecordDao carAccessRecordDao;

    @Autowired
    private FastdfsService fastdfsService;

    @Autowired
    FtpProperties ftpProperties;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCarAccessRecord(String xqCode, List<Map<String, Object>> carAccessRecords) throws OssRenderException, IOException {

        if (xqCode == null || StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "xqCode不能为空");
        }
        log.info("*****************************saveCarAccessRecord*****************************************8");

        for (int i = 0; i < carAccessRecords.size(); i++) {
            Map<String, Object> carAccessRecord = carAccessRecords.get(i);
            if (carAccessRecord.get("capTime") == null || carAccessRecord.get("capTime") == "") {
                carAccessRecord.remove("capTime");
            }
            // base64编码图片
            if (null != carAccessRecord.get("base64Img") && StringUtils.isNotBlank(carAccessRecord.get("base64Img").toString())) {
                byte[] picByte = PicUtil.stringToInputStream(carAccessRecord.get("base64Img").toString());
                // 获取最大id值
                int maxId = carAccessRecordDao.getMaxRecordId();
                String fileName = PicUtil.getPicName("car_access_record", maxId + 1);
                // 图片放入打包路径
     /*           FileUploadUtil.inputStreamToLocalFile(picByte, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(picByte, CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
                carAccessRecord.put("base64Img", fileName);*/

                //封装访问路径：年/月/日
                Date date=new Date();
                // 图片放入打包路径
                FileUploadUtil.inputStreamToLocalFile(picByte,
                        CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(picByte,
                        CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                carAccessRecord.put("base64Img", PicUtil.getPicRelativePath(date)+ fileName);
            }

            carAccessRecordDao.saveCarAccessRecord(xqCode, carAccessRecord);
            carAccessRecord.put("xqCode",xqCode);
//            String saveCarAccessRecord= SqlCreateUtil.getSqlByMybatis(CarAccessRecordDao.class.getName()+".saveCarAccessRecord",carAccessRecord);
            String []colNames={"xqCode","devChnName","devChnId","devChnNum","carNum","carColor","carColorName","carNumcolor","carNumcolorName",
                    "realCapturePicPath","capTime","carDirect","originalPicPath","base64Img"};
            int n=colNames.length;
            String []colValues=new String[colNames.length];
            for(int j=0;j<n;j++)
            {
                colValues[j]=SqlFileUtil.keyAddValue(carAccessRecord,colNames[j]);
            }
            String saveCarAccessRecordSql="INSERT INTO car_access_record(xq_code,dev_chn_name,dev_chn_id,dev_chn_num,car_num,car_color,car_color_name,carnum_color," +
                    "carnum_color_name," +
                    "real_capture_pic_path," +
                    "cap_time,car_direct," +
                    "original_pic_path,base64_img)\n" +
                    "        values ("+colValues[0]+","+colValues[1]+","+colValues[2]+","+colValues[3]+","+colValues[4]+","+colValues[5]+","+colValues[6]+","+colValues[7]+","+colValues[8]+"\n" +
                    "                ,"+colValues[9]+","+colValues[10]+","+colValues[11]+","+colValues[12]+","+colValues[13]+")";
            System.out.println(saveCarAccessRecordSql);
            SqlFileUtil.InsertSqlToFile(saveCarAccessRecordSql);
        }
    }
}
