package com.windaka.suizhi.mpi.task;

import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.constants.FtpConstants;
import com.windaka.suizhi.common.utils.FileUploadUtil;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.dao.FacePersonAttrDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.model.FaceCaptureAttrModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @ClassName CaptureFaceToyisaTask
 * @Description 人脸数据传给以萨ftp
 * @Author lixianhua
 * @Date 2020/1/14 10:21
 * @Version 1.0
 */
@Slf4j
@Service
public class CaptureFaceToyisaTask {

    @Resource
    private MsgSocketIdDao msgSocketIdDao;

    @Resource
    private FacePersonAttrDao facePersonAttrDao;

    public void run() {
        FTPClient ysClient = null;
        try {
            log.info("*********************人脸图像给以萨任务开始，开始时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "**********************");
            // 获取人脸最大值
            Integer faceNum = this.msgSocketIdDao.queryMsgSocketMaxId("face_person_attr_ys");
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
                ysClient = FtpUtil.loginFtp(PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_HOST),
                        PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_USERNAME),
                        PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PASSWORD));
                String time = TimesUtil.getServerDateTime(8, new Date());
                // 生成随机四位数
                Random random = new Random();
                String ranStr = String.format("%04d", random.nextInt(9999));
                // 文件名称
                String fileName = "wdt_face_" + time + "_" + ranStr + ".dat";
                for (FaceCaptureAttrModel model : faceList) {
                    log.info("****以萨一共获取" + faceList.size() + "条人脸记录****");
//                    File file = new File("D:/11.jpg");
                    File file = new File(CommonConstants.LOCAL_PROJECT_IMAGE_PATH + "/image/" + model.getBase64Img());
                    if (!file.exists()) {
                        continue;
                    }
//                    String base64Img = FileUploadUtil.getImageStr("D:/11.jpg");
                    String base64Img = FileUploadUtil.getImageStr(CommonConstants.LOCAL_PROJECT_IMAGE_PATH + "/image/" + model.getBase64Img());
                    base64Img = base64Img.replaceAll("[\\s*\t\n\r]", "");
                    Integer deviceId = model.getDeviceId();
                    String captureTime = TimesUtil.getServerDateTime(6, model.getCaptureTime());
                    String content = deviceId + ",," + captureTime + ",,,,,,," + base64Img + "," + model.getId();
                    // 写入临时文件
                    FileUploadUtil.writeStringtoFile(content, CommonConstants.FTP_DAT_TEMP, fileName);
                    num++;
//                    FileUploadUtil.writeStringtoFile(content, "d:/temp/dat/", fileName);
                }
                // 上传以萨ftp
//                InputStream inputStream = new FileInputStream(new File( "d:/temp/dat/" + fileName));
                InputStream inputStream = new FileInputStream(new File(CommonConstants.FTP_DAT_TEMP + fileName));
                FtpUtil.uploadFtpFile(ysClient, fileName, inputStream);
//                FtpUtil.uploadSimpleFile("10.10.6.131", 21,
//                        "han", "windaka0532",
//                        CommonConstants.GONGAN_YISA_FTP_FACE_PATH, fileName, inputStream);
                inputStream.close();
                // 删除临时目录文件
                FileUploadUtil.deleteServerFile(CommonConstants.FTP_DAT_TEMP, fileName);
            }
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("maxId", maxFaceId);
            updateMap.put("recordName", "face_person_attr_ys");
            // 更新最大值
            this.msgSocketIdDao.updateMsgSocketMaxId(updateMap);
            log.info("*********************人脸图像给以萨任务成功,一共成功写入" + num + "条人脸记录");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("*********************人脸图像给以萨任务失败，原因{}", e.getMessage());
        } finally {
            FtpUtil.closeConnect(ysClient);
        }
        log.info("*********************人脸图像给以萨任务结束，结束时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "**********************");
    }


}
