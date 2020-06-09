package com.windaka.suizhi.manageport.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @ClassName SpringBeanUtil
 * @Description 获取bean实体
 * @Author lixianhua
 * @Date 2019/12/8 11:43
 * @Version 1.0
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }

    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) getApplicationContext().getBean(clazz);
    }

    public static Object getBean(String name) throws BeansException {

        return getApplicationContext().getBean(name);
    }
}
