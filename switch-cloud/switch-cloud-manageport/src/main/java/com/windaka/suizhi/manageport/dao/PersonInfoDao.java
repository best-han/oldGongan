package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/*
    人员dao层
 */
@Mapper
public interface PersonInfoDao {
    /**
     * 功能描述: 添加人员信息（烟台专用）
     * @auther: lixianhua
     * @date: 2019/12/13 15:07
     * @param:
     * @return:
     */
    Integer insertPerson(Map<String, Object> map);
}
