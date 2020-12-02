package com.dantefung.log.example.feign.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProviderDomain {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public String sayHello(String name){
        log.info("invoke ProviderDomain sayHello");
        new AsynDomain().start();
        return "hello," + name;
    }
}
