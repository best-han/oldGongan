package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarGroupCarDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveCarGroupCars(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateCarGroupCar(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteCarGroupCar(Map params);


}
