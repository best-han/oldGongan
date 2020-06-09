package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.HTUserXQDao;
import com.windaka.suizhi.webapi.service.HTUserXQService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 当前登录用户关联小区ServiceImpl
 * @author pxl
 * @create: 2019-05-27 16:39
 */
@Mapper
public class HTUserXQServiceImpl implements HTUserXQService {
    @Autowired
    private HTUserXQDao dao;

    /**
     * 根据当前用户id，获取关联的小区Codes(多个小区用逗号进行分隔)
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String queryXQCodeByUserId(String userId) throws OssRenderException {
        return dao.queryXQCodeByUserId(userId);
    }
}
