package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.constants.FtpConstants;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.dao.FaceCaptureDeviceDao;
import com.windaka.suizhi.mpi.dao.FacePersonAttrDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.model.FaceCaptureAttrModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @ClassName CaputreFaceToytlf
 * @Description 抓拍头像给云天励飞
 * @Author lixianhua
 * @Date 2019/12/26 16:59
 * @Version 1.0
 */
@Slf4j
@Service
public class CaputreFaceToytlfTask {

    @Resource
    private MsgSocketIdDao msgSocketIdDao;

    @Resource
    private FacePersonAttrDao facePersonAttrDao;

    public void executeInternal() throws Exception {
        long start = System.currentTimeMillis();
        FTPClient ytlfClient = null;
        try {
            log.info("*********************人脸图像给云天励飞任务开始，开始时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "**********************");
            // 获取人脸最大值
            Integer faceNum = this.msgSocketIdDao.queryMsgSocketMaxId("face_person_attr_ytlf");
            Integer maxFaceId = this.facePersonAttrDao.getFaceMaxId();
            // 获取之前20分钟时间
            Calendar beforeTime = Calendar.getInstance();
            beforeTime.add(Calendar.MINUTE, -20);// 5分钟之前的时间
            Date before = beforeTime.getTime();
            Map<String, Object> map = new HashMap<>(8);
            map.put("maxId", faceNum == null ? 0 : faceNum);
            map.put("timeStr", before);
            // 查询最新人脸抓拍数据
            List<FaceCaptureAttrModel> faceList = facePersonAttrDao.getMoreRecord(map);
            int num = 0;
            if (null != faceList && faceList.size() > 0) {
                // 登陆云天励飞FTP
                ytlfClient = FtpUtil.loginFtp(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_HOST), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PASSWORD));
                String time = TimesUtil.getServerDateTime(8, new Date());
                // 切换日期目录
                FtpUtil.switchPath(ytlfClient,time.substring(0, 8));
                for (FaceCaptureAttrModel model : faceList) {
                    // 需要改成100+设备主键的形式 by lixianhua
                    String captureDevice = null == model.getDeviceId() ? "" : ("100" + model.getDeviceId());
//                    String filePath = "/" + time.substring(0, 8) + "/" + captureDevice + "/";
                    // 生成随机四位数
                    Random random = new Random();
                    String ranStr = String.format("%04d", random.nextInt(9999));
                    String specName = time.substring(0, 8) + "T" + time.substring(8) + ranStr + "_0_0_0_0.jpg";
                    InputStream inputStream = null;
                    File file = new File(CommonConstants.GONGAN_IMAGE_PATH + File.separator + "image" + File.separator + model.getBase64Img());
                    if (file.exists()) {
                        // 切换目录
                        FtpUtil.switchPath(ytlfClient,captureDevice);
                        inputStream = new FileInputStream(new File(CommonConstants.GONGAN_IMAGE_PATH + File.separator + "image" + File.separator + model.getBase64Img()));
                        FtpUtil.uploadFtpFile(ytlfClient,specName, inputStream);
//                        FtpUtil.uploadSimpleFile(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_HOST), Integer.parseInt(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PORT)), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PASSWORD), filePath, specName, inputStream);
                        inputStream.close();
                        num++;
                        // 返回上一级目录
                        FtpUtil.switchPath(ytlfClient,"..");
                    }
                }
            }
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("maxId", maxFaceId);
            updateMap.put("recordName", "face_person_attr_ytlf");
            // 更新最大值
            this.msgSocketIdDao.updateMsgSocketMaxId(updateMap);
            log.info("*********************人脸图像给云天励飞任务成功，一共写入" + num +"条数据");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("*********************人脸图像给云天励飞任务失败，原因:", e.getMessage());
        }finally {
            // 关闭FTP
            FtpUtil.closeConnect(ytlfClient);
        }
        long end = System.currentTimeMillis();
        log.info("*********************人脸图像给云天励飞任务结束，结束时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "一共用时"+ (end - start)/1000 +"秒**********************");

    }
}

