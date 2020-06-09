package com.windaka.suizhi.oauth.controller;

import com.windaka.suizhi.api.common.ReturnConstants;

import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.AppUserUtil;
import com.windaka.suizhi.common.utils.Tools;
//import com.windaka.suizhi.log.autoconfigure.LogMqClient;
import com.windaka.suizhi.oauth.service.impl.RedisAuthorizationCodeServices;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping
public class OAuth2Controller {

    @Autowired
    private RedisAuthorizationCodeServices redisAuthorizationCodeServices;

    @Autowired
    private HttpServletRequest request;
    /**
     * 当前登陆用户信息<br>
     * <p>
     * security获取当前登录用户的方法是SecurityContextHolder.getContext().getAuthentication()<br>
     * 返回值是接口org.springframework.security.core.Authentication，又继承了Principal<br>
     * 这里的实现类是org.springframework.security.oauth2.provider.OAuth2Authentication<br>
     * <p>
     * @return
     */
    @GetMapping("/user-me")
    public Authentication principal() throws Exception{
        try {
            OAuth2Authentication authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            //每次验证时候，更新一下redis，更新token过期时间
            if (authentication != null) {
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                String access_token = details.getTokenValue();
                redisAuthorizationCodeServices.refreshStoreAccessToken(access_token, authentication);
            }
            log.debug("user-me:{}", authentication.getName());
            return authentication;
        }catch (Exception e){
            throw new OssRenderException(ReturnConstants.STATUS_TOKEN_FAILED);
        }
    }

    /**
     * 个人信息修改时，更新redis中的个人信息
     * @param map
     * @return
     */
    @PostMapping("/oauth-internal/loginInfo/modify")
    public boolean updateLoginInfo(@RequestParam Map<String, Object> map){
        String cname = (String)map.get("cname");
        String phone = (String)map.get("phone");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (authentication instanceof OAuth2Authentication) {
                OAuth2Authentication oAuth2Auth = (OAuth2Authentication) authentication;
                authentication = oAuth2Auth.getUserAuthentication();

                if (authentication instanceof UsernamePasswordAuthenticationToken) {
                    UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                    Object principal = authentication.getPrincipal();
                    //先取出登录信息，并判断来源，然后修改信息
                    if (principal instanceof LoginAppUser) {
                        LoginAppUser loginAppUser = (LoginAppUser) principal;

                        //更新姓名
                        if (StringUtils.isNotBlank(cname)) {
                            loginAppUser.setCname(cname);
                        }

                        //更新手机号
                        if (StringUtils.isNotBlank(phone)) {
                            loginAppUser.setPhone(phone);
                        }
                    } else {
                        Map updMap = (Map) authenticationToken.getDetails();
                        updMap = (Map) updMap.get("principal");

                        //更新姓名
                        if (StringUtils.isNotBlank(cname)) {
                            updMap.put("cname", cname);
                        }

                        //更新手机号
                        if (StringUtils.isNotBlank(phone)) {
                            updMap.put("phone", phone);
                        }
                    }
                }
                OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) oAuth2Auth.getDetails();
                String access_token = details.getTokenValue();
                //将修改后的信息保存到redis中
                redisAuthorizationCodeServices.refreshStoreAccessToken(access_token, oAuth2Auth);
            }
            log.debug("更新登录信息[OAuth2Controller.updateLoginInfo]:{}",authentication.getName());
        }catch (Exception e){
            log.debug("更新登录信息失败[OAuth2Controller.updateLoginInfo]:{}",authentication.getName());

            return false;
        }
        return true;
    }


    @Autowired
    private ConsumerTokenServices tokenServices;

    /**
     * 注销登陆/退出
     * 移除access_token和refresh_token<br>
     * 用ConsumerTokenServices，该接口的实现类DefaultTokenServices已有相关实现，我们不再重复造轮子
     * @param access_token
     */
    @DeleteMapping(value = "/remove_token")
    public void removeToken(@RequestParam("access_token") String access_token) {
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        boolean flag = tokenServices.revokeToken(access_token);
        if (flag && loginAppUser!=null) {
            //saveL(loginAppUser, Tools.getIpAddress(request), flag);
        }
    }

 /*   @Autowired
    private LogMqClient logMqClient;

    *//**
     * 登出日志
     * @param loginAppUser
     * @param iPaddress
     * @param flag
     *//*
    private void saveLogoutLog(LoginAppUser loginAppUser, String iPaddress, boolean flag) {
        log.info("用户:{}登出", loginAppUser.getUsername());
                //异步手动调用日志消息发送客户端，发送消息队列
                logMqClient.sendLogMsg(loginAppUser.getUserId(),loginAppUser.getUsername(),"登出",null, "推出", flag, iPaddress);
    }*/

}
