package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.manageport.service.AppUserService;
import com.windaka.suizhi.manageport.service.TJCarInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 统计－车辆信息Controller
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@RestController
public class TJCarInfoController extends BaseController {
    @Autowired
    private TJCarInfoService service;

    @Autowired
    private AppUserService appUserService;

    /**
     * 新增统计－车辆信息
     * @param params
     *
     * @author pxl
     * @create: 2019-05-06 10:52
     */
    @PostMapping("/tj/carInfo")
    public Map<String,Object> saveMap(@RequestBody Map<String, Object> params) {
        try{
            service.saveMap(params);
            return render();
        }catch (Exception e){
            log.info("[TJCarInfoController.saveMap,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 查询统计－车辆信息
     * @param params
     *
     *  @author pxl
     *  @create: 2019-05-06 10:55
     */
    @GetMapping("/tj/carInfo")
    public Map<String,Object> queryList(@RequestParam Map<String, Object> params) {
        try{
            if (params.get("xqCode") == "") {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map map = service.query(params);
            return render(map);
        }catch (Exception e){
            log.info("[TJCarInfoController.queryList,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }
}
