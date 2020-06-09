package com.windaka.suizhi.zuul.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.zuul.commons.ZuulKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author: Aision
 */
@Slf4j
@RestController
@RequestMapping
public class KaptchaController extends BaseController {
    @Autowired
    private ZuulKaptcha zuulKaptcha;

    /**
     * 获取验证码
     * @return
     */
    @GetMapping("/plat/login/verifyCode")
    public Map render() {
        try {
            return render(zuulKaptcha.render());
        }catch (Exception e){
            return failRender(e);
        }
    }


}
