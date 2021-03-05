/*
 * Copyright (C), 2015-2020
 * FileName: AspectTest
 * Author:   DANTE FUNG
 * Date:     2021/3/5 9:10 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/5 9:10 下午   V1.0.0
 */
package com.dantefung.aspectJ;

/**
 * @Title: AspectTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/05 21/10
 * @since JDK1.8
 */
public class AspectTest {

	public static void main(String[] args) {
		TargetObject targetObject = new TargetObject();
		targetObject.test();
	}
}
