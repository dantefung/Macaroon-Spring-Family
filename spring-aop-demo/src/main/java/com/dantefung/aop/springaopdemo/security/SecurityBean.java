package com.dantefung.aop.springaopdemo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 演示支持 spring bean
 *
 * @author L.cm
 */
@Slf4j
@Component("sec")
public class SecurityBean {


    /**
     * 判断请求是否有权限
     *
     * @param permission 权限表达式
     * @return 是否有权限
     */
    public boolean hasPermission(String permission) {
        log.info("\r\n ===========> -3- enter SecurityBean.hasPermission()...");
        return "123".equals(permission);
    }
}
