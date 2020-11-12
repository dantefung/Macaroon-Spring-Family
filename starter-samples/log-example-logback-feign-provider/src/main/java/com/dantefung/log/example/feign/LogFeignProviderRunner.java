package com.dantefung.log.example.feign;

import com.dantefung.okra.log.annontation.EnableLogTraceInterceptor;
import com.dantefung.okra.log.common.enhance.bytes.AspectLogEnhance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableLogTraceInterceptor
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class LogFeignProviderRunner {

    static {AspectLogEnhance.enhance();}

    public static void main(String[] args) {
        SpringApplication.run(LogFeignProviderRunner.class, args);
    }
}
