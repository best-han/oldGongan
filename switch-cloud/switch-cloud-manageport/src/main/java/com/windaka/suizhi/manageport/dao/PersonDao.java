package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface PersonDao {
    /**
     * 新增人员
     * @param person
     * @return
     */
    int savePerson(Map<String,Object> person);
    /**
     * 查询最大ID
     * @param
     * @return
     */
    @Select("select MAX(id) from zs_person_info")
    String maxId();
    /**
     * 修改小区人员
     * @param person
     * @return
     */
    int updatePerson(Map<String,Object> person);
    /**
     * 小区人员删除
     * @param personCode
     * @return
     */
    int deletePerson(String personCode);


    //region  查询
    /**
     * 获取全部小区人员量（只查未删除的）
     * @param params
     * @return
     */
    int totalRows(Map<String, Object> params);
    /**
     * 查询小区单个人员
     * @param personCode
     * @return
     */
    Map<String,Object> queryPerson(String personCode);
    /**
     * 根据条件查询用户列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryPersonList(Map<String, Object> params);

    /**
     * 根据人名搜索用户列表
     * @param params
     * @return
     */
    List<Map<String,Object>> queryPersonByNameList(Map<String, Object> params);
    //endregion

    /**
     * 根据图片查询小区单个人员Code
     * @param img
     * @return
     */
    String queryPersonCode(@Param("img") String img);
    /**
     * 功能描述: 根据条件单表查询人员信息
     * @auther: lixianhua
     * @date: 2019/12/13 15:56
     * @param:
     * @return:
     */
    List<Map<String,Object>> getPersonListPure(Map<String, Object> params);


}
