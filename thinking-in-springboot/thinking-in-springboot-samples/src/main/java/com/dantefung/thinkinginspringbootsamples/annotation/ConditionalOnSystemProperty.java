package com.dantefung.thinkinginspringbootsamples.annotation;

import com.dantefung.thinkinginspringbootsamples.sample.condition.OnSystemPropertyCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @Title: ConditionalOnSystemProperty
 * @Description:
 * @author DANTE FUNG
 * @date 2020/5/20 15:49
 */
@Target({ElementType.METHOD})// 只能标注在方法上面
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnSystemPropertyCondition.class)// @Conditional作为@Conditional条件装配的元注解.
public @interface ConditionalOnSystemProperty {

	/**
	 * @return System 属性名称
	 */
	String name();

	/**
	 * @return System 属性值
	 */
	String value();
}
