package com.windaka.suizhi.manageport.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FaceInfoDao {

    /**
     * 批量保存
     * @param
     * @return
     */
    int saveFaceInfos(String xqCode, List<Map<String,Object>> list);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateFaceInfo(Map params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteFaceInfo(Map params);


}
