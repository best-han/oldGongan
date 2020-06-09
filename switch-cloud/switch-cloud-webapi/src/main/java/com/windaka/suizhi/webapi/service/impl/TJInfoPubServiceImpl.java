package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.dao.TJInfoPubDao;
import com.windaka.suizhi.webapi.model.TJInfoPub;
import com.windaka.suizhi.webapi.service.TJInfoPubService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计－信息发布ServiceImpl
 * @author pxl
 * @create: 2019-05-29 11:05
 */
@Slf4j
@Service
public class TJInfoPubServiceImpl implements TJInfoPubService {
    @Autowired
    private TJInfoPubDao dao;

    /**
     * 新增统计－信息发布
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMap(Map<String, Object> params) throws OssRenderException {
        String xqCode = (String) params.get("xqCode");
        List<Map<String,Object>> dataList = (List<Map<String,Object>>)params.get("dataList");
        String operDate = (String)params.get("operDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateOperDate = null;
        if (StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        if(StringUtils.isBlank(operDate)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"上传操作时间不能为空");
        }else{
            try{
                dateOperDate = sdf.parse(operDate);
            }catch (Exception e){
                throw new OssRenderException(ReturnConstants.CODE_FAILED,"操作时间日期格式不正确");
            }
        }
        if(dataList.size() == 0){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"上传的数据列表不能为空");
        }

        String year = String.format("%tY",dateOperDate);
        for (Map<String,Object> map:dataList) {
            String month = (String) map.get("month");
            //删除记录
            dao.delete(xqCode,year,month);

            //region Map转实体类对象
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();

            TJInfoPub model = new TJInfoPub();
            model.setXq_code(xqCode);
            model.setMonth(Integer.parseInt(month));
            model.setInfo_count(Integer.parseInt(map.get("infoCount").toString()));
            model.setOper_date(dateOperDate);
            model.setCre_by(loginAppUser.getUserId());
            model.setCre_time(new Date());
            model.setUpd_by(loginAppUser.getUserId());
            model.setUpd_time(new Date());
            //endregion

            //保存数据
            dao.save(model);
        }
    }

    /**
     * 根据小区Code、年份、月份，删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String xqCode,String year,String month){
        int result = dao.delete(xqCode,year,month);
    }

    /**
     * 查询列表
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Override
    public Map<String, Object> query(Map<String, Object> params) throws OssRenderException {
        String areaId = (String) params.get("areaId");
        String wyCode = (String) params.get("wyCode");
        String xqCode = (String) params.get("xqCode");

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

        List<Map<String,Object>> list = dao.query(params);
        Map<String,Object> map = new HashMap<String,Object>();
        if(list==null || list.size() == 0){
            //throw new OssRenderException(ReturnConstants.CODE_FAILED,"查询的数据不存在");
            Date date = new Date();
            if(date.getMonth()<6){
                map.put("monthOfYear", "up");
            }else{
                map.put("monthOfYear", "down");
            }
            String[] arrayInfoCountOfMonth = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
            map.put("infoCountOfMonth", arrayInfoCountOfMonth);
            String[] arrayMonthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            map.put("monthList", arrayMonthList);
        }else {


            map.put("monthOfYear", (list.get(0)).get("monthOfYear"));
            String[] arrayInfoCountOfMonth = {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> mapItem = list.get(i);
                if (mapItem.get("month").equals(i + 1)) {
                     arrayInfoCountOfMonth[i] = mapItem.get("infoCount").toString();
                }
            }
            map.put("infoCountOfMonth", arrayInfoCountOfMonth);
            String[] arrayMonthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
            map.put("monthList", arrayMonthList);
        }
        return map;
    }
}
