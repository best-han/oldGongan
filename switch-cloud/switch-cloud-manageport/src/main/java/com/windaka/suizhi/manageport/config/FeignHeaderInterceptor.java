package com.windaka.suizhi.manageport.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 服务间调用请求头携带token
 * @author: hjt
 * @Date: 2019/1/16 18:56
 * @Version 1.0
 */
@Component
public class FeignHeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        requestTemplate.header(HttpHeaders.AUTHORIZATION, request.getHeader("Authorization"));
    }
}
