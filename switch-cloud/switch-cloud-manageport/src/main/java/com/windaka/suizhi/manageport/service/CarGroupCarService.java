package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface CarGroupCarService {

    /**
     * 车辆库车辆关联关系保存
     * @param params
     */
    public void saveCarGroupCars(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车辆库车辆关联关系修改
     * @param params
     * @throws OssRenderException
     */
    public void updateCarGroupCar(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车辆库车辆关联关系删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteCarGroupCar(Map<String, Object> params) throws OssRenderException, IOException;
}
