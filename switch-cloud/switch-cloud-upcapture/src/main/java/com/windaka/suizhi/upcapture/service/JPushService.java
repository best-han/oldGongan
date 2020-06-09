package com.windaka.suizhi.upcapture.service;

import java.util.Set;

public interface JPushService {

    /**
     * 极光推送消息 hjt
     * @param content
     */
    public void sendJPush(String content, Set<String> alias, String event,int wclNum);

}
