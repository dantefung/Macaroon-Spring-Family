package com.dantefung.aop.springaopdemo.aspect;

import com.dantefung.aop.springaopdemo.annotation.SysLog;
import com.dantefung.aop.springaopdemo.annotation.SysLogType;
import com.dantefung.aop.springaopdemo.entity.AmsSysLogEntity;
import com.dantefung.aop.springaopdemo.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Order(1)
@Aspect
@Component
public class SysLogAspect {


    private static final String REGEX = "\\{(.*?)\\}";

    @Pointcut("@annotation(com.dantefung.aop.springaopdemo.annotation.SysLog)")
    public void amsLogPointCut() {
        // Do nothing.
    }

    @Around("amsLogPointCut()")
    public Object around(ProceedingJoinPoint point) {
        try {
            // 执行目标方法
            Object result = point.proceed();

            // 保存系统日志
            saveSysLog(point);

            return result;
        } catch (Throwable throwable) {
            log.error("around error,{}", throwable.getMessage());
            return null;
        }
    }

    private String match(String content, Map<String, Object> params) {
        Pattern compile = Pattern.compile(REGEX);
        Matcher matcher = compile.matcher(content);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String group = matcher.group(1);
            params.computeIfAbsent(group, k -> "");
            String temp = '"' + params.get(group).toString() + '"';
            matcher.appendReplacement(buffer, temp);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Method method = signature.getMethod();

        AmsSysLogEntity entity = new AmsSysLogEntity();
        Parameter[] parameters = method.getParameters();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(SysLogType.class)) {
                SysLogType amsSysLogType = parameters[i].getAnnotation(SysLogType.class);
                map.put(amsSysLogType.value(), args[i]);
            }
        }
        SysLog amsSysLog = method.getAnnotation(SysLog.class);
        if (amsSysLog != null) {
            //注解上的操作类型
            entity.setOpeatorType(amsSysLog.type());
            //注解上的操作内容
            String content = amsSysLog.content();
            String contentStr = match(content, map);
            entity.setOperatorContent(contentStr);
            //设置操作时间
            entity.setOperatorTime(new Date());
        }
        log.info(">>>>>>>>>>>>>{}", JsonUtil.obj2Json(entity));
        // TODO: 保存系统日志
    }
}
