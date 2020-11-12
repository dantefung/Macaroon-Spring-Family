package com.dantefung.log.example.feign.config;

import feign.Request;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Value("${http.connectTimeOutMillis}")
    private int connectTimeOutMillis;

    @Value("${http.readTimeOutMillis}")
    private int readTimeOutMillis;

    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis,readTimeOutMillis);
    }

    @Bean
    public Retryer feignRetryer() {
        return Retryer.NEVER_RETRY;
    }
}
