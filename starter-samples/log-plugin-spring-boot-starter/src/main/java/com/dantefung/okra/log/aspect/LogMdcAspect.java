package com.dantefung.okra.log.aspect;

import com.dantefung.okra.log.annontation.LogTrace;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE - 3)
public class LogMdcAspect {
	private static final String UNIQUE_ID = "traceRootId";

	@Pointcut("@annotation(com.dantefung.okra.log.annontation.LogTrace)"
			+ "|| @within(com.dantefung.okra.log.annontation.LogTrace)")
	public void logPointCut() {
		//
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Class targetClass = point.getTarget().getClass();
		Method method = signature.getMethod();

		LogTrace targetLogTrace = (LogTrace) targetClass.getAnnotation(LogTrace.class);
		LogTrace methodLogTrace = method.getAnnotation(LogTrace.class);
		String value = "";
		if (targetLogTrace != null || methodLogTrace != null) {
			if (methodLogTrace != null) {
				value = methodLogTrace.value();
			} else {
				value = targetLogTrace.value();
			}
		}
		MDC.put(UNIQUE_ID, UNIQUE_ID + ":" + value + ":" + UUID.randomUUID().toString().replace("-", ""));
		Object result = point.proceed();// 执行方法
		MDC.remove(UNIQUE_ID);
		return result;
	}
}