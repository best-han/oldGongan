package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.manageport.service.AppUserService;
import com.windaka.suizhi.manageport.service.TJPersonInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 统计－人员信息Controller
 * @author pxl
 * @create: 2019-05-06 10:22
 */
@Slf4j
@RestController
public class TJPersonInfoController extends BaseController {
    @Autowired
    private TJPersonInfoService service;

    @Autowired
    private AppUserService appUserService;

    /**
     * 新增统计－人员信息
     * @param params
     *
     * @author pxl
     * @create: 2019-05-06 10:52
     */
    @PostMapping("/tj/personInfo")
    public Map<String,Object> saveMap(@RequestBody Map<String, Object> params) {
        try{
            service.saveMap(params);
            return render();
        }catch (Exception e){
            log.info("[TJPersonInfoController.saveMap,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 查询统计－人员信息
     * @param params
     *
     *  @author pxl
     *  @create: 2019-05-06 10:55
     */
    @GetMapping("/tj/personInfo")
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
            log.info("[TJPersonInfoController.queryList,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 小区特殊人群分析统计  hjt
     * @param params
     * @return
     */
    @GetMapping("/tj/personInfo/faceTypes")
    public Map<String,Object> queryFaceTypesOfPerson(@RequestParam Map<String, Object> params) {
        try{
            if (params.get("xqCode") == "") {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            List<Map<String,Object>> list = service.queryFaceTypesOfPerson(params);
            return render(list);
        }catch (Exception e){
            log.info("[TJPersonInfoController.queryFaceTypesOfPerson,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 小区人员情况分析统计
     * @author wcl
     * @Date 2019/8/21 0021
     */
    @GetMapping("/tj/personInfo/overview")
    public Map<String,Object> queryPersonInfoForOverview(@RequestParam Map<String, Object> params) {
        try{
            if (params.get("xqCode") == "") {
                //验证当前用户查询权限
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String,Object> map = service.queryPersonInfoForOverview(params);
            return render(map);
        }catch (Exception e){
            log.info("[TJPersonInfoController.queryPersonInfoForOverview,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }
}
