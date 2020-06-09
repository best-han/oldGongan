package com.windaka.suizhi.mpi.dao;

import com.windaka.suizhi.mpi.model.CarInOut;
import com.windaka.suizhi.mpi.model.RecordAbnormal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: car_access_record对应dao
 * @auther: lixianhua
 * @date: 2019/12/16 16:32
 */
@Mapper
public interface CarAccessRecordDao {


    List<CarInOut> queryCarInOut(int lastId);
    /**
     * 功能描述: 查询车进出抓拍数据（海博）
     * @auther: lixianhua
     * @date: 2019/12/30 16:56
     * @param:
     * @return:
     */
    List<CarInOut> queryCarInfoForHb(Integer lastId);
    /**
     * 功能描述: 查询车辆进出抓拍数据（以萨）
     * @auther: lixianhua
     * @date: 2019/12/31 10:05
     * @param:
     * @return:
     */
    List<CarInOut> queryCarInfoForYs(Integer lastId);
    /**
     * 功能描述: 查询与车辆库匹配的最新数据
     * @auther: lixianhua
     * @date: 2020/1/7 10:00
     * @param:
     * @return:
     */
    List<RecordAbnormal> queryCarForAlarm(int lastId);

    @Select("select MAX(id) from car_access_record")
    Integer  getMaxId();
}
