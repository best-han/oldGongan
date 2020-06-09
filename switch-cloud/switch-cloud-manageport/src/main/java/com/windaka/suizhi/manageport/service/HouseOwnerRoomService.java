package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

public interface HouseOwnerRoomService {

    /**
     * 业主房产信息保存
     * @param params
     */
    public void saveHouseOwnerRooms(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 业主房产信息修改
     * @param params
     * @throws OssRenderException
     */
    public void updateHouseOwnerRoom(Map<String, Object> params) throws OssRenderException, IOException;

    /**
     * 业主房产信息删除
     * @param params
     * @throws OssRenderException
     */
    public void deleteHouseOwnerRoom(Map<String, Object> params) throws OssRenderException, IOException;
}
