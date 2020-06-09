package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface HouseService {
    /**
     * 楼栋列表 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String, Object> queryHouseBuildingList(Map<String, Object> params) throws OssRenderException;
    /**
     * 房屋结构信息接口 dee
     * @param params
     * @param purpose
     * @param beginTime
     * @param endTime
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> queryHouseRoomProperty(Map<String, Object> params,String purpose,String beginTime,String endTime) throws OssRenderException;

    /**
     * 房产居住统计接口 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseLiveNumTop10(Map<String, Object> params) throws OssRenderException;

    /**
     * 房屋分布统计接口 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseDistribution(Map<String, Object> params) throws OssRenderException;

    /**
     * 房产列表接口 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryHouseList(Map<String, Object> params) throws OssRenderException;

    List<Map<String,Object>> queryHouseList2(Map<String,Object> params) throws OssRenderException;

    Map<String,Object> queryHouseList3(Map<String,Object> params) throws OssRenderException;

    /**
     * 公安-疑似空闲住房 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryHouseRoomUnusedList(Map<String, Object> params) throws OssRenderException;

    List<Map<String,Object>> queryHouseRoomUnusedList2(Map<String, Object> params) throws OssRenderException;

    Map<String,Object> queryHouseRoomUnusedList3(Map<String, Object> params) throws OssRenderException;

    /**
     * 公安-欠费房屋列表 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryHouseRoomArrearageList(Map<String, Object> params) throws OssRenderException;

    Map<String,Object> queryHouseRoomArrearageList2(Map<String, Object> params) throws OssRenderException;

    /**
     * 公安-房屋基础信息查询（单个查询） dee
     * @param roomId
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryHouseRoomInfoByRoomId(String roomId) throws OssRenderException;

    /**
     * 公安-房屋基础信息-人员信息列表 dee
     * @param roomId
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryHouseRoomPersonsByRoomId(String roomId) throws OssRenderException;

    /**
     * 公安-房屋基础信息-汽车信息列表 dee
     * @param roomId
     * @return
     */
    List<Map<String,Object>> queryHouseRoomCarsByRoomId(String roomId) throws OssRenderException;

    /**
     * 公安-房屋基础信息-缴费信息列表(近一年) dee
     * @param roomId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Map<String,Object>> queryHouseRoomPaymentByRoomId(String roomId,String beginTime,String endTime) throws OssRenderException;

    Map<String,Object> queryHouseRoomPaymentByRoomId2(String roomId,Map<String, Object> params) throws OssRenderException;

    List<Map<String,Object>> queryRoomInfoByManageId(String manageId) throws OssRenderException;

    /**
     * 楼栋下每层每单元房屋的查询 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> queryHouseBuildingInnerList(Map<String, Object> params) throws OssRenderException;

}
