package com.dantefung.okra.log.aspect;

import com.dantefung.okra.log.annontation.LogTrace;
import com.dantefung.okra.log.util.TraceIdUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

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
		String traceId = TraceIdUtil.getTraceId();
		if (StringUtils.isEmpty(traceId)) {
			TraceIdUtil.setTraceId(this.getClass().getSimpleName() + ":" + value + ":" + TraceIdUtil.genTraceId());
		}

		Object result = null;
		try {
			result = point.proceed();// 执行方法
		} finally {
			TraceIdUtil.clearTraceId();
		}
		return result;
	}
}