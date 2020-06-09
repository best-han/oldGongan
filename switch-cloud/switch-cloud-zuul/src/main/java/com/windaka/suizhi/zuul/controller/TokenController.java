package com.windaka.suizhi.zuul.controller;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.oauth.OssClientInfo;
import com.windaka.suizhi.api.user.constants.CredentialType;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.Tools;
//import com.windaka.suizhi.log.autoconfigure.LogMqClient;
import com.windaka.suizhi.zuul.commons.ZuulKaptcha;
import com.windaka.suizhi.zuul.feign.Oauth2Client;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.windaka.suizhi.api.common.OssConstants.*;

/**
 * 登陆、刷新token、退出
 * @author Aisino
 */
@Slf4j
@RestController
public class TokenController extends BaseController {

    @Autowired
    private Oauth2Client oauth2Client;

    @Autowired
    private ZuulKaptcha zuulKaptcha;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     * @return
     */
    @PostMapping("/plat/login")
    public Map<String,Object> login(@RequestBody Map<String, Object> params) {
        System.out.println("登录集群测试1111111");
        if(params == null){
            return super.failRender(ReturnConstants.CODE_FAILED,
                    ReturnConstants.MSG_LOGIN_USERNAME_IS_NULL);
        }
        String username = Tools.null2String((String)params.get("username"));
        String password = Tools.null2String((String)params.get("password"));
        String verifyCode = Tools.null2String((String)params.get("verifyCode"));
        try {


            if(StringUtils.isBlank(username)){
                return super.failRender(ReturnConstants.CODE_FAILED,
                        ReturnConstants.MSG_LOGIN_USERNAME_IS_NULL);
            }

            if(StringUtils.isBlank(password)){
                return super.failRender(ReturnConstants.CODE_FAILED,
                        ReturnConstants.MSG_LOGIN_PASSWORD_IS_NULL);
            }

            if(StringUtils.isBlank(verifyCode)){
                return super.failRender(ReturnConstants.CODE_FAILED,
                        ReturnConstants.MSG_VERIFY_CODE_IS_NULL);
            }

            //首先验证验证码是否正确
   /*         if(!zuulKaptcha.validate(verifyCode, 300)){
                return super.failRender(ReturnConstants.CODE_FAILED,
                        ReturnConstants.MSG_LOGIN_FAILED_VERIFYCODE_BAD);
            }*/

            //组装登录参数，准备进入认证中心
            //认证中心通过spring security进行认证，用户信息存入redis及返回token
            Map<String, String> parameters = new HashMap<>();
            parameters.put(OAuth2Utils.GRANT_TYPE, "password");
            parameters.put(OAuth2Utils.CLIENT_ID, OssClientInfo.CLIENT_ID);
            parameters.put("client_secret", OssClientInfo.CLIENT_SECRET);
            parameters.put(OAuth2Utils.SCOPE, OssClientInfo.CLIENT_SCOPE);
            //		parameters.put("username", username);
            // 为了支持多类型登录，这里在username后拼装上登录类型
            parameters.put("username", username + "|" + password + "|" + CredentialType.USERNAME.name());
            parameters.put("password", password);

            Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

            Map<String, Object> data = null;
            if (tokenInfo != null && !StringUtils.isBlank(tokenInfo.get("access_token").toString())) {
                saveLoginLog(username,"用户名密码登陆", true);
                data = new HashMap<>();
                data.put("access_token", tokenInfo.get("access_token"));
                data.put("refresh_token", tokenInfo.get("refresh_token"));
                data.put("userId", ((Map<String, Object>)tokenInfo.get("loginUser")).get("userId"));

                //增加各种手机token放入缓存 hjt--2019/11/15
                String mobileType=(String)params.get("mobileType");
                String mobileToken=(String)params.get("mobileToken");
                //先清除之前登陆过的手机账号缓存，
                this.appLogout(username);
                if(StringUtils.isNotBlank(mobileType) && StringUtils.isNotBlank(mobileToken)){
                    if(mobileType.equals(MOBILE_HW)){
                        stringRedisTemplate.boundHashOps(MOBILE_USERNAME_HW).put(username,mobileToken);
                    }
                    if(mobileType.equals(MOBILE_XM)){
                        stringRedisTemplate.boundHashOps(MOBILE_USERNAME_XM).put(username,mobileToken);
                    }
                    if(mobileType.equals(MOBILE_VIVO)){
                        stringRedisTemplate.boundHashOps(MOBILE_USERNAME_VIVO).put(username,mobileToken);
                    }
                    if(mobileType.equals(MOBILE_OPPO)){
                        stringRedisTemplate.boundHashOps(MOBILE__USERNAME_OPPO).put(username,mobileToken);
                    }

                }//end

                return super.render(data, ReturnConstants.CODE_LOGIN_SUCESS, ReturnConstants.MSG_LOGIN_SUCESS);
            } else {
                saveLoginLog(username,"用户名密码登陆", false);
                return super.failRender(ReturnConstants.CODE_LOGIN_FAILEED_USER_OR_PWD_BAD, ReturnConstants.MSG_LOGIN_FAILEED_USER_OR_PWD_BAD);
            }
        }catch (Exception e){
            log.error("[TokenController.login]->用户{}登录失败,失败信息：{}", username,  e.getMessage());
            if(e instanceof FeignException){
                FeignException feignException = (FeignException)e;
                if(Tools.null2String(feignException.getMessage()).contains(ReturnConstants.CODE_LOGIN_FAILEED_USER_OR_PWD_BAD) ){
                    return super.failRender(ReturnConstants.CODE_LOGIN_FAILEED_USER_OR_PWD_BAD, ReturnConstants.MSG_LOGIN_FAILEED_USER_OR_PWD_BAD);
                }
            }
            return super.failRender(ReturnConstants.CODE_FAILED, ReturnConstants.MSG_LOGIN_FAILED);
        }
    }

    /**
     * 短信登录 为支持后期的多类型登录准备 需要使用此功能之前做测试后才能上线
     * @param phone
     * @param key
     * @param code
     * @return

    @PostMapping("/oss/login-sms")
    public Map<String, Object> smsLogin(String phone, String key, String code) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, OssClientInfo.CLIENT_ID);
        parameters.put("client_secret", OssClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, OssClientInfo.CLIENT_SCOPE);
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了校验短信验证码，我们也拼上code等
        parameters.put("username", phone + "|" + CredentialType.PHONE.name() + "|" + key + "|" + code + "|"
                + DigestUtils.md5Hex(key + code));
        // 短信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", phone);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        saveLoginLog(phone,"手机号短信登陆", true);
        return tokenInfo;
    }
     */

    /**
     * 微信登录 为支持后期的多类型登录准备 没有环境，需要使用此功能之前做测试后才能上线
     * @return

    @PostMapping("/oss/login-wechat")
    public Map<String, Object> smsLogin(String openid, String tempCode) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, OssClientInfo.CLIENT_ID);
        parameters.put("client_secret", OssClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, OssClientInfo.CLIENT_SCOPE);
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了服务端校验，我们也拼上tempCode
        parameters.put("username", openid + "|" + CredentialType.WECHAT_OPENID.name() + "|" + tempCode);
        // 微信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", tempCode);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        saveLoginLog(openid, "微信登陆", true);
        return tokenInfo;
    }
     */


    /**
     * 登陆日志
     *
     * @param username
     */
    private void saveLoginLog(String username, String remark, boolean flag) {
     //   log.info("{}登陆,凭证{}", remark, username);
        //异步手动调用日志消息发送客户端，发送消息队列
    //    logMqClient.sendLogMsg(null,username,"登录",null, remark, flag, Tools.getIpAddress(request));
    }

    /**
     * 系统刷新refresh_token
     *
     * @param refresh_token
     * @return
     */
    @PostMapping("/oss/refresh_token")
    public Map<String, Object> refresh_token(String refresh_token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(OAuth2Utils.CLIENT_ID, OssClientInfo.CLIENT_ID);
        parameters.put("client_secret", OssClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, OssClientInfo.CLIENT_SCOPE);
        parameters.put("refresh_token", refresh_token);
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        return oauth2Client.postAccessToken(parameters);
    }

    /**
     * 退出
     */
    @GetMapping("/plat/logout")
    public void logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNotBlank(token)){
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        oauth2Client.removeToken(access_token);
    }
    /**
     * app退出   删除当前目标设备的token
     */
    @GetMapping("/plat/appLogout")
    public Map<String,Object> appLogout(String username) {
        try{
            stringRedisTemplate.boundHashOps(MOBILE_USERNAME_HW).delete(username);
            stringRedisTemplate.boundHashOps(MOBILE_USERNAME_XM).delete(username);
            stringRedisTemplate.boundHashOps(MOBILE__USERNAME_OPPO).delete(username);
            stringRedisTemplate.boundHashOps(MOBILE_USERNAME_VIVO).delete(username);
            return super.render(null, ReturnConstants.CODE_SUCESS, username+"退出成功");
        }catch(Exception e){
            log.error("[TokenController.appLogout]->用户{}退出登录失败,失败信息：{}", username,  e.getMessage());
            return super.failRender(ReturnConstants.CODE_FAILED,username+"退出失败");
        }
    }
}
