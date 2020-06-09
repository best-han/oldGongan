package com.windaka.suizhi.upcapture.util;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.text.DateFormat;
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
     * 功能描述: 根据参数map获取sql
     * @auther: lixianhua
     * @date: 2019/12/8 11:27
     * @param:
     * @return:
     */
    public static String getSqlByMybatis(String methodName,Map<String, Object> params ){
        SqlSessionFactory sqlSessionFactory = SpringBeanUtil.getBean(SqlSessionFactory.class);
        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(methodName);
        BoundSql boundSql = ms.getBoundSql(params);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<Object> paramValues = getParamValues(params, parameterMappings,"");
        String sql = boundSql.getSql();
        String execuSql = getExecuteSql(sql, paramValues);
        return execuSql;
    }

    /**
     * 功能描述: 根据map获取sql(map中包含集合)
     * @auther: lixianhua
     * @date: 2019/12/11 16:31
     * @param:
     * @return:
     */
    public static String getSqlListByMybatis(String methodName,Map<String, Object> params ,String prefix){
        SqlSessionFactory sqlSessionFactory = SpringBeanUtil.getBean(SqlSessionFactory.class);
        MappedStatement ms = sqlSessionFactory.getConfiguration().getMappedStatement(methodName);
        BoundSql boundSql = ms.getBoundSql(params);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<Object> paramValues = getParamValues(params, parameterMappings, prefix);
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
        List<Object> paramValues = getParamValues(params, parameterMappings,"");
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
    private static List<Object> getParamValues(Map<String, Object> paramMap, List<ParameterMapping> parameterMappings,String prefix) {
        if (parameterMappings == null) {
            return new ArrayList<Object>();
        }
        List<Object> paramValues = new ArrayList<Object>();
        if (null!=paramMap.get("list")){
            // 包含集合
            try {
                List<Map<String,Object>> list = ( List<Map<String,Object>>)paramMap.get("list");
                Map<String,Integer> countMap = new HashMap<>();
                int i =0;
                for (ParameterMapping pm : parameterMappings) {
                    if (i==52)
                    {
                        System.out.println("111");
                    }
                    if (pm.getMode() != ParameterMode.OUT) {
                        String paramName = pm.getProperty();
                        if(paramName.contains(".")){
                            paramName = paramName.split("\\.")[1];
                        }
                        if(countMap.containsKey(paramName)){
                            Object paramValue =   list.get(countMap.get(paramName)).get(paramName);
                            paramValues.add(paramValue);
                            countMap.put(paramName,countMap.get(paramName)+1);
                        }else{
                            Object paramValue =   list.get(0).get(paramName);
                            paramValues.add(paramValue);
                            countMap.put(paramName,1);
                        }
    //                    Object paramValue = paramMap.get(paramName);
    //                    paramValues.add(paramValue);
                    }
                   i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("123");
        }else{
            // 不包含集合
            for (ParameterMapping pm : parameterMappings) {
                if (pm.getMode() != ParameterMode.OUT) {
                    String paramName = pm.getProperty();
                    String fullName = paramName;
                    if(paramName.contains(".")){
                        paramName = paramName.split("\\.")[1];
                    }
                    Object paramValue = paramMap.get(paramName);
                    paramValues.add(paramValue);
                }
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
        if (sql.indexOf("?") != 1 && paramValues.size() > 0) {
            for(int i = 0;i<paramValues.size();i++){
                Object paramValue = paramValues.get(i);
                if (null != paramValue) {
                    String value = paramValue.toString();
                    if (paramValue instanceof String) {
                        value = "'" + paramValue.toString() + "'";
                    } else if (paramValue instanceof Date || paramValue instanceof Timestamp) {
                        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                                DateFormat.DEFAULT, Locale.CHINA);
                        value ="'" +formatter.format( (Date)paramValue) + "'";
                    }
                    sql = sql.replaceFirst("\\?", value);
//                    paramValues.remove(0);
                }else{
                    sql = sql.replaceFirst("\\?", "null");
                }
            }
        }
        // 去除问号
//        if (sql.contains("?")){
//            sql = sql.replace("?","null");
//        }
        return sql;
    }
}
