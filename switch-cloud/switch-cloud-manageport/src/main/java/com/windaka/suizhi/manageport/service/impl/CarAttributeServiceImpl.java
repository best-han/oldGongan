package com.windaka.suizhi.manageport.service.impl;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.common.constants.CommonConstants;
import com.windaka.suizhi.common.exception.OssRenderException;
import com.windaka.suizhi.common.utils.FileUploadUtil;
import com.windaka.suizhi.common.utils.PicUtil;
import com.windaka.suizhi.common.utils.SaveUtil;
import com.windaka.suizhi.common.utils.SqlFileUtil;
import com.windaka.suizhi.manageport.dao.AbnormalRecordDao;
import com.windaka.suizhi.manageport.dao.CarAttributeDao;
import com.windaka.suizhi.manageport.service.CarAttributeService;
import com.windaka.suizhi.manageport.util.SqlCreateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CarAttributeServiceImpl implements CarAttributeService {

    @Autowired
    CarAttributeDao carAttributeDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCarAttributes(Map<String, Object> params) throws OssRenderException, IOException {
        String xqCode=(String)params.get("xqCode");
        if(StringUtils.isBlank(xqCode)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"小区Code不能为空");
        }
        List<Map<String,Object>> list=(List<Map<String,Object>>) params.get("list");
        if(CollectionUtils.isEmpty(list)){
            throw new OssRenderException(ReturnConstants.CODE_FAILED,"数据为空");
        }
        // 遍历查看base64图片是否存在
        for(Map<String,Object> map :list){
            if (null != map.get("base64Img") && org.apache.commons.lang3.StringUtils.isNotBlank(map.get("base64Img").toString())) {
                byte[] byteArr= PicUtil.stringToInputStream(map.get("base64Img").toString());
                // 根据最大主键
                Integer id = carAttributeDao.getMaxId();
                String fileName = PicUtil.getPicName("car_attribute", id==null?1:(id+1));
                //封装访问路径：年/月/日
                Date date=new Date();
                //图片放入打包路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_IMAGE_FILE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                // 图片放入访问路径
                FileUploadUtil.inputStreamToLocalFile(byteArr,
                        CommonConstants.LOCAL_PROJECT_IMAGE_PATH + File.separator +PicUtil.getPicRelativePath(date), fileName);
                map.put("base64Img", PicUtil.getPicRelativePath(date)+ fileName);
            }
        }
        carAttributeDao.saveCarAttributes(xqCode,list);
        //String sql= SqlCreateUtil.getSqlByMybatis(CarAttributeDao.class.getName()+".saveCarAttributes",list);
        //SqlFileUtil.InsertSqlToFile(sql);
        String []keyNames={"xqCode","manageId","captureTime","captureDeviceId","captureImg","carNum","carColor","carType","carBrand","base64Img"};
        String sqlContentFront="insert into car_attribute (xq_code,manage_id," +
                "`capture_time` ," +
                "`capture_device_id` ," +
                "`capture_img` ," +
                "`car_num` ," +
                "`car_color` ," +
                "`car_type` ," +
                "`car_brand`" +
                "`base64_img`" +
                ") " +
                "values";
        SaveUtil.listSqlSave(keyNames,sqlContentFront,xqCode,list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCarAttribute(Map<String, Object> params) throws OssRenderException, IOException {
        carAttributeDao.updateCarAttribute(params);
        String sql= SqlCreateUtil.getSqlByMybatis(CarAttributeDao.class.getName()+".updateCarAttribute",params);
        SqlFileUtil.InsertSqlToFile(sql);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCarAttribute(Map<String, Object> params) throws OssRenderException, IOException {
        carAttributeDao.deleteCarAttribute(params);
        String sql= SqlCreateUtil.getSqlByMybatis(CarAttributeDao.class.getName()+".deleteCarAttribute",params);
        SqlFileUtil.InsertSqlToFile(sql);
    }


}
