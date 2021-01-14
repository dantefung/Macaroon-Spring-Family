/**
 * Copyright (C), 2018-2019, 独立开发者DanteFung
 * FileName: SysLogTestService
 * Author:   admin
 * Date:     2019-11-24 01:18
 * Description: 测试系统日志
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dantefung.aop.springaopdemo.service.impl;

import com.dantefung.aop.springaopdemo.annotation.SysLog;
import com.dantefung.aop.springaopdemo.annotation.SysLogType;
import com.dantefung.aop.springaopdemo.service.SysLogTestService;
import com.dantefung.aop.springaopdemo.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @author Dante Fung

 * @create 2019-11-24 01:18

 * @desc 测试系统日志注解

 * @since 1.0.0
 **/
@Slf4j
@Service
public class SysLogTestServiceImpl implements SysLogTestService {


    @Override
    public String doBiz(String msg) {
    	// 目标对象自调用，不会受到切面管理
    	this.saveRenewalLog(msg);
    	// 解决aop自调用问题
		// 方式一
        SysLogTestService sysLogTestService = SpringContextUtils.getApplicationContext().getBean(SysLogTestService.class);
		log.info(">>>>>>>>>>>方式一:从容器中重新获取SysLogTestService的实例,{}",sysLogTestService.getClass());
        sysLogTestService.saveRenewalLog(msg);
        // 方式二,即使用 AopContext.currentProxy() 获取到代理对象，然后通过代理对象调用对应的方法。
		//还有个地方需要注意，以上方式还需要将 Aspect 的 expose-proxy 设置成 true。
		// 如果是 SpringBoot，则修改应用启动入口类的注解：
		//@EnableAspectJAutoProxy(exposeProxy = true)
		log.info(">>>>>>>>>>>方式二:AopContext.currentProxy()获取SysLogTestService的实例,{}",AopContext.currentProxy().getClass());
		if (AopContext.currentProxy() instanceof SysLogTestService) {
			((SysLogTestService)AopContext.currentProxy()).saveRenewalLog(msg);
		}
        return "ok!";
    }

    @SysLog(type = "会员续费", content = "客户{customerName}续费了一年")
    public Boolean saveRenewalLog(@SysLogType("customerName")  String customerName) {
        return Boolean.TRUE;
    }

}
