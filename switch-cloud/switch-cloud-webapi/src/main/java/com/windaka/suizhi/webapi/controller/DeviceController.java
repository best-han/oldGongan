package com.windaka.suizhi.webapi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class DeviceController extends BaseController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private DeviceService deviceService;


    /**
     * 获取设备统计数据
     * @param params
     * @return
     */
    @GetMapping(value = "/device/info")
    public Map<String,Object> queryDeviceNumInfo(@RequestParam Map<String,Object> params) {
        try{
            if (params.get("xqCode")=="" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String,Object> map = deviceService.queryDeviceNumStatistics(params);
            return render(map);
        }catch (Exception e){
            e.printStackTrace();
            log.info("[DeviceController.queryDeviceNumInfo,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 获取设备类型统计数据
     * @param params
     * @return
     */
    @GetMapping(value = "/device/statistics")
    public Map<String,Object> queryDeviceTypeNumInfo(@RequestParam Map<String,Object> params) {
        try{
            if (params.get("xqCode")=="" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String,Object> map = deviceService.queryDeviceTypeNumStatistics(params);
            return render(map);
        }catch (Exception e){
            e.printStackTrace();
            log.info("[DeviceController.queryDeviceTypeNumStatistics,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 获取设备分布信息
     * @param params
     * @return
     */
    @GetMapping(value = "/device/num")
    public Map<String,Object> queryDeviceDistribution(@RequestParam Map<String,Object> params) {
        try{
            if (params.get("xqCode")=="" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            List<Map<String,Object>> list = deviceService.queryDeviceDistribution(params);
//            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//            Map map = new HashedMap(8);
//            map.put("xqCode" ,"02");
//            map.put("xqName","凤凰城");
//            map.put("deviceNum","400");
//            map.put("percent",new BigDecimal("400").divide(new BigDecimal("2100"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
//            list.add(map);
//            map = new HashedMap(8);
//            map.put("xqCode" ,"11");
//            map.put("xqName","香汐海");
//            map.put("deviceNum","300");
//            map.put("percent",new BigDecimal("300").divide(new BigDecimal("2100"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
//            list.add(map);
//            map = new HashedMap(8);
//            map.put("xqCode" ,"12");
//            map.put("xqName","海棠墅");
//            map.put("deviceNum","500");
//            map.put("percent",new BigDecimal("500").divide(new BigDecimal("2100"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
//            list.add(map);
//            map = new HashedMap(8);
//            map.put("xqCode" ,"13");
//            map.put("xqName","官厅园");
//            map.put("deviceNum","700");
//            map.put("percent",new BigDecimal("700").divide(new BigDecimal("2100"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
//            list.add(map);
//            map = new HashedMap(8);
//            map.put("xqCode" ,"14");
//            map.put("xqName","南岛小镇");
//            map.put("deviceNum","200");
//            map.put("percent",new BigDecimal("200").divide(new BigDecimal("2100"), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).intValue());
//            list.add(map);
            return render(list);
        }catch (Exception e){
            log.info("[DeviceController.queryDeviceDistribution,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 获取设备明细
     * @param params
     * @return
     */
    @GetMapping(value = "/device/list")
    public Map<String,Object> queryDeviceInfo(@RequestParam Map<String,Object> params) {
        try{
            if (params.get("xqCode")=="" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String,Object> map = deviceService.queryDeviceInfo(params);
            return render(map);
        }catch (Exception e){
            log.info("[DeviceController.queryDeviceInfo,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }
}
