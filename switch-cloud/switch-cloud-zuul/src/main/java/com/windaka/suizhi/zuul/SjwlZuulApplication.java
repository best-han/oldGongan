package com.windaka.suizhi.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关
 * @author Aisino
 */
@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class SjwlZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SjwlZuulApplication.class, args);
    }
}
