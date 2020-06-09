package com.windaka.suizhi.webapi.controller;

import cn.hutool.core.util.ObjectUtil;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.service.AppUserService;
import com.windaka.suizhi.webapi.service.CarRecordService;

import java.util.*;

import com.windaka.suizhi.webapi.service.CarStatisticsService;
import com.windaka.suizhi.webapi.service.RoomService;
import com.windaka.suizhi.webapi.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarRecordController
        extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(CarRecordController.class);
    @Autowired
    private CarRecordService carRecordService;
    @Autowired
    private AppUserService appUserService;
    @Autowired
    private CarStatisticsService carStatisticsService;
    @Autowired
    private RoomService roomService;

    @PostMapping({"/car/alarmRecord"})
    public Map<String, Object> saveCarAlarmRecord(@RequestBody Map map) {
        try {
            this.carRecordService.saveCarAlarmRecords(map);
            return render();
        } catch (Exception e) {
            log.info("[CarRecordController.saveCarAlarmRecord,参数{},异常信息{}]", "", e.getMessage());
            return failRender(e);
        }
    }

    @PostMapping({"/car/jeevesRecord"})
    public Map<String, Object> saveCarJeevesRecord(@RequestBody Map<String, Object> map) {
        try {
            this.carRecordService.saveCarJeevesRecords(map);
            return render();
        } catch (Exception e) {
            log.info("[CarRecordController.saveCarJeevesRecord,参数{},异常信息{}]", "", e.getMessage());
            return failRender(e);
        }
    }

    //    @GetMapping({"/tj/carAccess/list"})
//    public Map<String, Object> queryCarAccessRecord(@RequestParam Map<String, Object> params)
//    {
//        try
//        {
//            if (params.get("xqCode") == "")
//            {
//                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//                params.put("xqCode", this.appUserService.checkAuth(loginAppUser.getUserId()));
//            }
//            Map<String, Object> map = this.carRecordService.queryCarAccessRecordList(params);
//            return render(map);
//        }
//        catch (Exception e)
//        {
//            log.info("[CallRecordController.queryCallRecord,参数{},异常信息{}]", params, e.getMessage());
//            return failRender(e);
//        }
//    }
    @GetMapping({"/tj/carAccess/list"})
    public Map<String, Object> queryCarAccessRecord(@RequestParam Map<String, Object> params) {
        try {
            if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
                params.put("areaId", 0);
            int page = 1;
            int limit = 10;
            if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
                page = Integer.parseInt(params.get("page").toString());
            if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
                limit = Integer.parseInt(params.get("limit").toString());
            Map resultMap = new LinkedHashMap();
            if (params.get("access") != null && !params.get("access").toString().trim().equals("")) {
                if (params.get("access").toString().equals("0"))
                    params.put("carDirect", "9");
                else if (params.get("access").toString().equals("1"))
                    params.put("carDirect", "8");
            }
            List list = carStatisticsService.queryTjCarAccessList(params);
            int totalRows = list.size();

            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;

            List nList = new LinkedList();
            Iterator li = list.iterator();
            int i = 0;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {
                    if (tMap.get("roomId") != null && !tMap.get("roomId").toString().trim().equals("")) {
                        String roomId = tMap.get("roomId").toString();
                        String roomName = roomService.queryRoomNameByManageId(roomId);
                        if (roomName != null && !roomName.toString().trim().equals("")) {
                            tMap.put("roomName", roomName);
                        } else {
                            tMap.put("roomName", "无");
                        }
                    } else {
                        tMap.put("roomName", "无");
                    }
                    tMap.remove("roomId");
                    if (tMap.get("carDirect") != null && !tMap.get("carDirect").toString().trim().equals("")) {
                        if (tMap.get("carDirect").toString().equals("8"))
                            tMap.put("carDirect", "进场");
                        else if (tMap.get("carDirect").toString().equals("9"))
                            tMap.put("carDirect", "出场");
                        else
                            tMap.put("carDirect", "未知");
                    } else {
                        tMap.put("carDirect", "未知");
                    }
                    if (tMap.get("originalPicPath") != null && !tMap.get("originalPicPath").toString().trim().equals("")) {
                        String image = tMap.get("originalPicPath").toString().trim();
                        String ip = PropertiesUtil.getLocalTomcatImageIp();
                        image = ip + image;
                        tMap.put("originalPicPath", image);
                    }
                    if (tMap.get("realCapturePicPath") != null && !tMap.get("realCapturePicPath").toString().trim().equals("")) {
                        String ip = PropertiesUtil.getLocalTomcatImageIp();
                        tMap.put("realCapturePicPath", ip + tMap.get("realCapturePicPath").toString().trim());
                    }
                    nList.add(tMap);
                }
                i++;
            }

            resultMap.put("list", nList);
            resultMap.put("totalRows", totalRows);
            resultMap.put("currentPage", currentPage);
            return render(resultMap);
        } catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping({"/tj/carAccess/list2"})
    public Map<String, Object> queryCarAccessRecord2(@RequestParam Map<String, Object> params) {
        try {
            if (params.get("areaId") == null || params.get("areaId").toString().trim().equals(""))
                params.put("areaId", 0);
            Map resultMap=carStatisticsService.queryTjCarAccessList2(params);
            return render(resultMap);
        } catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping({"/tj/car/overview"})
    public Map<String, Object> queryCarOverview(@RequestParam Map<String, Object> params) {
        try {
            if (params.get("xqCode") == "") {
                LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
                params.put("xqCode", this.appUserService.checkAuth(loginAppUser.getUserId()));
            }
            Map<String, Object> map = this.carRecordService.queryCarOverview(params);
            return render(map);
        } catch (Exception e) {
            log.info("[CallRecordController.queryCarOverview,参数{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 功能描述: 获取车辆报警
     *
     * @auther: lixianhua
     * @date: 2019/12/18 9:53
     * @param:
     * @return:
     */
    @GetMapping(value = "/alarm/car")
    public Map<String, Object> queryCarAlarmList(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map = carRecordService.queryAlarmCarList(params);
            return render(map);
        } catch (Exception e) {
            log.info("[DeviceController.queryDeviceInfo,参数：{},异常信息{}]", params, e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 功能描述: 根据主键获取报警车辆信息
     *
     * @auther: lixianhua
     * @date: 2020/1/9 11:01
     * @param:
     * @return:
     */
    @GetMapping(value = "/alarm/carInfo/{id}")
    public Map<String, Object> queryCarAlarmInfo(@PathVariable("id") Integer id) {
        try {
            Map<String, Object> map = carRecordService.queryAlarmCarById(id);
            return render(map);
        } catch (Exception e) {
            log.info("[CarRecordController.queryCarAlarmInfo,参数：{},异常信息{}]", id, e.getMessage());
            return failRender(e);
        }
    }

}
