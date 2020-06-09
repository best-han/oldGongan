package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.service.HouseBuildingService;
import com.windaka.suizhi.manageport.service.HouseCellService;
import com.windaka.suizhi.manageport.service.HouseOwnerRoomService;
import com.windaka.suizhi.manageport.service.HouseRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 房产相关
 */
@Slf4j
@RestController
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Autowired
    HouseBuildingService houseBuildingService;
    @Autowired
    HouseCellService houseCellService;
    @Autowired
    HouseOwnerRoomService houseOwnerRoomService;
    @Autowired
    HouseRoomService houseRoomService;

    /**
     * 楼宇基础信息表上传
     * @param params
     * @return
     */
    @PostMapping("/building")
    public Map<String,Object> saveHouseBuildings(@RequestBody Map<String,Object> params){
        try{
            houseBuildingService.saveHouseBuildings(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.saveHouseBuildings,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 楼宇基础信息表修改
     * @param params
     * @return
     */
    @PutMapping("/building")
    public Map<String,Object> updateHouseBuilding(@RequestBody Map<String,Object> params){
        try{
            houseBuildingService.updateHouseBuilding(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.updateHouseBuilding,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 楼宇基础信息表删除
     * @param params
     * @return
     */
    @DeleteMapping("/building")
    public Map<String,Object> deleteHouseBuilding(@RequestBody Map<String,Object> params){
        try{
            houseBuildingService.deleteHouseBuilding(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.deleteHouseBuilding,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 单元基础信息表上传
     * @param params
     * @return
     */
    @PostMapping("/cell")
    public Map<String,Object> saveHouseCells(@RequestBody Map<String,Object> params){
        try{
            houseCellService.saveHouseCells(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.saveHouseCells,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 单元基础信息表修改
     * @param params
     * @return
     */
    @PutMapping("/cell")
    public Map<String,Object> updateHouseCell(@RequestBody Map<String,Object> params){
        try{
            houseCellService.updateHouseCell(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.updateHouseCell,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 单元基础信息表删除
     * @param params
     * @return
     */
    @DeleteMapping("/cell")
    public Map<String,Object> deleteHouseCell(@RequestBody Map<String,Object> params){
        try{
            houseCellService.deleteHouseCell(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.deleteHouseCell,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 业主房产信息表传
     * @param params
     * @return
     */
    @PostMapping("/ownerRoom")
    public Map<String,Object> saveHouseOwnerRooms(@RequestBody Map<String,Object> params){
        try{
            houseOwnerRoomService.saveHouseOwnerRooms(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.saveHouseOwnerRooms,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 业主房产信息表修改
     * @param params
     * @return
     */
    @PutMapping("/ownerRoom")
    public Map<String,Object> updateHouseOwnerRoom(@RequestBody Map<String,Object> params){
        try{
            houseOwnerRoomService.updateHouseOwnerRoom(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.updateHouseOwnerRoom,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 业主房产信息表删除
     * @param params
     * @return
     */
    @DeleteMapping("/ownerRoom")
    public Map<String,Object> deleteHouseOwnerRoom(@RequestBody Map<String,Object> params){
        try{
            houseOwnerRoomService.deleteHouseOwnerRoom(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.deleteHouseOwnerRoom,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 房产基础信息表上传
     * @param params
     * @return
     */
    @PostMapping("/room")
    public Map<String,Object> saveHouseRooms(@RequestBody Map<String,Object> params){
        try{
            houseRoomService.saveHouseRooms(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.saveHouseRooms,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 房产基础信息表修改
     * @param params
     * @return
     */
    @PutMapping("/room")
    public Map<String,Object> updateHouseRoom(@RequestBody Map<String,Object> params){
        try{
            houseRoomService.updateHouseRoom(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.updateHouseRoom,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 房产基础信息表删除
     * @param params
     * @return
     */
    @DeleteMapping("/room")
    public Map<String,Object> deleteHouseRoom(@RequestBody Map<String,Object> params){
        try{
            houseRoomService.deleteHouseRoom(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.deleteHouseRoom,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 房屋用途（出租）变化记录上传 hjt
     * @param params
     * @return
     */
    @PostMapping("/room/record")
    public Map<String,Object> saveHouseRoomRentRecord(@RequestBody Map<String,Object> params){
        try{
            houseRoomService.saveHouseRoomRentRecord(params);
            return render();
        }catch(Exception e){
            log.info("[HouseController.saveHouseRoomRentRecord,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

}
