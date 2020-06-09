package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface FaceTypeDao {
    /**
     * 新增
     * @param params
     * @return
     */
    int saveFaceType(Map<String,Object> params);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateFaceType(Map<String,Object> params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteFaceType(Map<String,Object> params);

}
