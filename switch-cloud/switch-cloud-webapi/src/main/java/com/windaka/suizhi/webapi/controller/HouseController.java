package com.windaka.suizhi.webapi.controller;

import com.windaka.suizhi.common.controller.BaseController;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.service.HouseService;
import com.windaka.suizhi.webapi.service.PersonService;
import com.windaka.suizhi.webapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
/**
 * @Description 房屋信息
 * @Author dee（张大千）
 * @Date 2019/12/3  9:03
 */
public class HouseController extends BaseController {

    @Autowired
    private HouseService houseService;
    @Autowired
    private PersonService personService;

    /**
     * 房屋结构信息  dee
     * @param params
     * @return
     */
    @GetMapping("/house/room/property")
    public Map<String,Object> houseProperty(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            Map resultMap=new LinkedHashMap();
            //Map map=houseService.queryHouseRoomProperty(params,"3","2019-12-01","2019-12-06");
            List monthList=new ArrayList();
            List recordList=new ArrayList();
            String todayStartTime=DateUtil.getTodayStartTime();
            String []ymd=todayStartTime.split("-");
            int currY=Integer.parseInt(ymd[0]);
            int currM=Integer.parseInt(ymd[1]);
            int []t16={1823,1456,1767,1929,1823,1576};
            int []t26={213,353,234,317,124,375};
            int []t36=new int[6];
            for(int j=0;j<6;j++)
            {
                t36[j]=2400-t16[j]-t26[j];
            }
            for (int i=0;i<6;i++){
                if(currM<1)
                {
                    currM=12;
                    currY--;
                }
                monthList.add(currM);

                String beginTime=currY+"-"+currM+"-01";

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal=Calendar.getInstance();
                cal.clear();
                cal.set(Calendar.YEAR,currY);
                cal.set(Calendar.MONTH,currM-1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                String endTime=format.format(cal.getTime());

                int total=2400;
                Map totalMap=new LinkedHashMap();
//                int t1=0;
//                int t2_1=0;
//                int t2_2=0;
//                int t3=0;
//                Map m1=houseService.queryHouseRoomProperty(params,"1",beginTime,endTime);
//                Map m2=houseService.queryHouseRoomProperty(params,"2",beginTime,endTime);
//                Map m3=houseService.queryHouseRoomProperty(params,"3",beginTime,endTime);
//                Map m4=houseService.queryHouseRoomProperty(params,"4",beginTime,endTime);
//                if(m1!=null&&!m1.isEmpty())t1=Integer.parseInt(m1.get("total").toString());
//                if(m2!=null&&!m2.isEmpty())t2_1=Integer.parseInt(m2.get("total").toString());
//                if(m3!=null&&!m3.isEmpty())t2_2=Integer.parseInt(m3.get("total").toString());
//                if(m4!=null&&!m4.isEmpty())t3=Integer.parseInt(m4.get("total").toString());
                totalMap.put("selfOccupationNum",t16[i]);
                totalMap.put("rentNum",t26[i]);
                totalMap.put("vacancyNum",t36[i]);
                totalMap.put("totalNum",total);
//                totalMap.put("selfOccupationNum",t1);
//                totalMap.put("rentNum",t2_1+t2_2);
//                totalMap.put("vacancyNum",t3);
//                totalMap.put("totalNum",t1+t2_1+t2_2+t3);
                recordList.add(totalMap);
                currM--;
            }
            resultMap.put("monthList",monthList);
            resultMap.put("roomPropertyNum",recordList);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 房产居住统计(top10) dee
     * @param params
     * @return
     */
    @GetMapping("/house/statistics/live")
    public Map<String,Object> houseLive(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            List list=houseService.queryHouseLiveNumTop10(params);
            return render(list);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 房屋分布统计 dee
     * @param params
     * @return
     */
    @GetMapping("/house/statistics/room")
    public Map<String,Object> houseDistribute(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            //List list=houseService.queryHouseDistribution(params);
            Map xq1=new HashMap();
            Map xq2=new HashMap();
            Map xq3=new HashMap();
            Map xq4=new HashMap();
            Map xq5=new HashMap();
            xq1.put("xqName","凤凰城");xq1.put("xqCode","02");xq1.put("num",2400);
            xq2.put("xqName","香汐海");xq2.put("xqCode","11");xq2.put("num",530);
            xq3.put("xqName","海棠墅");xq3.put("xqCode","12");xq3.put("num",89);
            xq4.put("xqName","官厅园");xq4.put("xqCode","13");xq4.put("num",182);
            xq5.put("xqName","南岛小镇");xq5.put("xqCode","14");xq5.put("num",2652);
            List list=new LinkedList();
            list.add(xq1);
            list.add(xq2);
            list.add(xq3);
            list.add(xq4);
            list.add(xq5);
            NumberFormat numberFormat = NumberFormat.getInstance();
            Iterator i=list.iterator();
            double t=0;
            List nList=new LinkedList();
            while (i.hasNext())
            {
                Map map=(Map)i.next();
                if(map.get("xqName").toString().trim().equals("凤凰城"))
                {
//                    if(map.get("xqCode").toString().trim().equals("98"))
//                    {
//                        map.put("num",33);
//                        nList.add(map);
//                        t+=Double.parseDouble(map.get("num").toString());
//                    }
                    if(map.get("xqCode").toString().trim().equals("02"))
                    {
                        nList.add(map);
                        t+=Double.parseDouble(map.get("num").toString());
                    }
                }
                else
                {
                    nList.add(map);
                    t+=Double.parseDouble(map.get("num").toString());
                }
            }
            i=nList.iterator();
            while (i.hasNext())
            {
                Map map=(Map)i.next();
                numberFormat.setMaximumFractionDigits(2);
                double percent = Double.parseDouble(numberFormat.format((Double.parseDouble(map.get("num").toString())/t)*100));
                map.put("percent",percent);
            }
            return render(nList);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-房产列表查询 dee
     * @param params
     * @return
     */
//    @GetMapping("/house/room/list")
//    public Map<String,Object> houseList(@RequestParam Map<String, Object> params){
//        try {
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
////            List list=houseService.queryHouseList(params);
//            List list=houseService.queryHouseList2(params);
//            int totalRows=list.size();
//            int page=1;
//            int limit=10;
//            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                page=Integer.parseInt(params.get("page").toString());
//            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                limit=Integer.parseInt(params.get("limit").toString());
//            Map resultMap=new HashMap();
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
//                tMap.remove("isRented");
//                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                {
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

    @GetMapping("/house/room/list")
    public Map<String,Object> houseList3(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
                params.put("areaId",0);
            return render(houseService.queryHouseList3(params));
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-疑似闲置房屋列表 dee
     * @param params
     * @return
     */
//    @GetMapping("/house/room/unused/list")
//    public Map<String,Object> queryHouseRoomUnusedList(@RequestParam Map<String, Object> params){
//        try {
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            List list=houseService.queryHouseRoomUnusedList(params);
//            //List list=houseService.queryHouseRoomUnusedList2(params);
//            int totalRows=list.size();
//            int page=1;
//            int limit=10;
//            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                page=Integer.parseInt(params.get("page").toString());
//            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                limit=Integer.parseInt(params.get("limit").toString());
//            Map resultMap=new HashMap();
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
//                tMap.remove("isRented");
//                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                {
//                    int b=0;
//                    if(tMap.get("arrearageTime")!=null&&!tMap.get("arrearageTime").toString().trim().equals(""))
//                    {
//                        b=Integer.parseInt(tMap.get("arrearageTime").toString());
//                    }
//                    tMap.remove("id");
//                    tMap.remove("arrearageTime");
//                    tMap.put("unusedTime",b);
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
//
//    @GetMapping("/house/room/unused/list2")
//    public Map<String,Object> queryHouseRoomUnusedList2(@RequestParam Map<String, Object> params){
//        try {
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            List list=houseService.queryHouseRoomUnusedList2(params);
//            int totalRows=list.size();
//            int page=1;
//            int limit=10;
//            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                page=Integer.parseInt(params.get("page").toString());
//            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                limit=Integer.parseInt(params.get("limit").toString());
//            Map resultMap=new HashMap();
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
//            int b1,b2,b;
//            while (li.hasNext())
//            {
//                Map tMap=(Map)li.next();
//                tMap.remove("isRented");
//                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                {
//                    b1=Integer.parseInt(tMap.get("water_fee_arrearage_time").toString());
//                    b2=Integer.parseInt(tMap.get("property_fee_arrearage_time").toString());
//                    if(b1>b2)b=b1;
//                    else b=b2;
//                    tMap.remove("water_fee_arrearage_time");
//                    tMap.remove("property_fee_arrearage_time");
//                    tMap.put("unusedTime",b);
//                    nList.add(tMap);
//                    //List houseInfoL=
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

    @GetMapping("/house/room/unused/list")
    public Map<String,Object> queryHouseRoomUnusedList3(@RequestParam Map<String, Object> params){
        try {
            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
            {
                params.put("areaId",0);
            }
            Map resultMap=houseService.queryHouseRoomUnusedList3(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-欠费房屋列表 dee
     * @param params
     * @return
     */
//    @GetMapping("/house/room/arrearage/list")
//    public Map<String,Object> queryHouseRoomArrearageList(@RequestParam Map<String, Object> params){
//        try {
//            if(params.get("areaId")==null||params.get("areaId").toString().trim().equals(""))
//                params.put("areaId",0);
//            List list=houseService.queryHouseRoomArrearageList(params);
//            int totalRows=list.size();
//            int page=1;
//            int limit=10;
//            if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                page=Integer.parseInt(params.get("page").toString());
//            if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                limit=Integer.parseInt(params.get("limit").toString());
//            Map resultMap=new HashMap();
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
//            int b1,b2,b;
//            double c1,c2,c;
//            while (li.hasNext())
//            {
//                Map tMap=(Map)li.next();
//                tMap.remove("isRented");
//                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                {
//                    tMap.remove("id");
//                    tMap.remove("waterMeterNum");
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

    @GetMapping("/house/room/arrearage/list")
    public Map<String,Object> queryHouseRoomArrearageList2(@RequestParam Map<String, Object> params){
        try {
            Map resultMap=houseService.queryHouseRoomArrearageList2(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-房屋基础信息查询（单个查询） dee
     * @param roomId
     * @return
     */
    @GetMapping("/house/room/{roomId}/info")
    public Map<String, Object> queryHouseRoomInfoByRoomId(@PathVariable("roomId") String roomId) {
        try {
            List list = houseService.queryHouseRoomInfoByRoomId(roomId);
            Map resultMap=(Map)list.get(0);
            return render(resultMap);
        } catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 公安-房屋基础信息-人员信息列表 dee
     * @param roomId
     * @param params
     * @return
     */
    @GetMapping("/house/room/{roomId}/persons")
    public Map<String, Object> queryHouseRoomPersonsByRoomId(@PathVariable("roomId") String roomId,@RequestParam Map<String, Object> params) {
        try {
            List list = houseService.queryHouseRoomPersonsByRoomId(roomId);
            Map resultMap=new HashMap();
            if(list!=null&&!list.isEmpty())
            {
                int totalRows=list.size();
                int page=1;
                int limit=10;
                if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
                    page=Integer.parseInt(params.get("page").toString());
                if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
                    limit=Integer.parseInt(params.get("limit").toString());

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
                    if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                    {
                        if(tMap.get("image")!=null&&!tMap.get("image").toString().trim().equals(""))
                        {
                            tMap.put("image", PropertiesUtil.getLocalTomcatImageIp()+tMap.get("image").toString());
                        }
                        nList.add(tMap);
                    }
                    i++;
                }

                resultMap.put("list",nList);
                resultMap.put("totalRows",totalRows);
                resultMap.put("currentPage",currentPage);
            }
            else
            {
                resultMap.put("list",new LinkedList<>());
                resultMap.put("totalRows",0);
                resultMap.put("currentPage",1);
            }
            return render(resultMap);

        } catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

    @GetMapping("/house/room/{roomId}/cars")
    public Map<String, Object> queryHouseRoomCarsByRoomId(@PathVariable("roomId") String roomId,@RequestParam Map<String, Object> params){
        try {
            List list = houseService.queryHouseRoomCarsByRoomId(roomId);
            Map resultMap=new HashMap();
            if(list!=null&&!list.isEmpty())
            {
                int totalRows=list.size();
                int page=1;
                int limit=10;
                if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
                    page=Integer.parseInt(params.get("page").toString());
                if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
                    limit=Integer.parseInt(params.get("limit").toString());

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
                    if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                    {
                        if(tMap.get("carImg")!=null&&!tMap.get("carImg").toString().trim().equals(""))
                        {
                            tMap.put("carImg", PropertiesUtil.getLocalTomcatImageIp()+tMap.get("carImg").toString());
                        }
                        nList.add(tMap);
                    }
                    i++;
                }

                resultMap.put("list",nList);
                resultMap.put("totalRows",totalRows);
                resultMap.put("currentPage",currentPage);
            }
            else
            {
                resultMap.put("list",new LinkedList<>());
                resultMap.put("totalRows",0);
                resultMap.put("currentPage",1);
            }
            return render(resultMap);
        }catch (Exception e) {
            log.info(e.toString());
            return failRender(e);
        }
    }

//    @GetMapping("/house/room/{roomId}/payment")
//    public Map<String, Object> queryHouseRoomPaymentByRoomId(@PathVariable("roomId") String roomId,@RequestParam Map<String, Object> params){
//        try {
//            Map timeMap=DateUtil.getYearTRange();
//            String beginTime=timeMap.get("beginTime").toString();
//            String endTime=timeMap.get("endTime").toString();
//            //System.out.println(beginTime+" "+endTime);
//            List list = houseService.queryHouseRoomPaymentByRoomId(roomId,beginTime,endTime);
//            Map resultMap=new HashMap();
//            if(list!=null&&!list.isEmpty())
//            {
//                int totalRows=list.size();
//                int page=1;
//                int limit=10;
//                if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
//                    page=Integer.parseInt(params.get("page").toString());
//                if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
//                    limit=Integer.parseInt(params.get("limit").toString());
//
//                int currentPage=page;
//                int totalPage=totalRows/limit;
//                if(totalRows%limit!=0)totalPage+=1;
//                if(page>totalPage)currentPage=totalPage;
//                if(page<1)currentPage=1;
//
//                List nList=new LinkedList();
//                Iterator li=list.iterator();
//                int i=0;
//                while (li.hasNext())
//                {
//                    Map tMap=(Map)li.next();
//                    if(i>=(currentPage-1)*limit&&i<currentPage*limit)
//                    {
//                        if(tMap.get("record_date")!=null&&!tMap.get("record_date").toString().trim().equals(""))
//                        {
//                            String []ymds=tMap.get("record_date").toString().split("-");
//                            tMap.put("year",ymds[0]);
//                            tMap.put("month",ymds[1]);
//                        }
//                        else
//                        {
//                            tMap.put("year","");
//                            tMap.put("month","");
//                        }
//                        tMap.remove("record_date");
//                        nList.add(tMap);
//                    }
//                    i++;
//                }
//
//                resultMap.put("list",nList);
//                resultMap.put("totalRows",totalRows);
//                resultMap.put("currentPage",currentPage);
//            }
//            else
//            {
//                resultMap.put("list",new LinkedList<>());
//                resultMap.put("totalRows",0);
//                resultMap.put("currentPage",1);
//            }
//            return render(resultMap);
//        }catch (Exception e) {
//            log.info(e.toString());
//            return failRender(e);
//        }
//    }

    @GetMapping("/house/room/{roomId}/payment")
    public Map<String,Object> queryHouseRoomPaymentByRoomId2(@PathVariable("roomId") String roomId,@RequestParam Map<String, Object> params){
        try {
            Map resultMap=houseService.queryHouseRoomPaymentByRoomId2(roomId,params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 楼栋列表查询 dee
     * @param params
     * @return
     */
    @GetMapping("/house/building/list")
    public Map<String,Object> queryHouseBuildingList(@RequestParam Map<String, Object> params){
        try {
            Map resultMap=houseService.queryHouseBuildingList(params);
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }

    /**
     * 楼栋下每层每单元房屋的查询
     * @param params
     * @return
     */
    @GetMapping("/house/roomByBuilding/list")
    public Map<String,Object> queryHouseBuildingInnerList(@RequestParam Map<String, Object> params){
        try {
            Map resultMap=houseService.queryHouseBuildingInnerList(params);
            //System.out.println("houseRoomByBuildingList");
            return render(resultMap);
        }catch (Exception e){
            log.info(e.toString());
            return failRender(e);
        }
    }
}
