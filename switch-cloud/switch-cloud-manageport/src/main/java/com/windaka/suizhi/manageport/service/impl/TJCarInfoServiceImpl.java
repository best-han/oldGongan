package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.TJCarInfoDao;
import com.windaka.suizhi.manageport.model.TJCarInfo;
import com.windaka.suizhi.manageport.service.TJCarInfoService;
import com.windaka.suizhi.manageport.util.DateUtil;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计－车辆信息ServiceImpl
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@Service
public class TJCarInfoServiceImpl implements TJCarInfoService {


    @Autowired
    private TJCarInfoDao dao;

    /**
     * 新增统计－车辆信息
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMap(Map<String, Object> params) throws OssRenderException, IOException {
        String sqlContent;
        List<Map<String,Object>> dataList = (List<Map<String,Object>>)params.get("dataList");
        String operDate = (String)params.get("oper_date");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOperDate = null;
        String xqCode = (String) params.get("xqCode");

        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        if(StringUtils.isBlank(operDate)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"操作时间不能为空");
        }else{
            try{
                dateOperDate = df.parse(operDate);
            }catch (Exception e){
                throw new OssRenderException(ReturnConstants.CODE_FAILED,"操作时间日期格式不正确");
            }
        }
        if(dataList.size() == 0){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"上传的数据列表不能为空");
        }

        for (Map<String,Object> map:dataList) {
            int hour = Integer.parseInt(map.get("timing").toString());
            //删除记录
            dao.delete(xqCode,dateOperDate,hour);
//            sqlContent=String.format("delete from tj_car_stream where xq_code = %s and oper_date = %s and timing = %s",xqCode,dateOperDate,hour);
//            SqlFileUtil.InsertSqlToFile(sqlContent);

            //region Map转实体类对象
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

            TJCarInfo model = new TJCarInfo();
            model.setXq_code((String) params.get("xqCode"));
            model.setTiming(hour);
            model.setIn(Integer.parseInt(map.get("in").toString()));
            model.setOut(Integer.parseInt(map.get("out").toString()));
            model.setCre_by(loginAppUser.getUserId());
            model.setCre_time(new Date());
            model.setUpd_by(loginAppUser.getUserId());
            model.setUpd_time(new Date());
            model.setOperDate(dateOperDate);
            //endregion

            //保存数据
            dao.save(model);
//            sqlContent= SqlCreateUtil.getSqlByBean(TJCarInfoDao.class.getName()+".save",model);
//            SqlFileUtil.InsertSqlToFile(sqlContent);
        }
    }

    /**
     * 根据小区Code删除车辆信息
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String xqCode, Date operDate, int hour){
        int result = dao.delete(xqCode,operDate,hour);
    }

    /**
     * 查询列表
     * @return
     *
     * @author wcl
     * @create 2019-05-30
     */
    @Override
    public Map<String, Object> query(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        String wyCode = (String) params.get("wyCode");
        String xqCode = (String) params.get("xqCode");
        params.put("todayStartTime", DateUtil.getTodayStartTime());
        params.put("todayEndTime",DateUtil.getTodayEndTime());

        if (StringUtils.isBlank(areaId)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区编码不能为空");
//        }
//        if (StringUtils.isBlank(xqCode)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"物业编码不能为空");
//        }

//        //验证当前用户查询权限
//        TJUtil.getInstance(htUserXQDao).checkAuth(params);

        String[] arrayDateList = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
        String[] arrayEnterCount = {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
        String[] arrayOutCount = {"0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0"};
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH");

        List<Map<String,Object>> list = dao.query(params);
        if(list==null || list.size() == 0){
//            throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的数据不存在");
            int currentHour = Integer.parseInt(sdf1.format(new Date()));
            if (currentHour <= 12) {//判断上午还是下午
                map.put("AmOrPm", "AM");
            } else {
                map.put("AmOrPm", "PM");
            }

        }else {

            map.put("AmOrPm", (list.get(0)).get("AmOrPm"));
            int currentHour = Integer.parseInt(sdf1.format(new Date()));
            if (currentHour <= 12) {//判断上午还是下午
                map.put("AmOrPm", "AM");
            } else {
                map.put("AmOrPm", "PM");
            }

            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> mapItem = list.get(i);
                if (mapItem.get("timing").equals(i + 1)) {
                    arrayEnterCount[i] = mapItem.get("carIn").toString();
                    arrayOutCount[i] = mapItem.get("carOut").toString();
                }
            }

        }

        map.put("enterCount", arrayEnterCount);
        map.put("outCount", arrayOutCount);
        map.put("dateList", arrayDateList);

        return map;
    }
}
