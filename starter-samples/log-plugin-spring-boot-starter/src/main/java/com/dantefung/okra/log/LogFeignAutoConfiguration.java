package com.dantefung.okra.log;

import com.dantefung.okra.log.feign.TLogFeignFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TLog Feign 配置的springboot自动装配类
 * @author Bryan.Zhang
 * @since 2020/9/11
 */
@Configuration
@ConditionalOnClass(name = {"feign.RequestInterceptor"})
public class LogFeignAutoConfiguration {

    @Bean
    public TLogFeignFilter tLogFeignFilter(){
        return new TLogFeignFilter();
    }
}
