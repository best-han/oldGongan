package com.windaka.suizhi.mpi;

import com.windaka.suizhi.mpi.websocket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
public class SjwlMpiApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(SjwlMpiApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        SocketServer.setApplicationContext(configurableApplicationContext);
//        try {
//            ShellUtil.executeCommand("E:/software/nginx-rtmp-win32-dev/nginx.exe");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
