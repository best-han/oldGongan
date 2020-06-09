package com.windaka.suizhi.manageport.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.manageport.config.FtpProperties;
import com.windaka.suizhi.manageport.dao.FaceAlarmRecordDao;
import com.windaka.suizhi.manageport.dao.FaceTrackRecordDao;
import com.windaka.suizhi.manageport.dao.PersonDao;
import com.windaka.suizhi.manageport.model.FaceAlarmRecord;
import com.windaka.suizhi.manageport.service.FaceAlarmRecordService;
import com.windaka.suizhi.manageport.service.FastdfsService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import com.windaka.suizhi.manageport.util.VedioTimeUtil;
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
import java.util.*;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/5/9 0009 上午 10:17
 */
@Slf4j
@Service
public class FaceAlarmRecordServiceImpl implements FaceAlarmRecordService {
    @Autowired
    private FaceAlarmRecordDao faceAlarmRecordDao;
    @Autowired
    private PersonDao personDao;

    @Autowired
    private FastdfsService fastdfsService;

    @Autowired
    FtpProperties ftpProperties;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFaceTrackRecord(String xqCode, List<Map<String, Object>> params) throws OssRenderException, IOException {
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区Code不能为空");
        }

        for (int i = 0; i < params.size(); i++) {
            Map<String, Object> param = params.get(i);
            String clTime = (String) params.get(i).get("clTime");
            if ("0000-00-00 00:00:00".equals(clTime) || StringUtils.isBlank(clTime)) {
                params.get(i).put("clTime", null);
            }
            if (ObjectUtils.isEmpty(params.get(i).get("alarmTime"))) {
                params.get(i).put("alarmTime", null);
            }
            if (ObjectUtils.isEmpty(params.get(i).get("alarmLevel"))) {
                params.get(i).put("alarmLevel", 0);
            }
            if (ObjectUtils.isEmpty(params.get(i).get("clStatus"))) {
                params.get(i).put("clStatus", "0");//处理状态(0：未处理，1：已处理)
            }
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            if (ObjectUtils.isEmpty(params.get(i).get("creatBy"))) {
                params.get(i).put("creatBy", loginAppUser.getUserId());
            }
            if (ObjectUtils.isEmpty(params.get(i).get("updateBy"))) {
                params.get(i).put("updateBy", loginAppUser.getUserId());
            }
            // base64编码图片
            if (null != params.get(i).get("base64Img") && StringUtils.isNotBlank(params.get(i).get("base64Img").toString())) {
                byte[] byteArr = PicUtil.stringToInputStream(params.get(i).get("base64Img").toString());
                // 获取最大id值
                Integer maxId = faceAlarmRecordDao.getMaxRecord();
                String fileName = PicUtil.getPicName("record_abnormal", maxId == null ? 1 : (maxId + 1));
                // 图片放入打包路径
            /*    FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,  CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
                params.get(i).put("base64Img", fileName);*/

                //封装访问路径：年/月/日
                Date date=new Date();
                // 图片放入打包路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                params.get(i).put("base64Img", PicUtil.getPicRelativePath(date)+ fileName);

            }
            faceAlarmRecordDao.saveFaceAlarmRecord(xqCode, param);
            param.put("xqCode",xqCode);
            String sqlStr = SqlCreateUtil.getSqlByMybatis(FaceAlarmRecordDao.class.getName() + ".saveFaceAlarmRecord", param);
            SqlFileUtil.InsertSqlToFile(sqlStr);
        }
    }

    @Override
    public void updateFaceAlarmRecord(Map<String, Object> params) throws OssRenderException, IOException {
        String alarmId = (String) params.get("alarmId");
        if (StringUtils.isBlank(alarmId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "管理端报警记录主键ID不能为空");
        }
        FaceAlarmRecord faceAlarmRecord = new FaceAlarmRecord();
        faceAlarmRecord.setAlarmId((String) params.get("alarmId"));
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        faceAlarmRecord.setClUser(loginAppUser.getCname());
        faceAlarmRecord.setClContent((String) params.get("clContent"));
        faceAlarmRecordDao.updateFaceAlarmRecord(faceAlarmRecord);
        String sqlStr = SqlCreateUtil.getSqlByMybatis(FaceAlarmRecordDao.class.getName() + ".updateFaceAlarmRecord", params);
        SqlFileUtil.InsertSqlToFile(sqlStr);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<Map<String, Object>> queryAlarmRecordList(Map<String, Object> params) throws OssRenderException {

        //当前开始和结束日期是否正确
        String beginTime = (String) params.get("beginTime");
        String endTime = (String) params.get("endTime");
        if (!StringUtils.isBlank(beginTime)) {
            if (TimesUtil.checkDateFormat(beginTime)) {
//				params.put("beginTime",beginTime+" 00:00:00");
            } else {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "开始时间格式不正确");
            }
        } else {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "开始时间不能为空");
        }
        if (!StringUtils.isBlank(endTime)) {
            if (TimesUtil.checkDateFormat(endTime)) {
//				params.put("endTime",endTime+" 23:59:59");
            } else {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "结束时间格式不正确");
            }
        } else {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "结束时间不能为空");
        }

        String areaId = (String) params.get("areaId");
        if (StringUtils.isBlank(areaId)) {
            params.put("areaId", "0");
        }
        int totalRows = faceAlarmRecordDao.totalRows(params);//包括单查特殊报警也可以
        List<Map<String, Object>> list = Collections.emptyList();
        if (totalRows > 0) {
            long start, end;
            start = System.currentTimeMillis();
            PageUtil.pageParamConver(params, true);
            params.put("capVedioBeginTime", VedioTimeUtil.capVedioBeginTime());
            params.put("capVideoEndTime", VedioTimeUtil.capVideoEndTime());
            if (ObjectUtil.isNotNull(params.get("faceTypeCode")) && ObjectUtil.equal(params.get("faceTypeCode"), "5")) {//只查特殊报警（车）
                list = faceAlarmRecordDao.queryCarAlarmRecordList(params);
            } else if (ObjectUtil.isNotNull(params.get("faceTypeCode")) && StringUtils.isNotBlank(params.get("faceTypeCode").toString())) {//查人
                list = faceAlarmRecordDao.queryFaceAlarmRecordList(params);
            } else {//全查
                list = faceAlarmRecordDao.queryAllAlarmRecordList(params);
            }
            end = System.currentTimeMillis();
            log.info("*************************************************报警查询时间：" + (end - start) + "(ms)************************************************************************");
        }
		/*for(Map map : list){
			map.put("capVedioBeginTime", VedioTimeUtil.capVedioBeginTime());
			map.put("capVideoEndTime", VedioTimeUtil.capVideoEndTime());
		}*/
        return new Page<>(totalRows, MapUtils.getInteger(params, PageUtil.PAGE), list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> queryByAlarmId(String alarmId) throws OssRenderException {
        if (StringUtils.isBlank(alarmId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "管理端报警记录主键ID不能为空");
        }

        Map map = faceAlarmRecordDao.queryByAlarmId(alarmId);
        if (map == null) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "查询的报警记录不存在");
        }
        return map;
    }

    @Override
    public List<Map<String, Object>> queryPersonOrCar(Map<String, Object> params) throws OssRenderException {
        if (ObjectUtils.isEmpty(params.get("personOrCarName"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "请传入查询名称");
        }
        if (ObjectUtils.isEmpty(params.get("personOrCar"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "请传入查询类型");
        }

        List<Map<String, Object>> list = Collections.emptyList();
        if ("0".equals(params.get("personOrCar").toString())) {//人和车全查
            list = faceAlarmRecordDao.queryPersonOrCar(params);
        } else if ("1".equals(params.get("personOrCar").toString())) {//只搜索人名
            list = personDao.queryPersonByNameList(params);
        }
        return list;
    }
}
