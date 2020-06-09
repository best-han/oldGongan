package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;
import com.windaka.suizhi.webapi.model.CarLibrary;
import java.util.List;
import java.util.Map;

@Mapper
public interface CarTypeDao {
    /**
     * 新增
     * @param params
     * @return
     */
    int saveCarType(Map<String, Object> params);

    /**
     * 修改
     * @param params
     * @return
     */
    int updateCarType(Map<String, Object> params);

    /**
     * 删除
     * @param params
     * @return
     */
    int deleteCarType(Map<String, Object> params);

    int deleteCarTypeR(Map<String, Object> params);

    /**
     * 查询人脸库
     * @param params
     * @return
     */
    List<Map<String,Object>> queryCarTypes(Map<String, Object> params);
    
    CarLibrary selectCarType(Map<String, Object> params);

    /**
     * 查人脸库总数
     * @param params
     * @return
     */
    int queryTotalCarType(Map<String, Object> params);

    List<Map<String,Object>> queryCarTypes2(Map<String, Object> params);

}
