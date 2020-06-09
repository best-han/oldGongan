package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseOwnerRoomDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveHouseOwnerRooms(@Param("xqCode") String xqCode,@Param("list") List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateHouseOwnerRoom(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteHouseOwnerRoom(Map params);


}
