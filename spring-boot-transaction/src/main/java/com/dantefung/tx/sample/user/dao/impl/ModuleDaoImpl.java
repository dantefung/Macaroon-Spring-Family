/*
 * Copyright (C), 2015-2020
 * FileName: ModuleDaoImpl
 * Author:   DANTE FUNG
 * Date:     2021/3/29 15:28
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/29 15:28   V1.0.0
 */
package com.dantefung.tx.sample.user.dao.impl;

import com.dantefung.tx.sample.user.dao.ModuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

/**
 * @Title: ModuleDaoImpl
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/29 15/28
 * @since JDK1.8
 */
@Repository
public class ModuleDaoImpl implements ModuleDao {

	@Qualifier("jdbcTemplate2")
	@Autowired
	private JdbcTemplate jdbcTemplate2;

	@Transactional(transactionManager = "tansactionManager2", propagation = Propagation.REQUIRED)
	public void addModule(String name) {
		jdbcTemplate2
				.update("insert into module(code,name,description) value (?,?,?)", UUID.randomUUID().toString(), name,
						UUID.randomUUID().toString());
	}

	@Override
	public Map getModuleById(int id) {
		return jdbcTemplate2.queryForMap("select * from module where id = ?", new Object[]{id});
	}

}
