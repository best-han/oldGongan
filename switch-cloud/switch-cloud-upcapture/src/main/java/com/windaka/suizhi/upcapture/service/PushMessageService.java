package com.windaka.suizhi.upcapture.service;


import java.util.Set;

public interface PushMessageService {
    /**
     * 通知栏消息推送
     */
    public void pushMsg(Set<String> targets, String content, String title,int wclNum) throws Exception ;

    /**
     * 透传推送
     */
   // public int pushTransMsg(String target, String content) throws Exception ;

}
