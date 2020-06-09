package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

/**
 * 统计－信息发布Service
 * @author pxl
 * @create: 2019-05-29 11:02
 */
public interface TJInfoPubService {
    /**
     * 新增统计－信息发布
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-29 11:02
     */
    void saveMap(Map<String, Object> params) throws OssRenderException;

    /**
     * 根据小区Code、年份、月份，删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-29 11:02
     */
    void delete(String xqCode,String year,String month);

    /**
     * 查询列表
     * @param params
     *
     * @author pxl
     * @create 2019-05-29 11:02
     */
    Map<String,Object> query(Map<String, Object> params) throws OssRenderException;
}
