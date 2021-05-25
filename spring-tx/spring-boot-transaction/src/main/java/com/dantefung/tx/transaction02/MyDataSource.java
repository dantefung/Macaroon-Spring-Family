package com.dantefung.tx.transaction02;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@PropertySource( value = "classpath:/application.yml")
public class MyDataSource {

    @Value( "${spring.datasource.druid.driver-class-name}" )
    public  String driver ;

    @Value( "${spring.datasource.druid.url}" )
    public String url ;

    @Value( "${spring.datasource.druid.username}" )
    public String username;

    @Value( "${spring.datasource.druid.password}" )
    public String password ;

    //加载驱动

    @PostConstruct
    public void initDriver(){
        try {
            Driver drivers = (Driver) Class.forName( driver ).newInstance();
            // 把这个驱动注册到驱动管理器内
            DriverManager.registerDriver( drivers );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


    /**
     *@Description  获取连接
     *@Author DANTE FUNG
     *@Date 2021-03-01 23:16:09
     *@Param []
     *@return java.sql.Connection
     */
    public Connection getConnection(){

        Connection connection = null;
        try {
            connection = DriverManager.getConnection( url, username, password );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}