package com.windaka.suizhi.webapi.config;

import com.windaka.suizhi.common.LogImpl.LoginInfoImpl;
import com.windaka.suizhi.api.log.LoginInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hjt
 * @Date: 2018/12/21 01:59
 * @Version 1.0
 */
@Configuration
public class LogConfig {

    @Bean
    public LoginInfo loginInfo(){
        return new LoginInfoImpl();
    }

}
