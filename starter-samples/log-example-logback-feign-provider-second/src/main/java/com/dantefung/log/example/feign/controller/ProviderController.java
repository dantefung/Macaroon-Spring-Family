package com.dantefung.log.example.feign.controller;

import com.dantefung.log.example.feign.domain.ProviderDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProviderDomain providerDomain;

    @RequestMapping("/hi")
    public String sayHello(@RequestParam String name){
        log.info("invoke provider controller method sayHello");
        return providerDomain.sayHello(name);
    }
}
