package com.dantefung.aop.springaopdemo.advice;


import com.dantefung.aop.springaopdemo.entity.AppsysEventLog;

public class LogContext {
    private static ThreadLocal<AppsysEventLog> threadLocal = new ThreadLocal<>();

    private LogContext(){}

    public static AppsysEventLog get(){
        return threadLocal.get();
    }

    public static void put(AppsysEventLog event){
        threadLocal.set(event);
    }

    public static void remove(){
        threadLocal.remove();
    }
}
