package com.dantefung.springbootmvc.config;

import com.dantefung.springbootmvc.sign.DispatcherServletWrapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ConditionalOnProperty(value = "configuration.swith.sign.enabled", havingValue = "true", matchIfMissing = false)
public class SignConfiguration {

    public static final String KEY_NEED_SIGN = "needSign";
    public static final String KEY_APP_ID = "appId";
    public static final String KEY_PUBLIC_KEY = "publicKey";

    @Bean
    @LoadBalanced
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @Qualifier(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServletWrapper();
    }

}
