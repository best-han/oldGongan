package com.windaka.suizhi.mpi.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.DesUtil;
import com.windaka.suizhi.mpi.config.ConvertRtspRtmpConfig;
import com.windaka.suizhi.mpi.config.DesConfig;
import com.windaka.suizhi.mpi.dao.XqServerDao;
import com.windaka.suizhi.mpi.service.ConvertRtspRtmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@Component
public class ConvertRtspRtmpServiceImp implements ConvertRtspRtmpService {

    @Autowired
    private RestTemplate restTemplate;//这样就可以直接调用api使用了或者直接new对象也一样

    @Autowired
    private XqServerDao xqServerDao;

    @Autowired
    private ConvertRtspRtmpConfig convertRtspRtmpConfig;


    @Transactional
    @Override
    public String queryRtmpVideoUrl(String tokenId,String xqCode,String capDevCode, String capDevChannel) throws OssRenderException {


        if(tokenId==null|| tokenId.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少tokenId参数");
        }
        if(xqCode==null|| xqCode.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少xqCode参数");
        }
        if(capDevChannel==null|| capDevChannel.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少capDevChannel参数");
        }

        if(capDevCode==null|| capDevCode.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少capDevCode参数");
        }

        String  xqServer=xqServerDao.queryXqServerUrl(xqCode)+convertRtspRtmpConfig.getRtmpOpenPath();
        if(xqServer==null||xqServer.equalsIgnoreCase("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
        }
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        Map<String,Object> requestParam=new HashMap<>();
        requestParam.put("capDevChannel",capDevChannel);
        requestParam.put("capDevCode",capDevCode);
        String requestJson= JSONObject.toJSONString(requestParam);
        String desParam= DesUtil.encode(requestJson, DesConfig.DES_KEY);
        param.add("data",desParam);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String,Object>> formEntity = new HttpEntity<>(param,headers);
        ResponseEntity<String> responseEntity=null;
        JSONObject resultJson=null;
        try{
            responseEntity = restTemplate.postForEntity(xqServer,formEntity, String.class);
            resultJson=JSONObject.parseObject(responseEntity.getBody());
        }catch (Exception e){
            log.info("[ConvertRtspRtmpServiceImp.queryRtmpVideoUrl,参数：{},异常信息{}]","",e.getMessage());
        }
        if(resultJson.getBoolean("success")) {//表示获取成功
            JSONObject jsonObject = resultJson.getJSONObject("data");
            return jsonObject.getString("rtspUrl");
        }else{
            throw new OssRenderException(ReturnConstants.CODE_FAILED,resultJson.getString("errMsg"));
        }


    }
    @Transactional
    @Override
    public boolean closeRtmpVideoStream(String tokenId,String xqCode,String capDevCode, String capDevChannel) throws OssRenderException {

        if(tokenId==null|| tokenId.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少tokenId参数");
        }
        if(xqCode==null|| xqCode.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少xqCode参数");
        }
        if(capDevChannel==null|| capDevChannel.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少capDevChannel参数");
        }

        if(capDevCode==null|| capDevCode.equals("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"缺少capDevCode参数");
        }

        String  xqServer=xqServerDao.queryXqServerUrl(xqCode)+convertRtspRtmpConfig.getRtmpClosePath();
        if(xqServer==null||xqServer.equalsIgnoreCase("")){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"没有可查的服务");
        }
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        Map<String,Object> requestParam=new HashMap<>();
        requestParam.put("capDevChannel",capDevChannel);
        requestParam.put("capDevCode",capDevCode);
        String requestJson= JSONObject.toJSONString(requestParam);
        String desParam= DesUtil.encode(requestJson, DesConfig.DES_KEY);
        param.add("data",desParam);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String,Object>> formEntity = new HttpEntity<>(param,headers);
        ResponseEntity<String> responseEntity=null;
        JSONObject resultJson=null;
        try{
            responseEntity = restTemplate.postForEntity(xqServer,formEntity, String.class);
            resultJson=JSONObject.parseObject(responseEntity.getBody());
        }catch (Exception e){
            log.info("[ConvertRtspRtmpServiceImp.closeRtmpVideoStream,参数：{},异常信息{}]","",e.getMessage());
        }
        if(resultJson.getBoolean("success")) {//表示获取成功
            return true;
        }else{
            return false;
        }
    }


}
