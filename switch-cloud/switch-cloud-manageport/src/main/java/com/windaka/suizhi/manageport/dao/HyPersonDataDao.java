package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface HyPersonDataDao {

    /**
     * Hy 新增人员 ygy
     * @param params
     * @return
     */
    int saveHyPerson(Map<String,Object> params);

    /**
     * Hy 更新人员 ygy
     * @param params
     * @return
     */
    int updateHyPerson(Map<String,Object> params);

    /**
     * Hy 删除人员 ygy
     * @param paperNum
     * @return
     */
    int deleteHyPerson(String paperNum);

}
