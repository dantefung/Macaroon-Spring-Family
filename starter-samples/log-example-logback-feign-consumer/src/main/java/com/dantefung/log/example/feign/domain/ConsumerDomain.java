package com.dantefung.log.example.feign.domain;

import com.dantefung.log.example.feign.client.LogFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumerDomain {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LogFeignClient logFeignClient;

    public String sayHello(String name){
        log.info("invoke consumer domain method sayHello");
        return logFeignClient.sayHello(name);
    }

	public String sayHelloHello(String name){
		log.info("invoke consumer domain method sayHello");
		return logFeignClient.sayHelloHello(name);
	}

}
