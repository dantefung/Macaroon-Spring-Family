/*
 * Copyright (C), 2015-2020
 * FileName: Service1Test
 * Author:   DANTE FUNG
 * Date:     2021/3/29 15:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/29 15:38   V1.0.0
 */
package com.dantefung.tx.sample.user.service;

import com.dantefung.tx.Tester;
import com.dantefung.tx.sample.user.service.impl.Service1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: Service1Test
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/29 15/38
 * @since JDK1.8
 */
public class Service1Test extends Tester {

	@Autowired
	private Service1 service1;

	@Test
	public void testMultiDsTx() {
		service1.m1();
	}

}
