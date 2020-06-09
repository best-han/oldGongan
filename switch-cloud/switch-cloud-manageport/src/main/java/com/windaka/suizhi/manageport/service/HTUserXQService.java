package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;
import org.apache.ibatis.annotations.Mapper;

/**
 * 当前登录用户关联小区Service
 * @author pxl
 * @create: 2019-05-27 16:39
 */
@Mapper
public interface HTUserXQService {
    /**
     * 根据当前用户id，获取关联的小区Codes(多个小区用逗号进行分隔)
     * @param userId
     *
     * @author pxl
     * @create 2019-05-27 16:39
     */
    String queryXQCodeByUserId(String userId) throws OssRenderException;
}
