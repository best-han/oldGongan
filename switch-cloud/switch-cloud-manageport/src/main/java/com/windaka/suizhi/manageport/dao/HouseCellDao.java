package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseCellDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveHouseCells(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateHouseCell(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteHouseCell(Map params);


}
