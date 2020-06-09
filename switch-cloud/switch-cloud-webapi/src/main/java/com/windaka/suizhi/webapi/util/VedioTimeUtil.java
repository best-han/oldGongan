package com.windaka.suizhi.webapi.util;

import com.windaka.suizhi.api.common.OssConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/6/13 0013 上午 8:45
 */
public class VedioTimeUtil {

	public static String capVedioBeginTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.SECOND, OssConstants.HALF_TIME_VEDIO);
		return df.format(cal.getTime());
	}

	public static String capVideoEndTime() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Calendar cal =Calendar.getInstance();
		cal.add(Calendar.SECOND, -OssConstants.HALF_TIME_VEDIO);
		return df.format(cal.getTime());
	}


}
