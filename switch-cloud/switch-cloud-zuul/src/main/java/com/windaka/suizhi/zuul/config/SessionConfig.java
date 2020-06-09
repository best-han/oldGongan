package com.windaka.suizhi.zuul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author: Corey
 * @Date: 2018/12/17 13:40
 * @Version 1.0
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 3600,redisNamespace = "oss:zuul:session")
public class SessionConfig {
}
