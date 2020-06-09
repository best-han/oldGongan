package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarStatisticsDao {

    /**
     * 按小区查询登记车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarTotalNumByXq(Map<String,Object> params);

    /**
     * 按街道查询登记车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarTotalNumBySub(Map<String,Object> params);

    /**
     * 按地区查询登记车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarTotalNumByArea(Map<String,Object> params);

    /**
     * 按小区查询感知车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseNumByXq(Map<String,Object> params);

    /**
     * 按街道查询感知车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseNumBySub(Map<String,Object> params);

    /**
     * 按地区查询感知车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseNumByArea(Map<String,Object> params);

    /**
     * 按小区查询陌生感知车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSerseStrangerNumByXq(Map<String,Object> params);

    /**
     * 按街道查询陌生感知车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSerseStrangerNumBySub(Map<String,Object> params);

    /**
     * 按地区查询陌生感知车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSerseStrangerNumByArea(Map<String,Object> params);

    /**
     * 按小区查询出/入车辆数 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarPassNumByXq(Map<String,Object> params);

    /**
     * 按街道查询出/入车辆数 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarPassNumBySub(Map<String,Object> params);

    /**
     * 按地区查询出/入车辆数 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarPassNumByArea(Map<String,Object> params);

    /**
     * 按小区查询车辆分布情况 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarDistributionNumByXq(Map<String,Object> params);

    /**
     * 按街道查询车辆分布情况 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarDistributionNumBySub(Map<String,Object> params);

    /**
     * 按地区查询车辆分布情况 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarDistributionNumByArea(Map<String,Object> params);

    /**
     * 按小区查询高频进出车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseTop12ByXq(Map<String,Object> params);

    /**
     * 按街道查询高频进出车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseTop12BySub(Map<String,Object> params);

    /**
     * 按地区查询高频进出车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseTop12ByArea(Map<String,Object> params);

    /**
     * 按小区查询特殊车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSpecialListByXq(Map<String,Object> params);

    /**
     * 按街道查询特殊车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSpecialListBySub(Map<String,Object> params);

    /**
     * 按地区查询特殊车辆 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSpecialListByArea(Map<String,Object> params);

    /**
     * 按小区查询车辆列表 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarListByXq(Map<String,Object> params);

    /**
     * 按街道查询车辆列表 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarListBySub(Map<String,Object> params);

    List<Map<String,Object>> queryCarListLikeStr(Map<String,Object> params);

    /**
     * 按地区查询车辆列表 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarListByArea(Map<String,Object> params);

    List<Map<String,Object>> queryCarList2(Map<String,Object> params);

    /**
     * 公安-车辆基础信息 小区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarBaseInfoNewByXq(Map<String,Object> params);

    /**
     * 公安-车辆基础信息 街道 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarBaseInfoNewBySub(Map<String,Object> params);

    /**
     * 公安-车辆基础信息 地区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarBaseInfoNewByArea(Map<String,Object> params);

    /**
     * 公安-高频进出车辆统计 小区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHighSenseByXq(Map<String,Object> params);

    /**
     * 公安-高频进出车辆统计 街道 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHighSenseBySub(Map<String,Object> params);

    /**
     * 公安-高频进出车辆统计 地区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHighSenseByArea(Map<String,Object> params);

    /**
     * 公安-车辆出入场统计列表查询 小区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryTjCarAccessListByXq(Map<String,Object> params);

    /**
     * 公安-车辆出入场统计列表查询 物业 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryTjCarAccessListByWy(Map<String,Object> params);

    /**
     * 公安-车辆出入场统计列表查询 地区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryTjCarAccessListByArea(Map<String,Object> params);

    List<Map<String,Object>> queryTjCarAccessList2(Map<String,Object> params);

    /**
     * 公安-高频出入车辆2 小区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseHighAccessByXq(Map<String,Object> params);

    /**
     * 公安-高频出入车辆2 街道 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseHighAccessBySub(Map<String,Object> params);

    /**
     * 公安-高频出入车辆2 地区 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarSenseHighAccessByArea(Map<String,Object> params);

    List<Map<String,Object>> selectCarInfoByCarNum(String carNum);


    /**
     * @description 车辆抓拍记录查询dao
     * @param params
     * @return map
     * @author zhoutao
     * @date 2019/12/30 14:37
     */
    List<Map<String, Object>> queryCarCaptureList(Map<String, Object> params);

    /**
     * @description 车辆抓拍记录总条数
     * @param params
     * @return int
     * @author zhoutao
     * @date 2019/12/30 15:25
     */
    int queryCarCaptureNum(Map<String, Object> params);

    /**
     * 车辆轨迹 dee
     * @param params
     * @return
     */
    List<Map<String, Object>> queryCarCaptureTrackByCarNum(Map<String, Object> params);

    /**
     * 根据车牌号查询车辆出入记录（出入时间、出入地点）
     * @param params
     * @return
     */
    List<Map<String, Object>> queryCarSenseHighAccessInfoByCarNum(Map<String, Object> params);

    /**
     * 查询疑似新增车辆（15天内出现超过8天的未登记车辆）
     * @param params
     * @return
     */
    List<Map<String, Object>> queryCarCapDaysWithIn15Days(Map<String, Object> params);

}
