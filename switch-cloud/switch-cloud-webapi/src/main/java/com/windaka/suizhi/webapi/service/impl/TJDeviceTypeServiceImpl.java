package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.TJDeviceTypeDao;
import com.windaka.suizhi.webapi.model.TJDeviceType;
import com.windaka.suizhi.webapi.service.TJDeviceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 统计－设备类型信息ServiceImpl
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@Service
public class TJDeviceTypeServiceImpl implements TJDeviceTypeService {


    @Autowired
    private TJDeviceTypeDao dao;

    /**
     * 新增统计－设备类型信息
     * @param params
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMap(Map<String, Object> params) throws OssRenderException {

    }

    /**
     * 查询设备类型管理
     * @return
     *
     * @author pxl
     * @create 2019-05-06 10:26
     */
    @Override
    public TJDeviceType query(String deviceCode) {
        TJDeviceType model = dao.query(deviceCode);
        return model;
    }
}
