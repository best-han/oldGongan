package com.windaka.suizhi.mpi.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.windaka.suizhi.mpi.service.AdjustTaskData;

/**
 * 
 * @project: JeeSite Web
 * @Description: 项目启动时执行任务
 * @author: kakaxi
 * @date: 2019年12月9日 下午2:30:42
 */
@WebListener
public class SocketListener implements ServletContextListener{
	
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	ServletContext sc=servletContextEvent.getServletContext();
    	AdjustTaskData adjustTaskData = (AdjustTaskData)WebApplicationContextUtils.getWebApplicationContext(sc).getBean(AdjustTaskData.class);
        //获取大华平台车进出数据
        new Thread(new Runnable() {
			@Override
			public void run() {
				adjustTaskData.adjustTaskData();
			}
		}).start();
    }

    public void contextDestroyed(ServletContextEvent sce) {
      //停掉所有线程
      System.exit(0);
    }

}
