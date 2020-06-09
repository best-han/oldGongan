package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarGroupDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveCarGroups(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateCarGroup(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteCarGroup(Map params);


}
