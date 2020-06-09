package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.service.CarInfoService;
import com.windaka.suizhi.manageport.service.CarAttributeService;
import com.windaka.suizhi.manageport.service.CarGroupCarService;
import com.windaka.suizhi.manageport.service.CarGroupService;
import com.windaka.suizhi.manageport.service.CarParkingLotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 车相关信息
 */
@Slf4j
@RestController
@RequestMapping("/car")
public class CarController extends BaseController {

    @Autowired
    private CarInfoService carInfoService;
    @Autowired
    private CarAttributeService carAttributeService;
    @Autowired
    CarGroupCarService carGroupCarService;
    @Autowired
    CarGroupService carGroupService;
    @Autowired
    CarParkingLotService carParkingLotService;

    /**
     * 添加车辆信息
     * @param params
     */
    @PostMapping
    public Map<String,Object> saveCarInfo(@RequestBody Map<String, Object> params) {
        try{
            carInfoService.saveCarInfo((String) params.get("xqCode"),(List<Map<String, Object>>) params.get("dataList"));
            return render();
        }catch (Exception e){
            e.printStackTrace();
            log.info(e.toString());
            log.info("[CarInfoController.saveCarInfo,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 修改车辆信息
     * @param params
     */
    @PutMapping
    public Map<String,Object> updateCarInfo(@RequestBody Map<String, Object> params) {
        try{
            carInfoService.updateCarInfo(params);
            return render();
        }catch (Exception e){
            e.printStackTrace();
            log.info("[CarInfoController.updateCarInfo,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 删除车辆信息
     * @param params
     */
    @DeleteMapping
    public Map<String,Object> deleteCarInfo(@RequestBody Map<String, Object> params) {
        try{
            carInfoService.deleteCarInfo(params);
            return render();
        }catch (Exception e){
            log.info("[CarInfoController.deleteCarInfo,参数：{},异常信息{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 汽车属性上传--wu
     * @param params
     * @return
*/
    @PostMapping("/attribute")
    public Map<String,Object> saveCarAttributes(@RequestBody Map<String,Object> params){
        try{
            carAttributeService.saveCarAttributes(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.saveCarAttributes,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 汽车属性修改--wu
     * @param params
     * @return
*/
    @PutMapping("/attribute")
    public Map<String,Object> updateCarAttribute(@RequestBody Map<String,Object> params){
        try{
            carAttributeService.updateCarAttribute(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.updateCarAttribute,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     *汽车属性删除--wu
     * @param params
     * @return
*/
    @DeleteMapping("/attribute")
    public Map<String,Object> deleteCarAttribute(@RequestBody Map<String,Object> params){
        try{
            carAttributeService.deleteCarAttribute(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.deleteCarAttribute,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 车辆库车辆关联关系保存
     * @param params
     * @return
*/
    @PostMapping("/groupcar")
    public Map<String,Object> saveCarGroupCars(@RequestBody Map<String,Object> params){
        try{
            carGroupCarService.saveCarGroupCars(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.saveCarGroupCars,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 车辆库车辆关联关系修改
     * @param params
     * @return
*/
    @PutMapping("/groupcar")
    public Map<String,Object> updateCarGroupCar(@RequestBody Map<String,Object> params){
        try{
            carGroupCarService.updateCarGroupCar(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.updateCarGroupCar,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     *车辆库车辆关联关系删除
     * @param params
     * @return
*/
    @DeleteMapping("/groupcar")
    public Map<String,Object> deleteCarGroupCar(@RequestBody Map<String,Object> params){
        try{
            carGroupCarService.deleteCarGroupCar(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.deleteCarGroupCar,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 车辆库上传
     * @param params
     * @return
*/
    @PostMapping("/group")
    public Map<String,Object> saveCarGroups(@RequestBody Map<String,Object> params){
        try{
            carGroupService.saveCarGroups(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.saveCarGroups,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
   /* *
     * 车辆库修改
     * @param params
     * @return
*/
    @PutMapping("/group")
    public Map<String,Object> updateCarGroup(@RequestBody Map<String,Object> params){
        try{
            carGroupService.updateCarGroup(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.updateCarGroup,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     *车辆库删除
     * @param params
     * @return
*/
    @DeleteMapping("/group")
    public Map<String,Object> deleteCarGroup(@RequestBody Map<String,Object> params){
        try{
            carGroupService.deleteCarGroup(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.deleteCarGroup,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

/*
     * 车辆库上传
     * @param params
     * @return
*/
    @PostMapping("/parkingLot")
    public Map<String,Object> saveCarParkingLots(@RequestBody Map<String,Object> params){
        try{
            carParkingLotService.saveCarParkingLots(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.saveCarParkingLots,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
   /* *
     * 车辆库修改
     * @param params
     * @return
*/
    @PutMapping("/parkingLot")
    public Map<String,Object> updateCarParkingLot(@RequestBody Map<String,Object> params){
        try{
            carParkingLotService.updateCarParkingLot(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.updateCarParkingLot,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     *车辆库删除
     * @param params
     * @return
*/
    @DeleteMapping("/parkingLot")
    public Map<String,Object> deleteCarParkingLot(@RequestBody Map<String,Object> params){
        try{
            carParkingLotService.deleteCarParkingLot(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.deleteCarParkingLot,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     *车辆库全部删除
     * @param params
     * @return
     */
    @DeleteMapping("/parkingLot/all")
    public Map<String,Object> deleteCarParkingLotAll(@RequestBody Map<String,Object> params){
        try{
            carParkingLotService.deleteCarParkingLotAll(params);
            return render();
        }catch(Exception e){
            log.info("[CarController.deleteCarParkingLotAll,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }


}
