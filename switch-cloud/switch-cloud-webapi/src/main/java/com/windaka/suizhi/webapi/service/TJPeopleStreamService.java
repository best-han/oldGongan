package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Date;
import java.util.Map;

/**
 * 统计－人流统计Service
 * @author pxl
 * @create: 2019-05-06 10:22
 */
public interface TJPeopleStreamService {
    /**
     * 新增统计－人流统计
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    void saveMap(Map<String, Object> params) throws OssRenderException;

    /**
     * 根据小区Code,date,hour删除人流统计信息
     * @return
     * @author wcl
     * @create 2019-05-29
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
