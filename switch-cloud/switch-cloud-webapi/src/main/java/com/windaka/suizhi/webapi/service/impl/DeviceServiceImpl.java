package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.DeviceDao;
import com.windaka.suizhi.webapi.service.DeviceService;
import com.windaka.suizhi.webapi.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ntp.TimeStamp;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService {

    private final String sqlId = DeviceDao.class.getName();

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;


    /**
     * 获取设备统计数据
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> queryDeviceNumStatistics(Map<String, Object> params) throws OssRenderException {
        if (StringUtils.isBlank((String) params.get("areaId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "区域不能为空");
        }
        Map<String, Object> map = deviceDao.queryDeviceNum(params);

        return map;
    }

    /**
     * 获取设备类型统计数据
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> queryDeviceTypeNumStatistics(Map<String, Object> params) throws OssRenderException {
        if (StringUtils.isBlank((String) params.get("areaId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "区域不能为空");
        }
        // 获取设备总数和在线设备数量
        Map numMap = deviceDao.queryDeviceNum(params);
        // 获取类型统计数据
        Map<String, Object> map = new HashedMap(8);
        List<Map<String, Object>> list = deviceDao.queryDeviceTypeNum(params);
       String sqlStr = SqlCreateUtil.getSqlByMybatis(DeviceDao.class.getName()+".queryDeviceTypeNum",params);
//        Configuration conf = sqlSessionFactory.getConfiguration();
//        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(sqlId + ".queryDeviceTypeNum");
//        BoundSql boundSql = ms.getBoundSql(params);
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        List<Object> paramValues = getParamValues(params, parameterMappings);
//        String sql = boundSql.getSql();
//        String execuSql = this.getExecuteSql(sql, paramValues);
//        System.out.println(execuSql);
        System.out.println(sqlStr);
        map.put("devices", list);
        map.put("totalNum", numMap.get("totalNum"));
        map.put("onlineNum", numMap.get("onlineNum"));
        return map;
    }

    private List<Object> getParamValues(Map<String, Object> paramMap, List<ParameterMapping> parameterMappings) {
        if (parameterMappings == null) {
            return new ArrayList<Object>();
        }
        List<Object> paramValues = new ArrayList<Object>();
        for (ParameterMapping pm : parameterMappings) {
            if (pm.getMode() != ParameterMode.OUT) {
                String paramName = pm.getProperty();
                Object paramValue = paramMap.get(paramName);
                paramValues.add(paramValue);
            }
        }
        return paramValues;
    }

    private String getExecuteSql(String sql, List<Object> paramValues) {
        while (sql.indexOf("?") != 1 && paramValues.size() > 0) {
            Object paramValue = paramValues.get(0);
            String value = paramValue.toString();
            if (paramValue instanceof String) {
                value = "'" + paramValue.toString() +"'";
            }else if(paramValue instanceof Date|| paramValue instanceof Timestamp){
                value = "22";
            }
            sql = sql.replaceFirst("\\?",value);
            paramValues.remove(0);
        }
        return sql;
    }

    /**
     * 设备分布信息数据
     *
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> queryDeviceDistribution(Map<String, Object> params) throws OssRenderException {
        if (StringUtils.isBlank((String) params.get("areaId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "区域不能为空");
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        // 获取总设备数量
        Map<String, Object> mapNum = deviceDao.queryDeviceNum(params);
        Object totalNum = mapNum.get("totalNum");
        if (null != totalNum && Integer.parseInt(totalNum.toString()) != 0) {
            list = deviceDao.queryDeviceDistributionInfo(params);
            for (Map map : list) {
                map.put("percent", new BigDecimal(map.get("deviceNum").toString()).divide(new BigDecimal(totalNum.toString()), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
            }
        }
        return list;
    }

    /**
     * 获取设备明细
     *
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> queryDeviceInfo(Map<String, Object> params) throws OssRenderException {
        if (StringUtils.isBlank((String) params.get("areaId"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "区域不能为空");
        }
        if (StringUtils.isBlank((String) params.get("limit"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "每页数量不能为空");
        }
        if (StringUtils.isBlank((String) params.get("page"))) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "当前页数不能为空");
        }
        Map<String, Object> resultMap = new HashMap<String, Object>(8);
        // 获取设备总数
        Map<String, Object> map = deviceDao.queryDeviceNum(params);
        resultMap.put("totalRows", map.get("totalNum"));
        resultMap.put("currentPage", params.get("page"));
        if (null != map.get("totalNum") && Integer.parseInt(map.get("totalNum").toString()) != 0) {
            params.put("start", (Integer.parseInt(params.get("page").toString()) - 1) * Integer.parseInt(params.get("limit").toString()));
            params.put("end", Integer.parseInt(params.get("page").toString()) * Integer.parseInt(params.get("limit").toString()));
            List<Map<String, Object>> list = deviceDao.queryDeviceInfo(params);
            resultMap.put("list", list);
        }
        return resultMap;
    }
}
