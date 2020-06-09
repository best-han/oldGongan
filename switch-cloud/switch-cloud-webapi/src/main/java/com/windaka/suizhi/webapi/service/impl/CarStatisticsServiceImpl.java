package com.windaka.suizhi.webapi.service.impl;

import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.dao.*;
import com.windaka.suizhi.webapi.service.CarStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class CarStatisticsServiceImpl implements CarStatisticsService {

    @Autowired
    private CarStatisticsDao carStatisticsDao;

    @Autowired
    private SingleTableDao singleTableDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private CarGroupCarDao carGroupCarDao;

    @Autowired
    private CarTypeDao carTypeDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarTotalNum(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarTotalNumByXq(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarTotalNumBySub(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarTotalNumByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarSenseNum(Map<String,Object> params,String beginTime,String endTime) throws OssRenderException{
        Map<String, Object> implParams=new HashMap<>();
        if(beginTime!=null&&endTime!=null)
        {
            implParams.put("beginTime",beginTime);
            implParams.put("endTime",endTime);
        }
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            implParams.put("xqCode",params.get("xqCode").toString());
            return carStatisticsDao.queryCarSenseNumByXq(implParams);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            implParams.put("subdistrictId",params.get("subdistrictId").toString());
            return carStatisticsDao.queryCarSenseNumBySub(implParams);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            implParams.put("areaId",params.get("areaId").toString());
            return carStatisticsDao.queryCarSenseNumByArea(implParams);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarSerseStrangerNum(Map<String,Object> params,String beginTime,String endTime) throws OssRenderException{
        Map<String, Object> implParams=new HashMap<>();
        if(beginTime!=null&&endTime!=null)
        {
            implParams.put("beginTime",beginTime);
            implParams.put("endTime",endTime);
        }
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            implParams.put("xqCode",params.get("xqCode").toString());
            return carStatisticsDao.queryCarSerseStrangerNumByXq(implParams);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            implParams.put("subdistrictId",params.get("subdistrictId").toString());
            return carStatisticsDao.queryCarSerseStrangerNumBySub(implParams);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            implParams.put("areaId",params.get("areaId").toString());
            return carStatisticsDao.queryCarSerseStrangerNumByArea(implParams);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarPassNum(Map<String,Object> params,String carDirect,String beginTime,String endTime) throws OssRenderException{
        Map<String, Object> implParams=new HashMap<>();
        if(carDirect!=null)
        {
            implParams.put("carDirect",carDirect);
        }
        if(beginTime!=null&&endTime!=null)
        {
            implParams.put("beginTime",beginTime);
            implParams.put("endTime",endTime);
        }
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            implParams.put("xqCode",params.get("xqCode").toString());
            return carStatisticsDao.queryCarPassNumByXq(implParams);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            implParams.put("subdistrictId",params.get("subdistrictId").toString());
            return carStatisticsDao.queryCarPassNumBySub(implParams);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            implParams.put("areaId",params.get("areaId").toString());
            return carStatisticsDao.queryCarPassNumByArea(implParams);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarDistributionNum(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarDistributionNumByXq(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarDistributionNumBySub(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarDistributionNumByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarSenseTop12(Map<String,Object> params,int liveType,String beginTime,String endTime) throws OssRenderException{
        Map<String, Object> implParams=new HashMap<>();
        if(liveType!=-1)
            implParams.put("liveType",liveType);
        if(beginTime!=null&&endTime!=null)
        {
            implParams.put("beginTime",beginTime);
            implParams.put("endTime",endTime);
        }
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            implParams.put("xqCode",params.get("xqCode").toString());
            return carStatisticsDao.queryCarSenseTop12ByXq(implParams);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            implParams.put("subdistrictId",params.get("subdistrictId").toString());
            return carStatisticsDao.queryCarSenseTop12BySub(implParams);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            implParams.put("areaId",params.get("areaId").toString());
            return carStatisticsDao.queryCarSenseTop12ByArea(implParams);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarSpecialList(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarSpecialListByXq(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarSpecialListBySub(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarSpecialListByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarList(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCodes")!=null&&!params.get("xqCodes").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarListByXq(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarListBySub(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarListByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarList2(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCodes")!=null&&!params.get("xqCodes").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarList2(params);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarList2(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarList2(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryCarList3(Map<String,Object> params) throws OssRenderException{
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
        if(params.get("likeStr")!=null&&!params.get("likeStr").toString().trim().equals("")) {
            pf=1;
            tList=new LinkedList();
            Map innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr1",params.get("likeStr"));
            tList.addAll(carStatisticsDao.queryCarListLikeStr(innerParams));

            innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr2",params.get("likeStr"));
            List tListP=carStatisticsDao.queryCarListLikeStr(innerParams);
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
                        if(m1.get("carNum").toString().equals(m2.get("carNum").toString()))
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

            innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr3",params.get("likeStr"));
            List tListO=carStatisticsDao.queryCarListLikeStr(innerParams);
            if(tListO!=null&&!tListO.isEmpty())
            {
                int n1=tList.size();
                int n2=tListO.size();
                List ndList=new LinkedList();
                ndList.addAll(tList);
                for(int i=0;i<n2;i++)
                {
                    int f=0;
                    Map m1=(Map)tListO.get(i);
                    for(int j=0;j<n1;j++)
                    {
                        Map m2=(Map)tList.get(j);
                        if(m1.get("carNum").toString().equals(m2.get("carNum").toString()))
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
        else{
            tList=singleTableDao.selectCarInfoAndHouseOwnerRoom(params);
        }
        Map resultMap=new HashMap();
        if(tList!=null&&!tList.isEmpty()) {
            int totalRows = tList.size();
            int page = 1;
            int limit = 10;
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
            int i=0;
            int end=currentPage*limit;
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                if(i>=(currentPage-1)*limit&&i<end)
                {
                    Map nMap=new LinkedHashMap();
                    if(pf==0)
                    {
                        nMap=new LinkedHashMap();
                        nMap.put("xqCode",tMap.get("xq_code"));
                        nMap.put("carNum",tMap.get("car_num"));
                        nMap.put("carNumColorName",tMap.get("car_num_color_name"));
                        nMap.put("checkinDate",tMap.get("create_date"));
                        nMap.put("carBrandName",tMap.get("car_brand_name"));
                        nMap.put("carColorName",tMap.get("car_color_name"));
                        nMap.put("liveTypeName",tMap.get("live_type_name"));

                        if(tMap.get("car_img")!=null&&!tMap.get("car_img").toString().trim().equals(""))
                        {
                            String base64Img= PropertiesUtil.getLocalTomcatImageIp()+tMap.get("car_img").toString();
                            nMap.put("image",base64Img);
                        }
                        else
                            nMap.put("image","");

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
                        innerParam.put("personId",tMap.get("owner_id"));
                        List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                        String personName="无";
                        if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                        {
                            Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                            if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                            {
                                personName=zsPersonInfoMap.get("name").toString();
                            }
                        }
                        nMap.put("personName",personName);

                        innerParam=new HashMap();
                        innerParam.put("ownerId",tMap.get("owner_id"));

                        String roomId="无";

                        if(tMap.get("room_id")!=null&&!tMap.get("room_id").toString().trim().equals(""))
                        {
                            roomId=tMap.get("room_id").toString();
                        }

                        innerParam=new HashMap();
                        String roomName=roomDao.queryRoomNameByManageId(roomId);
                        if(roomName!=null&&!roomName.equals(""))
                        {
                            nMap.put("roomName",roomName);
                        }
                        else
                        {
                            nMap.put("roomName","无");
                        }
                    }
                    else
                    {
                        nMap=new LinkedHashMap();
                        nMap.put("xqCode",tMap.get("xqCode"));
                        nMap.put("xqName",tMap.get("xqName"));
                        nMap.put("carNum",tMap.get("carNum"));
                        nMap.put("carNumColorName",tMap.get("carNumColorName"));
                        nMap.put("checkinDate",tMap.get("checkinDate"));
                        nMap.put("carBrandName",tMap.get("carBrandName"));
                        nMap.put("carColorName",tMap.get("carColorName"));
                        nMap.put("roomName",tMap.get("roomName"));
                        Map innerParam;
                        if(tMap.get("liveTypeName")==null)
                        {
                            String liveTypeName="无";
                            innerParam=new HashMap();
                            innerParam.put("ownerId",tMap.get("ownerId"));
                            innerParam.put("roomId",tMap.get("roomId"));
                            List houseOwnerList=singleTableDao.selectHouseOwnerRoom(innerParam);
                            if(houseOwnerList!=null&&!houseOwnerList.isEmpty())
                            {
                                Map lMap=(Map)houseOwnerList.get(0);
                                if(lMap.get("live_type_name")!=null&&!lMap.get("live_type_name").toString().trim().equals(""))
                                {
                                    liveTypeName=lMap.get("live_type_name").toString();
                                }
                            }
                            nMap.put("liveTypeName",liveTypeName);
                        }
                        else
                        {
                            nMap.put("liveTypeName",tMap.get("liveTypeName"));
                        }

                        if(tMap.get("personName")==null)
                        {
                            innerParam=new HashMap();
                            innerParam.put("personId",tMap.get("ownerId"));
                            List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                            String personName="无";
                            if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                            {
                                Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                                if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                                {
                                    personName=zsPersonInfoMap.get("name").toString();
                                }
                            }
                            nMap.put("personName",personName);
                        }
                        else
                        {
                            nMap.put("personName",tMap.get("personName"));
                        }
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
    public List<Map<String,Object>> queryCarBaseInfoNew(Map<String,Object> params,int liveType,String beginTime,String endTime) throws OssRenderException{
        Map<String, Object> implParams=new HashMap<>();
        if(liveType!=-1)
            implParams.put("liveType",liveType+"");
        if(beginTime!=null&&endTime!=null)
        {
            implParams.put("beginTime",beginTime);
            implParams.put("endTime",endTime);
        }
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            implParams.put("xqCode",params.get("xqCode").toString());
            return carStatisticsDao.queryCarBaseInfoNewByXq(implParams);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            implParams.put("subdistrictId",params.get("subdistrictId").toString());
            return carStatisticsDao.queryCarBaseInfoNewBySub(implParams);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            implParams.put("areaId",params.get("areaId").toString());
            return carStatisticsDao.queryCarBaseInfoNewByArea(implParams);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryHighSense(Map<String,Object> params,String beginTime,String endTime) throws OssRenderException{
        Map<String, Object> implParams=new HashMap<>();
        if(beginTime!=null&&endTime!=null)
        {
            implParams.put("beginTime",beginTime);
            implParams.put("endTime",endTime);
        }
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            implParams.put("xqCode",params.get("xqCode").toString());
            return carStatisticsDao.queryHighSenseByXq(implParams);
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            implParams.put("subdistrictId",params.get("subdistrictId").toString());
            return carStatisticsDao.queryHighSenseBySub(implParams);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            implParams.put("areaId",params.get("areaId").toString());
            return carStatisticsDao.queryHighSenseByArea(implParams);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryHighSense2(Map<String,Object> params) throws OssRenderException{
        List tList=null;
        tList=singleTableDao.selectCarAccessRecordSenseNum(params);
        Map resultMap=new HashMap();
        if(tList!=null&&!tList.isEmpty()) {
            int totalRows = tList.size();
            int page = 1;
            int limit = 10;
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
            int i=0;
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    Map nMap=new LinkedHashMap();
                    nMap.put("carNum",tMap.get("car_num"));
                    nMap.put("senseNum",tMap.get("num"));

                    Map innerParam=new HashMap();
                    String id="无";
                    if(tMap.get("id")!=null&&!tMap.get("id").toString().trim().equals(""))
                        id=tMap.get("id").toString();
                    innerParam.put("id",id);
                    List carAccessRecordL=singleTableDao.selectCarAccessRecord(innerParam);
                    //String carGroupName="无";
                    String carNum="无";
                    if(carAccessRecordL!=null&&!carAccessRecordL.isEmpty())
                    {
                        Map carAccessRecordMap=(Map)carAccessRecordL.get(0);
                        nMap.put("xqCode",carAccessRecordMap.get("xq_code"));
                        nMap.put("image",PropertiesUtil.getLocalTomcatImageIp()+carAccessRecordMap.get("base64_img").toString());
                        nMap.put("lastCaptureImage",PropertiesUtil.getLocalTomcatImageIp()+carAccessRecordMap.get("base64_img").toString());
                        nMap.put("lastCaptureTime",carAccessRecordMap.get("cap_time"));
                        if(carAccessRecordMap.get("car_num")!=null&&!carAccessRecordMap.get("car_num").toString().trim().equals(""))
                            carNum=carAccessRecordMap.get("car_num").toString();
                    }
                    else
                    {
                        nMap.put("xqCode","无");
                        nMap.put("image","无");
                        nMap.put("lastCaptureImage","无");
                        nMap.put("lastCaptureTime","无");
                    }

                    innerParam=new HashMap();
                    innerParam.put("carNum",carNum);
                    List carInfoL=singleTableDao.selectCarInfo(innerParam);
                    String ownerId="无";
                    if(carInfoL!=null&&!carInfoL.isEmpty())
                    {
                        Map carInfoMap=(Map)carInfoL.get(0);
                        if(carInfoMap.get("owner_id")!=null&&!carInfoMap.get("owner_id").toString().trim().equals(""))
                        {
                            ownerId=carInfoMap.get("owner_id").toString();
                        }
                    }

                    innerParam=new HashMap();
                    innerParam.put("personId",ownerId);
                    List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                    String ownerName="无";
                    if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                    {
                        Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                        if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                        {
                            ownerName=zsPersonInfoMap.get("name").toString();
                        }
                    }
                    nMap.put("ownerName",ownerName);

                    innerParam=new HashMap();
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
    public List<Map<String,Object>> queryTjCarAccessList(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return carStatisticsDao.queryTjCarAccessListByXq(params);
        }
        if(params.get("wyCode")!=null&&!params.get("wyCode").toString().trim().equals(""))
        {
            return carStatisticsDao.queryTjCarAccessListByWy(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryTjCarAccessListByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryTjCarAccessList2(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("areaId");
            params.remove("wyCode");
        }
        if(params.get("wyCode")!=null&&!params.get("wyCode").toString().trim().equals(""))
        {
            params.remove("xqCode");
            params.remove("areaId");
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("xqCode");
            params.remove("wyCode");
        }
        int page = 1;
        int limit = 10;
        if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
            page = Integer.parseInt(params.get("page").toString());
        if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
            limit = Integer.parseInt(params.get("limit").toString());
        Map resultMap = new LinkedHashMap();
        if (params.get("access") != null && !params.get("access").toString().trim().equals("")) {
            if (params.get("access").toString().equals("0"))
                params.put("carDirect", "9");
            else if (params.get("access").toString().equals("1"))
                params.put("carDirect", "8");
        }
        List list=null;
        if(params.get("likeStr")!=null&&!params.get("likeStr").toString().trim().equals(""))
        {
            list=new LinkedList();
            Map innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr1",params.get("likeStr"));
            list.addAll(carStatisticsDao.queryTjCarAccessList2(innerParams));

            innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr2",params.get("likeStr"));
            List tListP=carStatisticsDao.queryTjCarAccessList2(innerParams);
            if(tListP!=null&&!tListP.isEmpty())
            {
                int n1=list.size();
                int n2=tListP.size();
                List ndList=new LinkedList();
                ndList.addAll(list);
                for(int i=0;i<n2;i++)
                {
                    int f=0;
                    Map m1=(Map)tListP.get(i);
                    for(int j=0;j<n1;j++)
                    {
                        Map m2=(Map)list.get(j);
                        if(m1.get("id").toString().equals(m2.get("id").toString()))
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==0)
                        ndList.add(m1);
                }
                list=ndList;
            }

            innerParams=new HashMap();
            innerParams.putAll(params);
            innerParams.put("likeStr3",params.get("likeStr"));
            List tListO=carStatisticsDao.queryTjCarAccessList2(innerParams);
            if(tListO!=null&&!tListO.isEmpty())
            {
                int n1=list.size();
                int n2=tListO.size();
                List ndList=new LinkedList();
                ndList.addAll(list);
                for(int i=0;i<n2;i++)
                {
                    int f=0;
                    Map m1=(Map)tListO.get(i);
                    for(int j=0;j<n1;j++)
                    {
                        Map m2=(Map)list.get(j);
                        if(m1.get("id").toString().equals(m2.get("id").toString()))
                        {
                            f=1;
                            break;
                        }
                    }
                    if(f==0)
                        ndList.add(m1);
                }
                list=ndList;
            }
        }
        else
        {
            list = carStatisticsDao.queryTjCarAccessList2(params);
        }
        if(list!=null&&!list.isEmpty())
        {
            int totalRows = list.size();

            int currentPage = page;
            int totalPage = totalRows / limit;
            if (totalRows % limit != 0) totalPage += 1;
            if (page > totalPage) currentPage = totalPage;
            if (page < 1) currentPage = 1;

            List nList = new LinkedList();
            Iterator li = list.iterator();
            int i = 0;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                if (i >= (currentPage - 1) * limit && i < currentPage * limit) {
                    Map nMap=new HashMap();
                    nMap.put("devChnNum",tMap.get("devChnNum"));
                    nMap.put("xqName",tMap.get("xqName"));
                    nMap.put("devChnId",tMap.get("devChnId"));
                    nMap.put("carNum",tMap.get("carNum"));
                    if(tMap.get("carColorName")==null)
                        nMap.put("carColorName","未识别");
                    else
                        nMap.put("carColorName",tMap.get("carColorName"));
                    nMap.put("xqCode",tMap.get("xqCode"));
                    nMap.put("devChnName",tMap.get("devChnName"));
                    nMap.put("capTime",tMap.get("capTime"));
                    if (tMap.get("carDirect") != null && !tMap.get("carDirect").toString().trim().equals("")) {
                        if (tMap.get("carDirect").toString().equals("8"))
                            nMap.put("carDirect", "进场");
                        else if (tMap.get("carDirect").toString().equals("9"))
                            nMap.put("carDirect", "出场");
                        else
                            nMap.put("carDirect", "未知");
                    } else {
                        nMap.put("carDirect", "未知");
                    }
                    if (tMap.get("originalPicPath") != null && !tMap.get("originalPicPath").toString().trim().equals("")) {
                        String image = tMap.get("originalPicPath").toString().trim();
                        String ip = PropertiesUtil.getLocalTomcatImageIp();
                        image = ip + image;
                        nMap.put("originalPicPath", image);
                    }
                    if (tMap.get("realCapturePicPath") != null && !tMap.get("realCapturePicPath").toString().trim().equals("")) {
                        String ip = PropertiesUtil.getLocalTomcatImageIp();
                        nMap.put("realCapturePicPath", ip + tMap.get("realCapturePicPath").toString().trim());
                    }

                    Map innerParam;
                    String ownerId="无";
                    String roomId="无";
                    if(tMap.get("ownerId")==null||tMap.get("roomId")==null)
                    {
                        innerParam=new HashMap();
                        String carNum="无";
                        if(tMap.get("carNum")!=null&&!tMap.get("carNum").toString().trim().equals(""))
                            carNum=tMap.get("carNum").toString();
                        innerParam.put("carNum",carNum);
                        List carInfoL=singleTableDao.selectCarInfo(innerParam);
                        if(carInfoL!=null&&!carInfoL.isEmpty())
                        {
                            Map carInfoMap=(Map)carInfoL.get(0);
                            if(carInfoMap.get("owner_id")!=null&&!carInfoMap.get("owner_id").toString().trim().equals(""))
                            {
                                ownerId=carInfoMap.get("owner_id").toString();
                            }
                            if(carInfoMap.get("room_id")!=null&&!carInfoMap.get("room_id").toString().trim().equals(""))
                            {
                                roomId=carInfoMap.get("room_id").toString();
                            }
                        }
                    }
                    else
                    {
                        ownerId=tMap.get("ownerId").toString();
                        roomId=tMap.get("roomId").toString();
                    }
                    nMap.put("personCode",ownerId);

                    if(tMap.get("liveTypeName")==null||tMap.get("liveType")==null)
                    {
                        String liveTypeName="无";
                        String liveType=null;
                        innerParam=new HashMap();
                        innerParam.put("ownerId",ownerId);
                        innerParam.put("roomId",roomId);
                        List houseOwnerList=singleTableDao.selectHouseOwnerRoom(innerParam);
                        if(houseOwnerList!=null&&!houseOwnerList.isEmpty())
                        {
                            Map lMap=(Map)houseOwnerList.get(0);
                            if(lMap.get("live_type_name")!=null&&!lMap.get("live_type_name").toString().trim().equals(""))
                            {
                                liveTypeName=lMap.get("live_type_name").toString();
                            }
                            if(lMap.get("live_type")!=null&&!lMap.get("live_type").toString().trim().equals(""))
                            {
                                liveType=lMap.get("live_type").toString();
                            }
                        }
                        nMap.put("liveTypeName",liveTypeName);
                        nMap.put("liveType",liveType);
                    }
                    else
                    {
                        nMap.put("liveTypeName",tMap.get("liveTypeName"));
                        nMap.put("liveType",tMap.get("liveType"));
                    }

                    if(tMap.get("personName")==null)
                    {
                        innerParam=new HashMap();
                        innerParam.put("personId",ownerId);
                        List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                        String personName="陌生人";
                        if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                        {
                            Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                            if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                            {
                                personName=zsPersonInfoMap.get("name").toString();
                            }
                        }
                        nMap.put("personName",personName);
                    }
                    else
                    {
                        nMap.put("personName",tMap.get("personName"));
                    }

                    String roomName=roomDao.queryRoomNameByManageId(roomId);
                    if(roomName!=null&&!roomName.equals(""))
                    {
                        nMap.put("roomName",roomName);
                    }
                    else
                    {
                        nMap.put("roomName","无");
                    }

                    nList.add(nMap);
                }
                i++;
            }

            resultMap.put("list", nList);
            resultMap.put("totalRows", totalRows);
            resultMap.put("currentPage", currentPage);
        }
        else
        {
            resultMap.put("list", new LinkedList<>());
            resultMap.put("totalRows", 0);
            resultMap.put("currentPage", 1);
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Map<String,Object>> queryCarSenseHighAccess(Map<String,Object> params) throws OssRenderException{
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarSenseHighAccessByXq(params);
        }
        if(params.get("wyCode")!=null&&!params.get("wyCode").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarSenseHighAccessBySub(params);
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            return carStatisticsDao.queryCarSenseHighAccessByArea(params);
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String,Object> queryCarSenseHighAccess2(Map<String,Object> params) throws OssRenderException{
        List tList=null;
        tList=singleTableDao.selectCarAccessRecordSenseNum(params);
        Map resultMap=new HashMap();
        if(tList!=null&&!tList.isEmpty()) {
            int totalRows = tList.size();
            int page = 1;
            int limit = 10;
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
            int i=0;
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    Map nMap=new LinkedHashMap();
                    nMap.put("carNum",tMap.get("car_num"));
                    nMap.put("senseNum",tMap.get("num"));

                    Map innerParam=new HashMap();

                    String id="无";
                    if(tMap.get("id")!=null&&!tMap.get("id").toString().trim().equals(""))
                        id=tMap.get("id").toString();
                    innerParam.put("id",id);
                    List carAccessRecordL=singleTableDao.selectCarAccessRecord(innerParam);
                    //String carGroupName="无";
                    String carNum="无";
                    if(carAccessRecordL!=null&&!carAccessRecordL.isEmpty())
                    {
                        Map carAccessRecordMap=(Map)carAccessRecordL.get(0);
                        nMap.put("xqCode",carAccessRecordMap.get("xq_code"));
                        nMap.put("image",PropertiesUtil.getLocalTomcatImageIp()+carAccessRecordMap.get("base64_img").toString());
                        nMap.put("lastCaptureImage",PropertiesUtil.getLocalTomcatImageIp()+carAccessRecordMap.get("base64_img").toString());
                        nMap.put("lastCaptureTime",carAccessRecordMap.get("cap_time"));
                        if(carAccessRecordMap.get("car_num")!=null&&!carAccessRecordMap.get("car_num").toString().trim().equals(""))
                            carNum=carAccessRecordMap.get("car_num").toString();
                    }
                    else
                    {
                        nMap.put("xqCode","无");
                        nMap.put("image","无");
                        nMap.put("lastCaptureImage","无");
                        nMap.put("lastCaptureTime","无");
                    }

                    innerParam=new HashMap();
                    innerParam.put("carNum",carNum);
                    List carInfoL=singleTableDao.selectCarInfo(innerParam);
                    String ownerId="无";
                    String roomId="无";
                    if(carInfoL!=null&&!carInfoL.isEmpty())
                    {
                        Map carInfoMap=(Map)carInfoL.get(0);
                        if(carInfoMap.get("owner_id")!=null&&!carInfoMap.get("owner_id").toString().trim().equals(""))
                        {
                            ownerId=carInfoMap.get("owner_id").toString();
                        }
                        if(carInfoMap.get("room_id")!=null&&!carInfoMap.get("room_id").toString().trim().equals(""))
                        {
                            roomId=carInfoMap.get("room_id").toString();
                        }
                    }

                    innerParam=new HashMap();
                    innerParam.put("personId",ownerId);
                    List zsPersonInfoL=singleTableDao.selectZsPersonInfo(innerParam);
                    String ownerName="无";
                    if(zsPersonInfoL!=null&&!zsPersonInfoL.isEmpty())
                    {
                        Map zsPersonInfoMap=(Map)zsPersonInfoL.get(0);
                        if(zsPersonInfoMap.get("name")!=null&&!zsPersonInfoMap.get("name").toString().trim().equals(""))
                        {
                            ownerName=zsPersonInfoMap.get("name").toString();
                        }
                    }
                    nMap.put("ownerName",ownerName);

                    innerParam=new HashMap();
                    innerParam.put("xqCode",nMap.get("xqCode"));
                    List xqL=singleTableDao.selectHtXqInfo(innerParam);
                    String xqName=null;
                    if(xqL!=null&&!xqL.isEmpty())
                    {
                        Map xqInfoMap=(Map)xqL.get(0);
                        if(xqInfoMap.get("name")!=null&&!xqInfoMap.get("name").toString().trim().equals(""))
                        {
                            xqName=xqInfoMap.get("name").toString();
                        }
                    }
                    nMap.put("xqName",xqName);

                    String roomName=roomDao.queryRoomNameByManageId(roomId);
                    if(roomName!=null&&!roomName.equals("roomId"))
                    {
                        nMap.put("roomName",roomName);
                    }
                    else
                    {
                        nMap.put("roomName","");
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

    public List<Map<String,Object>> selectCarInfoByCarNum(String carNum) throws OssRenderException{
        return carStatisticsDao.selectCarInfoByCarNum(carNum);
    }

    public int saveCarGroupCar(Map<String, Object> params){
        return carGroupCarDao.saveCarGroupCar(params);
    }

    @Override
    public int deleteCarGroupCar(Map<String, Object> params){
        return carGroupCarDao.deleteCarGroupCarPermenently(params);
    }

    @Override
    public int deleteCarGroup(Map<String, Object> params){
        try {
            carGroupCarDao.deleteCarGroupCarR(params);
            carTypeDao.deleteCarTypeR(params);
            return 1;
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return 0;
        }
    }

    @Override
    public Map<String, Object> queryCarGroupCar(Map<String, Object> params){
        List tList=null;
        tList=carGroupCarDao.queryCarGroupCar(params);
        Map resultMap=new HashMap();
        if(tList!=null&&!tList.isEmpty()) {
            int totalRows = tList.size();
            int page = 1;
            int limit = 10;
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
            int i=0;
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    Map nMap=new LinkedHashMap();
                    nMap.put("id",tMap.get("id"));
                    nMap.put("carGroupType",tMap.get("group_code"));
                    if(tMap.get("alarm_level")!=null&&!tMap.get("alarm_level").toString().equals(""))
                    {
                        if(tMap.get("alarm_level").toString().equals("1"))
                        {
                            nMap.put("alarmLevel","高");
                        }
                        else if(tMap.get("alarm_level").toString().equals("2"))
                        {
                            nMap.put("alarmLevel","中");
                        }
                        else if(tMap.get("alarm_level").toString().equals("3"))
                        {
                            nMap.put("alarmLevel","低");
                        }
                    }
                    nMap.put("controlReason",tMap.get("control_reason"));
                    nMap.put("carNum",tMap.get("car_num"));
                    nMap.put("carGroupName",tMap.get("carGroupName"));
                    nMap.put("createUserName",tMap.get("createUserName"));
                    nMap.put("control_beginTime",tMap.get("controlBeginTime"));
                    nMap.put("control_endTime",tMap.get("controlEndTime"));

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

    /**
     * @param params
     * @return map
     * @description 车辆抓拍记录查询
     * @author zhoutao
     * @date 2019/12/30 14:32
     */
    @Override
    public Map<String, Object> queryCarCaptureList(Map<String, Object> params) {
        if(params.get("page") == null || "".equals(params.get("page").toString().trim())){
            params.put("page", 1);
        }
        if(params.get("limit") == null || "".equals(params.get("limit").toString().trim())){
            params.put("limit", 10);
        }
        if(params.get("areaId") == null || "".equals(params.get("areaId").toString().trim())){
            params.put("areaId", 0);
        }
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        int start = (page - 1) * limit;
        params.put("start", start);
        params.put("limit", limit);
        // 判断是否需要颜色筛选
        if(params.get("carNumColor") != null&&!params.get("carNumColor").toString().trim().equals("")){
//            if("5".equals(params.get("carNumColor"))){
//                params.put("colorNum", 9);
//            }
//            if("6".equals(params.get("carNumColor"))){
//                params.put("colorNum", 10);
//            }
            params.put("colorNum", Integer.parseInt(params.get("carNumColor").toString())+4);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        List<Map<String, Object>> lists  = carStatisticsDao.queryCarCaptureList(params);
        for(Map<String, Object> tmp : lists) {
            // 抓拍路径补全
            if (tmp.get("base64Img") == null || "".equals(tmp.get("base64Img"))) {
                tmp.put("base64Img", "");
            } else {
                String img = tmp.get("base64Img").toString();
                String ip = PropertiesUtil.getLocalTomcatImageIp();
                img = ip + img;
                tmp.put("base64Img", img);
            }
            // 抓拍车辆颜色处理
            if (tmp.get("carNum") == null || "".equals(tmp.get("carNum"))) {
                tmp.put("carNum", "");
                tmp.put("carNumColor", "无");
            } else {
                String carCode = tmp.get("carNum").toString();
                char[] codeArray = carCode.toCharArray();
                if (codeArray.length == 7) {
                    tmp.put("carNumColor", "蓝色");
                }
                if (codeArray.length == 8) {
                    tmp.put("carNumColor", "绿色");
                }
            }
        }

        int totalRows = carStatisticsDao.queryCarCaptureNum(params);
        result.put("list", lists);
        result.put("currentPage", params.get("page"));
        result.put("totalRows", totalRows);
        if(params.get("carNum")!=null&&!params.get("carNum").toString().trim().equals(""))
        {
            List list2=carStatisticsDao.queryCarCaptureTrackByCarNum(params);
            result.put("list2",list2);
        }
        return result;
    }

    @Override
    public Map<String, Object> queryCarSenseHighAccessInfoByCarNum(Map<String, Object> params){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List nList = new LinkedList();
        int totalRows=0;
        int currentPage=1;
        if(params.get("carNum")!=null&&!params.get("carNum").toString().trim().equals(""))
        {
            List accessList=carStatisticsDao.queryCarSenseHighAccessInfoByCarNum(params);
            if(accessList!=null&&!accessList.isEmpty())
            {
                totalRows = accessList.size();
                int page = 1;
                int limit = totalRows;
                if (params.get("page") != null && !params.get("page").toString().trim().equals(""))
                    page = Integer.parseInt(params.get("page").toString());
                if (params.get("limit") != null && !params.get("limit").toString().trim().equals(""))
                    limit = Integer.parseInt(params.get("limit").toString());
                currentPage = page;
                int totalPage = totalRows / limit;
                if (totalRows % limit != 0) totalPage += 1;
                if (page > totalPage) currentPage = totalPage;
                if (page < 1) currentPage = 1;
                nList = new LinkedList();
                Iterator li = accessList.iterator();
                int i=0;
                while (li.hasNext())
                {
                    Map tMap=(Map)li.next();
                    if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                    {
                        nList.add(tMap);
                    }
                    i++;
                }
            }
        }
        resultMap.put("list",nList);
        resultMap.put("totalRows",totalRows);
        resultMap.put("currentPage",currentPage);
        return resultMap;
    }

    @Override
    public Map<String, Object> queryCarCapDaysWithIn15Days(Map<String, Object> params){
        List tList=null;
        if(params.get("xqCode")!=null&&!params.get("xqCode").toString().trim().equals(""))
        {
            params.remove("areaId");
            params.remove("subdistrictId");
        }
        if(params.get("subdistrictId")!=null&&!params.get("subdistrictId").toString().trim().equals(""))
        {
            params.remove("xqCode");
            params.remove("areaId");
        }
        if(params.get("areaId")!=null&&!params.get("areaId").toString().trim().equals(""))
        {
            params.remove("xqCode");
            params.remove("subdistrictId");
        }
        if(params.get("carNumColor") != null&&!params.get("carNumColor").toString().trim().equals("")){
            params.put("carNumColor", Integer.parseInt(params.get("carNumColor").toString())+4);
        }
        tList=carStatisticsDao.queryCarCapDaysWithIn15Days(params);
        Map resultMap=new HashMap();
        if(tList!=null&&!tList.isEmpty()) {
            int totalRows = tList.size();
            int page = 1;
            int limit = 10;
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
            int i=0;
            while (li.hasNext())
            {
                Map tMap=(Map)li.next();
                if(i>=(currentPage-1)*limit&&i<currentPage*limit)
                {
                    Map nMap=new HashMap();
                    nMap.put("carNum",tMap.get("car_num"));
                    nMap.put("capDays",tMap.get("pass_num"));
                    if(tMap.get("car_num")!=null&&!tMap.get("car_num").toString().trim().equals(""))
                    {
                        String carCode = tMap.get("car_num").toString();
                        char[] codeArray = carCode.toCharArray();
                        if (codeArray.length == 7) {
                            nMap.put("carNumColor", "蓝色");
                        }
                        if (codeArray.length == 8) {
                            nMap.put("carNumColor", "绿色");
                        }
                    }
                    else
                    {
                        nMap.put("carNumColor", "");
                    }
                    Map innerParam=new HashMap();
                    innerParam.putAll(params);
                    innerParam.put("carNum",tMap.get("car_num"));
                    List caIdList=singleTableDao.selectCarAttributeMaxId(innerParam);
                    String id="";
                    if(caIdList!=null&&!caIdList.isEmpty())
                    {
                        Map caIdMap=(Map)caIdList.get(0);
                        if(caIdMap.get("id")!=null&&!caIdMap.get("id").toString().trim().equals(""))
                        {
                            id=caIdMap.get("id").toString();
                        }
                    }
                    innerParam=new HashMap();
                    innerParam.put("id",id);
                    List singleCarAttributeList=singleTableDao.selectCarAttribute(innerParam);
                    String xqCode="";
                    String image="";
                    String carColorName="";
                    String carBrandName="";
                    if(singleCarAttributeList!=null&&!singleCarAttributeList.isEmpty())
                    {
                        Map caMap=(Map)singleCarAttributeList.get(0);
                        if(caMap.get("xq_code")!=null&&!caMap.get("xq_code").toString().trim().equals(""))
                        {
                            xqCode=caMap.get("xq_code").toString();
                        }
                        if(caMap.get("base64_img")!=null&&!caMap.get("base64_img").toString().trim().equals(""))
                        {
                            String ip = PropertiesUtil.getLocalTomcatImageIp();
                            image=ip+caMap.get("base64_img").toString();
                        }
                        if(caMap.get("car_color_name")!=null&&!caMap.get("car_color_name").toString().trim().equals(""))
                        {
                            carColorName=caMap.get("car_color_name").toString();
                        }
                        if(caMap.get("car_brand_name")!=null&&!caMap.get("car_brand_name").toString().trim().equals(""))
                        {
                            carBrandName=caMap.get("car_brand_name").toString();
                        }
                    }
                    innerParam=new HashMap();
                    innerParam.put("xqCode",xqCode);
                    List xqList=singleTableDao.selectHtXqInfo(innerParam);
                    String xqName="";
                    if(xqList!=null&&!xqList.isEmpty())
                    {
                        Map xqMap=(Map)xqList.get(0);
                        if(xqMap.get("name")!=null&&!xqMap.get("name").toString().trim().equals(""))
                        {
                            xqName=xqMap.get("name").toString();
                        }
                    }
                    nMap.put("xqCode",xqCode);
                    nMap.put("xqName",xqName);
                    nMap.put("image",image);
                    //nMap.put("carNumColor",carNumColor);
                    nMap.put("carColorName",carColorName);
                    nMap.put("carBrandName",carBrandName);
                    nList.add(nMap);
                }
                i++;
            }
            resultMap.put("list",nList);
            resultMap.put("totalRows",totalRows);
            resultMap.put("currentPage",currentPage);
            return resultMap;
        }
        else{
            resultMap.put("list",new LinkedList<>());
            resultMap.put("totalRows",0);
            resultMap.put("currentPage",1);
            return resultMap;
        }
    }

}
