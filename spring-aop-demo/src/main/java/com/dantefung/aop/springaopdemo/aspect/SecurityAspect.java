package com.dantefung.aop.springaopdemo.aspect;


import com.dantefung.aop.springaopdemo.annotation.PreAuthorize;
import com.dantefung.aop.springaopdemo.core.result.R;
import com.dantefung.aop.springaopdemo.security.SecurityFun;
import com.dantefung.aop.springaopdemo.utils.ClassUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 权限AOP
 *
 * @author L.cm
 */
@Slf4j
@Aspect
@Order(2)
@Component
public class SecurityAspect implements ApplicationContextAware {
    /**
     * 表达式处理
     */
    private static final ExpressionParser elParser = new SpelExpressionParser();

    /**
     * AOP 环切 注解 @PreAuthorize
     */
    @Around("@annotation(authorize)")
    public Object aroundPreAuthorize(ProceedingJoinPoint point, PreAuthorize authorize) throws Throwable {
        // 判断是否有权限
        boolean isOk = handleAuthorize(point, authorize);
        if (isOk) {
            return point.proceed();
        }
        return R.fail("demo表达式[" + authorize.value() + "]验证不通过");
    }

    /**
     * 判断是否有权限
     *
     * @param point     切点
     * @param authorize PreAuthorize注解
     */
    private boolean handleAuthorize(ProceedingJoinPoint point, PreAuthorize authorize) {
        // 注解表达式
        String condition = authorize.value();
        log.info(">>>>>>>>>>>>>>>注解表达式condition:{}", condition);
        if (StringUtils.isNotBlank(condition)) {
            Expression expression = elParser.parseExpression(condition);
            StandardEvaluationContext context = getEvaluationContext(point);
            log.info("\r\n>>>>>>>>>>>>\r\n -1- execute expression.getValue ...\r\n<<<<<<<<<<<");
            return expression.getValue(context, Boolean.class);
        }
        return false;
    }

    /**
     * 获取方法上的参数
     *
     * @param point 切点
     * @return {SimpleEvaluationContext}
     */
    private StandardEvaluationContext getEvaluationContext(ProceedingJoinPoint point) {
        // 初始化Sp el表达式上下文，并设置 SecurityFun
        StandardEvaluationContext context = new StandardEvaluationContext(new SecurityFun());
        // 设置表达式支持spring bean
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        StringBuffer sb = new StringBuffer();
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            // 读取方法参数
            MethodParameter methodParam = ClassUtil.getMethodParameter(method, i);
            Object value = args[i];
            sb.append(methodParam.getParameterName()).append(",");
            // 设置方法参数名和值为sp el变量
            context.setVariable(methodParam.getParameterName(), value);
        }
        log.info("\r\n >>>>>>>>>>\r\n methodName: {}, \r\n\r\n parameterName: {} \r\n\r\n value: {} \r\n <<<<<<<<<<", method.getName(), sb.toString(), args);
        return context;
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
