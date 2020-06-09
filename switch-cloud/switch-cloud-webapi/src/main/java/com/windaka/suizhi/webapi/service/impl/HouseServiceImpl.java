package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.webapi.dao.HouseDao;
import com.windaka.suizhi.webapi.dao.SingleTableDao;
import com.windaka.suizhi.webapi.service.HouseService;
import com.windaka.suizhi.webapi.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private SingleTableDao singleTableDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> queryHouseBuildingList(Map<String, Object> params) throws OssRenderException{
        List tList=null;
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("areaId");
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("xqCode");
        }
        Map resultMap=new HashMap();
        tList=houseDao.queryHouseBuildingList(params);
        if(tList!=null&&!tList.isEmpty()) {
            int totalRows = tList.size();
            int page = 1;
            int limit = totalRows;
            if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
                page = Integer.parseInt(params.get("page").toString());
            if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
                limit = Integer.parseInt(params.get("limit").toString());
            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;
            List nList = new LinkedList();
            Iterator li = tList.iterator();
            int i = 0;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {
                    nList.add(tMap);
                }
            }
            resultMap.put("list",nList);
            resultMap.put("totalRows",totalRows);
            resultMap.put("currentPage",currentPage);
            return resultMap;
        }
        else
        {
            resultMap.put("list",new LinkedList<>());
            resultMap.put("totalRows",0);
            resultMap.put("currentPage",1);
            return resultMap;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryHouseRoomProperty(Map<String, Object> params,String purpose,String beginTime,String endTime) throws OssRenderException{
        Map<String, Object> implParams=new HashMap<>();
        implParams.put("purpose",purpose);
        implParams.put("beginTime",beginTime);
        implParams.put("endTime",endTime);
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            implParams.put("xqCode",params.get("xqCode").toString());
            return houseDao.queryHouseRoomPropertyByXq(implParams);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            implParams.put("subdistrictId",params.get("subdistrictId").toString());
            return houseDao.queryHouseRoomPropertyBySub(implParams);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            implParams.put("areaId",params.get("areaId").toString());
            return houseDao.queryHouseRoomPropertyByArea(implParams);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseLiveNumTop10(Map<String, Object> params) throws OssRenderException
    {
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomLiveNumTop10ByXq(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomLiveNumTop10BySub(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomLiveNumTop10ByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseDistribution(Map<String, Object> params) throws OssRenderException
    {
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return houseDao.queryHouseDistributionByXq(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            return houseDao.queryHouseDistributionBySub(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return houseDao.queryHouseDistributionByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseList(Map<String, Object> params) throws OssRenderException
    {
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return houseDao.queryHouseListByXq(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return houseDao.queryHouseListByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseList2(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return houseDao.queryHouseList2(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return houseDao.queryHouseList2(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryHouseList3(Map<String,Object> params) throws OssRenderException{
        List tList=null;
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("areaId");
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("xqCode");
        }
        int pf=0;
        if(params.get("likeStr")!=null&&!params.get("likeStr").toString().trim().equals(""))
        {
            pf=1;
            tList=new LinkedList();
            Map innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr1",params.get("likeStr"));
            tList.addAll(houseDao.queryHouseListLikeStr(innerParams));
            innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr2",params.get("likeStr"));
            List tListP=houseDao.queryHouseListLikeStr(innerParams);
            if(tListP!=null&&!tListP.isEmpty())
            {
                int n1=tList.size();
                int n2=tListP.size();
                List ndList=new LinkedList();
                ndList.addAll(tList);
                for(int i=0;i<n2;i++)
                {
                    int f=0;
                    Map m1=(Map)tListP.get(i);
                    for(int j=0;j<n1;j++)
                    {
                        Map m2=(Map)tList.get(j);
                        if(m1.get("roomId").toString().equals(m2.get("roomId").toString()))
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==0)
                        ndList.add(m1);
                }
                tList=ndList;
            }
        }
        else
        {
            tList=singleTableDao.selectHouseRoom(params);
        }
        Map resultMap=new HashMap();
        if(tList!=null&&!tList.isEmpty())
        {
            int totalRows=tList.size();
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
            Iterator li=tList.iterator();
            int i=0;
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    Map nMap=new LinkedHashMap();
                    if(pf==1)
                    {
                        nMap.putAll(tMap);
                        Map innerParam=new HashMap();
                        innerParam.put("liveTypeR",1);
                        innerParam.put("roomId",tMap.get("roomId"));
                        List houseRoomOwnerL=singleTableDao.selectHouseOwnerRoom(innerParam);
                        String ownerId="无";
                        if(houseRoomOwnerL!=null&&!houseRoomOwnerL.isEmpty())
                        {
                            Map houseRoomOwnerMap=(Map)houseRoomOwnerL.get(0);
                            if(houseRoomOwnerMap.get("owner_id")!=null&&!houseRoomOwnerMap.get("owner_id").toString().trim().equals(""))
                            {
                                ownerId=houseRoomOwnerMap.get("owner_id").toString();
                            }
                        }
                        if(nMap.get("roomOwner")==null||nMap.get("roomOwner").toString().trim().equals(""))
                        {
                            innerParam=new HashMap();
                            innerParam.put("personId",ownerId);
                            List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                            String roomOwner="无";
                            if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                            {
                                Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                                if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                                {
                                    roomOwner=zsPersonInfoMap.get("name").toString();
                                }
                            }
                            nMap.put("roomOwner",roomOwner);
                        }
                    }
                    else
                    {
                        nMap.put("xqCode",tMap.get("xq_code"));
                        nMap.put("buildingArea",tMap.get("building_area"));
                        nMap.put("purposeName",tMap.get("purpose_name"));
                        nMap.put("funcName",tMap.get("func_name"));
                        nMap.put("roomId",tMap.get("manage_id"));
                        nMap.put("roomName",tMap.get("name"));
                        Map innerParam=new HashMap();
                        innerParam.put("xqCode",nMap.get("xqCode"));
                        List xqL=singleTableDao.selectHtXqInfo(innerParam);
                        String xqName="无";
                        if(xqL!=null&&!xqL.isEmpty())
                        {
                            Map xqInfoMap=(Map)xqL.get(0);
                            if(xqInfoMap.get("name")!=null&&!xqInfoMap.get("name").toString().trim().equals(""))
                            {
                                xqName=xqInfoMap.get("name").toString();
                            }
                        }
                        nMap.put("xqName",xqName);

                        innerParam=new HashMap();
                        innerParam.put("cellId",tMap.get("cell_id"));
                        List cellL=singleTableDao.selectHouseCell(innerParam);
                        String cellName="无",buildingId="无";
                        if(cellL!=null&&!cellL.isEmpty())
                        {
                            Map cellMap=(Map)cellL.get(0);
                            if(cellMap.get("name")!=null&&!cellMap.get("name").toString().trim().equals(""))
                            {
                                cellName=cellMap.get("name").toString();
                                buildingId=cellMap.get("building_id").toString();
                            }
                        }
                        nMap.put("cellName",cellName);

                        innerParam=new HashMap();
                        innerParam.put("buildingId",buildingId);
                        List buildingL=singleTableDao.selectHouseBuilding(innerParam);
                        String buildingName="无";
                        if(buildingL!=null&&!buildingL.isEmpty())
                        {
                            Map buildingMap=(Map)buildingL.get(0);
                            if(buildingMap.get("name")!=null&&!buildingMap.get("name").toString().trim().equals(""))
                            {
                                buildingName=buildingMap.get("name").toString();
                            }
                        }
                        nMap.put("buildingName",buildingName);

                        innerParam=new HashMap();
                        innerParam.put("liveTypeR",1);
                        innerParam.put("roomId",tMap.get("manage_id"));
                        List houseRoomOwnerL=singleTableDao.selectHouseOwnerRoom(innerParam);
                        String ownerId="无";
                        if(houseRoomOwnerL!=null&&!houseRoomOwnerL.isEmpty())
                        {
                            Map houseRoomOwnerMap=(Map)houseRoomOwnerL.get(0);
                            if(houseRoomOwnerMap.get("owner_id")!=null&&!houseRoomOwnerMap.get("owner_id").toString().trim().equals(""))
                            {
                                ownerId=houseRoomOwnerMap.get("owner_id").toString();
                            }
                        }

                        innerParam=new HashMap();
                        innerParam.put("personId",ownerId);
                        List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                        String roomOwner="无";
                        if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                        {
                            Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                            if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                            {
                                roomOwner=zsPersonInfoMap.get("name").toString();
                            }
                        }
                        nMap.put("roomOwner",roomOwner);

                        innerParam=new HashMap();
                        innerParam.put("roomId",tMap.get("manage_id"));
                        List liveNumL=singleTableDao.selectRoomLiveNum(innerParam);
                        int liveNum=0;
                        if(liveNumL!=null&&!liveNumL.isEmpty())
                        {
                            Map liveNumMap=(Map)liveNumL.get(0);
                            if(liveNumMap.get("live_num")!=null&&!liveNumMap.get("live_num").toString().trim().equals(""))
                            {
                                liveNum=Integer.parseInt(liveNumMap.get("live_num").toString());
                            }
                        }
                        nMap.put("liveNum",liveNum);

                        innerParam=new HashMap();
                        innerParam.put("roomId",tMap.get("manage_id"));
                        List bindingCarNumL=singleTableDao.selectBindingCarNum(innerParam);
                        int bindingCarNum=0;
                        if(bindingCarNumL!=null&&!bindingCarNumL.isEmpty())
                        {
                            Map bindingCarNumMap=(Map)bindingCarNumL.get(0);
                            if(bindingCarNumMap.get("binding_car_num")!=null&&!bindingCarNumMap.get("binding_car_num").toString().trim().equals(""))
                            {
                                bindingCarNum=Integer.parseInt(bindingCarNumMap.get("binding_car_num").toString());
                            }
                        }
                        nMap.put("bindingCarNum",bindingCarNum);
                    }

                    nList.add(nMap);
                }
                i++;
            }

            resultMap.put("list",nList);
            resultMap.put("totalRows",totalRows);
            resultMap.put("currentPage",currentPage);
            return resultMap;
        }
        else
        {
            resultMap.put("list",new LinkedList<>());
            resultMap.put("totalRows",0);
            resultMap.put("currentPage",1);
            return resultMap;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseRoomUnusedList(Map<String, Object> params) throws OssRenderException
    {
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomUnusedListByXq(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomUnusedListByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseRoomUnusedList2(Map<String, Object> params) throws OssRenderException
    {
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomUnusedListByXq2(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomUnusedListByArea2(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryHouseRoomUnusedList3(Map<String, Object> params) throws OssRenderException
    {
        List tList=null;
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("areaId");
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("xqCode");
        }
        int small=-1;
        if(params.get("small")!=null&&!params.get("small").toString().trim().equals(""))
        {
            small=Integer.parseInt(params.get("small").toString());
        }
        String today= DateUtil.getTodayStartTime();
        String []ymdhms=today.split(" ");
        String []ymds=ymdhms[0].split("-");
        int currentM=Integer.parseInt(ymds[1]);
        int currentY=Integer.parseInt(ymds[0]);
        //台式机改idea配置
        int endM=currentM;
        int endY=currentY;
        params.put("currentM",currentM+"");
        params.put("currentY",currentY+"");
        int startM=endM-12+1;
        int startY=currentY;
        if(startM<1){
            startM+=12;
            startY--;
        }
        System.out.println(startM);
        System.out.println(endM);
        int []yearList=new int[endY-startY+1];
        int n=yearList.length;
        for(int i=0;i<n;i++)
        {
            yearList[i]=startY+i;
            System.out.println(yearList[i]);
        }
        tList=singleTableDao.selectWaterUserAndRoomInfo(params);
        Map resultMap=new HashMap();
        List yList=singleTableDao.selectWaterUse(new HashMap<>());
        int si=0;
        int siTotal=yList.size();
        if(tList!=null&&!tList.isEmpty())
        {
            List nList=new LinkedList();
            Iterator li=tList.iterator();
            Map innerParams=new HashMap();
            innerParams.put("currentY",startY);
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                Map nMap=new LinkedHashMap();
                nMap.put("xqCode",tMap.get("xq_code"));
                nMap.put("cell_id",tMap.get("cell_id"));
                nMap.put("roomName",tMap.get("roomName"));
                nMap.put("buildingArea",tMap.get("buildingArea"));
                nMap.put("purposeName",tMap.get("purposeName"));
                nMap.put("funcName",tMap.get("funcName"));
                nMap.put("roomId",tMap.get("roomId"));
                int unusedTime=0;
                double []waterNums=new double[12];
                if(n==1)
                {
                    for(int j=0;j<12;j++)
                    {
                        if(tMap.get("m"+(j+1))!=null)
                            waterNums[j]=Double.parseDouble(tMap.get("m"+(j+1)).toString());
                        else
                            waterNums[j]=0;
                    }
                    for(int j=(currentM-1);j>=0;j--)
                    {
                        if(waterNums[j]<=2)
                        {
                            unusedTime++;
                        }
                        else
                            break;
                    }
                    String waterMeterNumR =new DecimalFormat("##0.00").format(waterNums[currentM-1]);
                    nMap.put("waterMeterNum",waterMeterNumR);
                    nMap.put("unusedTime",unusedTime);
                }
                else if(n==2)
                {
                    for(int j=0;j<=currentM-1;j++)
                    {
                        if(tMap.get("m"+(j+1))!=null)
                            waterNums[12-currentM+j]=Double.parseDouble(tMap.get("m"+(j+1)).toString());
                        else
                            waterNums[12-currentM+j]=0;
                    }
                    List roomYList=new LinkedList();
                    if(tMap.get("roomId")!=null&&!tMap.get("roomId").toString().trim().equals(""))
                    {
                        String tRoomId=tMap.get("roomId").toString();
                        while(si<siTotal)
                        {
                            Map yMap=(Map)yList.get(si);
                            if(yMap.get("room_id")!=null&&!yMap.get("room_id").toString().trim().equals(""))
                            {
                                String yRoomId=yMap.get("room_id").toString();
                                if(yRoomId.compareTo(tRoomId)<0)
                                {
                                    si++;
                                }
                                else if(yRoomId.equals(tRoomId))
                                {
                                    roomYList.add(yMap);
                                    si++;
                                }
                                else if(yRoomId.compareTo(tRoomId)>0)
                                    break;
                                System.out.println(yRoomId+","+tRoomId+":"+yRoomId.compareTo(tRoomId));
                            }
                            else
                                break;
                        }
                    }
                    List startYList=roomYList;
                    if(startYList!=null&&!startYList.isEmpty())
                    {
                        Map sylMap=(Map)startYList.get(0);
                        for(int j=startM-1;j<=11;j++)
                        {
                            if(sylMap.get("m"+(j+1))!=null)
                                waterNums[j-startM+1]=Double.parseDouble(sylMap.get("m"+(j+1)).toString());
                            else
                                waterNums[j-startM+1]=0;
                            //System.out.println(waterNums[j-startM+1]);
                        }
                        for(int j=11;j>=0;j--)
                        {
                            if(waterNums[j]<=2)
                            {
                                unusedTime++;
                            }
                            else
                                break;
                        }
                    }
                    String waterMeterNumR =new DecimalFormat("##0.00").format(waterNums[11]);
                    nMap.put("waterMeterNum",waterMeterNumR);
                    nMap.put("unusedTime",unusedTime);
                }
                if(unusedTime>=1)
                {
                    if(small!=-1&&unusedTime>=small)
                    {
                        nList.add(nMap);
                    }
                    else if(small==-1)
                    {
                        nList.add(nMap);
                    }
                }
            }
            nList.sort((Comparator<Map>) (o1, o2) -> {
                int unusedTime1=Integer.parseInt(o1.get("unusedTime").toString());
                int unusedTime2=Integer.parseInt(o2.get("unusedTime").toString());
                return unusedTime2 - unusedTime1;
            });
            int totalRows=nList.size();
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
            List fList=new LinkedList();
            li=nList.iterator();
            int i=0;
            while (li.hasNext())
            {
                Map nMap=(Map)li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    Map innerParam=new HashMap();
                    innerParam.put("xqCode",nMap.get("xqCode"));
                    List xqL=singleTableDao.selectHtXqInfo(innerParam);
                    String xqName="无";
                    if(xqL!=null&&!xqL.isEmpty())
                    {
                        Map xqInfoMap=(Map)xqL.get(0);
                        if(xqInfoMap.get("name")!=null&&!xqInfoMap.get("name").toString().trim().equals(""))
                        {
                            xqName=xqInfoMap.get("name").toString();
                        }
                    }
                    nMap.put("xqName",xqName);

                    innerParam=new HashMap();
                    innerParam.put("cellId",nMap.get("cell_id"));
                    List cellL=singleTableDao.selectHouseCell(innerParam);
                    String cellName="无",buildingId="无";
                    if(cellL!=null&&!cellL.isEmpty())
                    {
                        Map cellMap=(Map)cellL.get(0);
                        if(cellMap.get("name")!=null&&!cellMap.get("name").toString().trim().equals(""))
                        {
                            cellName=cellMap.get("name").toString();
                            buildingId=cellMap.get("building_id").toString();
                        }
                    }
                    nMap.put("cellName",cellName);

                    innerParam=new HashMap();
                    innerParam.put("buildingId",buildingId);
                    List buildingL=singleTableDao.selectHouseBuilding(innerParam);
                    String buildingName="无";
                    if(buildingL!=null&&!buildingL.isEmpty())
                    {
                        Map buildingMap=(Map)buildingL.get(0);
                        if(buildingMap.get("name")!=null&&!buildingMap.get("name").toString().trim().equals(""))
                        {
                            buildingName=buildingMap.get("name").toString();
                        }
                    }
                    nMap.put("buildingName",buildingName);

                    innerParam=new HashMap();
                    innerParam.put("liveTypeR",1);
                    innerParam.put("roomId",nMap.get("roomId"));
                    List houseRoomOwnerL=singleTableDao.selectHouseOwnerRoom(innerParam);
                    String ownerId="无";
                    if(houseRoomOwnerL!=null&&!houseRoomOwnerL.isEmpty())
                    {
                        Map houseRoomOwnerMap=(Map)houseRoomOwnerL.get(0);
                        if(houseRoomOwnerMap.get("owner_id")!=null&&!houseRoomOwnerMap.get("owner_id").toString().trim().equals(""))
                        {
                            ownerId=houseRoomOwnerMap.get("owner_id").toString();
                        }
                    }

                    innerParam=new HashMap();
                    innerParam.put("personId",ownerId);
                    List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                    String roomOwner="无";
                    String ownerPhone="无";
                    if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                    {
                        Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                        if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                        {
                            roomOwner=zsPersonInfoMap.get("name").toString();
                        }
                        if(zsPersonInfoMap.get("phone")!=null&&!zsPersonInfoMap.get("phone").toString().trim().equals(""))
                        {
                            ownerPhone=zsPersonInfoMap.get("phone").toString();
                        }
                    }
                    nMap.put("roomOwner",roomOwner);
                    nMap.put("ownerPhone",ownerPhone);
                    nMap.remove("cell_id");
                    fList.add(nMap);
                }
                i++;
            }

            resultMap.put("list",fList);
            resultMap.put("totalRows",totalRows);
            resultMap.put("currentPage",currentPage);
            return resultMap;
        }
        else
        {
            resultMap.put("list",new LinkedList<>());
            resultMap.put("totalRows",0);
            resultMap.put("currentPage",1);
            return resultMap;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseRoomArrearageList(Map<String, Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomArrearageListByXq(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return houseDao.queryHouseRoomArrearageListByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryHouseRoomArrearageList2(Map<String, Object> params) throws OssRenderException{
        List tList=null;
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("areaId");
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("xqCode");
        }
        int pf=0;
        if(params.get("likeStr")!=null&&!params.get("likeStr").toString().trim().equals(""))
        {
            pf=1;
            tList=new LinkedList();
            Map innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr1",params.get("likeStr"));
            tList.addAll(singleTableDao.selectArrearageRecordAndRoomInfoLikeStr(innerParams));
            innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr2",params.get("likeStr"));
            List tListP=singleTableDao.selectArrearageRecordAndRoomInfoLikeStr(innerParams);
            //List tListP=null;
            if(tListP!=null&&!tListP.isEmpty())
            {
                int n1=tList.size();
                int n2=tListP.size();
                List ndList=new LinkedList();
                ndList.addAll(tList);
                for(int i=0;i<n2;i++)
                {
                    int f=0;
                    Map m1=(Map)tListP.get(i);
                    for(int j=0;j<n1;j++)
                    {
                        Map m2=(Map)tList.get(j);
                        if(m1.get("roomId").toString().equals(m2.get("roomId").toString()))
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==0)
                        ndList.add(m1);
                }
                tList=ndList;
            }
        }
        else
        {
            tList=singleTableDao.selectArrearageRecordAndRoomInfo(params);
        }
        Map resultMap=new HashMap();
        String today= DateUtil.getTodayStartTime();
        String []ymdhms=today.split(" ");
        String []ymds=ymdhms[0].split("-");
        int currentM=Integer.parseInt(ymds[1]);
        int currentY=Integer.parseInt(ymds[0]);
        int endM=currentM;
        int endY=currentY;
        Map innerParamFront=new HashMap();

        innerParamFront.put("payType","物业费");
        List arrearageLastPaymentTimeWy=singleTableDao.selectArrearageLastPaymentTime(innerParamFront);
        int wyI=0;
        int wyTotal=arrearageLastPaymentTimeWy.size();

        innerParamFront.put("payType","水费");
        List arrearageLastPaymentTimeWater=singleTableDao.selectArrearageLastPaymentTime(innerParamFront);
        int waterI=0;
        int waterTotal=arrearageLastPaymentTimeWater.size();

        List yList=singleTableDao.selectWaterUse(new HashMap<>());
        int si=0;
        int siTotal=yList.size();

        if(tList!=null&&!tList.isEmpty())
        {
            List nList=new LinkedList();
            Iterator li=tList.iterator();

            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                Map nMap=new LinkedHashMap();
                nMap.putAll(tMap);
                Map innerParam;
                double arrearageNum=0;
                if(nMap.get("arrearageNum")!=null&&!nMap.get("arrearageNum").toString().trim().equals(""))
                {
                    arrearageNum=Double.parseDouble(nMap.get("arrearageNum").toString());
                }

                //String arrearageNumR =new DecimalFormat("##0.00").format(arrearageNum);
                //nMap.put("arrearageNum",arrearageNumR);
                int jumpMonth1=0;
                List roomWyList=new LinkedList();
                int jumpMonth2=0;
                List roomWaterList=new LinkedList();
                if(tMap.get("roomId")!=null&&!tMap.get("roomId").toString().trim().equals(""))
                {
                    String tRoomId=tMap.get("roomId").toString();
                    while(wyI<wyTotal)
                    {
                        Map wyMap=(Map)arrearageLastPaymentTimeWy.get(wyI);
                        if(wyMap.get("room_id")!=null&&!wyMap.get("room_id").toString().trim().equals(""))
                        {
                            String wyRoomId=wyMap.get("room_id").toString();
                            if(wyRoomId.compareTo(tRoomId)<0)
                            {
                                wyI++;
                            }
                            else if(wyRoomId.equals(tRoomId))
                            {
                                roomWyList.add(wyMap);
                                wyI++;
                            }
                            else if(wyRoomId.compareTo(tRoomId)>0)
                                break;
                        }
                    }
                    while(waterI<waterTotal)
                    {
                        Map waterMap=(Map)arrearageLastPaymentTimeWater.get(waterI);
                        if(waterMap.get("room_id")!=null&&!waterMap.get("room_id").toString().trim().equals(""))
                        {
                            String waterRoomId=waterMap.get("room_id").toString();
                            if(waterRoomId.compareTo(tRoomId)<0)
                            {
                                waterI++;
                            }
                            else if(waterRoomId.equals(tRoomId))
                            {
                                roomWaterList.add(waterMap);
                                waterI++;
                            }
                            else if(waterRoomId.compareTo(tRoomId)>0)
                                break;
                        }
                    }
                }
                if(roomWyList!=null&&!roomWyList.isEmpty())
                {
                    Map jumpMonth1Map=(Map)roomWyList.get(0);
                    jumpMonth1=(int)Double.parseDouble(jumpMonth1Map.get("jump_month").toString());
                }
                if(roomWaterList!=null&&!roomWaterList.isEmpty())
                {
                    Map jumpMonth2Map=(Map)roomWaterList.get(0);
                    jumpMonth2=(int)Double.parseDouble(jumpMonth2Map.get("jump_month").toString());
                }

                double diff=0;
                if(jumpMonth2!=0)
                {
                    int startM=endM-jumpMonth2+1;
                    int startY=currentY;
                    if(startM<1){
                        startM+=12;
                        startY--;
                    }
                    System.out.println(startM);
                    System.out.println(endM);
                    int []yearList=new int[endY-startY+1];
                    int n=yearList.length;

                    List roomYList=new LinkedList();
                    if(tMap.get("roomId")!=null&&!tMap.get("roomId").toString().trim().equals(""))
                    {
                        String tRoomId=tMap.get("roomId").toString();
                        while(si<siTotal)
                        {
                            Map yMap=(Map)yList.get(si);
                            if(yMap.get("room_id")!=null&&!yMap.get("room_id").toString().trim().equals(""))
                            {
                                String yRoomId=yMap.get("room_id").toString();
                                if(yRoomId.compareTo(tRoomId)<0)
                                {
                                    si++;
                                }
                                else if(yRoomId.equals(tRoomId))
                                {
                                    roomYList.add(yMap);
                                    si++;
                                }
                                else if(yRoomId.compareTo(tRoomId)>0)
                                    break;
                                System.out.println(yRoomId+","+tRoomId+":"+yRoomId.compareTo(tRoomId));
                            }
                            else
                                break;
                        }
                    }
                    if(roomYList!=null&&!roomYList.isEmpty())
                    {
                        int k=0;
                        Iterator ryI=roomYList.iterator();
                        int f=0;
                        while (ryI.hasNext())
                        {
                            Map ryMap=(Map)ryI.next();
                            int ryYear=Integer.parseInt(ryMap.get("year").toString());
                            if(ryYear==startY)f=1;
                            if(f==1)
                            {
                                int ks=0;
                                int ke=11;
                                if(k==0)ks=startM-1;
                                if(k==n-1)ke=endM-1;
                                if(k>=n)break;
                                for (int j=ks;j<=ke;j++)
                                {
                                    if(ryMap.get("m"+j)!=null&&!ryMap.get("m"+j).toString().trim().equals(""))
                                        diff+=Double.parseDouble(ryMap.get("m"+j).toString());
                                }
                                k++;
                            }
                        }
                    }
                }
                diff*=3.5;
                arrearageNum+=diff;
                String arrearageNumR =new DecimalFormat("##0.00").format(arrearageNum);
                nMap.put("arrearageNum",arrearageNumR);

                if(jumpMonth1==0)jumpMonth1++;
                if(jumpMonth2==0)jumpMonth2++;

                if(jumpMonth1>jumpMonth2)
                    nMap.put("arrearageTime",jumpMonth1);
                else
                    nMap.put("arrearageTime",jumpMonth2);
                nList.add(nMap);
            }
            nList.sort((Comparator<Map>) (o1, o2) -> {
                int arrearageTime1=Integer.parseInt(o1.get("arrearageTime").toString());
                int arrearageTime2=Integer.parseInt(o2.get("arrearageTime").toString());
                if(arrearageTime1!=arrearageTime2)
                {
                    return arrearageTime2-arrearageTime1;
                }
                else
                {
                    double arrearageNum1=Double.parseDouble(o1.get("arrearageNum").toString());
                    double arrearageNum2=Double.parseDouble(o2.get("arrearageNum").toString());
                    return (int)(arrearageNum2-arrearageNum1);
                }
            });
            int totalRows=nList.size();
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
            List fList=new LinkedList();
            li=nList.iterator();
            int i=0;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                Map nMap=new LinkedHashMap();
                Map innerParam=new HashMap();
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {
                    if(pf==0)
                    {
                        nMap.put("xqCode",tMap.get("xqCode"));
                        nMap.put("roomName",tMap.get("roomName"));
                        nMap.put("buildingArea",tMap.get("buildingArea"));
                        nMap.put("purposeName",tMap.get("purposeName"));
                        nMap.put("funcName",tMap.get("funcName"));
                        nMap.put("roomId",tMap.get("roomId"));
                        nMap.put("arrearageNum",tMap.get("arrearageNum"));
                        nMap.put("arrearageTime",tMap.get("arrearageTime"));
                        innerParam.put("xqCode",nMap.get("xqCode"));
                        List xqL=singleTableDao.selectHtXqInfo(innerParam);
                        String xqName="无";
                        if(xqL!=null&&!xqL.isEmpty())
                        {
                            Map xqInfoMap=(Map)xqL.get(0);
                            if(xqInfoMap.get("name")!=null&&!xqInfoMap.get("name").toString().trim().equals(""))
                            {
                                xqName=xqInfoMap.get("name").toString();
                            }
                        }
                        nMap.put("xqName",xqName);

                        innerParam=new HashMap();
                        innerParam.put("cellId",tMap.get("cell_id"));
                        List cellL=singleTableDao.selectHouseCell(innerParam);
                        String cellName="无",buildingId="无";
                        if(cellL!=null&&!cellL.isEmpty())
                        {
                            Map cellMap=(Map)cellL.get(0);
                            if(cellMap.get("name")!=null&&!cellMap.get("name").toString().trim().equals(""))
                            {
                                cellName=cellMap.get("name").toString();
                                buildingId=cellMap.get("building_id").toString();
                            }
                        }
                        nMap.put("cellName",cellName);

                        innerParam=new HashMap();
                        innerParam.put("buildingId",buildingId);
                        List buildingL=singleTableDao.selectHouseBuilding(innerParam);
                        String buildingName="无";
                        if(buildingL!=null&&!buildingL.isEmpty())
                        {
                            Map buildingMap=(Map)buildingL.get(0);
                            if(buildingMap.get("name")!=null&&!buildingMap.get("name").toString().trim().equals(""))
                            {
                                buildingName=buildingMap.get("name").toString();
                            }
                        }
                        nMap.put("buildingName",buildingName);

                        innerParam=new HashMap();
                        innerParam.put("liveTypeR",1);
                        innerParam.put("roomId",tMap.get("roomId"));
                        List houseRoomOwnerL=singleTableDao.selectHouseOwnerRoom(innerParam);
                        String ownerId="无";
                        if(houseRoomOwnerL!=null&&!houseRoomOwnerL.isEmpty())
                        {
                            Map houseRoomOwnerMap=(Map)houseRoomOwnerL.get(0);
                            if(houseRoomOwnerMap.get("owner_id")!=null&&!houseRoomOwnerMap.get("owner_id").toString().trim().equals(""))
                            {
                                ownerId=houseRoomOwnerMap.get("owner_id").toString();
                            }
                        }

                        innerParam=new HashMap();
                        innerParam.put("personId",ownerId);
                        List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                        String roomOwner="无";
                        String ownerPhone="无";
                        if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                        {
                            Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                            if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                            {
                                roomOwner=zsPersonInfoMap.get("name").toString();
                            }
                            if(zsPersonInfoMap.get("phone")!=null&&!zsPersonInfoMap.get("phone").toString().trim().equals(""))
                            {
                                ownerPhone=zsPersonInfoMap.get("phone").toString();
                            }
                        }
                        nMap.put("roomOwner",roomOwner);
                        nMap.put("ownerPhone",ownerPhone);
                    }
                    else
                    {
                        nMap.put("xqCode",tMap.get("xqCode"));
                        nMap.put("roomName",tMap.get("roomName"));
                        nMap.put("buildingArea",tMap.get("buildingArea"));
                        nMap.put("purposeName",tMap.get("purposeName"));
                        nMap.put("funcName",tMap.get("funcName"));
                        nMap.put("roomId",tMap.get("roomId"));
                        nMap.put("arrearageNum",tMap.get("arrearageNum"));
                        nMap.put("arrearageTime",tMap.get("arrearageTime"));
                        nMap.put("xqName",tMap.get("xqName"));
                        nMap.put("cellName",tMap.get("cellName"));
                        nMap.put("buildingName",tMap.get("buildingName"));
                        if(tMap.get("roomOwner")!=null)
                        {
                            nMap.put("roomOwner",tMap.get("roomOwner"));
                            nMap.put("ownerPhone",tMap.get("ownerPhone"));
                        }
                        else
                        {
                            innerParam=new HashMap();
                            innerParam.put("liveTypeR",1);
                            innerParam.put("roomId",tMap.get("roomId"));
                            List houseRoomOwnerL=singleTableDao.selectHouseOwnerRoom(innerParam);
                            String ownerId="无";
                            if(houseRoomOwnerL!=null&&!houseRoomOwnerL.isEmpty())
                            {
                                Map houseRoomOwnerMap=(Map)houseRoomOwnerL.get(0);
                                if(houseRoomOwnerMap.get("owner_id")!=null&&!houseRoomOwnerMap.get("owner_id").toString().trim().equals(""))
                                {
                                    ownerId=houseRoomOwnerMap.get("owner_id").toString();
                                }
                            }

                            innerParam=new HashMap();
                            innerParam.put("personId",ownerId);
                            List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                            String roomOwner="无";
                            String ownerPhone="无";
                            if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                            {
                                Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                                if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                                {
                                    roomOwner=zsPersonInfoMap.get("name").toString();
                                }
                                if(zsPersonInfoMap.get("phone")!=null&&!zsPersonInfoMap.get("phone").toString().trim().equals(""))
                                {
                                    ownerPhone=zsPersonInfoMap.get("phone").toString();
                                }
                            }
                            nMap.put("roomOwner",roomOwner);
                            nMap.put("ownerPhone",ownerPhone);
                        }
                    }
                    fList.add(nMap);
                }
                i++;
            }

            resultMap.put("list",fList);
            resultMap.put("totalRows",totalRows);
            resultMap.put("currentPage",currentPage);
            return resultMap;
        }
        else
        {
            resultMap.put("list",new LinkedList<>());
            resultMap.put("totalRows",0);
            resultMap.put("currentPage",1);
            return resultMap;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseRoomInfoByRoomId(String roomId) throws OssRenderException{
        if(roomId!=null&&!roomId.trim().equals(""))
        {
            return houseDao.queryHouseRoomInfoByRoomId(roomId);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryHouseRoomPaymentByRoomId2(String roomId,Map<String, Object> params) throws OssRenderException{
        Map resultMap=new HashMap();
        if(roomId!=null&&!roomId.trim().equals(""))
        {
            //return houseDao.queryHouseRoomInfoByRoomId(roomId);
            Map innerParam;
            innerParam=new HashMap();
            innerParam.put("roomId",roomId);
            List ifList=singleTableDao.selectHouseRoom(innerParam);
            if(ifList==null||ifList.isEmpty())
            {
                resultMap.put("list",new LinkedList<>());
                resultMap.put("totalRows",0);
                resultMap.put("currentPage",1);
                return resultMap;
            }
            Iterator commonI;

            List payList=new LinkedList();
            String todayStartTime=DateUtil.getTodayStartTime();
            String []ymd=todayStartTime.split("-");
            int currY=Integer.parseInt(ymd[0]);
            int currM=Integer.parseInt(ymd[1]);
            double lastWaterMoney=0;
            for (int i=0;i<12;i++){

                Map nMap=new HashMap();

                if(currM<1)
                {
                    currM=12;
                    currY--;
                }

                String beginTime=currY+"-"+currM+"-01";

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal=Calendar.getInstance();
                cal.clear();
                cal.set(Calendar.YEAR,currY);
                cal.set(Calendar.MONTH,currM-1);
                cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                String endTime=format.format(cal.getTime());

                innerParam.put("roomId",roomId);
                innerParam.put("sBeginTime",beginTime);
                innerParam.put("sEndTime",endTime);
                innerParam.put("payType","物业费");
                double wyPayment1=0;
                double wyPayment2=0;
                List sWyList=singleTableDao.selectPayRecord(innerParam);
                if(sWyList!=null&&!sWyList.isEmpty())
                {
                    commonI=sWyList.iterator();
                    while (commonI.hasNext())
                    {
                        Map vMap=(Map)commonI.next();
                        if(vMap.get("payment1")!=null&&!vMap.get("payment1").toString().equals(""))
                        {
                            wyPayment1+=Double.parseDouble(vMap.get("payment1").toString());
                        }
                        if(vMap.get("payment2")!=null&&!vMap.get("payment2").toString().equals(""))
                        {
                            wyPayment2+=Double.parseDouble(vMap.get("payment2").toString());
                        }
                    }
                }

//                innerParam=new HashMap();
//                innerParam.put("roomId",roomId);
//                innerParam.put("beginTime",beginTime);
//                innerParam.put("endTime",endTime);
//                innerParam.put("payType","物业费");
//                double wyPayment2=0;
//                List aWyList=singleTableDao.selectPayRecord(innerParam);
//                if(aWyList!=null&&!aWyList.isEmpty())
//                {
//                    commonI=aWyList.iterator();
//                    while (commonI.hasNext())
//                    {
//                        Map vMap=(Map)commonI.next();
//                        if(vMap.get("payment2")!=null&&!vMap.get("payment2").toString().equals(""))
//                        {
//                            wyPayment2+=Double.parseDouble(vMap.get("payment2").toString());
//                        }
//                    }
//                }
                String wyPayment1S=new DecimalFormat("##0.00").format(wyPayment1);
                nMap.put("propertyFee",wyPayment1S);
                if(Double.compare(wyPayment1,0)>0)
                {
                    if(Double.compare(wyPayment1,wyPayment2)<0)
                    {
                        String d=new DecimalFormat("##0.00").format(wyPayment2);
                        nMap.put("propertyFeeStatus","已缴费，实缴"+d+"元");
                    }
                    else if(Double.compare(wyPayment1,wyPayment2)==0)
                        nMap.put("propertyFeeStatus","已缴费");
                    else
                    {
                        if(Double.compare(wyPayment2,0)>0)
                        {
                            String d=new DecimalFormat("##0.00").format(wyPayment2);
                            nMap.put("propertyFeeStatus","已缴费，实缴"+d+"元");
                        }
                        else
                            nMap.put("propertyFeeStatus","未缴费");
                    }
                }
                else
                {
                    nMap.put("propertyFeeStatus","未缴费");
                }

//                innerParam=new HashMap();
//                innerParam.put("roomId",roomId);
//                innerParam.put("sBeginTime",beginTime);
//                innerParam.put("sEndTime",endTime);
//                innerParam.put("payType","水费");
//                double waterPayment1=0;
//                double waterPayment2=0;
//                List sWaterList=singleTableDao.selectPayRecord(innerParam);
//                if(sWaterList!=null&&!sWaterList.isEmpty())
//                {
//                    commonI=sWaterList.iterator();
//                    while (commonI.hasNext())
//                    {
//                        Map vMap=(Map)commonI.next();
//                        if(vMap.get("payment1")!=null&&!vMap.get("payment1").toString().equals(""))
//                        {
//                            waterPayment1+=Double.parseDouble(vMap.get("payment1").toString());
//                        }
//                        if(vMap.get("payment2")!=null&&!vMap.get("payment2").toString().equals(""))
//                        {
//                            waterPayment2+=Double.parseDouble(vMap.get("payment2").toString());
//                        }
//                    }
//                }

                innerParam=new HashMap();
                innerParam.put("roomId",roomId);
                innerParam.put("sBeginTime",beginTime);
                innerParam.put("sEndTime",endTime);
                innerParam.put("payType","水费");
                List aWaterList=singleTableDao.selectPayRecord(innerParam);
                if(aWaterList!=null&&!aWaterList.isEmpty())
                {
                    commonI=aWaterList.iterator();
                    while (commonI.hasNext())
                    {
                        Map vMap=(Map)commonI.next();
                        if(vMap.get("payment2")!=null&&!vMap.get("payment2").toString().equals(""))
                        {
                            lastWaterMoney+=Double.parseDouble(vMap.get("payment2").toString());
                        }
                    }
                }

//                waterPayment1=Double.parseDouble(new DecimalFormat("##0.00").format(waterPayment1));
//                nMap.put("waterFee",waterPayment1);
//                if(waterPayment1>0)
//                {
//                    if(waterPayment1<waterPayment2)
//                    {
//                        String d=new DecimalFormat("##0.00").format(waterPayment2-waterPayment1);
//                        nMap.put("waterFeeStatus","已缴费，并补缴"+d+"元");
//                    }
//                    else if(waterPayment1==waterPayment2)
//                        nMap.put("waterFeeStatus","已缴费");
//                    else
//                    {
//                        if(waterPayment2>0)
//                        {
//                            double d=Double.parseDouble(new DecimalFormat("##0.00").format(waterPayment1-waterPayment2));
//                            nMap.put("waterFeeStatus","已缴费，并欠费"+d+"元");
//                        }
//                        else
//                            nMap.put("waterFeeStatus","未缴费");
//                    }
//                }
//                else
//                {
//                    nMap.put("waterFeeStatus","未缴费");
//                }

                innerParam=new HashMap();
                innerParam.put("roomId",roomId);
                innerParam.put("currentY",currY);
                List waterUseList=singleTableDao.selectWaterUse(innerParam);
                double waterUse=0;
                if(waterUseList!=null&&!waterUseList.isEmpty())
                {
                    Map rMap=(Map)waterUseList.get(0);
                    if(rMap.get("m"+currM)!=null&&!rMap.get("m"+currM).toString().trim().equals(""))
                    {
                        waterUse=Double.parseDouble(rMap.get("m"+currM).toString());
                    }
                }
                nMap.put("waterUse",waterUse);

                double waterShouldPay=waterUse*3.5;
                String waterShouldPayS=new DecimalFormat("##0.00").format(waterShouldPay);
                nMap.put("waterFee",waterShouldPayS);
                if(Double.compare(waterShouldPay,0)>0)
                {
                    if(Double.compare(lastWaterMoney,waterShouldPay)>=0)
                    {
                        lastWaterMoney-=waterShouldPay;
                        nMap.put("waterFeeStatus","已缴费");
                    }
                    else
                    {
                        if(Double.compare(lastWaterMoney,0)>0)
                        {
                            lastWaterMoney=0;
                            nMap.put("waterFeeStatus","已缴费，实缴"+waterShouldPay+"元");
                        }
                        else
                        {
                            nMap.put("waterFeeStatus","未缴费");
                        }
                    }
                }
                else
                {
                    nMap.put("waterFeeStatus","未缴费");
                }

                nMap.put("year",currY);
                nMap.put("month",currM);

                payList.add(nMap);

                currM--;
            }

            int totalRows=payList.size();
            int page=1;
            int limit=12;
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
            Iterator li=payList.iterator();
            int i=0;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    nList.add(tMap);
                }
                i++;
            }
            resultMap.put("list",nList);
            resultMap.put("totalRows",totalRows);
            resultMap.put("currentPage",currentPage);
            return resultMap;
        }
        else
        {
            resultMap.put("list",new LinkedList<>());
            resultMap.put("totalRows",0);
            resultMap.put("currentPage",1);
            return resultMap;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseRoomPersonsByRoomId(String roomId) throws OssRenderException{
        if(roomId!=null&&!roomId.trim().equals(""))
        {
            return houseDao.queryHouseRoomPersonsByRoomId(roomId);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseRoomCarsByRoomId(String roomId) throws OssRenderException{
        if(roomId!=null&&!roomId.trim().equals(""))
        {
            return houseDao.queryHouseRoomCarsByRoomId(roomId);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHouseRoomPaymentByRoomId(String roomId,String beginTime,String endTime) throws OssRenderException{
        if(roomId!=null&&!roomId.trim().equals("")&&beginTime!=null&&!beginTime.trim().equals("")&&endTime!=null&&!endTime.trim().equals(""))
        {
            return houseDao.queryHouseRoomPaymentByRoomId(roomId,beginTime,endTime);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryRoomInfoByManageId(String manageId) throws OssRenderException{
        return queryRoomInfoByManageId(manageId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryHouseBuildingInnerList(Map<String, Object> params) throws OssRenderException{
        Map resultMap=new HashMap();
        List cellList=houseDao.queryCellListInBuilding(params);
        if(params.get("cellId")==null||params.get("cellId").toString().trim().equals(""))
        {
            if(cellList!=null&&!cellList.isEmpty())
            {
                Map cellListMap=(Map)cellList.get(0);
                params.put("cellId",cellListMap.get("cellId"));
            }
        }
        List buildingList=houseDao.queryHouseBuildingList(params);
        List floorNumList=houseDao.queryFloorNumListInBuilding(params);
        List floorList=new LinkedList();
        Iterator floorListI=floorNumList.iterator();
        String xqName="";
        String buildingName="";
        if(buildingList!=null&&!buildingList.isEmpty())
        {
            Map nMap=(Map)buildingList.get(0);
            if(nMap.get("xqName")!=null&&!nMap.get("xqName").toString().trim().equals(""))
            {
                xqName=nMap.get("xqName").toString();
            }
            if(nMap.get("buildingName")!=null&&!nMap.get("buildingName").toString().trim().equals(""))
            {
                buildingName=nMap.get("buildingName").toString();
            }
        }
        while (floorListI.hasNext())
        {
            Map floorListMap=new HashMap();
            Map currentFloorInfo=(Map)floorListI.next();
            int currentFloor=0;
            if(currentFloorInfo.get("floorNum")!=null&&!currentFloorInfo.get("floorNum").toString().trim().equals(""))
            {
                currentFloor=Integer.parseInt(currentFloorInfo.get("floorNum").toString());
                params.put("floorNum",currentFloor);
            }
            floorListMap.put("floorNum",currentFloor);
            List roomList=houseDao.queryRoomInfoInBuilding(params);
            floorListMap.put("roomList",roomList);

            //System.out.println(currentFloor);
            floorList.add(floorListMap);
        }
        int totalRows=floorList.size();
        int page=1;
        int limit=totalRows;
        if(params.get("page")!=null&&!params.get("page").toString().trim().equals(""))
            page=Integer.parseInt(params.get("page").toString());
        if(params.get("limit")!=null&&!params.get("limit").toString().trim().equals(""))
            limit=Integer.parseInt(params.get("limit").toString());
        List nList=new LinkedList();
        int currentPage=1;
        if(floorList!=null&&!floorList.isEmpty())
        {
            currentPage=page;
            int totalPage=totalRows/limit;
            if(totalRows%limit!=0)totalPage+=1;
            if(page>totalPage)currentPage=totalPage;
            if(page<1)currentPage=1;
            Iterator li=floorList.iterator();
            int i=0;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    nList.add(tMap);
                }
                i++;
            }
        }
        resultMap.put("floorList",nList);
        resultMap.put("xqName",xqName);
        resultMap.put("buildingName",buildingName);
        resultMap.put("totalRows",totalRows);
        resultMap.put("currentPage",currentPage);
        resultMap.put("cellList",cellList);
        //resultMap.put("");
        return resultMap;
    }

}
