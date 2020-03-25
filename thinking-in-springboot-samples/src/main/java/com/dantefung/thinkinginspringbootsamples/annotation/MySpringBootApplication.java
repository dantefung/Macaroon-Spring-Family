package com.dantefung.thinkinginspringbootsamples.annotation;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 用于元注解属性的显性别名
 * 1.把多个元注解的属性组合在一起形成新的注解
 *
 * 那么这个属性被解释成元注解属性的别名。(称之为显性的元注解属性重写)
 * 我们可以通过重写继承一个或多个其他注解的功能从而
 * 使得可以更细粒度精准地控制注解层级中属性的重写,
 *
 * 实现要求:
 * 1 如果一个属性是一个元注解属性的别名,那么这个属性必须用@AliasFor进行注释并且
 * 该属性必须指向元注解属性。
 * 2 别名化的属性必须声明相同的返回结果
 * 3.@AliasFor的annotation属性必须引用元注解
 * 4.被引用的元注解必须放置在声明了@AliasFor的注解类上
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@ComponentScan.Filter(type = FilterType.CUSTOM,
				classes = AutoConfigurationExcludeFilter.class) })
public @interface MySpringBootApplication {


	@AliasFor(annotation = EnableAutoConfiguration.class) Class<?>[] exclude() default {};


	@AliasFor(annotation = EnableAutoConfiguration.class) String[] excludeName() default {};


	@AliasFor(annotation = ComponentScan.class, attribute = "basePackages") String[] scanBasePackages() default {};

	@AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses") Class<?>[] scanBasePackageClasses() default {};
}