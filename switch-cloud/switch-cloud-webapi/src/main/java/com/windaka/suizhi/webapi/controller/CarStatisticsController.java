package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.service.CarStatisticsService;
import com.windaka.suizhi.webapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 车辆统计 dee
 */
@Slf4j
@RestController
public class CarStatisticsController extends BaseController {

    @Autowired
    private CarStatisticsService carStatisticsService;

    /**
     * 车辆基本信息（完成） dee
     * @param params
     * @return
     */
    @GetMapping("/car/baseInfo")
    public Map<String,Object> queryCarBaseInfo(@RequestParam Map<String, Object> params){
        try {
//            int [][]data={{10500,2969,117,3},{8750,2789,125,1},{11236,2750,128,3},{14759,3360,113,0},{12312,2321,142,10}};
//            Map map=new LinkedHashMap();
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
//            {
//                String xqCode=params.get("xqCode").toString();
//                if(xqCode.equals("11"))
//                {
//                    map.put("carTotalNum",data[0][0]);
//                    map.put("monthSenseNum",data[0][1]);
//                    map.put("todaySenseNum",data[0][2]);
//                    map.put("todaySerseStrangerNum",data[0][3]);
//                }
//                else if(xqCode.equals("12"))
//                {
//                    map.put("carTotalNum",data[1][0]);
//                    map.put("monthSenseNum",data[1][1]);
//                    map.put("todaySenseNum",data[1][2]);
//                    map.put("todaySerseStrangerNum",data[1][3]);
//                }
//                else if(xqCode.equals("13"))
//                {
//                    map.put("carTotalNum",data[2][0]);
//                    map.put("monthSenseNum",data[2][1]);
//                    map.put("todaySenseNum",data[2][2]);
//                    map.put("todaySerseStrangerNum",data[2][3]);
//                }
//                else if(xqCode.equals("14"))
//                {
//                    map.put("carTotalNum",data[3][0]);
//                    map.put("monthSenseNum",data[3][1]);
//                    map.put("todaySenseNum",data[3][2]);
//                    map.put("todaySerseStrangerNum",data[3][3]);
//                }
//                else if(xqCode.equals("02"))
//                {
//                    map.put("carTotalNum",data[4][0]);
//                    map.put("monthSenseNum",data[4][1]);
//                    map.put("todaySenseNum",data[4][2]);
//                    map.put("todaySerseStrangerNum",data[4][3]);
//                }
//            }
//            else if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
//            {
//                String subdistrictId=params.get("subdistrictId").toString();
//                if(subdistrictId.equals("2"))
//                {
//                    map.put("carTotalNum",data[0][0]+data[1][0]+data[2][0]+data[3][0]+data[4][0]);
//                    map.put("monthSenseNum",data[0][1]+data[1][1]+data[2][1]+data[3][1]+data[4][1]);
//                    map.put("todaySenseNum",data[0][2]+data[1][2]+data[2][2]+data[3][2]+data[4][2]);
//                    map.put("todaySerseStrangerNum",data[0][3]+data[1][3]+data[2][3]+data[3][3]+data[4][3]);
//                }
//            }
//            else
//            {
//                map.put("carTotalNum",data[0][0]+data[1][0]+data[2][0]+data[3][0]+data[4][0]);
//                map.put("monthSenseNum",data[0][1]+data[1][1]+data[2][1]+data[3][1]+data[4][1]);
//                map.put("todaySenseNum",data[0][2]+data[1][2]+data[2][2]+data[3][2]+data[4][2]);
//                map.put("todaySerseStrangerNum",data[0][3]+data[1][3]+data[2][3]+data[3][3]+data[4][3]);
//            }
//            return render(map);
            Map map=new LinkedHashMap();
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            int carTotalNum=0;
            int monthSenseNum=0;
            int todaySenseNum=0;
            int todaySerseStrangerNum=0;
            List l1=carStatisticsService.queryCarTotalNum(params);
            String beginTimeM=DateUtil.getMonthStartTime();
            String endTimeM=DateUtil.getMonthEndTime();
            List l2=carStatisticsService.queryCarSenseNum(params,beginTimeM,endTimeM);
            String beginTimeD=DateUtil.getTodayStartTime();
            String endTimeD=DateUtil.getTodayEndTime();
            List l3=carStatisticsService.queryCarSenseNum(params,beginTimeD,endTimeD);
            List l4=carStatisticsService.queryCarSerseStrangerNum(params,beginTimeD,endTimeD);
            Map tMap;
            if(l1!=null&&!l1.isEmpty())
            {
                tMap=(Map)l1.get(0);
                carTotalNum=Integer.parseInt(tMap.get("carTotalNum").toString());
            }
            if(l2!=null&&!l2.isEmpty())
            {
                tMap=(Map)l2.get(0);
                monthSenseNum=Integer.parseInt(tMap.get("senseNum").toString());
            }
            if(l3!=null&&!l3.isEmpty())
            {
                tMap=(Map)l3.get(0);
                todaySenseNum=Integer.parseInt(tMap.get("senseNum").toString());
            }
            if(l4!=null&&!l4.isEmpty())
            {
                tMap=(Map)l4.get(0);
                todaySerseStrangerNum=Integer.parseInt(tMap.get("todaySerseStrangerNum").toString());
            }
            map.put("carTotalNum",carTotalNum);
            map.put("monthSenseNum",monthSenseNum);
            map.put("todaySenseNum",todaySenseNum);
            map.put("todaySerseStrangerNum",todaySerseStrangerNum);
            return render(map);

        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 车辆出入信息统计（完成） dee
     * @param params
     * @return
     */
    @GetMapping("/car/stream")
    public Map<String,Object> queryCarStream(@RequestParam Map<String, Object> params){
        try {
//            Map map=new LinkedHashMap();
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//
//            int [][]outCountDay={
//                    {0,0,0,0,0,0,12,15,13,0,0,0,3,7,3,1,14,23,6,1,3,1,0,0},
//                    {0,0,0,0,0,1,5,9,15,0,0,0,3,2,3,4,7,3,3,4,5,0,0,0},
//                    {0,0,0,0,0,0,6,9,13,0,0,2,5,13,2,1,23,11,12,2,0,0,0,0},
//                    {0,0,0,0,0,0,11,10,22,0,0,0,5,4,2,1,5,2,3,3,1,1,0,0},
//                    {0,0,0,0,0,0,9,11,24,0,0,0,6,7,2,1,6,1,3,2,2,2,0,0}};
//            int [][]enterCountDay={
//                    {0,0,0,0,0,0,1,1,2,1,4,15,12,2,1,0,0,1,3,14,11,6,3,2},
//                    {0,0,0,0,0,0,3,0,2,7,4,8,4,1,5,0,1,2,4,12,9,2,1,0},
//                    {0,0,0,0,0,1,1,2,4,5,2,6,3,3,0,0,0,2,5,13,5,7,2,1},
//                    {0,0,0,0,0,0,2,3,3,6,4,15,12,2,0,0,0,2,7,10,13,6,0,0},
//                    {0,0,0,0,0,0,2,3,5,6,1,12,4,7,0,0,0,8,4,5,11,7,0,0}};
//            int []dateListDay={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
//                    18,19,20,21,22,23};
//            int [][]outCountWeek={
//                    {86,103,102,85,81,90,47},
//                    {90,102,109,53,81,110,105},
//                    {58,85,69,67,114,109,75},
//                    {45,90,45,66,65,115,77},
//                    {78,81,67,130,171,62,187}};
//            int [][]enterCountWeek={
//                    {100,94,36,70,49,79,28},
//                    {56,84,96,62,53,44,38},
//                    {75,36,84,39,91,68,48},
//                    {94,29,81,62,36,36,35},
//                    {46,190,112,85,70,140,75}};
//            String []dateListWeek=new String[7];
//            Date today=new Date();
//            SimpleDateFormat f=new SimpleDateFormat("HH");
//            String time=f.format(today);
//            int tt=Integer.parseInt(time);
//            for(int i=tt;i<24;i++)
//            {
//                outCountDay[0][i]=0;
//                enterCountDay[0][i]=0;
//                outCountDay[1][i]=0;
//                enterCountDay[1][i]=0;
//                outCountDay[2][i]=0;
//                enterCountDay[2][i]=0;
//                outCountDay[3][i]=0;
//                enterCountDay[3][i]=0;
//                outCountDay[4][i]=0;
//                enterCountDay[4][i]=0;
//            }
//
//            ArrayList<String> weekDays=DateUtil.getDays(7);
//            Iterator<String> wdi=weekDays.iterator();
//            int j=0;
//            while (wdi.hasNext())
//            {
//                String wdt=wdi.next();
//                String wds=DateUtil.getWeek(wdt);
//                dateListWeek[j]=wds;
//                j++;
//                //System.out.println(wds);
//            }
//
//            Map dayMap=new LinkedHashMap();
//            Map weekMap=new LinkedHashMap();
//            if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
//            {
//                String xqCode=params.get("xqCode").toString();
//                if(xqCode.equals("11"))
//                {
//                    dayMap.put("outCount",outCountDay[0]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[0]);
//                    weekMap.put("outCount",outCountWeek[0]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[0]);
//                }
//                else if(xqCode.equals("12"))
//                {
//                    dayMap.put("outCount",outCountDay[1]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[1]);
//                    weekMap.put("outCount",outCountWeek[1]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[1]);
//                }
//                else if(xqCode.equals("13"))
//                {
//                    dayMap.put("outCount",outCountDay[2]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[2]);
//                    weekMap.put("outCount",outCountWeek[2]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[2]);
//                }
//                else if(xqCode.equals("14"))
//                {
//                    dayMap.put("outCount",outCountDay[3]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[3]);
//                    weekMap.put("outCount",outCountWeek[3]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[3]);
//                }
//                else if(xqCode.equals("02"))
//                {
//                    dayMap.put("outCount",outCountDay[4]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[4]);
//                    weekMap.put("outCount",outCountWeek[4]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[4]);
//                }
//            }
//            else if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
//            {
//                String subdistrictId=params.get("subdistrictId").toString();
//                if(subdistrictId.equals("2"))
//                {
//                    int []sOutCountDay=new int [24];
////                    int []sOutCountMonth=new int [30];
////                    int []sOutCountYear=new int [12];
//                    int []sOutCountWeek=new int [7];
//                    for (int i=0;i<24;i++)
//                    {
//                        sOutCountDay[i]=outCountDay[0][i]+outCountDay[1][i]+outCountDay[2][i]+outCountDay[3][i]+outCountDay[4][i];
//                    }
////                    for (int i=0;i<30;i++)
////                    {
////                        sOutCountMonth[i]=outCountMonth[0][i]+outCountMonth[1][i]+outCountMonth[2][i]+outCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sOutCountYear[i]=outCountYear[0][i]+outCountYear[1][i]+outCountYear[2][i]+outCountYear[3][i];
////                    }
//                    for (int i=0;i<7;i++)
//                    {
//                        sOutCountWeek[i]=outCountWeek[0][i]+outCountWeek[1][i]+outCountWeek[2][i]+outCountWeek[3][i]+outCountWeek[4][i];
//                    }
//                    int []sEnterCountDay=new int [24];
////                    int []sEnterCountMonth=new int [30];
////                    int []sEnterCountYear=new int [12];
//                    int []sEnterCountWeek=new int [7];
//                    for (int i=0;i<24;i++)
//                    {
//                        sEnterCountDay[i]=enterCountDay[0][i]+enterCountDay[1][i]+enterCountDay[2][i]+enterCountDay[3][i]+enterCountDay[4][i];
//                    }
////                    for (int i=0;i<30;i++)
////                    {
////                        sEnterCountMonth[i]=enterCountMonth[0][i]+enterCountMonth[1][i]+enterCountMonth[2][i]+enterCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sEnterCountYear[i]=enterCountYear[0][i]+enterCountYear[1][i]+enterCountYear[2][i]+enterCountYear[3][i];
////                    }
//                    for (int i=0;i<7;i++)
//                    {
//                        sEnterCountWeek[i]=enterCountWeek[0][i]+enterCountWeek[1][i]+enterCountWeek[2][i]+enterCountWeek[3][i]+enterCountWeek[4][i];
//                    }
//                    dayMap.put("outCount",sOutCountDay);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",sEnterCountDay);
//                    weekMap.put("outCount",sOutCountWeek);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",sEnterCountWeek);
//
//                }
//            }
//            else
//            {
//                int []sOutCountDay=new int [24];
////                    int []sOutCountMonth=new int [30];
////                    int []sOutCountYear=new int [12];
//                int []sOutCountWeek=new int [7];
//                for (int i=0;i<24;i++)
//                {
//                    sOutCountDay[i]=outCountDay[0][i]+outCountDay[1][i]+outCountDay[2][i]+outCountDay[3][i]+outCountDay[4][i];
//                }
////                    for (int i=0;i<30;i++)
////                    {
////                        sOutCountMonth[i]=outCountMonth[0][i]+outCountMonth[1][i]+outCountMonth[2][i]+outCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sOutCountYear[i]=outCountYear[0][i]+outCountYear[1][i]+outCountYear[2][i]+outCountYear[3][i];
////                    }
//                for (int i=0;i<7;i++)
//                {
//                    sOutCountWeek[i]=outCountWeek[0][i]+outCountWeek[1][i]+outCountWeek[2][i]+outCountWeek[3][i]+outCountWeek[4][i];
//                }
//                int []sEnterCountDay=new int [24];
////                    int []sEnterCountMonth=new int [30];
////                    int []sEnterCountYear=new int [12];
//                int []sEnterCountWeek=new int [7];
//                for (int i=0;i<24;i++)
//                {
//                    sEnterCountDay[i]=enterCountDay[0][i]+enterCountDay[1][i]+enterCountDay[2][i]+enterCountDay[3][i]+enterCountDay[4][i];
//                }
////                    for (int i=0;i<30;i++)
////                    {
////                        sEnterCountMonth[i]=enterCountMonth[0][i]+enterCountMonth[1][i]+enterCountMonth[2][i]+enterCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sEnterCountYear[i]=enterCountYear[0][i]+enterCountYear[1][i]+enterCountYear[2][i]+enterCountYear[3][i];
////                    }
//                for (int i=0;i<7;i++)
//                {
//                    sEnterCountWeek[i]=enterCountWeek[0][i]+enterCountWeek[1][i]+enterCountWeek[2][i]+enterCountWeek[3][i]+enterCountWeek[4][i];
//                }
//                dayMap.put("outCount",sOutCountDay);
//                dayMap.put("dateList",dateListDay);
//                dayMap.put("enterCount",sEnterCountDay);
//                weekMap.put("outCount",sOutCountWeek);
//                weekMap.put("dateList",dateListWeek);
//                weekMap.put("enterCount",sEnterCountWeek);
//
//            }
//            map.put("day",dayMap);
//            map.put("week",weekMap);
//            return render(map);
            Map map=new LinkedHashMap();
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            int []dateListDay={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
                    18,19,20,21,22,23};
            String []dateListWeek=new String[7];

            ArrayList<String> weekDays=DateUtil.getDays(7);
            Iterator<String> wdi=weekDays.iterator();
            int j=0;
            while (wdi.hasNext())
            {
                String wdt=wdi.next();
                //System.out.println(wdt);
                String wds=DateUtil.getWeek(wdt);
                dateListWeek[j]=wds;
                j++;
            }

            int []enterCountDay=new int[24];
            int []outCountDay=new int[24];

            int []enterCountWeek=new int[7];
            int []outCountWeek=new int[7];

            String todayYMDH=DateUtil.getTodayStartTime();
            String []ts=todayYMDH.split(" ");
            String todayYMD=ts[0];
            String beginTime;
            String endTime;
            Map tMap;
            List inL;
            List outL;
            for (int i=0;i<24;i++)
            {
                beginTime=todayYMD+" "+dateListDay[i]+":00:00";
                endTime=todayYMD+" "+dateListDay[i]+":59:59";
                enterCountDay[i]=0;
                outCountDay[i]=0;
                inL=carStatisticsService.queryCarPassNum(params,"8",beginTime,endTime);
                outL=carStatisticsService.queryCarPassNum(params,"9",beginTime,endTime);
                if(inL!=null&&!inL.isEmpty())
                {
                    tMap=(Map)inL.get(0);
                    enterCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
                }
                if(outL!=null&&!outL.isEmpty())
                {
                    tMap=(Map)outL.get(0);
                    outCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
                }
            }

            wdi=weekDays.iterator();
            j=0;
            while (wdi.hasNext())
            {
                String wdt=wdi.next();
                beginTime=wdt+" 00:00:00";
                endTime=wdt+" 23:59:59";
                enterCountWeek[j]=0;
                outCountWeek[j]=0;
                inL=carStatisticsService.queryCarPassNum(params,"8",beginTime,endTime);
                outL=carStatisticsService.queryCarPassNum(params,"9",beginTime,endTime);
                if(inL!=null&&!inL.isEmpty())
                {
                    tMap=(Map)inL.get(0);
                    enterCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
                }
                if(outL!=null&&!outL.isEmpty())
                {
                    tMap=(Map)outL.get(0);
                    outCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
                }
                j++;
            }
            Map dayMap=new LinkedHashMap();
            Map weekMap=new LinkedHashMap();
            dayMap.put("outCount",outCountDay);
            dayMap.put("dateList",dateListDay);
            dayMap.put("enterCount",enterCountDay);
            weekMap.put("outCount",outCountWeek);
            weekMap.put("dateList",dateListWeek);
            weekMap.put("enterCount",enterCountWeek);
            map.put("day",dayMap);
            map.put("week",weekMap);
            return render(map);

        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping("/car/stream3")
    public Map<String,Object> queryCarStream3(@RequestParam Map<String, Object> params){
        try {
//            Map map=new LinkedHashMap();
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//
//            int [][]outCountDay={
//                    {0,0,0,0,0,0,13,11,20,0,0,0,5,13,2,1,23,11,12,2,3,1,0,0},
//                    {0,0,0,0,0,1,9,8,17,0,0,0,3,5,3,7,5,2,3,4,5,0,0,0},
//                    {0,0,0,0,0,0,6,9,13,0,0,2,5,13,2,1,23,11,12,2,0,0,0,0},
//                    {0,0,0,0,0,0,11,10,22,0,0,0,5,4,2,1,5,2,3,3,1,1,0,0},
//                    {0,0,0,0,0,0,9,13,24,0,0,0,6,7,2,1,6,1,3,2,2,2,0,0}};
//            int [][]enterCountDay={
//                    {0,0,0,0,0,0,1,1,2,1,4,15,12,2,1,0,0,1,3,14,11,6,3,2},
//                    {0,0,0,0,0,0,3,0,1,2,7,11,14,1,5,0,1,2,4,12,9,2,1,0},
//                    {0,0,0,0,0,1,1,2,4,5,2,6,3,3,0,0,0,2,5,13,5,7,2,1},
//                    {0,0,0,0,0,0,2,3,3,6,4,15,12,2,0,0,0,2,8,9,11,6,0,0},
//                    {0,0,0,0,0,0,2,6,7,6,1,12,4,7,0,0,0,8,4,5,11,7,0,0}};
//            int []dateListDay={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
//                    18,19,20,21,22,23};
//            int [][]outCountWeek={
//                    {86,103,102,85,81,90,47},
//                    {90,102,109,53,81,110,105},
//                    {58,85,69,67,114,109,75},
//                    {45,90,45,66,65,115,77},
//                    {78,81,67,130,171,62,187}};
//            int [][]enterCountWeek={
//                    {100,94,36,70,49,79,28},
//                    {56,84,96,62,53,44,38},
//                    {75,36,84,39,91,68,48},
//                    {94,29,81,62,36,36,35},
//                    {46,190,112,85,70,140,75}};
//            String []dateListWeek=new String[7];
//
//            ArrayList<String> weekDays=DateUtil.getDays(7);
//            Iterator<String> wdi=weekDays.iterator();
//            int j=0;
//            while (wdi.hasNext())
//            {
//                String wdt=wdi.next();
//                String wds=DateUtil.getWeek(wdt);
//                dateListWeek[j]=wds;
//                j++;
//                //System.out.println(wds);
//            }
//
//            Map dayMap=new LinkedHashMap();
//            Map weekMap=new LinkedHashMap();
//            if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
//            {
//                String xqCode=params.get("xqCode").toString();
//                if(xqCode.equals("11"))
//                {
//                    dayMap.put("outCount",outCountDay[0]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[0]);
//                    weekMap.put("outCount",outCountWeek[0]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[0]);
//                }
//                else if(xqCode.equals("12"))
//                {
//                    dayMap.put("outCount",outCountDay[1]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[1]);
//                    weekMap.put("outCount",outCountWeek[1]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[1]);
//                }
//                else if(xqCode.equals("13"))
//                {
//                    dayMap.put("outCount",outCountDay[2]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[2]);
//                    weekMap.put("outCount",outCountWeek[2]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[2]);
//                }
//                else if(xqCode.equals("14"))
//                {
//                    dayMap.put("outCount",outCountDay[3]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[3]);
//                    weekMap.put("outCount",outCountWeek[3]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[3]);
//                }
//                else if(xqCode.equals("02"))
//                {
//                    dayMap.put("outCount",outCountDay[4]);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",enterCountDay[4]);
//                    weekMap.put("outCount",outCountWeek[4]);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",enterCountWeek[4]);
//                }
//            }
//            else if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
//            {
//                String subdistrictId=params.get("subdistrictId").toString();
//                if(subdistrictId.equals("2"))
//                {
//                    int []sOutCountDay=new int [24];
////                    int []sOutCountMonth=new int [30];
////                    int []sOutCountYear=new int [12];
//                    int []sOutCountWeek=new int [7];
//                    for (int i=0;i<24;i++)
//                    {
//                        sOutCountDay[i]=outCountDay[0][i]+outCountDay[1][i]+outCountDay[2][i]+outCountDay[3][i]+outCountDay[4][i];
//                    }
////                    for (int i=0;i<30;i++)
////                    {
////                        sOutCountMonth[i]=outCountMonth[0][i]+outCountMonth[1][i]+outCountMonth[2][i]+outCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sOutCountYear[i]=outCountYear[0][i]+outCountYear[1][i]+outCountYear[2][i]+outCountYear[3][i];
////                    }
//                    for (int i=0;i<7;i++)
//                    {
//                        sOutCountWeek[i]=outCountWeek[0][i]+outCountWeek[1][i]+outCountWeek[2][i]+outCountWeek[3][i]+outCountWeek[4][i];
//                    }
//                    int []sEnterCountDay=new int [24];
////                    int []sEnterCountMonth=new int [30];
////                    int []sEnterCountYear=new int [12];
//                    int []sEnterCountWeek=new int [7];
//                    for (int i=0;i<24;i++)
//                    {
//                        sEnterCountDay[i]=enterCountDay[0][i]+enterCountDay[1][i]+enterCountDay[2][i]+enterCountDay[3][i]+enterCountDay[4][i];
//                    }
////                    for (int i=0;i<30;i++)
////                    {
////                        sEnterCountMonth[i]=enterCountMonth[0][i]+enterCountMonth[1][i]+enterCountMonth[2][i]+enterCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sEnterCountYear[i]=enterCountYear[0][i]+enterCountYear[1][i]+enterCountYear[2][i]+enterCountYear[3][i];
////                    }
//                    for (int i=0;i<7;i++)
//                    {
//                        sEnterCountWeek[i]=enterCountWeek[0][i]+enterCountWeek[1][i]+enterCountWeek[2][i]+enterCountWeek[3][i]+enterCountWeek[4][i];
//                    }
//                    dayMap.put("outCount",sOutCountDay);
//                    dayMap.put("dateList",dateListDay);
//                    dayMap.put("enterCount",sEnterCountDay);
//                    weekMap.put("outCount",sOutCountWeek);
//                    weekMap.put("dateList",dateListWeek);
//                    weekMap.put("enterCount",sEnterCountWeek);
//
//                }
//            }
//            else
//            {
//                int []sOutCountDay=new int [24];
////                    int []sOutCountMonth=new int [30];
////                    int []sOutCountYear=new int [12];
//                int []sOutCountWeek=new int [7];
//                for (int i=0;i<24;i++)
//                {
//                    sOutCountDay[i]=outCountDay[0][i]+outCountDay[1][i]+outCountDay[2][i]+outCountDay[3][i]+outCountDay[4][i];
//                }
////                    for (int i=0;i<30;i++)
////                    {
////                        sOutCountMonth[i]=outCountMonth[0][i]+outCountMonth[1][i]+outCountMonth[2][i]+outCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sOutCountYear[i]=outCountYear[0][i]+outCountYear[1][i]+outCountYear[2][i]+outCountYear[3][i];
////                    }
//                for (int i=0;i<7;i++)
//                {
//                    sOutCountWeek[i]=outCountWeek[0][i]+outCountWeek[1][i]+outCountWeek[2][i]+outCountWeek[3][i]+outCountWeek[4][i];
//                }
//                int []sEnterCountDay=new int [24];
////                    int []sEnterCountMonth=new int [30];
////                    int []sEnterCountYear=new int [12];
//                int []sEnterCountWeek=new int [7];
//                for (int i=0;i<24;i++)
//                {
//                    sEnterCountDay[i]=enterCountDay[0][i]+enterCountDay[1][i]+enterCountDay[2][i]+enterCountDay[3][i]+enterCountDay[4][i];
//                }
////                    for (int i=0;i<30;i++)
////                    {
////                        sEnterCountMonth[i]=enterCountMonth[0][i]+enterCountMonth[1][i]+enterCountMonth[2][i]+enterCountMonth[3][i];
////                    }
////                    for (int i=0;i<12;i++)
////                    {
////                        sEnterCountYear[i]=enterCountYear[0][i]+enterCountYear[1][i]+enterCountYear[2][i]+enterCountYear[3][i];
////                    }
//                for (int i=0;i<7;i++)
//                {
//                    sEnterCountWeek[i]=enterCountWeek[0][i]+enterCountWeek[1][i]+enterCountWeek[2][i]+enterCountWeek[3][i]+enterCountWeek[4][i];
//                }
//                dayMap.put("outCount",sOutCountDay);
//                dayMap.put("dateList",dateListDay);
//                dayMap.put("enterCount",sEnterCountDay);
//                weekMap.put("outCount",sOutCountWeek);
//                weekMap.put("dateList",dateListWeek);
//                weekMap.put("enterCount",sEnterCountWeek);
//
//            }
//            map.put("day",dayMap);
//            map.put("week",weekMap);
//            return render(map);
            Map map=new LinkedHashMap();
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            int []dateListDay={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,
                    18,19,20,21,22,23};
            String []dateListWeek=new String[7];

            ArrayList<String> weekDays=DateUtil.getDays(7);
            Iterator<String> wdi=weekDays.iterator();
            int j=0;
            while (wdi.hasNext())
            {
                String wdt=wdi.next();
                //System.out.println(wdt);
                String wds=DateUtil.getWeek(wdt);
                dateListWeek[j]=wds;
                j++;
            }

            int []enterCountDay=new int[24];
            int []outCountDay=new int[24];

            int []enterCountWeek=new int[7];
            int []outCountWeek=new int[7];

            String todayYMDH=DateUtil.getTodayStartTime();
            String []ts=todayYMDH.split(" ");
            String todayYMD=ts[0];
            String beginTime;
            String endTime;
            Map tMap;
            List inL;
            List outL;
            for (int i=0;i<24;i++)
            {
                beginTime=todayYMD+" "+dateListDay[i]+":00:00";
                endTime=todayYMD+" "+dateListDay[i]+":59:59";
                enterCountDay[i]=0;
                outCountDay[i]=0;
                inL=carStatisticsService.queryCarPassNum(params,"8",beginTime,endTime);
                outL=carStatisticsService.queryCarPassNum(params,"9",beginTime,endTime);
                if(inL!=null&&!inL.isEmpty())
                {
                    tMap=(Map)inL.get(0);
                    enterCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
                }
                if(outL!=null&&!outL.isEmpty())
                {
                    tMap=(Map)outL.get(0);
                    outCountDay[i]=Integer.parseInt(tMap.get("passNum").toString());
                }
            }

            wdi=weekDays.iterator();
            j=0;
            while (wdi.hasNext())
            {
                String wdt=wdi.next();
                beginTime=wdt+" 00:00:00";
                endTime=wdt+" 23:59:59";
                enterCountWeek[j]=0;
                outCountWeek[j]=0;
                inL=carStatisticsService.queryCarPassNum(params,"8",beginTime,endTime);
                outL=carStatisticsService.queryCarPassNum(params,"9",beginTime,endTime);
                if(inL!=null&&!inL.isEmpty())
                {
                    tMap=(Map)inL.get(0);
                    enterCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
                }
                if(outL!=null&&!outL.isEmpty())
                {
                    tMap=(Map)outL.get(0);
                    outCountWeek[j]=Integer.parseInt(tMap.get("passNum").toString());
                }
                j++;
            }
            Map dayMap=new LinkedHashMap();
            Map weekMap=new LinkedHashMap();
            dayMap.put("outCount",outCountDay);
            dayMap.put("dateList",dateListDay);
            dayMap.put("enterCount",enterCountDay);
            weekMap.put("outCount",outCountWeek);
            weekMap.put("dateList",dateListWeek);
            weekMap.put("enterCount",enterCountWeek);
            map.put("day",dayMap);
            map.put("week",weekMap);
            return render(map);

        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 车辆分布信息（控制器完成） dee
     * @param params
     * @return
     */
    @GetMapping("/car/num")
    public Map<String,Object> queryCarNum(@RequestParam Map<String, Object> params){
        try {
            List list=new LinkedList();
            NumberFormat numberFormat = NumberFormat.getInstance();
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            int []carNums={450,45,129,1289,898};
            Map<String, Object> im=new HashMap<>();
            im.put("xqCode","02");
            List l1=carStatisticsService.queryCarBaseInfoNew(im,-1,null,null);
            if(l1!=null&&!l1.isEmpty())
            {
                Map tMap=(Map)l1.get(0);
                carNums[4]=Integer.parseInt(tMap.get("carNum").toString());
            }
            int total=0;
            int n=carNums.length;
            for(int i=0;i<n;i++)
                total+=carNums[i];
            double dt=(double)total;
            if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
            {
                String xqCode=params.get("xqCode").toString();
                Map perMap=new LinkedHashMap();
                if(xqCode.equals("11"))
                {
                    perMap.put("xqCode",xqCode);
                    perMap.put("xqName","香汐海");
                    perMap.put("carNum",carNums[0]);
                    perMap.put("percent",100);
                }
                else if(xqCode.equals("12"))
                {
                    perMap.put("xqCode",xqCode);
                    perMap.put("xqName","海棠墅");
                    perMap.put("carNum",carNums[1]);
                    perMap.put("percent",100);
                }
                else if(xqCode.equals("13"))
                {
                    perMap.put("xqCode",xqCode);
                    perMap.put("xqName","官厅园");
                    perMap.put("carNum",carNums[2]);
                    perMap.put("percent",100);
                }
                else if(xqCode.equals("14"))
                {
                    perMap.put("xqCode",xqCode);
                    perMap.put("xqName","南岛小镇");
                    perMap.put("carNum",carNums[3]);
                    perMap.put("percent",100);
                }
                else if(xqCode.equals("02"))
                {
                    perMap.put("xqCode",xqCode);
                    perMap.put("xqName","凤凰城");
                    perMap.put("carNum",carNums[4]);
                    perMap.put("percent",100);
                }
                list.add(perMap);
            }
            else if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
            {
                String subdistrictId=params.get("subdistrictId").toString();
                if(subdistrictId.equals("1"))
                {
                    for(int i=0;i<n;i++)
                    {
                        Map perMap=new LinkedHashMap();
                        if(i==0){
                            perMap.put("xqCode","11");
                            perMap.put("xqName","香汐海");
                        }
                        else if(i==1){
                            perMap.put("xqCode","12");
                            perMap.put("xqName","海棠墅");
                        }
                        else if(i==2){
                            perMap.put("xqCode","13");
                            perMap.put("xqName","官厅园");
                        }
                        else if(i==3){
                            perMap.put("xqCode","14");
                            perMap.put("xqName","南岛小镇");
                        }
                        else if(i==4){
                            perMap.put("xqCode","02");
                            perMap.put("xqName","凤凰城");
                        }
                        perMap.put("carNum",carNums[i]);
                        numberFormat.setMaximumFractionDigits(2);
                        double dcm=(double)carNums[i];
                        double percent = Double.parseDouble(numberFormat.format(dcm/dt*100));
                        perMap.put("percent",percent);
                        list.add(perMap);
                    }
                }
            }
            else
            {
                for(int i=0;i<n;i++)
                {
                    Map perMap=new LinkedHashMap();
                    if(i==0){
                        perMap.put("xqCode","11");
                        perMap.put("xqName","香汐海");
                    }
                    else if(i==1){
                        perMap.put("xqCode","12");
                        perMap.put("xqName","海棠墅");
                    }
                    else if(i==2){
                        perMap.put("xqCode","13");
                        perMap.put("xqName","官厅园");
                    }
                    else if(i==3){
                        perMap.put("xqCode","14");
                        perMap.put("xqName","南岛小镇");
                    }
                    else if(i==4){
                        perMap.put("xqCode","02");
                        perMap.put("xqName","凤凰城");
                    }
                    perMap.put("carNum",carNums[i]);
                    numberFormat.setMaximumFractionDigits(2);
                    double dcm=(double)carNums[i];
                    double percent = Double.parseDouble(numberFormat.format(dcm/dt*100));
                    perMap.put("percent",percent);
                    list.add(perMap);
                }
            }
            return render(list);
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            List list=carStatisticsService.queryCarDistributionNum(params);
//            NumberFormat numberFormat = NumberFormat.getInstance();
//            Iterator i=list.iterator();
//            double t=0;
//            List nList=new LinkedList();
//            while (i.hasNext())
//            {
//                Map map=(Map)i.next();
//                nList.add(map);
//                t+=Double.parseDouble(map.get("carNum").toString());
//            }
//            if(t==0)t=1;
//            i=nList.iterator();
//            while (i.hasNext())
//            {
//                Map map=(Map)i.next();
//                numberFormat.setMaximumFractionDigits(2);
//                double percent = Double.parseDouble(numberFormat.format((Double.parseDouble(map.get("carNum").toString())/t)*100));
//                map.put("percent",percent);
//            }
//            return render(nList);

        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 街道-高频进出车辆统计-车辆感知（TOP12） dee
     * @param params
     * @return
     */
    @GetMapping("/car/sense")
    public Map<String,Object> queryCarSense(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            //params.put("1",);
            String beginTime = null;
            String endTime = null;
            if(params.get("timeType")!=null&&!params.get("timeType").toString().trim().equals(""))
            {
                String tt=params.get("timeType").toString();
                if(tt.equals("day"))
                {
                    beginTime=DateUtil.getTodayStartTime();
                    endTime=DateUtil.getTodayEndTime();
                }
                else if(tt.equals("month"))
                {
                    beginTime=DateUtil.getMonthStartTime();
                    endTime=DateUtil.getMonthEndTime();
                }
            }

            Map resultMap=new LinkedHashMap();
            List list1=carStatisticsService.queryCarSenseTop12(params,1,beginTime,endTime);
            List list3=carStatisticsService.queryCarSenseTop12(params,3,beginTime,endTime);
            List list4=carStatisticsService.queryCarSenseTop12(params,-1,beginTime,endTime);
            Iterator i=list1.iterator();
            while (i.hasNext())
            {
                Map tMap=(Map)i.next();
                tMap.putIfAbsent("image","");
                tMap.putIfAbsent("lastCaptureImage","");
            }
            i=list3.iterator();
            while (i.hasNext())
            {
                Map tMap=(Map)i.next();
                tMap.putIfAbsent("image","");
                tMap.putIfAbsent("lastCaptureImage","");
            }
            i=list4.iterator();
            while (i.hasNext())
            {
                Map tMap=(Map)i.next();
                tMap.putIfAbsent("image","");
                tMap.putIfAbsent("lastCaptureImage","");
                tMap.put("ownerName","陌生人");
            }
            resultMap.put("ownerCar",list1);
            resultMap.put("tenementCar",list3);
            resultMap.put("strangCar",list4);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     *街道-特殊车辆管理（列表查询） dee
     * @param params
     * @return
     */
    @GetMapping("/car/special/list")
    public Map<String,Object> queryCarSpecialList(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            int page=1;
            int limit=10;
            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
                page=Integer.parseInt(params.get("page").toString());
            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
                limit=Integer.parseInt(params.get("limit").toString());
            Map resultMap=new HashMap();

            List list=carStatisticsService.queryCarSpecialList(params);
            int totalRows=list.size();

            int currentPage=page;
            int totalPage=totalRows/limit;
            if(totalRows%limit!=0)totalPage+=1;
            if(page>totalPage)currentPage=totalPage;
            if(page<1)currentPage=1;

            List nList=new LinkedList();
            Iterator li=list.iterator();
            int i=0;
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                tMap.putIfAbsent("image","");
                tMap.putIfAbsent("carGroupType","");
                tMap.putIfAbsent("carGroupTypeName","陌生人");
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    nList.add(tMap);
                }
                i++;
            }

            resultMap.put("list",nList);
            resultMap.put("totalRows",totalRows);
            resultMap.put("currentPage",currentPage);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-车辆列表 dee
     * @param params
     * @return
     */
//    @GetMapping("/car/list")
//    public Map<String,Object> queryCarList(@RequestParam Map<String, Object> params){
//        try {
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
////            List list=carStatisticsService.queryCarList(params);
//            List list=carStatisticsService.queryCarList2(params);
//            int page=1;
//            int limit=10;
//            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                page=Integer.parseInt(params.get("page").toString());
//            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                limit=Integer.parseInt(params.get("limit").toString());
//            Map resultMap=new HashMap();
//            int totalRows=list.size();
//
//            int currentPage=page;
//            int totalPage=totalRows/limit;
//            if(totalRows%limit!=0)totalPage+=1;
//            if(page>totalPage)currentPage=totalPage;
//            if(page<1)currentPage=1;
//
//            List nList=new LinkedList();
//            Iterator li=list.iterator();
//            int i=0;
//            while (li.hasNext())
//            {
//                Map tMap=(Map)li.next();
//                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                {
//                    if(tMap.get("image")!=null&&!tMap.get("image").toString().trim().equals(""))
//                    {
//                        String image=tMap.get("image").toString().trim();
//                        String ip= PropertiesUtil.getLocalTomcatImageIp();
//                        image=ip+image;
//                        tMap.put("image",image);
//                    }
//                    if(tMap.get("carNum")!=null&&!tMap.get("carNum").toString().trim().equals(""))
//                    {
//                        if(tMap.get("carNum").toString().length()==7)
//                        {
//                            tMap.put("carNumColorName","蓝色");
//                        }
//                        if(tMap.get("carNum").toString().length()==8)
//                        {
//                            tMap.put("carNumColorName","绿色");
//                        }
//                    }
//                    nList.add(tMap);
//                }
//                i++;
//            }
//
//            resultMap.put("list",nList);
//            resultMap.put("totalRows",totalRows);
//            resultMap.put("currentPage",currentPage);
//            return render(resultMap);
//
//        }catch (Exception e){
//            log.info(e.toString());
//            return failRender(e);
//        }
//    }

    @GetMapping("/car/list")
    public Map<String,Object> queryCarList3(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            Map resultMap=carStatisticsService.queryCarList3(params);
            return render(resultMap);

        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping("/car/baseInfo/new")
    public Map<String,Object> queryCarBaseInfoNew(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            Map resultMap=new HashMap();
            int carTotalNum=0;
            int permanentPersonCarNum=0;
            int flowPersonCarNum=0;
            int monthAddCarNum=0;
            //List l1=carStatisticsService.queryCarTotalNum(params);
            List l1=carStatisticsService.queryCarBaseInfoNew(params,-1,null,null);
            List l2=carStatisticsService.queryCarBaseInfoNew(params,0,null,null);
            //List l3=carStatisticsService.queryCarBaseInfoNew(params,3,null,null);
            String beginTime=DateUtil.getMonthStartTime();
            String endTime=DateUtil.getMonthEndTime();
            List l4=carStatisticsService.queryCarBaseInfoNew(params,-1,beginTime,endTime);
            //params.put("beginTime",beginTime);
            //params.put("endTime",endTime);
            //List l4=carStatisticsService.queryCarTotalNum(params);
            if(l1!=null&&!l1.isEmpty())
            {
                Map tMap=(Map)l1.get(0);
                carTotalNum=Integer.parseInt(tMap.get("carNum").toString());
            }
            if(l2!=null&&!l2.isEmpty())
            {
                Map tMap=(Map)l2.get(0);
                permanentPersonCarNum=Integer.parseInt(tMap.get("carNum").toString());
            }
//            if(l3!=null&&!l3.isEmpty())
//            {
//                Map tMap=(Map)l3.get(0);
//                flowPersonCarNum=Integer.parseInt(tMap.get("carNum").toString());
//            }
            flowPersonCarNum=carTotalNum-permanentPersonCarNum;
            if(l4!=null&&!l4.isEmpty())
            {
                Map tMap=(Map)l4.get(0);
                monthAddCarNum=Integer.parseInt(tMap.get("carNum").toString());
            }
//            flowPersonCarNum=carTotalNum-permanentPersonCarNum;
//            monthAddCarNum=537;
            resultMap.put("carTotalNum",carTotalNum);
            resultMap.put("permanentPersonCarNum",permanentPersonCarNum);
            resultMap.put("flowPersonCarNum",flowPersonCarNum);
            resultMap.put("monthAddCarNum",monthAddCarNum);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-高频出入车辆1 dee
     * @param params
     * @return
     */
//    @GetMapping("/car/highSense")
//    public Map<String,Object> queryCarHighSense(@RequestParam Map<String, Object> params){
//        try {
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            int page=1;
//            int limit=10;
//            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                page=Integer.parseInt(params.get("page").toString());
//            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                limit=Integer.parseInt(params.get("limit").toString());
//            Map resultMap=new LinkedHashMap();
//            String beginTime = null;
//            String endTime = null;
//            if(params.get("timeType")!=null&&!params.get("timeType").toString().trim().equals(""))
//            {
//                String tt=params.get("timeType").toString();
//                if(tt.equals("day"))
//                {
//                    beginTime=DateUtil.getTodayStartTime();
//                    endTime=DateUtil.getTodayEndTime();
//                }
//                else if(tt.equals("month"))
//                {
//                    beginTime=DateUtil.getMonthStartTime();
//                    endTime=DateUtil.getMonthEndTime();
//                }
//            }
//            List list=carStatisticsService.queryHighSense(params,beginTime,endTime);
//            int totalRows=list.size();
//
//            int currentPage=page;
//            int totalPage=totalRows/limit;
//            if(totalRows%limit!=0)totalPage+=1;
//            if(page>totalPage)currentPage=totalPage;
//            if(page<1)currentPage=1;
//
//            List nList=new LinkedList();
//            Iterator li=list.iterator();
//            int i=0;
//            while (li.hasNext())
//            {
//                Map tMap=(Map)li.next();
//                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                {
//                    if(tMap.get("lastCaptureImage")!=null&&!tMap.get("lastCaptureImage").toString().trim().equals(""))
//                    {
//                        String ip=PropertiesUtil.getLocalTomcatImageIp();
//                        tMap.put("lastCaptureImage",ip+tMap.get("lastCaptureImage").toString().trim());
//                    }
//                    if(tMap.get("image")!=null&&!tMap.get("image").toString().trim().equals(""))
//                    {
//                        String ip=PropertiesUtil.getLocalTomcatImageIp();
//                        tMap.put("image",ip+tMap.get("image").toString().trim());
//                    }
//                    nList.add(tMap);
//                }
//                i++;
//            }
//
//            resultMap.put("list",nList);
//            resultMap.put("totalRows",totalRows);
//            resultMap.put("currentPage",currentPage);
//            return render(resultMap);
//        }catch (Exception e){
//            log.info(e.toString());
//            return failRender(e);
//        }
//    }

    @GetMapping("/car/highSense")
    public Map<String,Object> queryCarHighSense(@RequestParam Map<String, Object> params){
        try {
            Map resultMap=carStatisticsService.queryHighSense2(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-高频出入车辆2 dee
     * @param params
     * @return
     */
//    @GetMapping("/car/sense/highAccess")
//    public Map<String,Object> queryCarSenseHighAccess(@RequestParam Map<String, Object> params){
//        try {
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            int page=1;
//            int limit=10;
//            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                page=Integer.parseInt(params.get("page").toString());
//            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                limit=Integer.parseInt(params.get("limit").toString());
//            Map resultMap=new LinkedHashMap();
//            String beginTime = null;
//            String endTime = null;
//            if(params.get("timeType")!=null&&!params.get("timeType").toString().trim().equals(""))
//            {
//                String tt=params.get("timeType").toString();
//                if(tt.equals("day"))
//                {
//                    beginTime=DateUtil.getTodayStartTime();
//                    endTime=DateUtil.getTodayEndTime();
//                    params.put("beginTime",beginTime);
//                    params.put("endTime",endTime);
//                }
//                else if(tt.equals("month"))
//                {
//                    beginTime=DateUtil.getMonthStartTime();
//                    endTime=DateUtil.getMonthEndTime();
//                    params.put("beginTime",beginTime);
//                    params.put("endTime",endTime);
//                }
//            }
//            List list=carStatisticsService.queryCarSenseHighAccess(params);
//            int totalRows=list.size();
//
//            int currentPage=page;
//            int totalPage=totalRows/limit;
//            if(totalRows%limit!=0)totalPage+=1;
//            if(page>totalPage)currentPage=totalPage;
//            if(page<1)currentPage=1;
//
//            List nList=new LinkedList();
//            Iterator li=list.iterator();
//            int i=0;
//            while (li.hasNext())
//            {
//                Map tMap=(Map)li.next();
//                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                {
//                    if(tMap.get("lastCaptureImage")!=null&&!tMap.get("lastCaptureImage").toString().trim().equals(""))
//                    {
//                        String ip=PropertiesUtil.getLocalTomcatImageIp();
//                        tMap.put("lastCaptureImage",ip+tMap.get("lastCaptureImage").toString().trim());
//                    }
//                    if(tMap.get("image")!=null&&!tMap.get("image").toString().trim().equals(""))
//                    {
//                        String ip=PropertiesUtil.getLocalTomcatImageIp();
//                        tMap.put("image",ip+tMap.get("image").toString().trim());
//                    }
//                    nList.add(tMap);
//                }
//                i++;
//            }
//
//            resultMap.put("list",nList);
//            resultMap.put("totalRows",totalRows);
//            resultMap.put("currentPage",currentPage);
//            return render(resultMap);
//        }catch (Exception e){
//            log.info(e.toString());
//            return failRender(e);
//        }
//    }

    @GetMapping("/car/sense/highAccess")
    public Map<String,Object> queryCarSenseHighAccess(@RequestParam Map<String, Object> params){
        try {
            Map resultMap=carStatisticsService.queryCarSenseHighAccess2(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * @description 车辆查抓拍记录查询控制器
     * @param params
     * @return  map
     * @author zhoutao
     * @date 2019/12/30 14:33
     */
    @GetMapping("/car/capture/list")
    public Map<String, Object> queryCarCaptureList(@RequestParam Map<String, Object> params){
        try{
            Map<String, Object> result = carStatisticsService.queryCarCaptureList(params);
            return render(result);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping("/car/sense/highAccess/info")
    public Map<String,Object> queryCarSenseHighAccessInfo(@RequestParam Map<String, Object> params){
        try {
            Map resultMap=carStatisticsService.queryCarSenseHighAccessInfoByCarNum(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping("/car/added/list")
    public Map<String,Object> queryCarAddedList(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            Map resultMap=carStatisticsService.queryCarCapDaysWithIn15Days(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

}
