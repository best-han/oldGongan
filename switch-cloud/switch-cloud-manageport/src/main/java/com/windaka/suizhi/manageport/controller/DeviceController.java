package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.service.AppUserService;
import com.windaka.suizhi.manageport.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DeviceController
 * @Description 设备控制层
 * @Author lixianhua
 * @Date 2019/12/9 8:42
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/device")
public class DeviceController extends BaseController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private AppUserService appUserService;

    /**
     * 功能描述:
     *
     * @auther: 新增设备
     * @date: 2019/12/9 8:51
     * @param:
     * @return:
     */
    @PostMapping
    public Map<String, Object> addDeviceRecord(@RequestBody Map<String, Object> params) {
        try {
            deviceService.addDeviceRecord(params);
            return render();
        } catch (Exception e) {
            log.info("[DeviceController.addDeviceRecord,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 功能描述: 修改设备
     *
     * @auther: lixianhua
     * @date: 2019/12/9 9:38
     * @param:
     * @return:
     */
    @PutMapping
    public Map<String, Object> updateDeviceRecord(@RequestBody Map<String, Object> params) {
        try {
            deviceService.updateDeviceRecord(params);
            return render();
        } catch (Exception e) {
            log.info("[CarInfoController.updateDeviceRecord,参数：{},异常信息：{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 功能描述: 删除设备
     *
     * @auther: lixianhua
     * @date: 2019/12/9 10:14
     * @param:
     * @return:
     */
    @DeleteMapping
    public Map<String, Object> deleteDeviceRecord(@RequestBody Map<String, Object> params) {
        try {
            deviceService.deleteDeviceRecord(params);
            return render();
        } catch (Exception e) {
            log.info("[CarInfoController.deleteCarInfo,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }

    }
}