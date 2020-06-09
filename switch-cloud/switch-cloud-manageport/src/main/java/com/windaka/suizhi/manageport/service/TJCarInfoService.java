package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 统计－车辆信息Service
 * @author pxl
 * @create: 2019-05-06 10:22
 */
public interface TJCarInfoService {
    /**
     * 新增统计－车辆信息
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    void saveMap(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 根据小区Code,operDate,hour删除车辆信息
     * @return
     *
     * @author wcl
     * @create 2019-05-30
     */
    void delete(String xqCode, Date operDate, int hour);

    /**
     * 查询列表
     * @param params
     *
     * @author pxl
     * @create 2019-05-06 10:56
     */
    Map<String,Object> query(Map<String, Object> params) throws OssRenderException;
}
