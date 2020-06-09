package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

public interface CarRecordService {
    /**
     * 车辆报警记录上传
     *
     * @param map
     * @throws OssRenderException
     */
    void saveCarAlarmRecords(Map<String, Object> map) throws OssRenderException;

    /**
     * 车辆占道报警记录上传
     *
     * @param map
     * @throws OssRenderException
     */
    void saveCarJeevesRecords(Map<String, Object> map) throws OssRenderException;

    /**
     * 车辆报警记录处理
     *
     * @param map
     * @throws OssRenderException
     */
    void updateCarRecord(Map<String, Object> map) throws OssRenderException;

    /**
     * 车辆报警记录查询(单个)
     *
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map queryCarRecord(Map<String, Object> params) throws OssRenderException;

    /**
     * 车辆出入记录列表查询----默认当日  hjt
     *
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String, Object> queryCarAccessRecordList(Map<String, Object> params) throws OssRenderException;

    /**
     * 车辆情况分析统计  hjt
     *
     * @param paramMap
     * @return
     * @throws OssRenderException
     */
    Map<String, Object> queryCarOverview(Map<String, Object> paramMap) throws OssRenderException;

    /**
     * 功能描述: 获取报警车辆信息
     *
     * @auther: lixianhua
     * @date: 2019/12/18 8:42
     * @param:
     * @return:
     */
    Map<String, Object> queryAlarmCarList(Map<String, Object> params) throws OssRenderException;

    /**
     * 功能描述: 根据主键获取车辆报警信息
     *
     * @auther: lixianhua
     * @date: 2020/1/9 11:03
     * @param:
     * @return:
     */
    Map<String, Object> queryAlarmCarById(Integer id) throws OssRenderException;
}
