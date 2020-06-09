package com.windaka.suizhi.webapi.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 统计－缴费信息Dao
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Mapper
public interface TJPayDao {

    /**
     * 新增统计－缴费信息
     * @param model
     * @return
     *
     * @author pxl    hjt
     * @create 2019-05-06 10:26
     */
    //int save(TJPay model);
    int savePayment(Map<String,Object> map);

    /**
     * 根据小区Code删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    int deletePaymentByXqCode(String xqCode);

    /**
     * 上传数据前根据上传数据选择性删除 hjt
     * @param map
     * @return
     */
    int deletePayment(Map<String,Object> map);

    /**
     * 查询统计－人流统计列表
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    Map<String,Object> query(@Param("params") Map<String, Object> params);

    /**
     * 查询当年所有缴费信息列表  hjt
     * @param params
     * @return
     */
    List<Map<String,Object>> queryTjPayList(Map params);

    /**
     * 查询当天所有缴费和  hjt
     * @param params
     * @return
     */
    double queryTjPaySumPayDay(Map params);

    /**
     * 查询某个月的缴费信息 hjt
     * @param params
     * @return
     */
    Map<String,Object> queryTjPayOneMonth(Map params);

    /**
     * 查询当年所有缴费总和  hjt
     * @param params
     * @return
     */
    Map<String,Object> queryTjPaySumMonth(Map params);

}
