package com.windaka.suizhi.upcapture.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public interface HvPushService {

   /* *//**
     * 获取h华为Token
     *//*
    public String refreshToken();

    *//**
     * 消息体的封装
     * @return
     *//*
    public JSONObject formatJsonMsg(String title,String content,int wclNum);*/

    /**
     * 发送消息
     * @param title
     * @param content
     * @param deviceTokens
     * @throws Exception
     */
    public void pushHvMsg(String title,String content,JSONArray deviceTokens,int wclNum) throws Exception;



}
