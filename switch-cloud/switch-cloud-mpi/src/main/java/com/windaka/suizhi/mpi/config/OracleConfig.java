package com.windaka.suizhi.mpi.config;

import com.windaka.suizhi.common.utils.TimesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OracleConfig
 * @Description oracle连接配置类
 * @Author lixianhua
 * @Date 2020/1/10 8:28
 * @Version 1.0
 */
@Slf4j
public class OracleConfig {
    // oracle的驱动
    static String driverClass = "oracle.jdbc.driver.OracleDriver";
    // 连接oracle路径
//    static String url = "jdbc:mysql://10.10.6.54:3306/switch_cloud?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
    static String url = "jdbc:oracle:thin:@10.49.236.216:1521:orcl";
    // 数据库用户名
    static String user = "renxiang";
    // 用户登录密码
    static String password = "renxiang";

    public static Connection getconn() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        // 建立驱动
        Class.forName(driverClass);
        // 驱动成功后进行连接
        conn = DriverManager.getConnection(url, user, password);
        log.info("orcale连接成功");
        return conn;
    }

    /**
     * 功能描述: 查询海博视图信息
     *
     * @auther: lixianhua
     * @date: 2020/1/11 8:30
     * @param:
     * @return:
     */
    public static List<Map<String, String>> query(Map<String, Object> condition) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        // 身份证去重条件
        String repeatStr = "";
        List<Map<String, String>> list = new ArrayList<>();
        if (null != condition.get("idNoStr") && StringUtils.isNotBlank(condition.get("idNoStr").toString())) {
            repeatStr = "and CREDENTIAL_NUMBER not in( " + condition.get("idNoStr").toString() + ") ";
//            repeatStr = "and paper_number not in( "+ condition.get("idNoStr").toString()+") ";
        }
        // 获取连接对象hao
        conn = getconn();
        // 创建statement类对象，用来执行SQL语句
        st = conn.createStatement();
        String today = TimesUtil.getServerDateTime(10, new Date()) + " 00:00:00";
//        String today ="2019-12-01 00:00:00";
        // 创建sql查询语句
        String sqlStr =
                "select * from analysis_warning_shzy where CAMERANAME like '" + condition.get("location").toString() + "' and to_char(TIME) > '" + today + "' and IMPORTANT_VALUE is not null " + repeatStr;
        log.info(sqlStr);
        // 执行sql语句并返回结果
        rs = st.executeQuery(sqlStr);
        // 证件号码不重复
        List<String> idNoList = new ArrayList<>();
        while (rs.next()) {
            String idNo = rs.getString("CREDENTIAL_NUMBER");
//            String idNo = rs.getString("paper_number");
            if (idNoList.contains(idNo)) {
                continue;
            }
            Map<String, String> map = new HashMap<>(8);
            String time = TimesUtil.getServerDateTime(6, rs.getTimestamp("TIME"));
            String groupName = rs.getString("IMPORTANT_VALUE");
            String personName = rs.getString("PERSON_NAME");
            String location = rs.getString("CAMERANAME");
            int num = location.indexOf("[");
            String similar = rs.getBigDecimal("SCORE").multiply(new BigDecimal(100).setScale(0,BigDecimal.ROUND_HALF_UP)).toString();
            map.put("idNo", idNo);
            map.put("captureTime", time);
            map.put("groupName", groupName);
            map.put("personName", personName);
            map.put("similar", similar);
            map.put("location",location.substring(0,num));
//            Date date = rs.getTimestamp("create_date");
//            map.put("captureTime",TimesUtil.getServerDateTime(4,date));
//            map.put("idNo",rs.getString("paper_number"));
            idNoList.add(idNo);
            list.add(map);
        }
        // 关闭连接
        if (rs != null) rs.close();
        if (st != null) st.close();
        if (conn != null) conn.close();
        log.info("获取到" + list.size() + "条人脸记录");
        return list;
    }
}
