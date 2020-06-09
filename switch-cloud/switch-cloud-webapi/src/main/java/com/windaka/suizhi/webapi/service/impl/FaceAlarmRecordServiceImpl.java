package com.windaka.suizhi.webapi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.webapi.dao.AbnormalRecordDao;
import com.windaka.suizhi.webapi.dao.CarRecordDao;
import com.windaka.suizhi.webapi.dao.FaceAlarmRecordDao;
import com.windaka.suizhi.webapi.dao.PersonDao;
import com.windaka.suizhi.webapi.model.FaceAlarmRecord;
import com.windaka.suizhi.webapi.service.FaceAlarmRecordService;
import com.windaka.suizhi.webapi.util.VedioTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
    private CarRecordDao carRecordDao;
    @Autowired
    private AbnormalRecordDao abnormalRecordDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFaceTrackRecord(String xqCode, List<Map<String, Object>> params) throws OssRenderException {
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "小区Code不能为空");
        }

        for (int i = 0; i < params.size(); i++) {
            Map<String, Object> param = params.get(i);
            String clTime = (String) params.get(i).get("clTime");
            if (clTime.equals("0000-00-00 00:00:00") || StringUtils.isBlank(clTime)) {
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
            faceAlarmRecordDao.saveFaceAlarmRecord(xqCode, param);
        }
    }

    @Override
    public void updateFaceAlarmRecord(Map<String, Object> params) throws OssRenderException {
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

    /**
     * 功能描述: 获取人脸报警明细
     *
     * @auther: lixianhua
     * @date: 2019/12/18 10:01
     * @param:
     * @return:
     */
    @Override
    public Map<String, Object> queryAlarmPersonList(Map<String, Object> params) throws OssRenderException {
        if (StringUtils.isBlank((String) params.get("limit"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "每页数量不能为空");
        }
        if (StringUtils.isBlank((String) params.get("page"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "当前页数不能为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>(8);
        // 获取车辆报警总数
        Integer totalNum = faceAlarmRecordDao.queryTotalNum(params);
        resultMap.put("totalRows", totalNum);
        resultMap.put("currentPage", Integer.parseInt(params.get("page").toString()));
        if (totalNum > 0) {
            params.put("start", (Integer.parseInt(params.get("page").toString()) - 1) * Integer.parseInt(params.get("limit").toString()));
            params.put("end", Integer.parseInt(params.get("page").toString()) * Integer.parseInt(params.get("limit").toString()));
            params.put("prefixUrl", PropertiesUtil.getLocalTomcatImageIp());
            // 获取车辆报警信息
            List<Map<String, Object>> list = faceAlarmRecordDao.queryPersonAlarmList(params);
            resultMap.put("list", list);
        }else{
            resultMap.put("list",new ArrayList<>());
        }
        return resultMap;
    }

    /**
     * 功能描述: 获取处理信息
     *
     * @auther: lixianhua
     * @date: 2019/12/18 21:53
     * @param:
     * @return:
     */
    @Override
    public Map<String, Object> queryHandleInfo(Map<String, Object> params) throws OssRenderException {
        if (StringUtils.isBlank((String) params.get("alarmType"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "报警类型不能为空");
        }
        if (StringUtils.isBlank((String) params.get("id"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "报警主键不能为空");
        }
        if ("1".equals(params.get("alarmType").toString())) {
            params.put("tableName", "face_alarm_record");
        } else if ("2".equals(params.get("alarmType").toString())) {
            params.put("tableName", "car_alarm_record");
        } else if ("3".equals(params.get("alarmType").toString())) {
            params.put("tableName", "record_abnormal");
        }
        Map<String, Object> map = this.faceAlarmRecordDao.queryHandleContent(params);
        if (null != map.get("handleTime")) {
            map.put("handleTime", TimesUtil.dateToString((Date) map.get("handleTime"), EnumDateStyle.YYYY_M_D_CN));
        }
        return map;
    }

    /**
     * 功能描述: 保存报警处理信息
     *
     * @auther: lixianhua
     * @date: 2019/12/19 9:06
     * @param:
     * @return:
     */
    @Override
    public void saveAlarmHandleInfo(Map<String, Object> params) throws OssRenderException {
        if (StringUtils.isBlank((String) params.get("alarmType"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "报警类型不能为空");
        }
        if (null==params.get("id")) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "报警主键不能为空");
        }
        if (StringUtils.isBlank((String) params.get("content")) || StringUtils.isBlank(((String) params.get("content")).trim())) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "报警内容不能为空");
        }
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        int updateNum = 0;
        // 人员报警
        if ("1".equals((String) params.get("alarmType"))) {
            params.put("clStatus","1");
            params.put("clTime",new Date());
            params.put("clUser",loginAppUser.getUsername());
            updateNum = this.faceAlarmRecordDao.updateFaceAlarmById(params);
        // 车辆报警
        } else if ("2".equals((String) params.get("alarmType"))) {
            params.put("clStatus","1");
            params.put("clTime",new Date());
            params.put("clUser",loginAppUser.getUsername());
            updateNum = this.carRecordDao.updateCarAlarmById(params);
        // 事件报警
        } else if ("3".equals((String) params.get("alarmType"))) {
            params.put("handleStatus","1");
            params.put("handleTime",new Date());
            params.put("handler",loginAppUser.getUsername());
            updateNum = this.abnormalRecordDao.updateAlarmById(params);
        }
        if (updateNum == 0){
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "异常处理失败");
        }
    }
    /**
     * 功能描述: 获取首页预警信息
     * @auther: lixianhua
     * @date: 2019/12/29 12:17
     * @param:
     * @return:
     */
    @Override
    public List<Map<String, Object>> queryHomeAlarmList(Map<String, Object> params) {
        params.put("prefixUrl", PropertiesUtil.getLocalTomcatImageIp());
        // 获取首页预警信息（包括流浪动物，车辆占道，人员预警，车辆预警）
        List<Map<String, Object>> list = faceAlarmRecordDao.queryHomeAlarmList(params);
        return list;
    }
    /**
     * 功能描述: 根据主键获取人员报警信息
     * @auther: lixianhua
     * @date: 2020/1/9 8:52
     * @param:
     * @return:
     */
    @Override
    public Map<String, Object> queryAlarmPersonById(Integer id) throws OssRenderException {
        if (null == id) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "主键不能为空");
        }
        Map<String,Object> condition = new HashMap<>(8);
        condition.put("id",id);
        condition.put("prefixUrl",PropertiesUtil.getLocalTomcatImageIp());
        Map<String,Object> result = this.faceAlarmRecordDao.queryFaceAlarmByCondition(condition);
        return result;
    }
}
