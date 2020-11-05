package com.dantefung.okra.log.aspect;

import com.dantefung.okra.log.annontation.LogTrace;
import com.dantefung.okra.log.util.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE - 3)
public class LogMdcAspect {

	@Pointcut("@annotation(com.dantefung.okra.log.annontation.LogTrace)"
			+ "|| @within(com.dantefung.okra.log.annontation.LogTrace)")
	public void logPointCut() {
		//
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		log.info("\r\n\r\n<======================LogMdcAspect切面开始...===================>\r\n\r\n");
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
		if (StringUtils.isEmpty(traceId) || TraceIdUtil.defaultTraceId(traceId)) {
			traceId = TraceIdUtil.genTraceId();
			log.info("---------------->{} init traceId:{} ...", this.getClass().getSimpleName(), traceId);
			TraceIdUtil.setTraceId(this.getClass().getSimpleName() +"<generated>:" + TraceIdUtil.TRACE_ID + ":" + value + ":" + traceId);
		}

		Object result = null;
		try {
			result = point.proceed();// 执行方法
		} finally {
			TraceIdUtil.clearTraceId();
		}
		log.info("\r\n\r\n<======================LogMdcAspect切面结束...===================>\r\n\r\n");
		return result;
	}
}