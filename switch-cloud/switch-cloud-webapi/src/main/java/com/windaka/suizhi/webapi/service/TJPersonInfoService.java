package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

/**
 * 统计－人员信息Service
 * @author pxl
 * @create: 2019-05-06 10:22
 */
public interface TJPersonInfoService {
    /**
     * 新增统计－人员信息
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    void saveMap(Map<String, Object> params) throws OssRenderException;

    /**
     * 删除所有统计－人员信息
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

    /**
     * 小区特殊人群分析统计（按上传统计的人脸类型返回数量和百分比）
     * @param params
     * @return
     * @throws OssRenderException
     */
    List<Map<String,Object>> queryFaceTypesOfPerson(Map<String, Object> params) throws OssRenderException;

    /**
     * 小区人员情况分析统计
     * @author wcl
     * @Date 2019/8/21 0021
     */
    Map<String,Object> queryPersonInfoForOverview(Map<String, Object> params) throws OssRenderException;

}
