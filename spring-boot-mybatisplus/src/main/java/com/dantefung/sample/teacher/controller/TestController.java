/*
 * Copyright (C), 2015-2018
 * FileName: TestController
 * Author:   DANTE FUNG
 * Date:     2020/4/27 16:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/27 16:44   V1.0.0
 */
package com.dantefung.sample.teacher.controller;

import com.dantefung.sample.teacher.domain.User;
import com.dantefung.sample.teacher.domain.UserExample;
import com.dantefung.sample.teacher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title: TestController
 * @Description:
 * @author DANTE FUNG
 * @date 2020/4/27 16:44
 */
@RestController
public class TestController {

	@Autowired
	private UserService userService;

	/**
	 * QUERY OBJECT PATTERN
	 * Object Query is a simple query builder thought for java,
	 * that allow to write typesafe and refactor resistant query,
	 * without bound to persistence engine.
	 * @return
	 */
	@GetMapping("/selectByExample")
	public List<User> selectByExample() {
		UserExample userExample = new UserExample();
		UserExample.Criteria criteria = userExample.createCriteria();
		criteria.andIdEqualTo(1L);
		userExample.setDistinct(Boolean.TRUE);
		userExample.setOrderByClause("create_time DESC");
		// criteria 和 criteria2 的条件会用OR连接
		UserExample.Criteria criteria2 = userExample.or();
		criteria2.andIdIsNotNull();
		return userService.selectByExample(userExample);
	}

	/**
	 * ==>  Preparing: select distinct id, login_name, `name`, `password`, salt, sex, age, phone, user_type, `status`, organization_id, create_time from user WHERE ( id = ? ) or( id is not null ) order by create_time DESC
	 * ==> Parameters: 1(Long)
	 * <==    Columns: id, login_name, name, password, salt, sex, age, phone, user_type, status, organization_id, create_time
	 * <==        Row: 1, admin, admin, 9283a03246ef2dacdc21a9b137817ec1, test, 0, 25, 18707173376, 0, 0, 1, 2015-12-06 13:14:05
	 * <==        Row: 15, test, test, 05a671c66aefea124cc08b76ea6d30bb, test, 0, 25, 18707173376, 1, 0, 6, 2015-12-06 13:13:03
	 * <==        Row: 14, dreamlu, dreamlu, 05a671c66aefea124cc08b76ea6d30bb, test, 0, 25, 18707173376, 1, 0, 5, 2015-10-11 23:12:58
	 * <==        Row: 13, snoopy, snoopy, 05a671c66aefea124cc08b76ea6d30bb, test, 0, 25, 18707173376, 1, 0, 3, 2015-10-01 13:12:07
	 * <==      Total: 4
	 */
}
