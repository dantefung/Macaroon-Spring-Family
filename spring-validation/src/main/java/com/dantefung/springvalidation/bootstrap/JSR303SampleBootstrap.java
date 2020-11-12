/*
 * Copyright (C), 2015-2020
 * FileName: JSR303SampleBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/11/12 14:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/12 14:28   V1.0.0
 */
package com.dantefung.springvalidation.bootstrap;

import com.dantefung.springvalidation.annotation.ListValue;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.util.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Iterator;
import java.util.Set;

/**
 * @Title: JSR303SampleBootstrap
 * @Description:
 * @author DANTE FUNG
 * @date 2020/11/12 14/28
 * @since JDK1.8
 */
public class JSR303SampleBootstrap {

	public static void main(String[] args) {
		UserModel userModel = new UserModel();
		userModel.setUsername(null);
		userModel.setPassword("123");
		userModel.setAddress("012345678901234567890123456789");
		userModel.setGender(0);
		validateByMyLogic(userModel, Verify.class);
		validateByBeanPostProcessorLogic(userModel);

	}

	public static void validateByMyLogic(Object object, Class<?>... groups) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			StringBuilder msg = new StringBuilder("Bean state is invalid: ");
			for (ConstraintViolation<Object> constraint : constraintViolations) {
				msg.append(constraint.getMessage()).append(";");
			}
			System.out.println("==>" + msg);
		}
	}

	public static void validateByBeanPostProcessorLogic(Object bean) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		// 以下代码摘自: org.springframework.validation.beanvalidation.BeanValidationPostProcessor
		Assert.state(validator != null, "No Validator set");
		Object objectToValidate = AopProxyUtils.getSingletonTarget(bean);
		if (objectToValidate == null) {
			objectToValidate = bean;
		}
		Set<ConstraintViolation<Object>> result = validator.validate(objectToValidate);

		if (!result.isEmpty()) {
			StringBuilder sb = new StringBuilder("Bean state is invalid: ");
			for (Iterator<ConstraintViolation<Object>> it = result.iterator(); it.hasNext(); ) {
				ConstraintViolation<Object> violation = it.next();
				sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
				if (it.hasNext()) {
					sb.append("; ");
				}
			}
			throw new BeanInitializationException(sb.toString());
		}
	}


	@Data
	static class UserModel {
		@NotNull(message = "username can not be null")
		@Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "username is illegal")
		private String username;
		@Size(min = 5, max = 10, message = "password's length is illegal")
		private String password;
		@Length(message = "address长度需要在1和20之间", min = 1, max = 20, groups = {Verify.class})
		private String address;
		@ListValue(message = "性别应指定相应的值", vals = {1, 2}, groups = {Verify.class})
		private Integer gender;
	}

	/**
	 * 验证数据 Group
	 */
	public interface Verify {
	}

}



