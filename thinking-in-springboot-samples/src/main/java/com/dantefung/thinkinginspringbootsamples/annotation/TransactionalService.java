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

	//////////////////////////////测试1:  START///////////////////////////

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

	// 多层次注解属性之间的@AliasFor关系只能由较低层向较高层建立。
	/**
	 * 建立 {@link Transactional#transactionManager()} 别名
	 *
	 * @return {@link PlatformTransactionManager} Bean 名称，默认关联 "txManager" Bean
	 */
	@AliasFor(attribute = "transactionManager", annotation = Transactional.class) // “显性覆盖”
	String manager() default "txManager";


	//////////////////////////////测试2:  START///////////////////////////
	// 同一注解内，只要属性方法之间需要相互“@AliasFor”，则它们的默认值就必须相等。
	/**
	 * @return 服务 Bean 名称
	 */
//	@AliasFor(attribute = "value")
//	String name() default "txManager";
	/**
	 * @Transactional 注解内部的属性间@AliasFor
	 *
	 *     @AliasFor("transactionManager")
	 *     String value() default "";
	 *
	 *     @AliasFor("value")
	 *     String transactionManager() default "";
	 *
	 * 覆盖 {@link Transactional#value()} 默认值
	 *
	 * @return {@link PlatformTransactionManager} Bean 名称
	 */
	//@AliasFor("name")
	//String value() default "txManager";




	//////////////////////////////测试3:  START///////////////////////////

	/**
	 * 由于Java注解的静态性，@TransationalService无法调整其元注解@Transactional属性方法的默认值。因此，AnnotationAttributes只能读取这些默认值。
	 *
	 * 假设@TransactionalService需要调整@Transactional.transactionManager()的默认值，可结合“隐性覆盖”原则进行设计，即@TransactionalService 增加 transactionManager()属性方法.
	 */
	/**
	 * 覆盖{@link Transactional#transactionManager()}默认值
	 * @return {@link PlatformTransactionManager}Bean名称，默认关联"txManager"Bean
	 */
	//String transactionManager() default "txManager"; // "隐性覆盖"
	//String value() default ""; // “隐性覆盖”

}

