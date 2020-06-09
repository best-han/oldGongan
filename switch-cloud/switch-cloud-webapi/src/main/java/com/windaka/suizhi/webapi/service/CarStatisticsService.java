package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface CarStatisticsService {
    /**
     * 查询登记车辆总数 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarTotalNum(Map<String,Object> params) throws OssRenderException;

    /**
     * 查询感知车辆总数 dee
     * @param params
     * @param beginTime
     * @param endTime
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarSenseNum(Map<String,Object> params,String beginTime,String endTime) throws OssRenderException;

    /**
     * 查询陌生感知车辆总数 dee
     * @param params
     * @param beginTime
     * @param endTime
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarSerseStrangerNum(Map<String,Object> params,String beginTime,String endTime) throws OssRenderException;

    /**
     * 查询出/入车辆数 dee
     * @param params
     * @param beginTime
     * @param endTime
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarPassNum(Map<String,Object> params,String carDirect,String beginTime,String endTime) throws OssRenderException;

    /**
     * 街道-车辆分布信息 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarDistributionNum(Map<String,Object> params) throws OssRenderException;

    /**
     * 街道-高频进出车辆统计-车辆感知（TOP12）接口 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseTop12(Map<String,Object> params,int liveType,String beginTime,String endTime) throws OssRenderException;

    /**
     * 街道-特殊车辆管理（列表查询） 接口 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarSpecialList(Map<String,Object> params) throws OssRenderException;

    /**
     * 公安-车辆列表查询 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarList(Map<String,Object> params) throws OssRenderException;

    /**
     * 公安-车辆基础信息 dee
     * @param params
     * @param liveType
     * @param beginTime
     * @param endTime
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarBaseInfoNew(Map<String,Object> params,int liveType,String beginTime,String endTime) throws OssRenderException;

    /**
     * 公安-高频进出车辆统计 dee
     * @param params
     * @param beginTime
     * @param endTime
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryHighSense(Map<String,Object> params,String beginTime,String endTime) throws OssRenderException;

    Map<String,Object> queryHighSense2(Map<String,Object> params) throws OssRenderException;

    /**
     * 公安-车辆出入场统计列表查询 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryTjCarAccessList(Map<String,Object> params) throws OssRenderException;

    Map<String,Object> queryTjCarAccessList2(Map<String,Object> params) throws OssRenderException;

    /**
     * 公安-高频出入车辆2 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryCarSenseHighAccess(Map<String,Object> params) throws OssRenderException;

    Map<String,Object> queryCarSenseHighAccess2(Map<String,Object> params) throws OssRenderException;

    List<Map<String,Object>> queryCarList2(Map<String,Object> params) throws OssRenderException;

    Map<String,Object> queryCarList3(Map<String,Object> params) throws OssRenderException;

    List<Map<String,Object>> selectCarInfoByCarNum(String carNum) throws OssRenderException;

    int saveCarGroupCar(Map<String, Object> params);

    int deleteCarGroupCar(Map<String, Object> params);

    int deleteCarGroup(Map<String, Object> params);

    Map<String, Object> queryCarGroupCar(Map<String, Object> params);

    /**
     * @description 车辆抓拍记录查询
     * @param params
     * @return map
     * @author zhoutao
     * @date 2019/12/30 14:32
     */
    Map<String, Object> queryCarCaptureList(Map<String, Object> params);

    Map<String, Object> queryCarSenseHighAccessInfoByCarNum(Map<String, Object> params);

    Map<String, Object> queryCarCapDaysWithIn15Days(Map<String, Object> params);

}
