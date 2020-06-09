package com.windaka.suizhi.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
@Slf4j
public class SaveUtil {
    public static void listSqlSave(String []keyNames, String sqlContentFront, String xqCode,List<Map<String, Object>> list) throws IOException {
        String []keyValues=new String[keyNames.length];
        Iterator listI=list.iterator();
        int n=keyNames.length;
        String sqlContent;
        while (listI.hasNext())
        {
            Map tMap=(Map)listI.next();
            for (int i=0;i<n;i++)
            {
                if(keyNames[i].equals("xqCode"))
                {
                    if(xqCode==null||xqCode.trim().equals(""))
                        keyValues[i]=null;
                    else
                        keyValues[i]="'"+xqCode+"'";
                }
                else
                    keyValues[i]=SqlFileUtil.keyAddValue(tMap,keyNames[i]);
            }
            sqlContent=sqlContentFront;
            sqlContent+=" (";
            for(int i=0;i<n;i++)
            {
                if(i!=n-1)
                    sqlContent+=keyValues[i]+",";
                else
                    sqlContent+=keyValues[i];
            }
            sqlContent+=")";
            if(sqlContent.contains("face_information")){
                log.info("************人脸日志："+sqlContent);
            }
            SqlFileUtil.InsertSqlToFile(sqlContent);
        }
    }
}
