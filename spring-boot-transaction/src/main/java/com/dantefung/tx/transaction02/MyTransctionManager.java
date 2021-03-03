package com.dantefung.tx.transaction02;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MyTransctionManager {

    //开始事务
    public void startTx(Connection connection){
        try {
            if (connection != null){
                connection.setAutoCommit( Boolean.FALSE );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //提交事务
    public void commitTx(Connection connection){
        try {
            if (connection != null){
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //事务回滚
    public void rollBackTx(Connection connection){
		System.out.println("rollback!");
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭事务
    public void closeTx(Connection connection){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}