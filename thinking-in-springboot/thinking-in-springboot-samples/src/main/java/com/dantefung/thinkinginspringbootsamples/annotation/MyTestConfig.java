package com.dantefung.thinkinginspringbootsamples.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;

/**
 * 如果注解中的一个或多个属性声明为同一个元注解属性的属性重写（直接地或传递地重写）
 * 那么这些注解会被当作彼此的隐性别名集来对待
 * 结果是它们的行为类似于注解中的显性别名
 */
@ContextConfiguration
 public @interface MyTestConfig {
 
    @AliasFor(annotation = ContextConfiguration.class, attribute = "locations")
    String[] value() default {};
 
    @AliasFor(annotation = ContextConfiguration.class, attribute = "locations")
    String[] groovyScripts() default {};
 
    @AliasFor(annotation = ContextConfiguration.class, attribute = "locations")
    String[] xmlFiles() default {};
 }