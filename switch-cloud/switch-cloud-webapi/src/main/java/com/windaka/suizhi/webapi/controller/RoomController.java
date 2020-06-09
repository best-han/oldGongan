package com.windaka.suizhi.webapi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RoomController extends BaseController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private AppUserService appUserService;

    /**
     * 趋势研判分析-房屋性质统计 hjt
     * @param
     */
    @GetMapping("/house/room/property/statistics")
    public Map<String,Object> queryRoomPropertyStatistics(@RequestParam Map<String,Object> params) {
        try{
            if (params.get("xqCode")=="" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            List<Map<String,Object>> list = roomService.queryRoomPropertyStatistics(params);
            return render(list);
        }catch (Exception e){
            log.info("[RoomController.queryRoomPropertyStatistics,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 租房信息统计 hjt
     * @param
     */
    @GetMapping("/house/rent/statistics")
    public Map<String,Object> queryRoomRentAddNum(@RequestParam Map<String,Object> params) {
        try{
            if (params.get("xqCode")=="" || ObjectUtil.isNull(params.get("xqCode"))) {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String,Object> map = roomService.queryRoomRentAddNum(params);
            return render(map);
//            Map resultMap=new HashMap();
//            resultMap.put("rentAddDay",5);
//            resultMap.put("rentAddMonth",23);
//            resultMap.put("rentAddYear",77);
//            return render(resultMap);
        }catch (Exception e){
            log.info("[RoomController.queryRoomRentAddNum,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }


}
