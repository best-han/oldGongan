package com.windaka.suizhi.webapi.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.util.List;
import java.util.Map;

public interface RoomService {
    /**
     * 房屋性质统计 hjt
     * @param params
     * @return
     * @throws OssRenderException
     */
    public List<Map<String,Object>> queryRoomPropertyStatistics(Map<String,Object> params) throws OssRenderException;

    /**
     * 租房信息统计 hjt
     * @param params
     * @return
     * @throws OssRenderException
     */
    Map<String, Object> queryRoomRentAddNum(Map<String, Object> params) throws OssRenderException;

    String queryRoomNameByManageId(String manageId) throws OssRenderException;
}
