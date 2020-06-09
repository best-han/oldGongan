package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.model.CarLibrary;
import com.windaka.suizhi.webapi.service.CarStatisticsService;
import com.windaka.suizhi.webapi.service.CarTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 人脸相关
 */
@Slf4j
@RestController
@RequestMapping("/library")
public class CarGroupController extends BaseController {

    @Autowired
    CarTypeService carTypeService;

    @Autowired
    CarStatisticsService carStatisticsService;

    /**
     * 车辆类型上传（车辆库）
     * @param params
     * @return
     */
    @PostMapping("/addCarType")
    public Map<String,Object> saveFaceType(@RequestBody Map<String,Object> params){
        try{
            String type= UUID.randomUUID().toString();
            params.put("type",type);
            carTypeService.saveCarType(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.saveFaceType,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 车辆类型修改
     * @param params
     * @return
     */
    @PutMapping("/updateCarType")
    public Map<String,Object> updateFaceType(@RequestBody Map<String,Object> params){
        try{
            if(params.get("typeName")==null||params.get("libraryName")==null||params.get("id")==null){
                throw new OssRenderException(ReturnConstants.CODE_FAILED,"参数错误");
            }
            carTypeService.updateCarType(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.updateFaceType,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 车辆类型删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteCarType/{id}")
    public Map<String,Object> deleteFaceType(@PathVariable("id") String id){
    	if(id==null){
    		return failRender("0000","参数错误");
    	}
        try{
            //carTypeService.deleteCarType(params);
            Map innerParams=new HashMap();
            innerParams.put("id",id);
            carStatisticsService.deleteCarGroup(innerParams);
            return render();
        }catch(Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }
    /**
     * 车辆类型查询
     * @param params
     * @return
     */
    @GetMapping("/selectCarTypeList")
    public Map<String,Object> queryFaceTypes(@RequestParam Map<String,Object> params){
        try{
            Map<String,Object> map=carTypeService.queryCarTypes(params);
            return render(map);
        }catch(Exception e){
            log.info("[FaceController.queryFaceTypes,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     * 车辆库单个查询
     * @param params
     * @return
     */
    @GetMapping("/selectCarType")
    public Map<String,Object> selectFaceType(@RequestParam Map<String,Object> params){
    	if(params.get("id")==null){
    		return failRender("0000","参数错误");
    	}
    	try{
    		CarLibrary carLibrary=carTypeService.selectCarType(params);
    		return render(carLibrary);
    	}catch(Exception e){
    		log.info("[FaceController.queryFaceTypes,参数：{},异常信息：{}]",params,e.getMessage());
    		return failRender(e);
    	}
    }


}
