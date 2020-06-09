package com.windaka.suizhi.mpi.dao;

import com.windaka.suizhi.mpi.model.CarInOut;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 功能描述: 车辆报警
 * @auther: lixianhua
 * @date: 2019/12/16 20:30
 */
@Mapper
public interface CarAlarmDao {

    /**
     * 功能描述: 添加车报警记录
     * @auther: lixianhua
     * @date: 2019/12/16 20:33
     * @param:
     * @return:
     */
    Integer  insertRecord(Map<String,Object> temp);
    /**
     * 功能描述: 根据车牌号获取车辆信息
     * @auther: lixianhua
     * @date: 2020/1/7 10:14
     * @param:
     * @return:
     */
    @Select("select cl_status clStatus from car_alarm_record where car_number = #{carNum}")
    Map<String, Object> getRecordByCarNum(String carNum);
}
