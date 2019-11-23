/**
 * Copyright (C), 2018-2019, 独立开发者DanteFung
 * FileName: SysLogTestService
 * Author:   admin
 * Date:     2019-11-24 01:20
 * Description: 测试系统日志注解
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.dantefung.aop.springaopdemo.service;

/**
 * @author Dante Fung

 * @create 2019-11-24 01:20

 * @desc 测试系统日志注解

 * @since 1.0.0
 **/
public interface SysLogTestService {

    String doBiz(String msg);

    Boolean saveRenewalLog(String customerName);
}
