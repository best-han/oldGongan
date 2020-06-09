package com.windaka.suizhi.manageport.feign;

import com.windaka.suizhi.manageport.config.FeignHeaderInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author: hjt
 * @Date: 2019/1/16 16:36
 * @Version 1.0
 */
@FeignClient(name = "sjwl-oauth", configuration = FeignHeaderInterceptor.class)
public interface OauthClient {
    @PostMapping("/oauth-internal/loginInfo/modify")
    public boolean updateLoginInfo(@RequestParam Map<String, Object> map);
}
