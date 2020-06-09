package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarAttributeDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveCarAttributes(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateCarAttribute(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteCarAttribute(Map params);

    /**
     * 功能描述: 获取最大主键
     * @auther: lixianhua
     * @date: 2019/12/30 11:00
     * @param:
     * @return:
     */
    @Select("select MAX(id)  from car_attribute")
    Integer getMaxId();
}
