package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

/**
 * 统计－智慧灯杆Service
 * @author pxl
 * @create: 2019-05-28 17:01
 */
public interface TJWisdomlampService {
    /**
     * 新增统计－智慧灯杆
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-28 17:01
     */
    void saveMap(Map<String, Object> params) throws OssRenderException;

    /**
     * 删除所有统计－智慧灯杆
     * @return
     *
     * @author pxl
     * @create 2019-05-28 17:01
     */
    void delete(String xqCode);

    /**
     * 查询列表
     * @param params
     *
     * @author pxl
     * @create 2019-05-28 17:01
     */
    Map<String,Object> query(Map<String, Object> params) throws OssRenderException;
}
