package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.Map;

public interface SpecialBaseInfoService {

    /**
     * 查询当前街道或者小区下特别关注的数量 hjt
     * @return
     * @throws OssRenderException
     */
    public Map<String,Object> querySpecialNum(Map<String,Object> param) throws OssRenderException;
}
