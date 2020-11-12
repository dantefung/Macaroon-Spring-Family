package com.dantefung.springvalidation.config;

import com.dantefung.springvalidation.filter.LocaleFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
 
    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new LocaleFilter());
        registration.addUrlPatterns("/*");
        registration.setName("localeFilter");
        registration.setOrder(2);
        return registration;
    }
 
}