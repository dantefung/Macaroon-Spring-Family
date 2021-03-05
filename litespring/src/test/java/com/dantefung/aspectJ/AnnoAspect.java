/*
 * Copyright (C), 2015-2020
 * FileName: CoreAspect
 * Author:   DANTE FUNG
 * Date:     2021/3/5 9:04 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/5 9:04 下午   V1.0.0
 */
package com.dantefung.aspectJ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Title: 声明一个切面CoreAspectTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/05 21/04
 * @since JDK1.8
 */
@Aspect
public class AnnoAspect {

	@After("execution(* com.dantefung.aspectJ.TargetObject.*(..))")
	public void afterDo(JoinPoint joinPoint) {
		System.out.println(("After | target:" + joinPoint.getTarget() + "{}, signature:" + joinPoint.getSignature()));
	}

}
