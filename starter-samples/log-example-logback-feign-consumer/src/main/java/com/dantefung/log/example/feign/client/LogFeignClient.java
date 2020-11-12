package com.dantefung.log.example.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("log-logback-feign-provider")
public interface LogFeignClient {
    @RequestMapping(value = "hi",method = RequestMethod.GET)
    String sayHello(@RequestParam(value = "name") String name);

	@RequestMapping(value = "hihi",method = RequestMethod.GET)
	String sayHelloHello(@RequestParam(value = "name") String name);
}
