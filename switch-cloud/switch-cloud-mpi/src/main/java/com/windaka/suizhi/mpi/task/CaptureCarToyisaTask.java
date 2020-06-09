package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.constants.FtpConstants;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.config.FtpProperties;
import com.windaka.suizhi.mpi.dao.CarAccessRecordDao;
import com.windaka.suizhi.mpi.dao.DictDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.model.CarAccessYsDto;
import com.windaka.suizhi.mpi.model.CarInOut;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CaptureCarToyisaTask
 * @Description 车辆数据写成sql，传给以萨
 * @Author lixianhua
 * @Date 2019/12/27 8:50
 * @Version 1.0
 */
@Slf4j
@Service
public class CaptureCarToyisaTask {

    @Resource
    MsgSocketIdDao msgSocketIdDao;

    @Resource
    FtpProperties ftpProperties;


    @Autowired
    CarAccessRecordDao carAccessRecordDao;

    @Autowired
    DictDao dictDao;


    public void runTask() {
        FTPClient ysClient = null;
        InputStream inputStream = null;
        InputStream stream = null;
        long start = System.currentTimeMillis();
        try {
            log.info("*********************以萨车辆任务开始，开始时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "**********************");
            Integer lastId = msgSocketIdDao.queryMsgSocketMaxId("car_access_record_ys");
            // 获取最新车辆抓拍数据
            List<CarInOut> carList = carAccessRecordDao.queryCarInfoForYs(lastId);
            int num = 0;
            if (null != carList && carList.size() > 0) {
                // 登陆以萨ftp
               ysClient =  FtpUtil.loginFtp(PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_HOST),
                        PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_USERNAME),
                        PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PASSWORD));
//                ysClient =  FtpUtil.loginFtp("10.10.6.131", "21",
//                                "han","windaka0532");
                // 切换ftp目录
                FtpUtil.switchPath(ysClient, CommonConstants.GONGAN_YISA_FTP_PIC_PATH);
                for (CarInOut temp : carList) {
                    Date captureTime = temp.getCaptureTime();
                    // 抓拍时间必须存在
                    if (null == captureTime) {
                        continue;
                    }
                    String time = String.valueOf(captureTime.getTime());
                    String imgPath = StringUtils.isBlank(temp.getImg()) ? "" : temp.getImg();
                    String carNum = StringUtils.isBlank(temp.getCarNum()) ? "无牌" : temp.getCarNum();
                    String devId = StringUtils.isBlank(temp.getDevId()) ? "" : temp.getDevId();
                    String devNo = "";
                    Map<String, Object> condition = new HashMap<>(8);
                    condition.put("dictLabel", devId);
                    // 根据设备id获取编号
                    List<Map<String, Object>> list = dictDao.getDictList(condition);
                    if (null != list && list.size() == 1) {
                        devNo = list.get(0).get("dictValue").toString();
                    }
                    String picName = devNo + "_" + time + "_" + carNum + ".jpg";
                    // 图片上传以萨ftp
//                    File file = new File("d:/002.jpg");
                    File file = new File(CommonConstants.LOCAL_PROJECT_IMAGE_PATH + "/image/" + imgPath);
                    // 判断文件是否存在img
                    if (file.exists()) {
                        inputStream = new FileInputStream(file);
//                        FtpUtil.uploadSimpleFile(PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_HOST),
//                                Integer.parseInt(PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PORT)), PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_USERNAME),
//                                PropertiesUtil.getPertiesValue(FtpConstants.YS_FTP_PASSWORD), CommonConstants.GONGAN_YISA_FTP_PIC_PATH, picName, inputStream);
                        boolean flag= FtpUtil.uploadFtpFile(ysClient, picName, inputStream);
//                        FtpUtil.uploadSimpleFile("10.10.6.131", 21,
//                                "han","windaka0532",
//                                CommonConstants.GONGAN_YISA_FTP_PIC_PATH, picName, inputStream);
                        if(flag){
                            num++;
                        }
                        inputStream.close();
                    }
                }
                // 更新最大值
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("maxId", carList.get(carList.size() - 1).getId());
                updateMap.put("recordName", "car_access_record_ys");
                // 更新最大值
                this.msgSocketIdDao.updateMsgSocketMaxId(updateMap);
            }
            log.info("以萨车辆任务执行成功，一共执行成功" + num + "条数据");
        } catch (Exception e) {
            log.error("以萨车辆任务执行失败", e);
        }
        long end = System.currentTimeMillis();
        log.info("*********************以萨车辆任务结束，结束时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "一共用时"+ (end - start)/1000 +"秒**********************");
    }


}
