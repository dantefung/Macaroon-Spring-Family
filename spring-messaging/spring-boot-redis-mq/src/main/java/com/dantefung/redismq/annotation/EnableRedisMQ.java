package com.dantefung.redismq.annotation;

import com.dantefung.redismq.config.RedisMQConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Title: EnableRedisMQ
 * @Description: 启动RedisMQ配置
 * @author DANTE FUNG
 * @date 2020/4/29 15:08
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Import(RedisMQConfig.class)
public @interface EnableRedisMQ {
}
