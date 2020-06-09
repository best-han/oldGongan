package com.windaka.suizhi.manageport.service;

import com.windaka.suizhi.common.exception.OssRenderException;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName DeviceService
 * @Description 设备接口
 * @Author lixianhua
 * @Date 2019/12/9 8:42
 * @Version 1.0
 */
public interface DeviceService {
    /**
     * 功能描述: 添加设备
     * @auther: lixianhua
     * @date: 2019/12/9 9:00
     * @param:
     * @return:
     */
    void addDeviceRecord(Map<String, Object> params) throws OssRenderException, IOException;
    /**
     * 功能描述: 修改设备
     * @auther: lixianhua
     * @date: 2019/12/9 9:39
     * @param:
     * @return:
     */
    void updateDeviceRecord(Map<String, Object> params) throws OssRenderException, IOException;
    /**
     * 功能描述: 删除设备
     * @auther: lixianhua
     * @date: 2019/12/9 10:15
     * @param:
     * @return:
     */
    void deleteDeviceRecord(Map<String, Object> params) throws OssRenderException, IOException;
}
