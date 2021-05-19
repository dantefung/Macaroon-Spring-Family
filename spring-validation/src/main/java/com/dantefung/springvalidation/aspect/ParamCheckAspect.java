package com.dantefung.springvalidation.aspect;

import cn.hutool.core.util.ArrayUtil;
import com.dantefung.springvalidation.annotation.ValidGroupTag;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * @author chenli
 * @date 2021/1/20 11:41
 * @description：用于service层方法参数校验
 */
@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE - 2)
public class ParamCheckAspect {
    private static Validator validator;
    static {
        HibernateValidatorConfiguration configure = Validation.byProvider(HibernateValidator.class).configure();
        ValidatorFactory validatorFactory = configure.failFast(true).buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Pointcut("@annotation(com.dantefung.springvalidation.annotation.ValidGroupTag))")
    private void validateMethod() {
        //
    }

    @Before("validateMethod()")
    public void before(JoinPoint joinPoint) {
    	// 方法上的所有参数对象
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取方法签名上的所有参数名称
        String[] argNames = signature.getParameterNames();
        // 获取方法签名的方法名
        Method method = signature.getMethod();
		// 获取每个参数上所标注的注解数组 1-n  所以是二维的
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        if (ArrayUtil.isEmpty(args) || ArrayUtil.isEmpty(parameterAnnotations)) return;
        Integer paramIndex = null;
        String[] paramNameArr = null;

        boolean flag = true;
        for (int i = 0; i < parameterAnnotations.length && flag; i++) {
            for (Annotation an : parameterAnnotations[i]) {
                if (an instanceof ValidGroupTag) {
                	// 获取要校验的参数列表
                    paramNameArr = ((ValidGroupTag) an).value();
                    paramIndex = i;
                    flag = false;
                    break;
                }
            }
        }

        if (flag) return;
        List<Class<?>> list = (List<Class<?>>) args[paramIndex];
        if (CollectionUtils.isEmpty(list)) return;
        Class<?>[] groups = list.toArray(new Class<?>[list.size()]);

        // 执行方法参数的校验
        List<String> messages = Lists.newArrayList();
        for (int i = 0; i < args.length; i++) {
            //跳过注解参数
            if (i == paramIndex) continue;

            Object obj = args[i];
            String argName = argNames[i];
            validateParam(paramNameArr, groups, messages, obj, argName);
        }
    }

    private void validateParam(String[] paramNameArr, Class<?>[] groups, List<String> messages, Object obj, String argName) {
        Set<ConstraintViolation<Object>> result;
        if (ArrayUtil.isEmpty(paramNameArr)) {
            //没有设置校验所有参数
            result = validator.validate(obj, groups);
            throwOutErrMsg(result, messages);
        }else {
            //校验匹配的参数(@ValidGroupTag("user")指定的参数)
            if (ArrayUtil.indexOf(paramNameArr, argName) != -1) {
                result = validator.validate(obj, groups);
                throwOutErrMsg(result, messages);
            }
        }
    }

    private void throwOutErrMsg(Set<ConstraintViolation<Object>> result, List<String> messages) {
        if (!CollectionUtils.isEmpty(result)) {
            result.forEach(error -> messages.add(error.getMessage()));
            throw new RuntimeException(messages.get(0));
        }
    }

}
