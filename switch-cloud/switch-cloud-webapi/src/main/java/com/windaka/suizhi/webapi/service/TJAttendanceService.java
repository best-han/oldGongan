package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

/**
 * 统计－考勤管理Service
 * @author pxl
 * @create: 2019-05-06 10:22
 */
public interface TJAttendanceService {
    /**
     * 新增统计－缴费信息
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    void saveMap(Map<String, Object> params) throws OssRenderException;

    /**
     * 根据小区Code删除记录
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    void delete(String xqCode);

    /**
     * 查询列表
     * @param params
     *
     * @author pxl
     * @create 2019-05-06 10:56
     */
    Map<String,Object> query(Map<String, Object> params) throws OssRenderException;
}
