package com.windaka.suizhi.mpi.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 功能描述: 车辆库及关联表对应dao
 * @auther: lixianhua
 * @date: 2019/12/16 18:52
 */
@Mapper
public interface CarGroupDao {
    /**
     * 功能描述: 根据车牌号获取车库的车信息
     * @auther: lixianhua
     * @date: 2019/12/16 18:59
     * @param:
     * @return:
     */
    Map<String, Object> getGroupByCarNum(String carNum);
}
