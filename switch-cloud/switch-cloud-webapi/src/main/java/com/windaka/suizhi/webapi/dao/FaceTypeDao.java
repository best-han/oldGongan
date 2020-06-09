package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;

import com.windaka.suizhi.webapi.model.FaceLibrary;

import java.util.List;
import java.util.Map;

@Mapper
public interface FaceTypeDao {
    /**
     * 新增
     * @param params
     * @return
     */
    int saveFaceType(Map<String, Object> params);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateFaceType(Map<String, Object> params);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteFaceTypeByCode(String id);

    /**
     * 查询人脸库
     * @param params
     * @return
     */
    List<Map<String,Object>> queryFaceTypes(Map<String, Object> params);
    
    FaceLibrary selectFaceType(Map<String, Object> params);

    /**
     * 查人脸库总数
     * @param params
     * @return
     */
    int queryTotalFaceType(Map<String, Object> params);

}
