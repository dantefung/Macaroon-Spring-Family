package com.dantefung.log.example.feign.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsynDomain extends Thread{

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        log.info("这是异步方法哦");
        log.info("异步方法开始");
        log.info("异步方法结束");
    }
}
