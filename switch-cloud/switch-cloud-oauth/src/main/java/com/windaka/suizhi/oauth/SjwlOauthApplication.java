package com.windaka.suizhi.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 认证中心
 * @author hjt
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SjwlOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjwlOauthApplication.class, args);
    }
}
