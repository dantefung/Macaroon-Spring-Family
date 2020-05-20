package com.dantefung.thinkinginspringbootsamples.annotation;

import com.dantefung.thinkinginspringbootsamples.config.HelloWorldConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Title: EnableHelloWorld
 * @Description: 实现“@Enable模块驱动”
 * @author DANTE FUNG
 * @date 2020/3/26 20:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HelloWorldConfiguration.class) // 导入HelloWorldConfiguration
public @interface EnableHelloWorld {

}
