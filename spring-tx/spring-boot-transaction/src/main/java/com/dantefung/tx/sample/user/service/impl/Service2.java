/*
 * Copyright (C), 2015-2020
 * FileName: Service2
 * Author:   DANTE FUNG
 * Date:     2021/3/3 9:28 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/3 9:28 下午   V1.0.0
 */
package com.dantefung.tx.sample.user.service.impl;

import com.dantefung.tx.sample.user.dao.ModuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Title: Service2
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/03 21/28
 * @since JDK1.8
 */
@Component
public class Service2 {

	@Autowired
	private ModuleDao moduleDao;

	@Transactional(transactionManager = "tansactionManager2", propagation = Propagation.REQUIRED)
	public void m2() {
		moduleDao.addModule("service2" + Math.random());
	}
}
