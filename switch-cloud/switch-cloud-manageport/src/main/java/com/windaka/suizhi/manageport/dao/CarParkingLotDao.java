package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarParkingLotDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveCarParkingLots(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateCarParkingLot(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteCarParkingLot(Map params);
    /**
     * 删除小区下所有
     * @param params
     * @return
     */
    int deleteCarParkingLotAll(Map params);


}
