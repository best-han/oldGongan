package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface CarParkingLotService {

    /**
     * 车场信息保存
     * @param params
     */
    public void saveCarParkingLots(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车场信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateCarParkingLot(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 车场信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteCarParkingLot(Map<String, Object> params) throws OssRenderException, IOException;
    /**
     * 车场信息全部删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteCarParkingLotAll(Map<String, Object> params) throws OssRenderException, IOException;
}
