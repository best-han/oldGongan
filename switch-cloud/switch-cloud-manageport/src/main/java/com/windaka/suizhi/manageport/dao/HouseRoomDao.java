package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseRoomDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveHouseRooms(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateHouseRoom(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteHouseRoom(Map params);

    /**
     * 房屋用途（出租）变化记录保存
     * @param
     * @return
     */
    int saveHouseRoomRentRecord(Map params);

    /**
     * 功能描述: 单表查询房屋基础信息
     * @auther: lixianhua
     * @date: 2019/12/13 16:18
     * @param:
     * @return:
     */
    List<Map<String, Object>> getRoomListPure(Map<String, Object> roomMap);
}
