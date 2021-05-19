/*
 * Copyright (C), 2015-2020
 * FileName: UserService
 * Author:   DANTE FUNG
 * Date:     2021/5/19 17:57
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/19 17:57   V1.0.0
 */
package com.dantefung.springvalidation.service;

import com.dantefung.springvalidation.entity.User;

import java.util.List;

/**
 * @Title: UserService
 * @Description:
 * @author DANTE FUNG
 * @date 2021/05/19 17/57
 * @since JDK1.8
 */
public interface UserService {

	void saveUser(User user, List<Class<?>> group);
}
