package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface HouseRoomService {

    /**
     * 房产基础信息保存
     * @param params
     */
    public void saveHouseRooms(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 房产基础信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateHouseRoom(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 房产基础信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteHouseRoom(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 房屋用途（出租）变化记录保存
     * @param params
     * @throws OssRenderException
     */
    public void saveHouseRoomRentRecord(Map<String, Object> params) throws OssRenderException, IOException;
}
