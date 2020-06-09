package com.windaka.suizhi.webapi.util;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.*;

/**
 * @ClassName SqlCreateUtil
 * @Description sql生成工具类
 * @Author lixianhua
 * @Date 2019/12/8 11:19
 * @Version 1.0
 */
public class SqlCreateUtil {

    private static ApplicationContext applicationContext;

    /**
     * 功能描述: 获取sql
     * @auther: lixianhua
     * @date: 2019/12/8 11:27
     * @param:
     * @return:
     */
    public static String getSqlByMybatis(String methodName,Map<String, Object> params){
        SqlSessionFactory sqlSessionFactory = SpringBeanUtil.getBean(SqlSessionFactory.class);
        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(methodName);
        BoundSql boundSql = ms.getBoundSql(params);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<Object> paramValues = getParamValues(params, parameterMappings);
        String sql = boundSql.getSql();
        String execuSql = getExecuteSql(sql, paramValues);
        return execuSql;
    }

    /**
     * 功能描述: 根据参数实体获取sql
     * @auther: lixianhua
     * @date: 2019/12/8 14:15
     * @param:
     * @return:
     */
    public static String getSqlByBean(String methodName,Object object ){
        SqlSessionFactory sqlSessionFactory = SpringBeanUtil.getBean(SqlSessionFactory.class);
        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(methodName);
        Map<String, Object> params = beanToMap(object);
        BoundSql boundSql = ms.getBoundSql(params);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<Object> paramValues = getParamValues(params, parameterMappings);
        String sql = boundSql.getSql();
        String execuSql = getExecuteSql(sql, paramValues);
        return execuSql;
    }

    /**
     * 功能描述: 根据实体类获取Map
     * @auther: lixianhua
     * @date: 2019/12/8 14:14
     * @param:
     * @return:
     */
    public static Map<String, Object> beanToMap(Object obj) {
        Map<String, Object> params = new HashMap<String, Object>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (int i = 0; i < descriptors.length; i++) {
                String name = descriptors[i].getName();
                if (!"class".equals(name)) {
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 功能描述: 获取查询参数值
     * @auther: lixianhua
     * @date: 2019/12/8 11:20
     * @param:
     * @return:
     */
    private static List<Object> getParamValues(Map<String, Object> paramMap, List<ParameterMapping> parameterMappings) {
        if (parameterMappings == null) {
            return new ArrayList<Object>();
        }
        List<Object> paramValues = new ArrayList<Object>();
        for (ParameterMapping pm : parameterMappings) {
            if (pm.getMode() != ParameterMode.OUT) {
                String paramName = pm.getProperty();
                Object paramValue = paramMap.get(paramName);
                paramValues.add(paramValue);
            }
        }
        return paramValues;
    }
    /**
     * 功能描述: 获取可执行sql字符串
     * @auther: lixianhua
     * @date: 2019/12/8 11:21
     * @param: 
     * @return: 
     */
    private static String getExecuteSql(String sql, List<Object> paramValues) {
        while (sql.indexOf("?") != 1 && paramValues.size() > 0) {
            Object paramValue = paramValues.get(0);
            String value = paramValue.toString();
            if (paramValue instanceof String) {
                value = "'" + paramValue.toString() +"'";
            }else if(paramValue instanceof Date || paramValue instanceof Timestamp){
                value = "22";
            }
            sql = sql.replaceFirst("\\?",value);
            paramValues.remove(0);
        }
        return sql;
    }
}
