package com.windaka.suizhi.zuul.commons;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_DATE;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * @author: Corey
 * @Date: 2018/12/17 14:06
 * @Version 1.0
 */
@Component
public class ZuulKaptcha  {

    @Autowired
    private Producer producer;

    @Autowired
    private HttpServletRequest request;

    /**
     * 生成验证码并返回
     * @return
     */
    public Map<String, Object> render() throws OssRenderException{
        Map<String, Object> map = null;
        try{
            String verifyCode = producer.createText();
            request.getSession().setAttribute(KAPTCHA_SESSION_KEY, verifyCode);
            request.getSession().setAttribute(KAPTCHA_SESSION_DATE, System.currentTimeMillis());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(producer.createImage(verifyCode), "png", out);

            String verifyCodeImg = new BASE64Encoder().encode(out.toByteArray());

            map = new HashMap<>();
            map.put("verifyCode",verifyCodeImg);
            return map;
        } catch (IOException e) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED,ReturnConstants.MSG_VERIFY_CODE_CREATE_FAILED);
        }
    }

    /**
     * 验证验证码的正确性
     * @param code
     * @param second
     * @return
     */
    public boolean validate(String code, long second) {
        if(StringUtils.isBlank(code)){
            return false;
        }
        HttpSession httpSession = request.getSession(false);
        String sessionVerifyCode;
        if (httpSession != null && (sessionVerifyCode = (String) httpSession.getAttribute(KAPTCHA_SESSION_KEY)) != null) {

            if (sessionVerifyCode.equalsIgnoreCase(code)) {

                long sessionTime = (long) httpSession.getAttribute(KAPTCHA_SESSION_DATE);
                long duration = (System.currentTimeMillis() - sessionTime) / 1000;
                if (duration < second) {
                    httpSession.removeAttribute(KAPTCHA_SESSION_KEY);
                    httpSession.removeAttribute(KAPTCHA_SESSION_DATE);
                    return true;
                }
            }
        }
        return false;
    }
}
