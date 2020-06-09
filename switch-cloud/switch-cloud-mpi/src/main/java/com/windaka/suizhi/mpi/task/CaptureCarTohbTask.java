package com.windaka.suizhi.mpi.task;

import com.alibaba.fastjson.JSON;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.constants.FtpConstants;
import com.windaka.suizhi.common.utils.FileUploadUtil;
import com.windaka.suizhi.common.utils.FtpUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.common.utils.TimesUtil;
import com.windaka.suizhi.mpi.dao.CarAccessRecordDao;
import com.windaka.suizhi.mpi.dao.DictDao;
import com.windaka.suizhi.mpi.dao.MsgSocketIdDao;
import com.windaka.suizhi.mpi.model.CarAccessDto;
import com.windaka.suizhi.mpi.model.CarInOut;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName CaptureCarTohbTask
 * @Description 车辆抓拍数据通过ftp给海博
 * @Author lixianhua
 * @Date 2019/12/26 20:03
 * @Version 1.0
 */
@Service
@Slf4j
public class CaptureCarTohbTask {


    private String fileStr;

    @Resource
    MsgSocketIdDao msgSocketIdDao;

    @Autowired
    CarAccessRecordDao carAccessRecordDao;

    @Autowired
    DictDao dictDao;

    public void executeInternal() {
        FTPClient hbClient = null;
        InputStream inputStream = null;
        InputStream stream = null;
        long start = System.currentTimeMillis();
        try {
            log.info("*********************（海博）车辆抓拍数据接入公安网任务开始，开始时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "**********************");
            Integer lastId = msgSocketIdDao.queryMsgSocketMaxId("car_access_record_hb");
            List<CarInOut> carList = carAccessRecordDao.queryCarInfoForHb(lastId);
            int num = 0;
            if (null != carList && carList.size() > 0) {
                // 登陆海博ftp
//                hbClient = FtpUtil.loginFtp("10.10.6.131", "21","han", "windaka0532");
                hbClient = FtpUtil.loginFtp( PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_HOST),
                       PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PORT), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_USERNAME),
                        PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PASSWORD));
                String time = TimesUtil.getServerDateTime(8, new Date());
                if (StringUtils.isNotBlank(fileStr) && time.substring(0, 10).equals(fileStr.substring(0, 10))) {
                    fileStr = fileStr.substring(0, 10) + String.format("%02d", Integer.parseInt(fileStr.substring(10)) + 1);
                } else {
                    fileStr = time.substring(0, 10) + "01";
                }
                // 文件名称
                String fileName = "passcar_" + fileStr + ".txt";
                // 图片目录
                String filePath = CommonConstants.HB_FTP_PATH_PIC + TimesUtil.getServerDateTime(12, new Date()) + "/";
                String imagePath = "/fenghuangcheng/" + TimesUtil.getServerDateTime(12, new Date()) + "/";
                // 切换ftp目录
                FtpUtil.switchPath(hbClient,filePath);
                // 本地txt写入标识
                boolean txtFlag = false;
                for (CarInOut car : carList) {
                    CarAccessDto carDto = new CarAccessDto();
                    carDto.setId(UUID.randomUUID().toString().replace("-", ""));
                    // 根据deviceId获取国际编码
                    Map<String, Object> condition = new HashMap<>(8);
                    condition.put("dictLabel", car.getDevId());
                    List<Map<String, Object>> result = this.dictDao.getDictList(condition);
                    if (null != result && result.size() == 1) {
                        carDto.setDeviceID(result.get(0).get("dictValue").toString());
                    } else {
                        carDto.setDeviceID("");
                    }
                    carDto.setHasPlate(Strings.isBlank(car.getCarNum()) ? "false" : "true");
                    carDto.setIdCard(car.getIdCard());
                    String picName = FileUploadUtil.getFileNameFromPath(car.getImg());
                    carDto.setImage(imagePath + picName);
                    carDto.setPlateClass("02");
                    String color = car.getPlateColor();
                    // 车牌颜色与文档颜色对应
                    if (StringUtils.isNotBlank(color)) {
                        if ("0".equals(color)) {
                            color = "1";
                        } else if ("1".equals(color)) {
                            color = "2";
                        } else if ("2".equals(color)) {
                            color = "4";
                        } else if ("3".equals(color)) {
                            color = "6";
                        } else if ("4".equals(color)) {
                            color = "5";
                        } else if ("5".equals(color)) {
                            color = "8";
                        } else if ("6".equals(color)) {
                            color = "99";
                        } else if ("7".equals(color)) {
                            color = "9";
                        } else if ("8".equals(color)) {
                            color = "3";
                        } else {
                            color = "5";
                        }
                    } else {
                        color = "5";
                    }
                    carDto.setPlateColor(color);
                    carDto.setShotTime(car.getTime());
                    carDto.setPlateNo(Strings.isBlank(car.getCarNum()) ? "无车牌" : car.getCarNum());
                    String json = JSON.toJSONString(carDto);
                    // 判断文件是否存在
                    File file = new File("d:/002.jpg");
//                    File file = new File(CommonConstants.LOCAL_PROJECT_IMAGE_PATH + "/image/"+car.getImg());
                    if (!file.exists()) {
                        log.info("***********************" + car.getImg() +
                                "文件不存在");
                        continue;
                    }
                    // 图片上传海博ftp
                    if(StringUtils.isNotBlank(picName)){
                        num++;
                        inputStream = new FileInputStream(file);
                        // 图片上传ftp
//                    FtpUtil.uploadSimpleFile(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_HOST), Integer.parseInt(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PORT)), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PASSWORD), filePath, picName, inputStream);
//                    FtpUtil.uploadSimpleFile("10.10.6.131", 21,
//                            "han","windaka0532",
//                            CommonConstants.HB_FTP_PATH_PIC, picName, inputStream);
                       boolean flag =  FtpUtil.uploadFtpFile(hbClient, picName, inputStream);
                        inputStream.close();
                        if(flag){
                            txtFlag = true;
                            // 写入txt文件中
                            FileUploadUtil.writeStringtoFile(json, CommonConstants.HB_FTP_PATH_TMP, fileName);
                        }
                    }
                }
                if(txtFlag){
                    // txt文件上传ftp
                    stream = new FileInputStream(new File(CommonConstants.HB_FTP_PATH_TMP + fileName));
                FtpUtil.uploadSimpleFile(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_HOST), Integer.parseInt(PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PORT)), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_USERNAME), PropertiesUtil.getPertiesValue(FtpConstants.YTLF_FTP_PASSWORD), CommonConstants.HB_FTP_PATH_TXT, fileName, stream);
//                    FtpUtil.uploadSimpleFile("10.10.6.131", 21,
//                            "han","windaka0532",
//                            CommonConstants.HB_FTP_PATH_TXT, fileName, stream);
                    stream.close();
                    // 删除服务器上的文件
                    FileUploadUtil.deleteServerFile(CommonConstants.HB_FTP_PATH_TMP, fileName);
                }
                Map<String, Object> updateMap = new HashMap<>();
                updateMap.put("maxId", carList.get(carList.size() - 1).getId());
                updateMap.put("recordName", "car_access_record_hb");
                // 更新最大值
                this.msgSocketIdDao.updateMsgSocketMaxId(updateMap);
            }
            log.info("（海博）车辆抓拍数据接入公安网任务执行成功,一共执行了"+  num +"条数据");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("（海博）车辆抓拍数据接入公安网任务执行失败");
        }
        FtpUtil.closeConnect(hbClient);
        long end = System.currentTimeMillis();
        long all = (end - start)/1000;
        log.info("*********************（海博）车辆抓拍数据接入公安网任务结束，结束时间为:" + TimesUtil.getServerDateTime(9, new Date()) + "一共用时"+ all +"秒**********************");
    }

}
