package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.Page;
import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.*;
import com.windaka.suizhi.manageport.dao.HyPersonDataDao;
import com.windaka.suizhi.manageport.dao.PersonDao;
import com.windaka.suizhi.manageport.dao.XqPersonRelationDao;
import com.windaka.suizhi.manageport.model.Person;
import com.windaka.suizhi.manageport.service.FastdfsService;
import com.windaka.suizhi.manageport.service.PersonService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private XqPersonRelationDao xqPersonRelationDao;

    @Autowired
    private HyPersonDataDao hyPersonDataDao;

    @Autowired
    private FastdfsService fastdfsService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void savePersons(String xqCode, List<Map<String, Object>> persons) throws OssRenderException {


        //添加角色
        if (xqCode == null || StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少xqCode参数");
        }
        for (int i = 0; i < persons.size(); i++) {
            Map<String, Object> person = persons.get(i);
            String manageId = (String) person.get("manageId");
            if (manageId == null || StringUtils.isBlank(manageId)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少manageId参数");
            }
            String createTime = (String) person.get("createDate");
            if (createTime == null && StringUtils.isBlank(createTime)) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少createDate参数");
            }
            // 清洗数据

            String personIdentityId = (String) person.get("personIdentityId");
            if (personIdentityId != null) {
                if (StringUtils.isEmpty(personIdentityId)) {
                    person.remove("personIdentityId");
                } else {
                    Integer personIdentityIdTemp = Integer.parseInt(personIdentityId);
                    person.put("personIdentityId", personIdentityIdTemp);
                }
            }

            String updateDate = (String) person.get("updateDate");
            if (updateDate != null) {
                if (StringUtils.isEmpty(updateDate)) {
                    person.remove("updateDate");
                } else {
                    Date updateDateTemp = TimesUtil.stringToDate(updateDate, TimesUtil.DATE_TIME_FORMAT);
                    if (updateDateTemp != null && !updateDateTemp.equals("")) {
                        person.put("updateDate", updateDateTemp);
                    }
                }
            }
            String extendD1 = (String) person.get("extendD1");
            if (extendD1 != null) {
                if (StringUtils.isEmpty(extendD1)) {
                    person.remove("extendD1");
                } else {
                    Date extendD1Temp = TimesUtil.stringToDate(extendD1);
                    if (extendD1Temp != null && !extendD1Temp.equals("")) {
                        person.put("extendD1", extendD1Temp);
                    }
                }
            }
            String extendD2 = (String) person.get("extendD2");
            if (extendD2 != null) {
                if (StringUtils.isEmpty(extendD2)) {
                    person.remove("extendD2");
                } else {
                    Date extendD2Temp = TimesUtil.stringToDate(extendD2);
                    if (extendD2Temp != null && !extendD2Temp.equals("")) {
                        person.put("extendD2", extendD2Temp);
                    }
                }
            }

            String extendD3 = (String) person.get("extendD3");
            if (extendD3 != null) {
                if (StringUtils.isEmpty(extendD3)) {
                    person.remove("extendD3");
                } else {
                    Date extendD3Temp = TimesUtil.stringToDate(extendD3);
                    if (extendD3Temp != null && !extendD3Temp.equals("")) {
                        person.put("extendD3", extendD3Temp);
                    }
                }
            }
            String extendD4 = (String) person.get("extendD4");
            if (extendD4 != null) {
                if (StringUtils.isEmpty(extendD4)) {
                    person.remove("extendD4");
                } else {
                    Date extendD4Temp = TimesUtil.stringToDate(extendD4);
                    if (extendD4Temp != null && !extendD4Temp.equals("")) {
                        person.put("extendD4", extendD4Temp);
                    }
                }
            }

            String extendI1 = (String) person.get("extendI1");
            if (extendI1 != null) {
                if (StringUtils.isEmpty(extendI1)) {
                    person.remove("extendI1");
                } else {
                    BigDecimal extendI1Temp = BigDecimal.valueOf(Double.parseDouble(extendI1));
                    person.put("extendI1", extendI1Temp);
                }
            }
            String extendI2 = (String) person.get("extendI2");
            if (extendI2 != null) {
                if (StringUtils.isEmpty(extendI2)) {
                    person.remove("extendI2");
                } else {
                    BigDecimal extendI2Temp = BigDecimal.valueOf(Double.parseDouble(extendI2));
                    person.put("extendI2", extendI2Temp);
                }
            }
            String extendI3 = (String) person.get("extendI3");
            if (extendI3 != null) {
                if (StringUtils.isEmpty(extendI3)) {
                    person.remove("extendI3");
                } else {
                    BigDecimal extendI3Temp = BigDecimal.valueOf(Double.parseDouble(extendI3));
                    person.put("extendI3", extendI3Temp);
                }
            }
            String extendI4 = (String) person.get("extendI4");
            if (extendI4 != null) {
                if (StringUtils.isEmpty(extendI4)) {
                    person.remove("extendI4");
                } else {
                    BigDecimal extendI4Temp = BigDecimal.valueOf(Double.parseDouble(extendI4));
                    person.put("extendI4", extendI4Temp);
                }
            }
            String extendF1 = (String) person.get("extendF1");
            if (extendF1 != null) {
                if (StringUtils.isEmpty(extendF1)) {
                    person.remove("extendF1");
                } else {
                    BigDecimal extendF1Temp = BigDecimal.valueOf(Double.parseDouble(extendF1));
                    person.put("extendF1", extendF1Temp);
                }
            }
            String extendF2 = (String) person.get("extendF2");
            if (extendF2 != null) {
                if (StringUtils.isEmpty(extendF2)) {
                    person.remove("extendF2");
                } else {
                    BigDecimal extendF2Temp = BigDecimal.valueOf(Double.parseDouble(extendF2));
                    person.put("extendF2", extendF2Temp);
                }
            }
            String extendF3 = (String) person.get("extendF3");
            if (extendF3 != null) {
                if (StringUtils.isEmpty(extendF3)) {
                    person.remove("extendF3");
                } else {
                    BigDecimal extendF3Temp = BigDecimal.valueOf(Double.parseDouble(extendF3));
                    person.put("extendF3", extendF3Temp);
                }
            }
            String extendF4 = (String) person.get("extendF4");
            if (extendF4 != null) {
                if (StringUtils.isEmpty(extendF4)) {
                    person.remove("extendF4");
                } else {
                    BigDecimal extendF4Temp = BigDecimal.valueOf(Double.parseDouble(extendF4));
                    person.put("extendF4", extendF4Temp);
                }
            }
            //保存图片
//            if (person.get("extendS4") != null && !((String) person.get("extendS4")).equals("")) {
//                // 上传图片到fastdfs上
//                Map<String, Object> map = fastdfsService.uploadFile(PicUtil.stringToInputStream((String) person.get("extendS4")), "jpg");
//                if (null == map) {
//                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "图片上传异常，保存失败");
//                }
//                person.put("extendS4", map.get("filePath"));
//            }

            if (person.get("extendS4") != null && !((String) person.get("extendS4")).equals("")) {
                byte[] byteArr = PicUtil.stringToInputStream(person.get("extendS4").toString());
                // 获取最大主键
                String id = personDao.maxId();
                String fileName = PicUtil.getPicName("zs_person_info", (null != id ? (Integer.parseInt(id) +1 ): 1));
                // 图片放入打包路径
             /*   FileUploadUtil.inputStreamToLocalFile(byteArr, CommonConstants.LOCAL_IMAGE_FILE_PATH, fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,  CommonConstants.LOCAL_PROJECT_IMAGE_PATH, fileName);
                person.put("extendS4", fileName);*/

                //封装访问路径：年/月/日
                Date date=new Date();
                //人的图片需永久存储，以此路径防止定时任务清除
                String personPicRelativePath="person"+ File.separator +PicUtil.getPicRelativePath(date);
                // 图片放入打包路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +personPicRelativePath, fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +personPicRelativePath, fileName);
                person.put("extendS4", personPicRelativePath+ fileName);
            }
            int s = 0;
            try {
                s = personDao.savePerson(person);
                String savePerson = SqlCreateUtil.getSqlByBean(PersonDao.class.getName() + ".savePerson", person);
                SqlFileUtil.InsertSqlToFile(savePerson);
            } catch (Exception e) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败:" + e.getMessage());
            }
            if (s == 0) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
            } else {//管理端：通过房间表间接绑定--有保安等没有房产的会关联不上，所以还是需要此表关联
                if (xqPersonRelationDao.saveXqPersonRelation(xqCode, manageId) == 0) {
                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "批量保存人员失败");
                }
            }

            //新增人员去人脸库做比对。。。。。  外网暂不提供此功能！！！！！


        }

    }

    /**
     * 将表中记录去和人脸服务器犯罪人员比对 hjt
     * @param
     */
  /*  public boolean personImageToFaceCrime(Person temp){
        boolean result=false;
        // 头像图片
        File file=new File(PropertiesUtil.getLocalTomcatImageIp() + temp.getExtendS4());
        if(file.exists()) {//若图片存在，则推送  hjt
            String strBase64 = null;
            try {
                InputStream in = new FileInputStream(file);
                // in.available()返回文件的字节长度
                byte[] bytes = new byte[in.available()];
                // 将文件中的内容读入到数组中
                in.read(bytes);
                strBase64 = new BASE64Encoder().encode(bytes);//将字节流数组转换为字符串
                in.close();
                //增加人脸特征给人脸服务器以便其提取功能
                int id=temp.getId();
                Map mapFaceContrast = FaceContrastUtil.addFace(id, strBase64, "2");
                if (StringUtils.isBlank((String)mapFaceContrast.get("id"))) {
                    log.info("PersonInfoIsCrimeTask人脸特征存入失败");
                }else{// if("noId".equals(mapFaceContrast.get("result")))
                    Map<String,Object> params=new HashMap<>();
                    params.put("faceTypePersonId",mapFaceContrast.get("id"));
                    params.put("id",id);
                    personDao.updatePsersonFaceTypePersonId(params);
                }
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            result=true;//只要图片存在就是成功比对过了，将其从队列中移除
        }
        return result;
    }*/

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePerson(Map<String, Object> person) throws OssRenderException, IOException {
        //修改小区人员
        String manageId = (String) person.get("manageId");
        String xqCode = (String) person.get("xqCode");
        if (manageId == null || manageId.equals("")) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少manageId参数");
        }
        if (xqCode == null || StringUtils.isBlank(xqCode)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少xqCode参数");
        }

        String personIdentityId = (String) person.get("personIdentityId");
        if (personIdentityId != null) {
            if (StringUtils.isEmpty(personIdentityId)) {
                person.remove("personIdentityId");
            } else {
                Integer personIdentityIdTemp = Integer.parseInt(personIdentityId);
                person.put("personIdentityId", personIdentityIdTemp);
            }
        }

        String updateDate = (String) person.get("updateDate");
        if (updateDate != null) {
            if (StringUtils.isEmpty(updateDate)) {
                person.remove("updateDate");
            } else {
                Date updateDateTemp = TimesUtil.stringToDate(updateDate);
                if (updateDateTemp != null && !updateDateTemp.equals("")) {
                    person.put("updateDate", updateDateTemp);
                }
            }
        }
        String extendD1 = (String) person.get("extendD1");
        if (extendD1 != null) {
            if (StringUtils.isEmpty(extendD1)) {
                person.remove("extendD1");
            } else {
                Date extendD1Temp = TimesUtil.stringToDate(extendD1);
                if (extendD1Temp != null && !extendD1Temp.equals("")) {
                    person.put("extendD1", extendD1Temp);
                }
            }
        }
        String extendD2 = (String) person.get("extendD2");
        if (extendD2 != null) {
            if (StringUtils.isEmpty(extendD2)) {
                person.remove("extendD2");
            } else {
                Date extendD2Temp = TimesUtil.stringToDate(extendD2);
                if (extendD2Temp != null && !extendD2Temp.equals("")) {
                    person.put("extendD2", extendD2Temp);
                }
            }
        }

        String extendD3 = (String) person.get("extendD3");
        if (extendD3 != null) {
            if (StringUtils.isEmpty(extendD3)) {
                person.remove("extendD3");
            } else {
                Date extendD3Temp = TimesUtil.stringToDate(extendD3);
                if (extendD3Temp != null && !extendD3Temp.equals("")) {
                    person.put("extendD3", extendD3Temp);
                }
            }
        }
        String extendD4 = (String) person.get("extendD4");
        if (extendD4 != null) {
            if (StringUtils.isEmpty(extendD4)) {
                person.remove("extendD4");
            } else {
                Date extendD4Temp = TimesUtil.stringToDate(extendD4);
                if (extendD4Temp != null && !extendD4Temp.equals("")) {
                    person.put("extendD4", extendD4Temp);
                }
            }
        }

        String extendI1 = (String) person.get("extendI1");
        if (extendI1 != null) {
            if (StringUtils.isEmpty(extendI1)) {
                person.remove("extendI1");
            } else {
                BigDecimal extendI1Temp = BigDecimal.valueOf(Double.parseDouble(extendI1));
                person.put("extendI1", extendI1Temp);
            }
        }
        String extendI2 = (String) person.get("extendI2");
        if (extendI2 != null) {
            if (StringUtils.isEmpty(extendI2)) {
                person.remove("extendI2");
            } else {
                BigDecimal extendI2Temp = BigDecimal.valueOf(Double.parseDouble(extendI2));
                person.put("extendI2", extendI2Temp);
            }
        }
        String extendI3 = (String) person.get("extendI3");
        if (extendI3 != null) {
            if (StringUtils.isEmpty(extendI3)) {
                person.remove("extendI3");
            } else {
                BigDecimal extendI3Temp = BigDecimal.valueOf(Double.parseDouble(extendI3));
                person.put("extendI3", extendI3Temp);
            }
        }
        String extendI4 = (String) person.get("extendI4");
        if (extendI4 != null) {
            if (StringUtils.isEmpty(extendI4)) {
                person.remove("extendI4");
            } else {
                BigDecimal extendI4Temp = BigDecimal.valueOf(Double.parseDouble(extendI4));
                person.put("extendI4", extendI4Temp);
            }
        }
        String extendF1 = (String) person.get("extendF1");
        if (extendF1 != null) {
            if (StringUtils.isEmpty(extendF1)) {
                person.remove("extendF1");
            } else {
                BigDecimal extendF1Temp = BigDecimal.valueOf(Double.parseDouble(extendF1));
                person.put("extendF1", extendF1Temp);
            }
        }
        String extendF2 = (String) person.get("extendF2");
        if (extendF2 != null) {
            if (StringUtils.isEmpty(extendF2)) {
                person.remove("extendF2");
            } else {
                BigDecimal extendF2Temp = BigDecimal.valueOf(Double.parseDouble(extendF2));
                person.put("extendF2", extendF2Temp);
            }
        }
        String extendF3 = (String) person.get("extendF3");
        if (extendF3 != null) {
            if (StringUtils.isEmpty(extendF3)) {
                person.remove("extendF3");
            } else {
                BigDecimal extendF3Temp = BigDecimal.valueOf(Double.parseDouble(extendF3));
                person.put("extendF3", extendF3Temp);
            }
        }
        String extendF4 = (String) person.get("extendF4");
        if (extendF4 != null) {
            if (StringUtils.isEmpty(extendF4)) {
                person.remove("extendF4");
            } else {
                BigDecimal extendF4Temp = BigDecimal.valueOf(Double.parseDouble(extendF4));
                person.put("extendF4", extendF4Temp);
            }
        }

        /*if (person.get("extendS4") != null && !((String) person.get("extendS4")).equals("")) {//保存图片

            //String xqCode=xqPersonRelationDao.queryXqCodeByPersonCode(code);

            person.put("xqCode", xqCode);
            person.put("manageId", manageId);
            person.put("uploadFile", person.get("extendS4"));
            try {
                String uploadPath = FileUploadUtil.uploadFile(person);
                if (uploadPath == null && uploadPath.equals("")) {
                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "修改人员图片失败3");
                } else {
                    person.put("extendS4", uploadPath);
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "修改人员图片失败2");
            } catch (IOException e) {
                e.printStackTrace();
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "修改人员图片失败1");
            }
        }*/

        if (person.get("extendS4") != null && !((String) person.get("extendS4")).equals("")) {
            byte[] byteArr = PicUtil.stringToInputStream(person.get("extendS4").toString());
            // 获取最大主键
            String id = personDao.maxId();
            String fileName = PicUtil.getPicName("zs_person_info", (null != id ? (Integer.parseInt(id) +1 ): 1));
            //封装访问路径：年/月/日
            Date date=new Date();
            //人的图片需永久存储，以此路径防止定时任务清除
            String personPicRelativePath="person"+ File.separator +PicUtil.getPicRelativePath(date);
            // 图片放入打包路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,
                    CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +personPicRelativePath, fileName);
            // 图片放入访问路径
            FileUploadUtil.inputStreamToLocalFile(byteArr,
                    CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +personPicRelativePath, fileName);
            person.put("extendS4", personPicRelativePath+ fileName);
        }


        int i = 0;
        try {
            i = personDao.updatePerson(person);
            String updatePerson = SqlCreateUtil.getSqlByBean(PersonDao.class.getName() + ".updatePerson", person);
            SqlFileUtil.InsertSqlToFile(updatePerson);

        } catch (Exception e) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "没有找到要修改的人员");
        }
        if (i == 0) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "修改小区人员失败");
        }

        if(person.get("extendS6").equals("1")){//外国人不入海原
            //修改Hy 人员信息 根据paperNum
            String paperNum=null;
            paperNum=person.get("paperNumber").toString();
            if (paperNum == null || paperNum.equals("")) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "Hy-----缺少paperNum参数");
            }
            Map<String,Object> hyPerson=new HashMap<String,Object>();
            hyPerson.put("name",person.get("name"));
            hyPerson.put("paperType",person.get("paperTypeName"));
            hyPerson.put("paperNumber",person.get("paperNumber"));
            hyPerson.put("sex",person.get("sexName"));
            hyPerson.put("birthday",person.get("birthday"));
            hyPerson.put("nation",person.get("nationName"));
            hyPerson.put("address",person.get("orgionPlace"));//
            hyPerson.put("phone",person.get("phone"));
//        hyPerson.put("typeName",person.get("personIdentityName"));
//        hyPerson.put("addressCode",person.get("addr_code"));//标准地址编码
            hyPerson.put("marriage",person.get("marriageStatusName"));
            hyPerson.put("education",person.get("educationName"));
            hyPerson.put("political",person.get("politicalStatusName"));
            hyPerson.put("fulladdress",person.get("address"));
            int s=0;
            s=hyPersonDataDao.updateHyPerson(hyPerson);
            if (s< 1) {
                throw new OssRenderException(ReturnConstants.CODE_FAILED, "Hy------数据更新异常");
            }

            String[] colNames={"name","paperTypeName","paperNumber","sexName","birthday","nationName","orgionPlace","phone","marriageStatusName","educationName","politicalStatusName","address"};
            String[] colValues = new String[colNames.length];
            int n=colNames.length;
            for(int j=0;j<n;j++)
            {
                colValues[j]=SqlFileUtil.keyAddValue(person,colNames[j]);
            }
            String sql="update hy_person_data  SET name="+colValues[0]+",paper_type="+colValues[1]+",paper_num="+colValues[2]+",sex="+colValues[3]+",birthday="+colValues[4]+",nation="+colValues[5]+",address="+colValues[6]+",phone="+colValues[7]+",\n" +
                    "marriage="+colValues[8]+",education="+colValues[9]+",political="+colValues[10]+",fulladdress="+colValues[11]+",upd_time=now()\n" +
                    "        where paper_num = "+colValues[2];
            SqlFileUtil.InsertSqlToFile(sql);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePerson(String manageId) throws OssRenderException {
        int i = 0;
        try {
            i = personDao.deletePerson(manageId);
            String deletePerson = "delete from zs_person_info where manage_id = " + manageId;
            SqlFileUtil.InsertSqlToFile(deletePerson);
        } catch (Exception e) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "删除小区人员失败");
        }
        if (i == 0) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "没有要删除的人");
        } else {
            /*if(xqPersonRelationDao.deleteXqPersonRelation(personCode)==0){
                throw new OssRenderException(ReturnConstants.CODE_FAILED,"删除小区人员失败");
            }*/
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> queryPerson(String personCode) throws OssRenderException {
        //查看小区人员
        Map<String, Object> persons = personDao.queryPerson(personCode);
        if (persons == null) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "查无此人");
        } else {
            return persons;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Page<Map<String, Object>> queryPersonList(Map<String, Object> params) throws OssRenderException {

        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        if (page == null || page.equals("")) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少page参数");
        }
        if (limit == null || limit.equals("")) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "缺少limit参数");
        }
        int totalRows = personDao.totalRows(params);
        List<Map<String, Object>> list = Collections.emptyList();
        if (totalRows > 0) {
            PageUtil.pageParamConver(params, true);
            list = personDao.queryPersonList(params);
        }
        return new Page<>(totalRows, MapUtils.getInteger(params, PageUtil.PAGE), list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String queryPersonCode(Map<String, Object> params) throws OssRenderException {
        String image = (String) params.get("txImg");
        if (StringUtils.isBlank(image)) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "Base64图片不能为空");
        }

        String contrastValue = (String) params.get("contrastValue");
        if (contrastValue == null) {
            throw new OssRenderException(ReturnConstants.CODE_FAILED, "图片相似度不能为空");
        }

        String personCode = personDao.queryPersonCode(image);
        return personCode;
    }

}
