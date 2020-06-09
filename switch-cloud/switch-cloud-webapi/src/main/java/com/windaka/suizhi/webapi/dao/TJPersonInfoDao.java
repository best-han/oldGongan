package com.windaka.suizhi.webapi.dao;

import com.windaka.suizhi.webapi.model.TJPersonInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 统计－人员信息Dao
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Mapper
public interface TJPersonInfoDao {

    /**
     * 新增统计－人员信息
     * @param model
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int save(TJPersonInfo model);

    /**
     * 删除所有统计－人员信息
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int delete(String xqCode);

    /**
     * 查询统计－人员信息列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    Map<String,Object> query(@Param("params") Map<String, Object> params);

    /**
     * 总人脸类型数据查询  hjt
     * @param params
     * @return
     */
    int querySumFaceType(@Param("params") Map<String, Object> params);

    /**
     * 单个人脸类型数据查询  hjt
     * @param params
     * @return
     */
    int querySumByFaceType(@Param("params") Map<String, Object> params);

    /**
     * 查询总人数
     * @author wcl
     * @Date 2019/8/21
     */
    int querySumPserson(@Param("params") Map<String, Object> params);

    /**
     *  查询男性人数
     * @author wcl
     * @Date 2019/8/21 0021
     */
    int queryManNum(@Param("params") Map<String, Object> params);

    /**
     *  查询女性人数
     * @author wcl
     * @Date 2019/8/21 0021
     */
    int queryWomanNum(@Param("params") Map<String, Object> params);

    /**
     *  查询已婚人数
     * @author wcl
     * @Date 2019/8/21 0021
     */
    int queryMarryNum(@Param("params") Map<String, Object> params);

    /**
     *  查询未婚人数
     * @author wcl
     * @Date 2019/8/21 0021
     */
    int querySingleNum(@Param("params") Map<String, Object> params);

    /**
     *  查询人脸已注册数量
     * @author wcl
     * @Date 2019/8/21 0021
     */
    int queryFaceRegisterNum(@Param("params") Map<String, Object> params);
}
