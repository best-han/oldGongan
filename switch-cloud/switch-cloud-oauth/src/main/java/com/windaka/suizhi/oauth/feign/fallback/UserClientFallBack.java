package com.windaka.suizhi.oauth.feign.fallback;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.oauth.feign.UserClient;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: hjt
 * @Date: 2018/12/14 13:45
 * @Version 1.0
 */
@Component
public class UserClientFallBack extends BaseController implements UserClient {
    @Override
    public Map<String, Object> queryByUsername(String username, String password) {

        return failRender(ReturnConstants.CODE_FAILED, ReturnConstants.MSG_CLIENT_FAILED);
    }

    @Override
    public void wechatLoginCheck(String tempCode, String openid) {

    }
}
