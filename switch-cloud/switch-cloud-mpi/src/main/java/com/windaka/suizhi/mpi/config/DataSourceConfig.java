package com.windaka.suizhi.mpi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName DataSourceConfig
 * @Description Hikaricp连接池配置类
 * @Author lixianhua
 * @Date 2019/12/9 11:06
 * @Version 1.0
 */
@Slf4j
@Configuration
public class DataSourceConfig {

    private static DataSource dataSource;

    private static Connection conn;

    //    static {
//        try {
//            HikariConfig config = new HikariConfig();
//            config.setJdbcUrl("jdbc:mysql://10.10.6.54:3306/switch_cloud?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false"); //数据源
//            config.setUsername("root"); //用户名
//            config.setPassword("root"); //密码
//            ds = new HikariDataSource(config);
//            conn = ds.getConnection();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    // 获取连接
    public  DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://10.10.6.54:3306/switch_cloud?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false"); //数据源
        config.setUsername("root"); //用户名
        config.setPassword("root"); //密码
        DataSource ds = new HikariDataSource(config);
//        conn = ds.getConnection();
//        if (conn == null || conn.isClosed()) {
//            conn = ds.getConnection();
//        }
        return ds;
    }
    // 回收连接
    public static void closeConn() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn = null;
        }
    }
    @Bean
    public static  DataSource dataSource(DataConnectProperties properties) {
        if(null!=dataSource){
            return dataSource;
        }
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getUrl()); //数据源
        config.setUsername(properties.getUsername()); //用户名
        config.setPassword(properties.getPassword()); //密码
        dataSource = new HikariDataSource(config);
        return dataSource;
    }
}
