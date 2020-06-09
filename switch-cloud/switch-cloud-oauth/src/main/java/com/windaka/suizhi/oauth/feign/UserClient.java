package com.windaka.suizhi.oauth.feign;

import com.windaka.suizhi.oauth.feign.fallback.UserClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "sjwl-user", fallback = UserClientFallBack.class)
public interface UserClient {

    @GetMapping(value = "/users-anon/internal", params = {"username","password"})
    Map<String,Object> queryByUsername(@RequestParam("username") String username, @RequestParam("password") String password);

    @GetMapping("/wechat/login-check")
    public void wechatLoginCheck(@RequestParam("tempCode") String tempCode, @RequestParam("openid") String openid);
}
