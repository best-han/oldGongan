package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface XqPersonRelationDao {

    int saveXqPersonRelation(String xq_code,String person_id);
    int deleteXqPersonRelation(String personId);
    String queryXqCodeByPersonCode(String personId);

}
