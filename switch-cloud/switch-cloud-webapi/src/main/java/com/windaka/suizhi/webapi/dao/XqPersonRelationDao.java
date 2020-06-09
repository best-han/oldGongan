package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface XqPersonRelationDao {

    int saveXqPersonRelation(String xq_code,String person_code);
    int deleteXqPersonRelation(String personCode);
    String queryXqCodeByPersonCode(String personCode);

}
