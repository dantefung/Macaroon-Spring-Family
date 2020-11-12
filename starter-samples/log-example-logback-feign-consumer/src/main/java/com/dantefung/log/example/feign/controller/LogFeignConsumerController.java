package com.dantefung.log.example.feign.controller;

import com.dantefung.log.example.feign.domain.ConsumerDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogFeignConsumerController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumerDomain consumerDomain;

    @RequestMapping("/hi")
    public String sayHello(@RequestParam String name){
        log.info("invoke consumer controller method sayHello");
        return consumerDomain.sayHello(name);
    }

	@RequestMapping("/hihi")
	public String sayHelloHello(@RequestParam String name){
		log.info("invoke consumer controller method sayHelloHello");
		return consumerDomain.sayHelloHello(name);
	}
}
