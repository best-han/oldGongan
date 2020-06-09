package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.common.exception.OssRenderException;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface HouseDao {

    /**
     * 查询楼栋列表
     * @param params
     * @return
     */
    List<Map<String, Object>> queryHouseBuildingList(Map<String, Object> params);
    /**
     * 房屋总数量 dee
     * @param params
     * @return
     */
    int roomTotalNum(Map<String, Object> params);
    /**
     * 按小区查找房屋结构信息 dee
     * @param params
     * @return
     */
    Map<String,Object> queryHouseRoomPropertyByXq(Map<String,Object> params);

    /**
     * 按街道查找房屋结构信息 dee
     * @param params
     * @return
     */
    Map<String,Object> queryHouseRoomPropertyBySub(Map<String,Object> params);

    /**
     * 按地区查找房屋结构信息 dee
     * @param params
     * @return
     */
    Map<String,Object> queryHouseRoomPropertyByArea(Map<String,Object> params);

    /**
     * 按小区查找居住人数最多的top10房屋 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseRoomLiveNumTop10ByXq(Map<String,Object> params);

    /**
     * 按街道查找居住人数最多的top10房屋 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseRoomLiveNumTop10BySub(Map<String,Object> params);

    /**
     * 按地区查找居住人数最多的top10房屋 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseRoomLiveNumTop10ByArea(Map<String,Object> params);

    /**
     * 按小区查询房屋分布统计信息 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseDistributionByXq(Map<String,Object> params);

    /**
     * 按街道查询房屋分布统计信息 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseDistributionBySub(Map<String,Object> params);

    /**
     * 按地区查询房屋分布统计信息 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseDistributionByArea(Map<String,Object> params);

    /**
     * 按小区查询房产列表 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseListByXq(Map<String,Object> params);

    /**
     * 按地区查询房产列表 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseListByArea(Map<String,Object> params);

    List<Map<String,Object>> queryHouseList2(Map<String,Object> params);

    List<Map<String,Object>> queryHouseListLikeStr(Map<String,Object> params);

    /**
     * 按小区查询疑似空闲房屋 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseRoomUnusedListByXq(Map<String,Object> params);

    List<Map<String,Object>> queryHouseRoomUnusedListByXq2(Map<String,Object> params);

    /**
     * 按地区查询疑似空闲房屋 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseRoomUnusedListByArea(Map<String,Object> params);

    List<Map<String,Object>> queryHouseRoomUnusedListByArea2(Map<String,Object> params);

    /**
     * 按小区查询欠费房屋列表 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseRoomArrearageListByXq(Map<String,Object> params);

    /**
     * 按地区查询欠费房屋列表 dee
     * @param params
     * @return
     */
    List<Map<String,Object>> queryHouseRoomArrearageListByArea(Map<String,Object> params);

    /**
     * 房屋基础信息查询（单个查询） dee
     * @param roomId
     * @return
     */
    List<Map<String,Object>> queryHouseRoomInfoByRoomId(String roomId);

    /**
     * 房屋基础信息-人员信息列表 dee
     * @param roomId
     * @return
     */
    List<Map<String,Object>> queryHouseRoomPersonsByRoomId(String roomId);

    /**
     * 房屋基础信息-汽车信息列表 dee
     * @param roomId
     * @return
     */
    List<Map<String,Object>> queryHouseRoomCarsByRoomId(String roomId);

    /**
     * 房屋基础信息-缴费信息列表 dee
     * @param roomId
     * @return
     */
    List<Map<String,Object>> queryHouseRoomPaymentByRoomId(String roomId,String beginTime,String endTime);

    List<Map<String,Object>> queryRoomInfoByManageId(String manageId);

    List<Map<String,Object>> queryRoomInfoInBuilding(Map<String,Object> params);

    List<Map<String,Object>> queryFloorNumListInBuilding(Map<String,Object> params);

    List<Map<String,Object>> queryCellListInBuilding(Map<String,Object> params);

}
