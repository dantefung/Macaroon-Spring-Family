package com.dantefung.log.example.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LogEurekaRunner {

    public static void main(String[] args) {
        SpringApplication.run(LogEurekaRunner.class, args);
    }
}
