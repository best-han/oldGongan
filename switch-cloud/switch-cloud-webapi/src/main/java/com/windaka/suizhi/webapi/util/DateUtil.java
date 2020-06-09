package com.windaka.suizhi.webapi.util;

import org.apache.http.util.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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

    public static String getMonthStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar monthStart = Calendar.getInstance();
        monthStart.add(Calendar.MONTH, 0);
        monthStart.set(Calendar.DAY_OF_MONTH, 1);
        monthStart.set(Calendar.HOUR_OF_DAY, 0);
        monthStart.set(Calendar.MINUTE, 0);
        monthStart.set(Calendar.SECOND, 0);
        monthStart.set(Calendar.MILLISECOND, 0);
        return sdf.format(monthStart.getTime());
    }
    public static String getMonthStartTime2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar monthStart = Calendar.getInstance();
        monthStart.add(Calendar.MONTH, 0);
        monthStart.set(Calendar.DAY_OF_MONTH, 1);
        monthStart.set(Calendar.HOUR_OF_DAY, 0);
        monthStart.set(Calendar.MINUTE, 0);
        monthStart.set(Calendar.SECOND, 0);
        monthStart.set(Calendar.MILLISECOND, 0);
        return sdf.format(monthStart.getTime());
    }

    public static String getMonthEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar monthEnd = Calendar.getInstance();
        monthEnd.add(Calendar.MONTH, 1);
        monthEnd.set(Calendar.DAY_OF_MONTH, 0);
        monthEnd.set(Calendar.HOUR_OF_DAY, 23);
        monthEnd.set(Calendar.MINUTE, 59);
        monthEnd.set(Calendar.SECOND, 59);
        monthEnd.set(Calendar.MILLISECOND, 999);
        return sdf.format(monthEnd.getTime());
    }

    public static String getYearStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar yearStart = Calendar.getInstance();
        yearStart.add(Calendar.YEAR, 0);
        yearStart.set(Calendar.DAY_OF_YEAR, 1);
        yearStart.set(Calendar.HOUR_OF_DAY, 0);
        yearStart.set(Calendar.MINUTE, 0);
        yearStart.set(Calendar.SECOND, 0);
        yearStart.set(Calendar.MILLISECOND, 0);
        return sdf.format(yearStart.getTime());
    }

    public static String getYearEndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar yearEnd = Calendar.getInstance();
        yearEnd.add(Calendar.YEAR, 1);
        yearEnd.set(Calendar.DAY_OF_YEAR, 0);
        yearEnd.set(Calendar.HOUR_OF_DAY, 23);
        yearEnd.set(Calendar.MINUTE, 59);
        yearEnd.set(Calendar.SECOND, 59);
        yearEnd.set(Calendar.MILLISECOND, 999);
        return sdf.format(yearEnd.getTime());
    }

    public static String getStringTime(Date time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    /**
     * .Description://根据字符日期返回星期几
     * .Author:麦克劳林
     * .@Date: 2018/12/29
     */
    public static String getWeek(String dateTime) {
        String week = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateTime);
            SimpleDateFormat dateFm = new SimpleDateFormat("EEEE", Locale.CHINESE);
            week = dateFm.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }

    /**
     * 获取过去7天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> getDays(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals - 1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获得近一年的时间范围
     *
     * @return
     */
    public static Map getYearTRange() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map condition = new HashMap();
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 24);
        condition.put("endTime", df.format(calendar.getTime()));
        calendar.set(Calendar.HOUR_OF_DAY, -8640);
        condition.put("beginTime", df.format(calendar.getTime()));
        return condition;
    }

    /**
     * 得到某年某月的天数
     * @param year
     * @param month
     * @return
     */
    public static int getDaysByYearMonth(int year, int month) {
//        Calendar a=Calendar.getInstance();
//        a.set(Calendar.YEAR,year);
//        a.set(Calendar.MONTH,month);
//        a.set(Calendar.DATE,1);
//        a.roll(Calendar.DATE,-1);
//        int maxDate=a.get(Calendar.DATE);
//        return maxDate;
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static List getDayListOfMonth() {
        List list = new ArrayList();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year)+"-"+month+"-"+i;
            list.add(aDate);
        }
        return list;
    }




}
