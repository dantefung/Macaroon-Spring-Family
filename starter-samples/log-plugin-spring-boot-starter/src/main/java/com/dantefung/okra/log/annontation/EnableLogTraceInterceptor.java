package com.dantefung.okra.log.annontation;

import com.dantefung.okra.log.common.web.LogInterceptorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Title: EnableLogEnhance
 * @Description: 开启增强日志框架
 * @author DANTE FUNG
 * @date 2020/11/13 00/13
 * @since JDK1.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(LogInterceptorConfiguration.class)
public @interface EnableLogTraceInterceptor {
}
