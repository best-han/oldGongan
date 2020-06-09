package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.webapi.service.CarStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 车辆库车辆相关
 */
@Slf4j
@RestController
@RequestMapping("/car")
public class CarGroupCarController extends BaseController {

    @Autowired
    private CarStatisticsService carStatisticsService;

    @PostMapping("/groupCar")
    public Map<String,Object> saveCarGroupCar(@RequestBody Map<String,Object> params){
        try{
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            params.put("userId",loginAppUser.getUserId());
            carStatisticsService.saveCarGroupCar(params);
            return render();
        }catch(Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    @DeleteMapping("/groupCar/{id}")
    public Map<String,Object> deleteCarGroupCar(@PathVariable("id") String id){
        try{
            Map innerParam=new HashMap();
            innerParam.put("id",id);
            LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
            innerParam.put("userId",loginAppUser.getUserId());
            carStatisticsService.deleteCarGroupCar(innerParam);
            return render();
        }catch(Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping("/groupCar/list")
    public Map<String,Object> queryGroupCar(@RequestParam Map<String, Object> params){
        try {
            Map resultMap=carStatisticsService.queryCarGroupCar(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

}
