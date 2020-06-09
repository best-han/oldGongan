package com.windaka.suizhi.common.utils;

import com.windaka.suizhi.common.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Slf4j
public class SqlFileUtil {
    public static String filePath="F:/switch_cloud_file";
    public static String fileName="switch_cloud.sql";
    public static String fileNamePrefix="switch_cloud";
    /**
     * 拼接sql语句 hjt
     * @param sqlStr
     * @return
     */
    public static String GerenateSql(String sqlStr){
        if(!sqlStr.substring(sqlStr.length()-1,sqlStr.length()).equals(";")){
            sqlStr=sqlStr+";";
        }
        sqlStr=sqlStr+"commit;";//oracle拼接提交事务语句，若为mysql则去掉
        return sqlStr;
    }

    /**
     * 将执行语句写入sql  hjt
     * @param sqlStr
     * @return
     */
    public static boolean InsertSqlToFile(String sqlStr) throws IOException{
        File dir=new File(filePath);
        if(!dir.exists()){
            dir.mkdir();
        }
        int i=TimesUtil.getSecond(new Date());
        i=i+10;
        String s=String.valueOf(i).substring(0,1);//将60秒分为6块，取秒前一位：1 2 3 4 5 6
//        File sqlFile=new File(filePath+"/"+fileName);//测试
        File sqlFile=new File(CommonConstants.LOCAL_SQL_PATH+File.separator+fileNamePrefix+"_"+s+".sql");//正式
        log.info("**************************写入sql文件"+sqlFile.getPath());
        FileWriter writer=null;
        try{
            if(!sqlFile.exists()){
                sqlFile.createNewFile();
            }
            sqlStr=GerenateSql(sqlStr);
            writer=new FileWriter(sqlFile,true);
            writer.append(sqlStr);
            writer.flush();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }finally {
            if(null != writer){
                writer.close();
            }
        }
        return true;
    }
    /**
     * 功能描述: 将sql写入指定目录下文件中
     * @auther: lixianhua
     * @date: 2019/12/13 22:03
     * @param:
     * @return:
     */
    public static boolean writeSqlToFile(String sqlStr,String path,String fileName) throws IOException{
        File dir=new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File sqlFile=new File(path+"/"+fileName);
        FileWriter writer=null;
        try{
            if(!sqlFile.exists()){
                sqlFile.createNewFile();
            }
            sqlStr=GerenateSql(sqlStr);
            writer=new FileWriter(sqlFile,true);
            writer.append(sqlStr);
            writer.flush();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }finally {
            if(null != writer){
                writer.close();
            }
        }
        return true;
    }

    //key值初始化null
    public static void keyAddNull(String []keyNames){
        for(String keyName:keyNames){
            keyName=null;
        }
    }

    //为key值赋值
    public static String keyAddValue(Map<String,Object> params,String key){
        String str=null;
        if(params.get(key)!=null&&!params.get(key).toString().trim().equals("")){
            str ="'"+params.get(key).toString()+"'";
        }
        return str;
    }
    public static String listKeyAddValue(List<Map<String, Object>> params, String key){
        String str=null;
        for (Map<String,Object> param:params){
            if(param.get(key)!=null&&!param.get(key).toString().trim().equals("")){
                str =param.get(key).toString();
            }
        }

        return str;
    }



    public static void main(String[] args) throws Exception{
       // InsertSqlToFile("nihao");
       /* Calendar c=new GregorianCalendar();
        Date date=new Date();
        c.setTime(date);
        log.info(TimesUtil.getSecond(date)+"");
        c.add(Calendar.SECOND, -10);
        log.info(TimesUtil.getSecond(c.getTime())+"");*/
       while(true){
           Date date=new Date();
           log.info((TimesUtil.getSecond(date)+"").substring(0,1));
           Thread.sleep(1000);
       }

    }



}
