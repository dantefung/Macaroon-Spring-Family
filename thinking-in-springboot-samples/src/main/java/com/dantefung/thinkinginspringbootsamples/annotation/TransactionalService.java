package com.dantefung.thinkinginspringbootsamples.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * @Title: TransactionalService
 * @Description: Annotation-Driven 注解派生
 * {@link Transactional @Transactional} 和 {@link Service @Service} 组合注解
 * @author DANTE FUNG
 * @date 2020/3/24 15:02
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Service(value = "transactionalService")
@Service(value = "testMerged")
@Transactional
public @interface TransactionalService {

	/**
	 * @return 服务 Bean 名称
	 */
	@AliasFor(attribute = "value")
	String name() default "";
	/**
	 * 覆盖 {@link Transactional#value()} 默认值
	 *
	 * @return {@link PlatformTransactionManager} Bean 名称
	 */
	@AliasFor("name")
	String value() default "";
	/**
	 * 建立 {@link Transactional#transactionManager()} 别名
	 *
	 * @return {@link PlatformTransactionManager} Bean 名称，默认关联 "txManager" Bean
	 */
	@AliasFor(attribute = "transactionManager", annotation = Transactional.class)
	String manager() default "txManager";
}

