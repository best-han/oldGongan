package com.windaka.suizhi.mpi.dao;

import com.windaka.suizhi.mpi.model.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface PersonDao {
    /**
     * 查询最大ID
     * @param
     * @return
     */
    @Select("select MAX(id) from zs_person_info")
    String maxId();

    /**
     * 查询没有进入比对的小区新增人员，但是每次最多取100个
     * @param id
     * @return
     */
    List<Person> queryPersonNoCompareList(int id);

    /**
     * 犯罪人员更新上其犯罪人员库中的id
     * @param
     * @return
     */
    int updatePsersonFaceTypePersonId(Map<String,Object> params);

    /**
     * 查询小区单个人员
     *
     * @param personCode
     * @return
     */
    Map<String, Object> queryPerson(String personCode);



}
