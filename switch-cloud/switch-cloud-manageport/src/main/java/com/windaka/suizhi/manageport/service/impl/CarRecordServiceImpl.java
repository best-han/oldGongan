package com.windaka.suizhi.manageport.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.manageport.config.FtpProperties;
import com.windaka.suizhi.manageport.dao.CarRecordDao;
import com.windaka.suizhi.manageport.dao.FaceAlarmRecordDao;
import com.windaka.suizhi.manageport.dao.XqDao;
import com.windaka.suizhi.manageport.service.CarRecordService;
import com.windaka.suizhi.manageport.service.FastdfsService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import javafx.beans.binding.ObjectBinding;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:17
 */
@Slf4j
@Service
public class CarRecordServiceImpl implements CarRecordService {
    @Autowired
    CarRecordDao carRecordDao;
    @Autowired
    XqDao xqDao;

    @Autowired
    private FastdfsService fastdfsService;

    @Autowired
    FtpProperties ftpProperties;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCarAlarmRecords(Map<String, Object> mapInfo) throws OssRenderException, IOException {
        if (ObjectUtil.isNull(mapInfo.get("xqCode"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区Code不能为空");
        }
        String xqCode=mapInfo.get("xqCode").toString();
        List<Map<String, Object>> list = (List<Map<String, Object>>) mapInfo.get("list");
        if (ObjectUtil.isNull(list) || list.size() < 1) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "数据不能为空");
        }
        checkParamsAndReturn(list);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        mapInfo.put("creBy", loginAppUser.getUserId());
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(0);
            map.put("creBy", loginAppUser.getUserId());
            // base64编码图片字符串
            if (null != map.get("base64Img") && StringUtils.isNotBlank(map.get("base64Img").toString())) {
                byte[] byteArr = PicUtil.stringToInputStream(map.get("base64Img").toString());
                // 获取最大id值
                Integer maxId = carRecordDao.getMaxRecord();
                String fileName = PicUtil.getPicName("car_alarm_record", maxId == null ? 1 : (maxId + 1));
                // 图片放入打包路径
           /*     FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,  CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
                map.put("base64Img", fileName);*/

                //封装访问路径：年/月/日
                Date date=new Date();
                // 图片放入打包路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                map.put("base64Img", PicUtil.getPicRelativePath(date)+ fileName);
            }
            map.put("xqCode",xqCode);
            carRecordDao.saveCarAlarmRecords(map);
            String sqlStr = SqlCreateUtil.getSqlByMybatis(CarRecordDao.class.getName() + ".saveCarAlarmRecords", map);
            SqlFileUtil.InsertSqlToFile(sqlStr);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCarJeevesRecords(Map<String, Object> map) throws OssRenderException, IOException {
        if (ObjectUtil.isNull(map.get("xqCode"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区Code不能为空");
        }
        List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
        if (ObjectUtil.isNull(list) || list.size() < 1) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "数据不能为空");
        }
        checkParamsAndReturn(list);
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        map.put("creBy", loginAppUser.getUserId());
        for (Map<String, Object> entity : list) {
            entity.put("xqCode", map.get("xqCode"));
            entity.put("creBy", map.get("creBy"));
            Object object = entity.get("base64Img");
            if (null != object && StringUtils.isNotBlank(object.toString())) {
                byte[] byteArr = PicUtil.stringToInputStream(object.toString());
                // 获取最大主键
                Integer id = carRecordDao.getCarMaxRecord();
                String fileName = PicUtil.getPicName("car_jeeves_record", (null != id ? id : 0) + 1);
                // 图片放入打包路径
              /*  FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,  CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
                entity.put("base64Img", fileName);*/

                //封装访问路径：年/月/日
                Date date=new Date();
                // 图片放入打包路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                entity.put("base64Img", PicUtil.getPicRelativePath(date)+ fileName);
            }
        }
        carRecordDao.saveCarJeevesRecords(map);
        String sqlStr = SqlCreateUtil.getSqlByMybatis(CarRecordDao.class.getName() + ".saveCarJeevesRecords", map);
        SqlFileUtil.InsertSqlToFile(sqlStr);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCarRecord(Map<String, Object> map) throws OssRenderException, IOException {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        map.put("updBy", loginAppUser.getUserId());
        map.put("clUser", loginAppUser.getCname());
        String faceTypeCode = (String) map.get("faceTypeCode");
        int i = 0;
        if ("51".equals(faceTypeCode)) {//车辆报警记录
            i = carRecordDao.updateCarAlarmRecord(map);
            String sqlStr = SqlCreateUtil.getSqlByMybatis(CarRecordDao.class.getName() + ".updateCarAlarmRecord", map);
            SqlFileUtil.InsertSqlToFile(sqlStr);
        } else {//52车辆占道
            i = carRecordDao.updateCarJeevesRecord(map);
            String sqlStr = SqlCreateUtil.getSqlByMybatis(CarRecordDao.class.getName() + ".updateCarJeevesRecord", map);
            SqlFileUtil.InsertSqlToFile(sqlStr);
        }
        if (i == 0) {
            throw new OssRenderException(ReturnConstants.MSG_OPER_FAILED, "无对应操作数据");
        }
    }

    @Override
    public Map queryCarRecord(Map<String, Object> params) throws OssRenderException {
        String faceTypeCode = (String) params.get("faceTypeCode");
        Map map = new HashMap();
        if ("51".equals(faceTypeCode)) {//车辆报警记录
            map = carRecordDao.queryCarAlarmRecord(params.get("alarmId").toString());
        } else {//52车辆占道
            map = carRecordDao.queryCarJeevesRecord(params.get("alarmId").toString());
        }
        return map;
    }

    /**
     * 校验上传参数是否为空，为空设置默认值
     *
     * @param list
     * @return
     */
    public void checkParamsAndReturn(List<Map<String, Object>> list) {
        for (Map map : list) {
            if (ObjectUtils.isEmpty(map.get("status"))) {
                map.put("status", "0");//状态（0正常 1删除 2停用）
            }
            if (ObjectUtils.isEmpty(map.get("clStatus"))) {
                map.put("clStatus", "0");//处理状态(0：未处理，1：已处理)
            }
            if (ObjectUtils.isEmpty(map.get("capTime"))) {
                map.put("capTime", null);//
            }
            if (ObjectUtils.isEmpty(map.get("clTime"))) {
                map.put("clTime", null);//
            }
        }
    }

    @Override
    public Map<String, Object> queryCarAccessRecordList(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "区域不能为空");
        }
        //当前开始和结束日期是否正确
        String beginTime = (String) params.get("beginTime");
        String endTime = (String) params.get("endTime");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        if (!StringUtils.isBlank(endTime)) {
            if (!TimesUtil.checkDateFormat(endTime)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "结束时间格式不正确");
            }
        } else {//默认最近7天
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"结束时间不能为空");
            cal.add(Calendar.DATE, 1);//含头不含尾，所以最后一天加1
            params.put("endTime", sdf2.format(cal.getTime()));
        }
        if (!StringUtils.isBlank(beginTime)) {
            if (!TimesUtil.checkDateFormat(beginTime)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "开始时间格式不正确");
            }
        } else {//默认最近7天
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"开始时间不能为空");
            cal.add(Calendar.DATE, -1);
            params.put("beginTime", sdf2.format(cal.getTime()));
        }
        int totalRows = carRecordDao.queryTotalCarAccessRecord(params);
        List<Map<String, Object>> list = Collections.emptyList();
        if (totalRows > 0) {
            PageUtil.pageParamConver(params, true);
            list = carRecordDao.queryCarAccessRecordList(params);
        }
        Map<String, Object> mapResult = new HashMap<>();//返回结果map
        mapResult.put("list", list);
        mapResult.put("totalRows", totalRows);
        mapResult.put("currentPage", MapUtils.getInteger(params, PageUtil.PAGE));
        return mapResult;
    }

    @Override
    public Map<String, Object> queryCarOverview(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "区域不能为空");
        }
        //当前开始和结束日期是否正确
        String beginTime = (String) params.get("beginTime");
        String endTime = (String) params.get("endTime");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        if (!StringUtils.isBlank(endTime)) {
            if (!TimesUtil.checkDateFormat(endTime)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "结束时间格式不正确");
            }
        } else {//默认最近7天
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"结束时间不能为空");
            cal.add(Calendar.DATE, 1);//含头不含尾，所以最后一天加1
            params.put("endTime", sdf2.format(cal.getTime()));
        }
        if (!StringUtils.isBlank(beginTime)) {
            if (!TimesUtil.checkDateFormat(beginTime)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "开始时间格式不正确");
            }
        } else {//默认最近7天
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"开始时间不能为空");
            cal.add(Calendar.DATE, -1);
            params.put("beginTime", sdf2.format(cal.getTime()));
        }
        Map<String, Object> mapResult = new HashMap();
        params.put("carDirect", "8");
        int inNum = this.carRecordDao.queryTotalCarAccessRecord(params);
        mapResult.put("inNum", Integer.valueOf(inNum));
        params.put("carDirect", "9");
        int outNum = this.carRecordDao.queryTotalCarAccessRecord(params);
        mapResult.put("outNum", Integer.valueOf(outNum));
        int strangeNum = this.carRecordDao.totalStrangCar(params);
        mapResult.put("strangeNum", Integer.valueOf(strangeNum));
        int jeevesNum = this.carRecordDao.totalJeevesCar(params);
        mapResult.put("jeevesNum", Integer.valueOf(jeevesNum));
        Map map = this.xqDao.queryCountInfo(params);
        if (MapUtils.isEmpty(map)) {
            mapResult.put("parkingNum", Integer.valueOf(0));
        } else {
            mapResult.put("parkingNum", map.get("parkingNum"));
        }
        return mapResult;
    }


}
