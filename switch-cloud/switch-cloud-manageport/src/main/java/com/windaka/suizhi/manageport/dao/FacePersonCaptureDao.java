package com.windaka.suizhi.manageport.dao;


import org.apache.ibatis.annotations.Mapper;
import com.windaka.suizhi.manageport.model.FacePersonAttr;


/**
 * 
 * @project: switch-cloud-manageport
 * @Description: 
 * @author: yangkai
 * @date: 2019年12月5日 下午4:38:28
 */
@Mapper
public interface FacePersonCaptureDao {

	int deleteByPrimaryKey(Long id);

    int insert(FacePersonAttr record);

    int insertSelective(FacePersonAttr record);

    FacePersonAttr selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FacePersonAttr record);

    int updateByPrimaryKey(FacePersonAttr record);

    /**
     * 功能描述: 获取最大人员抓拍主键
     * @auther: lixianhua
     * @date: 2019/12/7 19:17
     * @param:
     * @return:
     */
    Integer getMaxId();
}
