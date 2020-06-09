package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.constants.FtpConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.manageport.config.FtpProperties;
import com.windaka.suizhi.manageport.dao.FaceCaptureDeviceDao;
import com.windaka.suizhi.manageport.dao.FacePersonCaptureDao;
import com.windaka.suizhi.manageport.model.FacePersonAttr;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Service
public class FacePersonCaptureService {

    @Resource
    private FacePersonCaptureDao facePersonCaptureDao;

    @Autowired
    private FastdfsService fastdfsService;

    @Autowired
    private FaceCaptureDeviceDao faceCaptureDeviceDao;

    @Autowired
    FtpProperties ftpProperties;


    @Transactional(rollbackFor = Exception.class)
    public void addFacePersonAttr(FacePersonAttr facePersonAttr) throws OssRenderException, IOException {
        if (StringUtils.isNotBlank(facePersonAttr.getBase64Img())) {
            byte[] byteArr = PicUtil.stringToInputStream(facePersonAttr.getBase64Img());
            // 根据最大主键值
            Integer id = facePersonCaptureDao.getMaxId();
            String fileName =  PicUtil.getPicName("face_person_attr", null==id?1:(id+1));
            // 图片放入打包路径
         /*   FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
            // 图片放入访问路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,  CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
            facePersonAttr.setBase64Img(fileName);*/

            //封装访问路径：年/月/日
            Date date=new Date();
            // 图片放入打包路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,
                    CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
            // 图片放入访问路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,
                    CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
            facePersonAttr.setBase64Img(PicUtil.getPicRelativePath(date)+ fileName);

            // 如果是人脸图片(personId不为空也不为null)  则人脸图像给云天励飞
          /*  if(StringUtils.isNotBlank(facePersonAttr.getPersonId())){
                String time =TimesUtil.getServerDateTime(8,new Date());
                //获取设备信息
                Map<String,Object> deviceMap = this.faceCaptureDeviceDao.getFaceDevice(facePersonAttr.getCaptureDeviceId());
                String captureDevice = null==deviceMap.get("id")?"":String.format("%09d",Integer.parseInt(deviceMap.get("id").toString()));
                String filePath = "/"+time.substring(0,8)+"/"+captureDevice+"/";
                String specName = time.substring(0,8)+"T"+time.substring(8)+"_0_0_0_0.jpg";
                FtpUtil.uploadSimpleFile(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_HOST),Integer.parseInt(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PORT)),PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_USERNAME),PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PASSWORD),filePath,specName,byteArr);
            }*/
        }
        facePersonCaptureDao.insertSelective(facePersonAttr);
        String sql=SqlCreateUtil.getSqlByBean(FacePersonCaptureDao.class.getName()+".insertSelective",facePersonAttr);
        SqlFileUtil.InsertSqlToFile(sql);
    }

}
