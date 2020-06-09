package com.windaka.suizhi.mpi.controller;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.mpi.model.DeviceInfo;
import com.windaka.suizhi.mpi.service.ConvertRtspRtmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import com.windaka.suizhi.mpi.websocket.SocketServer;

@Slf4j
@RestController
public class ConvertRtspRtmpController extends BaseController {

    @Autowired
    ConvertRtspRtmpService convertRtspRtmpService;

    @GetMapping("/video")
    public Map<String,Object> queryRtmpVideoUrl(@RequestParam Map<String,Object> params) {

        try{
            Map<String,Object> map = new HashMap<>();
            String xqCode=(String) params.get("xqCode");//小区code
            String capDevChannel=(String) params.get("capDevChannel");//要转流的地址
            String tokenId=(String)params.get("tokenId");//tokenI
            String capDevCode=(String)params.get("capDevCode");//代表某个NVR的设备Id
            String url=convertRtspRtmpService.queryRtmpVideoUrl(tokenId,xqCode,capDevCode,capDevChannel);
            map.put("rtmpUrl",url);
            if(!url.equals("")){//表示成功获取到url
                DeviceInfo deviceInfo=new DeviceInfo();
                deviceInfo.setXqCode((String) params.get("xqCode"));
                deviceInfo.setCapDevChannel((String) params.get("capDevChannel"));
                SocketServer.tokenDeviceMap.put((String)params.get("tokenId"),deviceInfo);
            }
            return render(map);

        }catch (Exception e){
            log.info("[ConvertRtspRtmpController.queryRtmpVideoUrl,参数：{},异常信息{}]","",e.getMessage());
            return failRender(e);
        }


    }
    @DeleteMapping("/video")
    public Map<String,Object> closeRtmpVideoStream(@RequestParam Map<String,Object> params) {

        try{
            Map<String,Object> map = new HashMap<>();
            String xqCode=(String) params.get("xqCode");
            String capDevChannel=(String) params.get("capDevChannel");//要转流的地址
            String tokenId=(String)params.get("tokenId");//tokenId
            String capDevCode=(String)params.get("capDevCode");//代表某个NVR的设备Id
            if(convertRtspRtmpService.closeRtmpVideoStream(tokenId,xqCode,capDevCode,capDevChannel)){
                return render();
            }else{
                return failRender(ReturnConstants.CODE_FAILED,"关闭视频流失败");
            }

        }catch (Exception e){
            log.info("[ConvertRtspRtmpController.closeRtmpVideoStream,参数：{},异常信息{}]","",e.getMessage());
            return failRender(e);
        }



    }



}
