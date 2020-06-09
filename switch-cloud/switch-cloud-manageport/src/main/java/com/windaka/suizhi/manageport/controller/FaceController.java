package com.windaka.suizhi.manageport.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.manageport.service.FaceInfoService;
import com.windaka.suizhi.manageport.service.FaceStrangerInfoService;
import com.windaka.suizhi.manageport.service.FaceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 人脸相关
 */
@Slf4j
@RestController
@RequestMapping("/face")
public class FaceController extends BaseController {

    @Autowired
    FaceInfoService faceInfoService;
    @Autowired
    FaceStrangerInfoService faceStrangerInfoService;
    @Autowired
    FaceTypeService faceTypeService;

    /**
     *人脸特征上传
     * @param params
     * @return
     */
    @PostMapping("/info")
    public Map<String,Object> saveFaceInfos(@RequestBody Map<String,Object> params){
        try{
            faceInfoService.saveFaceInfos(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.saveFaceInfos,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 人脸特征修改
     * @param params
     * @return
     */
    @PutMapping("/info")
    public Map<String,Object> updateFaceInfo(@RequestBody Map<String,Object> params){
        try{
            faceInfoService.updateFaceInfo(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.updateFaceInfo,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 人脸特征删除
     * @param params
     * @return
     */
    @DeleteMapping("/info")
    public Map<String,Object> deleteFaceInfo(@RequestBody Map<String,Object> params){
        try{
            faceInfoService.deleteFaceInfo(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.deleteFaceInfo,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     *人脸特征上传
     * @param params
     * @return
     */
    @PostMapping("/strangerInfo")
    public Map<String,Object> saveFaceStrangerInfos(@RequestBody Map<String,Object> params){
        try{
            faceStrangerInfoService.saveFaceStrangerInfos(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.saveFaceStrangerInfos,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 人脸特征修改
     * @param params
     * @return
     */
    @PutMapping("/strangerInfo")
    public Map<String,Object> updateFaceStrangerInfo(@RequestBody Map<String,Object> params){
        try{
            faceStrangerInfoService.updateFaceStrangerInfo(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.updateFaceStrangerInfo,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }
    /**
     * 人脸特征删除
     * @param params
     * @return
     */
    @DeleteMapping("/strangerInfo")
    public Map<String,Object> deleteFaceStrangerInfo(@RequestBody Map<String,Object> params){
        try{
            faceStrangerInfoService.deleteFaceStrangerInfo(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.deleteFaceStrangerInfo,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }

    /**
     *人脸类型上传（人脸库）
     * @param params
     * @return
     */
    @PostMapping("/type")
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
    @PutMapping("/type")
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
     * @param params
     * @return
     */
    @DeleteMapping("/type")
    public Map<String,Object> deleteFaceType(@RequestBody Map<String,Object> params){
        try{
            faceTypeService.deleteFaceType(params);
            return render();
        }catch(Exception e){
            log.info("[FaceController.deleteFaceType,参数：{},异常信息：{}]",params,e.getMessage());
            return failRender(e);
        }
    }


}
