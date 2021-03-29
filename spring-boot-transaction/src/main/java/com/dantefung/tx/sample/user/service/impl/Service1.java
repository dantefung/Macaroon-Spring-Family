/*
 * Copyright (C), 2015-2020
 * FileName: Service1
 * Author:   DANTE FUNG
 * Date:     2021/3/3 9:27 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/3 9:27 下午   V1.0.0
 */
package com.dantefung.tx.sample.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: Service1
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/03 21/27
 * @since JDK1.8
 */
@Component
public class Service1 {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Service2 service2;

	@Transactional(transactionManager = "transactionManager", propagation = Propagation.REQUIRED)
	public void m1() {
		this.jdbcTemplate.update("insert into tb_user(username, age, ctm) values('m1', '11', now())");
		this.service2.m2();
	}
}
