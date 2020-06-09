package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AnimalAttributeDao {

    /**
     * 批量保存动物属性
     * @param params
     * @return
     */
    int saveAnimalAttributes(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateAnimalAttribute(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteAnimalAttribute(Map params);


}
