package com.dantefung.aop.springaopdemo.config;

import com.dantefung.aop.springaopdemo.advice.DispatcherServletWrapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
@ConditionalOnProperty(
        value = "logctr.eventlog.plugin.tokenaspect.enabled",
        havingValue = "true",
        matchIfMissing = true)
public class LogConfig {

    @Bean
    @Qualifier(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServletWrapper();
    }
}