package com.windaka.suizhi.webapi.service.impl;

import cn.hutool.core.map.MapUtil;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.dao.*;
import com.windaka.suizhi.webapi.service.SpecialBaseInfoService;
import com.windaka.suizhi.webapi.service.SubdistrictService;
import com.windaka.suizhi.webapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SubdistrictServiceImpl implements SubdistrictService {

    @Autowired
    private SubdistrictDao subdistrictDao;
    @Autowired
    private XqSubdistrictRelationDao xqSubdistrictRelationDao;
    @Autowired
    private CarStatisticsDao carStatisticsDao;
    @Autowired
    private HouseDao houseDao;

    @Override
    public List<Map<String,Object>> querySubdistrictAndXq() throws OssRenderException{
        LoginAppUser appUser= AppUserUtil.getLoginAppUser();
        String userId=appUser.getUserId();
        List<Map<String,Object>> subdistrictList=subdistrictDao.querySubdistrictByUserId(userId);
        for(Map map:subdistrictList){
            List<Map<String,Object>> xqList=xqSubdistrictRelationDao.queryXqBySubdistrictId(map.get("subdistrictId").toString());
            map.put("xqList",xqList);
        }
        return subdistrictList;
    }

    @Override
    public void saveSubdistrict(Map<String,Object> params) throws OssRenderException{
        if (MapUtil.isEmpty(params) || params.get("name") == null ) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"请传入值");
        }
        subdistrictDao.saveSubdistrict(params);
    }

    @Override
    public void deleteSubdistrict(String subdistrictId) throws OssRenderException{
        subdistrictDao.deleteSubdistrict(subdistrictId);
    }

    @Override
    public void saveXqSubdistrict(Map<String,Object> params) throws OssRenderException{
        if (MapUtil.isEmpty(params) || params.get("subdistrictId") == null || params.get("xqCodes") == null ) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"请传入值");
        }
        String subdistrictId=(String) params.get("subdistrictId");
        subdistrictDao.deleteXqSubdistrict(subdistrictId);
        subdistrictDao.saveXqSubdistrict(params);
    }

    @Autowired
    XqDao xqDao;
    @Autowired
    PersonDao personDao;
    @Autowired
    SpecialBaseInfoService specialBaseInfoService;
    @Override
    public Map<String,Object> queryStatistics(Map<String, Object> params) throws OssRenderException{
        Map<String,Object> map=new HashMap<>();
        Map map1 = xqDao.queryCountInfo(params);
        if(map1==null || StringUtils.isBlank(map1.get("hsSum").toString())){
            map.put("hsSum",0);
        }else{
            map.put("hsSum",map1.get("hsSum"));
        }
        map.put("personTotalNum", personDao.personTotalNum(params));
        map.put("carTotalNum", 0);
        map.put("deviceSum", 0);
        Map map2 =specialBaseInfoService.querySpecialNum(params);
        BigDecimal specialSum=new BigDecimal(map2.get("specialPersonNum").toString()).add(new BigDecimal(map2.get("specialCarNum").toString()));
        map.put("specialSum", specialSum);
        return map;
    }

    @Autowired
    CarRecordDao carRecordDao;
    @Autowired
    AbnormalRecordDao abnormalRecordDao;
    @Autowired
    OpenRecordDao openRecordDao;
    @Override
    public Map<String,Object> queryStatisticsToday(Map<String, Object> params) throws OssRenderException{
        Map<String,Object> map=new HashMap<>();
        params.put("beginTime", DateUtil.getTodayStartTime());
        params.put("endTime",DateUtil.getTodayEndTime());
        map.put("todayPersonSenseNum", openRecordDao.queryTotalOpenRecord(params));//今天人员出入次数
        //map.put("todayPersonSenseNum", personDao.todaySenseNum(params));
        map.put("todayCarSenseNum",carRecordDao.queryTotalCarAccessRecord(params));

        map.put("abnormalTotalNum", abnormalRecordDao.totalRows(params));

        return map;
    }
    @Override
    public Map<String,Object> getSubdistrictBaseInfo(Map<String, Object> params) throws OssRenderException{
        Map<String,Object> map=new HashMap<>();
        map.put("personTotalNum",personDao.personTotalNum(params));
        map.put("flowPersonTotalNum",personDao.floatPersonNum(params));
        List carTotalNumL=null;
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("subdistrictId");
            params.remove("areaId");
            carTotalNumL=carStatisticsDao.queryCarBaseInfoNewByXq(params);
        }
        else if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            params.remove("xqCode");
            params.remove("areaId");
            carTotalNumL=carStatisticsDao.queryCarBaseInfoNewBySub(params);
        }
        else if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("subdistrictId");
            params.remove("xqCode");
            carTotalNumL=carStatisticsDao.queryCarBaseInfoNewByArea(params);
        }
        int carTotalNum=0;
        if(carTotalNumL!=null&&!carTotalNumL.isEmpty())
        {
            Map nMap=(Map)carTotalNumL.get(0);
            carTotalNum=Integer.parseInt(nMap.get("carNum").toString());
        }
        map.put("carTotalNum",carTotalNum);
        int roomTotalNum=0;
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("subdistrictId");
            params.remove("areaId");
            roomTotalNum=houseDao.roomTotalNum(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            params.remove("xqCode");
            params.remove("areaId");
            roomTotalNum=houseDao.roomTotalNum(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("subdistrictId");
            params.remove("xqCode");
            roomTotalNum=houseDao.roomTotalNum(params);
        }
        map.put("roomTotalNum",roomTotalNum);
        return map;
    }
}
