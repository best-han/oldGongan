package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.RoomDao;
import com.windaka.suizhi.webapi.service.RoomService;
import com.windaka.suizhi.webapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ROUND_HALF_UP;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Override
    public List<Map<String,Object>> queryRoomPropertyStatistics(Map<String,Object> params) throws OssRenderException{
        if (StringUtils.isBlank((String)params.get("areaId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"区域不能为空");
        }
        int roomRecordNum=roomDao.queryRoomRecordNumByXqCodes(params);
        params.put("limit",roomRecordNum);
        List<Map<String,Object>> list=roomDao.queryRoomPropertyNum(params);
        int sum=0;
        for(Map m:list){
            sum=sum+Integer.parseInt(m.get("num").toString());
        }
        BigDecimal b2=new BigDecimal(sum);
        for(Map m:list){
            m.put("percent",new BigDecimal(Integer.parseInt(m.get("num").toString())).divide(b2,2, ROUND_HALF_UP).doubleValue());
        }
        return list;
    }
    @Override
    public Map<String, Object> queryRoomRentAddNum(Map<String, Object> params) throws OssRenderException{
        Map<String, Object> map=new HashMap<>();
        params.put("startTime", DateUtil.getTodayStartTime());
        params.put("endTime",DateUtil.getTodayEndTime());
        int rentAddDay=roomDao.queryRoomRentAddNum(params);
        map.put("rentAddDay",rentAddDay);
        params.put("startTime", DateUtil.getMonthStartTime());
        params.put("endTime",DateUtil.getMonthEndTime());
        int rentAddMonth=roomDao.queryRoomRentAddNum(params);
        map.put("rentAddMonth",rentAddMonth);
        params.put("startTime", DateUtil.getYearStartTime());
        params.put("endTime",DateUtil.getYearEndTime());
        int rentAddYear=roomDao.queryRoomRentAddNum(params);
        map.put("rentAddYear",rentAddYear);
        return map;
    }
    @Override
    public String queryRoomNameByManageId(String manageId) throws OssRenderException{
        return roomDao.queryRoomNameByManageId(manageId);
    }
}
