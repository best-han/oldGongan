package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.webapi.model.FaceLibrary;
import com.windaka.suizhi.webapi.service.FaceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 人脸相关
 */
@Slf4j
@RestController
@RequestMapping("/library")
public class FaceTypeController extends BaseController {

    @Autowired
    FaceTypeService faceTypeService;

    /**
     *人脸类型上传（人脸库）
     * @param params
     * @return
     */
    @PostMapping("/addFaceType")
    public Map<String,Object> saveFaceType(@RequestBody Map<String,Object> params){
    	try{
            faceTypeService.saveFaceType(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.saveFaceType,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 人脸类型修改
     * @param params
     * @return
     */
    @PutMapping("/updateFaceType")
    public Map<String,Object> updateFaceType(@RequestBody Map<String,Object> params){
    	try{
            faceTypeService.updateFaceType(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.updateFaceType,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 人脸类型删除
     * @param id
     * @return
     */
    @DeleteMapping("/deleteFaceType/{id}")
    public Map<String,Object> deleteFaceType(@PathVariable("id") String id){
        try{
            faceTypeService.deleteFaceType(id);
            return render();
        }catch(Exception e){
            log.info("[FaceController.deleteFaceType,参数：{},异常信息：{}]",id,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 人脸类型查询
     * @param params
     * @return
     */
    @GetMapping("/selectFaceTypeList")
    public Map<String,Object> queryFaceTypes(@RequestParam Map<String,Object> params){
        try{
            Map<String,Object> map=faceTypeService.queryFaceTypes(params);
            return render(map);
        }catch(Exception e){
            log.info("[FaceController.queryFaceTypes,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    
    @GetMapping("/selectFaceType")
    public Map<String,Object> selectFaceType(@RequestParam Map<String,Object> params){
    	try{
    		FaceLibrary faceLibrary=faceTypeService.selectFaceType(params);
    		return render(faceLibrary);
    	}catch(Exception e){
    		log.info("[FaceController.selectFaceType,参数：{},异常信息：{}]",params,e.getMessage());
    		return failRender(e);
    	}
    }
}
