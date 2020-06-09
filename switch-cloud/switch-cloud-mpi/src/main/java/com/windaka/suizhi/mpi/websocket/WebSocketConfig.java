package com.windaka.suizhi.mpi.websocket;

import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


import javax.servlet.Servlet;

@Configuration
@ComponentScan("com.windaka.suizhi.mpi.websocket")
public class WebSocketConfig  {

    @Bean
    @ConditionalOnClass({ Servlet.class, Tomcat.class })
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
