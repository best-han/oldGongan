package com.windaka.suizhi.webapi.service.impl;

import com.netflix.discovery.converters.Auto;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.FaceContrastUtil;
import com.windaka.suizhi.common.utils.FileUploadUtil;
import com.windaka.suizhi.common.utils.PicUtil;
import com.windaka.suizhi.common.utils.PropertiesUtil;
import com.windaka.suizhi.webapi.dao.FaceTypePersonDao;
import com.windaka.suizhi.webapi.dao.PersonDao;
import com.windaka.suizhi.webapi.dao.SwitchCloudDao;
import com.windaka.suizhi.webapi.service.FaceTypePersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;


@Slf4j
@RestController
public class FaceTypePersonServiceImpl implements FaceTypePersonService {
    @Autowired
    private FaceTypePersonDao faceTypePersonDao;
    @Autowired
    SwitchCloudDao switchCloudDao;
    @Autowired
    PersonDao personDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveFaceTypePerson(Map<String, Object> params) throws OssRenderException {

        if (null != params.get("personImg") && StringUtils.isNotBlank(params.get("personImg").toString())) {
            String personImg=params.get("personImg").toString();

            byte[] picByte = PicUtil.stringToInputStream(personImg.split(",")[1]);
            // 获取最大id值
            Integer nextId = switchCloudDao.queryNextId("face_type_person");
            String fileName = PicUtil.getPicName("face_type_person", nextId);
            // 图片存到本地以便访问
        /*    FileUploadUtil.inputStreamToLocalFile(picByte, CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
            params.put("personImg", fileName);*/

            //封装访问路径：年/月/日
            Date date=new Date();
            // 图片放入访问路径
            /*FileUploadUtil.inputStreamToLocalFile(picByte,
                    CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);*/
            FileUploadUtil.inputStreamToLocalFile(picByte,
                    CommonConstants.GONGAN_IMAGE_PATH + File.separator +"image"+File.separator +PicUtil.getPicRelativePath(date), fileName);
            params.put("personImg", PicUtil.getPicRelativePath(date)+ fileName);

            //增加人脸特征给人脸服务器以便其提取功能
            Map mapFaceContrast= FaceContrastUtil.addFace(nextId,personImg.split(",")[1],"0");
            if(StringUtils.isBlank((String)mapFaceContrast.get("id"))){
                throw new OssRenderException(ReturnConstants.CODE_FAILED,"人脸特征存入失败");
            }
            params.put("faceFeature",mapFaceContrast.get("faceFeature"));
            params.put("source","0");//前端维护
            //params.put("faceTypePersonId",faceTypePersonId);
            faceTypePersonDao.saveFaceTypePerson(params);
            if(params.get("personPaperNum") !=null || params.get("personPaperNum") !=""){
                personDao.updatePersonFacePersonTypeIdByPaperNum(params.get("personPaperNum").toString());
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delFaceTypePerson(Map<String, Object> params) throws OssRenderException {
		/*String xqCode=(String)params.get("xqCode");//公安局内不需要
		if (StringUtils.isBlank(xqCode)) {
			throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
		}*/
//        List personCodes = (List<String>) params.get("personCodes");
//        if (CollectionUtils.isEmpty(personCodes)) {
//            throw new OssRenderException(ReturnConstants.CODE_FAILED, "personCodes不能为空");
//        }
//        faceTypePersonDao.delFaceTypePerson(params);撤控
        faceTypePersonDao.deletFaceTypePerson(params);//彻底删除
        //调用人脸特征删除
        FaceContrastUtil.delFace(Integer.parseInt(params.get("id").toString()));
    }

    @Override
    public Map<String, Object> faceFaceTypeList(Map<String, Object> params) throws OssRenderException {

        Map<String, Object> map = new HashMap<String, Object>();

        //int totalRows = faceTypePersonDao.faceFaceTypeListTotal(params);
        List<Map<String, Object>> lists = faceTypePersonDao.faceFaceTypeList(params);
        if(lists.size()>0){
            for (Map<String,Object> list:lists){
                if (list.get("personImg")!=null && !list.get("personImg").toString().trim().equals("")){
                    String img=list.get("personImg").toString();
                    String ip=PropertiesUtil.getLocalTomcatImageIp();
                    img=ip+img;
                    list.put("personImg",img);
                }
                if (list.get("alarmLevel")!=null &&!list.get("alarmLevel").toString().trim().equals("")){
                    int level=Integer.parseInt(list.get("alarmLevel").toString());
                    if (level==1){
                        list.put("alarmLevel","高");
                    }else if (level==2){
                        list.put("alarmLevel","中");
                    }else if (level==3){
                        list.put("alarmLevel","低");
                    }else {
                        list.put("alarmLevel","");
                    }
                }
            }
        }

        if(lists!=null&&!lists.isEmpty()) {
            int totalRows=lists.size();
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
            Iterator li = lists.iterator();
            int i=0;
            int end=currentPage*limit;
            while (li.hasNext()) {
                Map tMap = (Map) li.next();
                if (i >= (currentPage - 1) * limit && i < end) {
                    nList.add(tMap);
                }
                i++;
            }
            map.put("totalRows", totalRows);
            map.put("currentPage", currentPage);
            map.put("list", nList);
        }
        else
        {
            map.put("totalRows", 0);
            map.put("currentPage", 1);
            map.put("list", new LinkedList<>());
        }
        return map;
    }
}
