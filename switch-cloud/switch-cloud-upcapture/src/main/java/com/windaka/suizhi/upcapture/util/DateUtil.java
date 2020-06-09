package com.windaka.suizhi.upcapture.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Description TODO
 * @Author wcl
 * @Date 2019/8/15 0015 下午 4:10
 */
public class DateUtil {

	public static String getTodayStartTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar todayStart = Calendar.getInstance();
		todayStart.set(Calendar.HOUR_OF_DAY, 0);
		todayStart.set(Calendar.MINUTE, 0);
		todayStart.set(Calendar.SECOND, 0);
		todayStart.set(Calendar.MILLISECOND, 0);
		return sdf.format(todayStart.getTime());
	}

	public static String getTodayEndTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR_OF_DAY, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		return sdf.format(todayEnd.getTime());
	}

}
