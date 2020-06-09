package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.manageport.service.AppUserService;
import com.windaka.suizhi.manageport.service.CarRecordService;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarRecordController
        extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(CarRecordController.class);
    @Autowired
    private CarRecordService carRecordService;
    @Autowired
    private AppUserService appUserService;

    @PostMapping({"/car/alarmRecord"})
    public Map<String, Object> saveCarAlarmRecord(@RequestBody Map map)
    {
        try
        {
            this.carRecordService.saveCarAlarmRecords(map);
            return render();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("[CarRecordController.saveCarAlarmRecord,参数{},异常信息{}]", "", e.getMessage());
            return failRender(e);
        }
    }

    @PostMapping({"/car/jeevesRecord"})
    public Map<String, Object> saveCarJeevesRecord(@RequestBody Map<String, Object> map)
    {
        try
        {
            this.carRecordService.saveCarJeevesRecords(map);
            return render();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            log.info("[CarRecordController.saveCarJeevesRecord,参数{},异常信息{}]", "", e.getMessage());
            return failRender(e);
        }
    }

    @GetMapping({"/tj/carAccess/list"})
    public Map<String, Object> queryCarAccessRecord(@RequestParam Map<String, Object> params)
    {
        try
        {
            if (params.get("xqCode") == "")
            {
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", this.appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String, Object> map = this.carRecordService.queryCarAccessRecordList(params);
            return render(map);
        }
        catch (Exception e)
        {
            log.info("[CallRecordController.queryCallRecord,参数{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    @GetMapping({"/tj/car/overview"})
    public Map<String, Object> queryCarOverview(@RequestParam Map<String, Object> params)
    {
        try
        {
            if (params.get("xqCode") == "")
            {
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", this.appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String, Object> map = this.carRecordService.queryCarOverview(params);
            return render(map);
        }
        catch (Exception e)
        {
            log.info("[CallRecordController.queryCarOverview,参数{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }
}
