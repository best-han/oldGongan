package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface SubdistrictService {

    /**
     * 根据当前登录人查询其绑定街道以及街道下所有小区 hjt
     * @return
     * @throws OssRenderException
     */
    public List<Map<String,Object>> querySubdistrictAndXq() throws OssRenderException;

    /**
     * 街道-新增 hjt
     * @return
     * @throws OssRenderException
     */
    public void saveSubdistrict(Map<String,Object> params) throws OssRenderException;

    /**
     * 街道-删除 hjt
     * @return
     * @throws OssRenderException
     */
    public void deleteSubdistrict(String subdistrictId) throws OssRenderException;

    /**
     * 街道小区绑定 hjt
     * @return
     * @throws OssRenderException
     */
    public void saveXqSubdistrict(Map<String,Object> params) throws OssRenderException;

    /**
     *  查询所有汇总数据之和  hjt
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> queryStatistics(Map<String, Object> params) throws OssRenderException;
    /**
     *  查询今日流动数据之和  hjt
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String,Object> queryStatisticsToday(Map<String, Object> params) throws OssRenderException;

    /**
     * 首页-社区基础要素 dee
     * @param params
     * @return
     * @throws OssRenderException
     */
    public Map<String,Object> getSubdistrictBaseInfo(Map<String, Object> params) throws OssRenderException;
}
