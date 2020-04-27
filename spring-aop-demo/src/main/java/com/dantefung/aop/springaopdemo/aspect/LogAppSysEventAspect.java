package com.dantefung.aop.springaopdemo.aspect;

import cn.hutool.http.ContentType;
import com.dantefung.aop.springaopdemo.advice.LogContext;
import com.dantefung.aop.springaopdemo.annotation.LogAppSysEvent;
import com.dantefung.aop.springaopdemo.entity.AppsysEventLog;
import com.dantefung.aop.springaopdemo.entity.UserDto;
import com.dantefung.aop.springaopdemo.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

/**
 * 记录应用系统操作接口生成日志
 */
@Aspect
@Component
@ConditionalOnProperty(
        value = "logctr.eventlog.plugin.tokenaspect.enabled",
        havingValue = "true",
        matchIfMissing = true)
@Order(value = 1)
@Slf4j
public class LogAppSysEventAspect {


    @Around("@annotation(com.dantefung.aop.springaopdemo.annotation.LogAppSysEvent)")
    public Object tokenRequiredHandler(ProceedingJoinPoint joinPoint) throws IOException {
    	log.info("\r\n\r\n<======================LogAppSysEvent切面开始...===================>\r\n\r\n");
        LogAppSysEvent logAppSysEvent = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(LogAppSysEvent.class);
        logAppSysEvent.appsysid();
        log.info("appSysId is :>>>>>>>{}", logAppSysEvent.appsysid());
        log.info("eventcode is :>>>>>>>{}", logAppSysEvent.eventcode());
        //情况1:从登录接口/获取token接口获取用户对象
        UserDto userDto = getUserDtoFromParams();
		sendLogAppSysEventMsgToMQ(logAppSysEvent, userDto);
		//情况2:从token获取
		HttpServletRequest httpServletRequest = getHttpServletRequest();
		String token = httpServletRequest.getHeader("X-OS-KERNEL-TOKEN");
		// 省略一堆处理.....
		//sendLogAppSysEventMsgToMQ(logAppSysEvent, userDto);

        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("接口未知异常:" + throwable.getMessage(), throwable);

        }
		log.info("\r\n\r\n<======================LogAppSysEvent切面结束...===================>\r\n\r\n");
        return null;
    }

    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Assert.notNull(servletRequestAttributes, "ServletRequestAttributes got from RequestContextHolder cannot be null. Please check the web config.");

        return servletRequestAttributes.getRequest();

    }


    private UserDto getUserDtoFromParams() {
        UserDto userDto = new UserDto();
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        String userLogin = httpServletRequest.getHeader("userlogin");
        if (!StringUtils.isEmpty(userLogin)) {
            String baseCredentials = userLogin.split(" ")[1];
            String usernamePassword = new String(Base64Utils.decodeFromString(baseCredentials), StandardCharsets.UTF_8);
            String[] credentials = usernamePassword.split(":");
            String loginAcc = credentials[0].substring(credentials[0].indexOf('|') + 1);
            // 通过登录账号获取用户ID
//            String result = userApiService.getUserIdByLoginAcc(loginAcc);
			String userId = "cb72d8ddd456f54f92cce23639c504fa";
            log.info("result is :>>>>>>>{}", userId);
            if (!StringUtils.isEmpty(userId)) {
                //获取用户id
                userDto.setId(userId);
                userDto.setLoginAcc(loginAcc);
                return userDto;
            }

        } else {
			userDto.setLoginAcc("测试测试");
			return userDto;
		}

        return null;
    }


    private void sendLogAppSysEventMsgToMQ(LogAppSysEvent logAppSysEvent, UserDto userDto) throws IOException {
        if (userDto != null) {
            String id = UUID.randomUUID().toString().replace("-", "");
            AppsysEventLog appsysEventLog = new AppsysEventLog();
            appsysEventLog.setId(id);
            appsysEventLog.setOriginAppSysId(logAppSysEvent.appsysid());
            appsysEventLog.setEventCode(logAppSysEvent.eventcode());
            appsysEventLog.setCollectType(1);
            appsysEventLog.setUserId(userDto.getId());
            appsysEventLog.setMemo(logAppSysEvent.remark());
            appsysEventLog.setEventTime(new Date(System.currentTimeMillis()));
            log.info("当前时间:>>>>>>>>>{}", new Date(System.currentTimeMillis()));
            appsysEventLog.setLoginAcc(userDto.getLoginAcc());
            HttpServletRequest httpServletRequest = getHttpServletRequest();
            appsysEventLog.setEventHost(httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort());
            appsysEventLog.setEventUrl(httpServletRequest.getRequestURI());
            appsysEventLog.setCreateBy(userDto.getLoginAcc());
            appsysEventLog.setUpdateBy(userDto.getLoginAcc());
            if(httpServletRequest.getMethod().equalsIgnoreCase(RequestMethod.GET.name())){
                appsysEventLog.setEventDataInput(httpServletRequest.getQueryString());
            }else if(ContentType.isFormUrlEncoed(httpServletRequest.getContentType())){
                Enumeration<String> enumerations = httpServletRequest.getParameterNames();
                StringBuilder sb = new StringBuilder();
                while (enumerations.hasMoreElements()) {
                    String key = enumerations.nextElement();
                    sb.append(key).append("=").append(httpServletRequest.getParameter(key)).append("&");
                }
				log.info("=============>:EventDataInput:{}", sb.toString());
                appsysEventLog.setEventDataInput(sb.toString());
            }else {

                appsysEventLog.setEventDataInput( IOUtils.toString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8));
            }

            log.info("appsysEventLog inserted[appsysEventLog] Message: {}", JsonUtil.obj2Json(appsysEventLog));

            LogContext.put(appsysEventLog);

        }


    }
}
