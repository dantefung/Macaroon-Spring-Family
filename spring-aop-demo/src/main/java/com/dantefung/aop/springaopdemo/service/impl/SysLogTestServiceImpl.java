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
import org.springframework.stereotype.Service;

/**
 * @author Dante Fung

 * @create 2019-11-24 01:18

 * @desc 测试系统日志注解

 * @since 1.0.0
 **/
@Service
public class SysLogTestServiceImpl implements SysLogTestService {


    @Override
    public String doBiz(String msg) {
        SysLogTestService sysLogTestService = SpringContextUtils.getApplicationContext().getBean(SysLogTestService.class);
        sysLogTestService.saveRenewalLog("msg");
        return "ok!";
    }

    @SysLog(type = "会员续费", content = "客户{customerName}续费了一年")
    public Boolean saveRenewalLog(@SysLogType("customerName")  String customerName) {
        return Boolean.TRUE;
    }

}
