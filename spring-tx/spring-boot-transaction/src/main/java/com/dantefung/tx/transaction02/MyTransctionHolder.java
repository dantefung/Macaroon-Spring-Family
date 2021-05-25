package com.dantefung.tx.transaction02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component   //交给spring去管理
public class MyTransctionHolder {

    //本来线程不安全的，通过ThreadaLocal这么封装一下，立刻就变成了线程的局部变量，不仅仅安全了，
    // 还保证了一个线程下面的操作拿到的Connection是同一个对象
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    @Autowired
    private MyDataSource myDataSource;

    //获取连接
    public Connection getConnection(){
        try {
            if (threadLocal.get() == null){
                Connection connection = myDataSource.getConnection();
                threadLocal.set( connection );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return threadLocal.get();

    }
}
