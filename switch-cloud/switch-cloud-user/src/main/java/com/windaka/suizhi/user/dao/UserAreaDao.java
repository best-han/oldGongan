package com.windaka.suizhi.user.dao;

import com.windaka.suizhi.api.user.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 用户区域信息
 * @author: hjt
 * @Date: 2018/12/20 10:52
 * @Version 1.0
 */
@Mapper
public interface UserAreaDao {

    /**
     * 根据区域ID获取区域信息
     * @param areaId
     * @return
     */
    @Select("SELECT * FROM ht_area WHERE del_flag = 1 AND area_id = #{areaId}")
    Area queryByAreaId(String areaId);
    /**
     * @Author: hjt
     * @Date: 2018/12/6
     * @Description: 查询当下级前区域列表
     */
    List<Map<String,Object>> queryAreaInfoByPid(String parentId);
}
