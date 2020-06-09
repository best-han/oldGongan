package com.windaka.suizhi.webapi.util;

import com.windaka.suizhi.api.common.ReturnConstants;
import com.windaka.suizhi.api.user.LoginAppUser;
import com.windaka.suizhi.webapi.dao.HTUserXQDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 统计类接口公用类
 */
public class TJUtil {

    @Autowired
    private HTUserXQDao htUserXQDao;




//    /**
//     * 根据当前用户、传参，进行权限验证
//     * @param params
//     * @throws OssRenderException
//     */
//    public static void checkAuth(Map<String, Object> params)throws OssRenderException {
//        String wyCode = (String) params.get("wyCode");
//        String xqCode = (String) params.get("xqCode");
//
//        //验证当前用户查询权限
//        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
//        String userID = loginAppUser.getUserId();
//        String sysLevel = loginAppUser.getSysLevel();   //用户级别(1：超管，2：物业，3：小区)
//
//        if(sysLevel == null || !sysLevel.equals("1")){
//            String xqCodes = htUserXQDao.queryXQCodeByUserId(userID);
//            String wyCodes = "";
//            if(StringUtils.isBlank(xqCodes)){
//                throw new OssRenderException(ReturnConstants.CODE_FAILED,"该用户未关联查看小区");
//            }else {
//                if (!StringUtils.isBlank(xqCode) && !xqCodes.contains("'" + xqCode + "'")) {
//                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "无权限查看该小区数据");
//                } else if (StringUtils.isBlank(xqCode)) {
//                    params.put("xqCode", xqCodes);
//                }
//
////                wyCodes = xqWyRelationDao.queryWYCodesByXQCodes(xqCodes);
////                if ((!StringUtils.isBlank(wyCode) && !wyCodes.contains("'" + wyCode + "'")) || StringUtils.isBlank(wyCodes)) {
////                    throw new OssRenderException(ReturnConstants.CODE_FAILED, "无权限查看该物业数据");
////                } else if (StringUtils.isBlank(wyCode) && !StringUtils.isBlank(wyCodes)) {
////                    params.put("wyCode", wyCodes);
////                }
//            }
//        }
//    }

}
