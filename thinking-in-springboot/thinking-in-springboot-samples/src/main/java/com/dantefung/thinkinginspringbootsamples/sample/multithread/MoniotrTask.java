package com.dantefung.thinkinginspringbootsamples.sample.multithread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mTask")
@Scope("prototype")
public class MoniotrTask extends Thread {

    @Override
    public void run() {
        log.info("线程:"+Thread.currentThread().getName()+"运行中.....");
    }

}