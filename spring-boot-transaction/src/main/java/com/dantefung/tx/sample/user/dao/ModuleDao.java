/*
 * Copyright (C), 2015-2020
 * FileName: ModuleDao
 * Author:   DANTE FUNG
 * Date:     2021/3/29 15:23
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/29 15:23   V1.0.0
 */
package com.dantefung.tx.sample.user.dao;

import java.util.Map;

/**
 * @Title: ModuleDao
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/29 15/23
 * @since JDK1.8
 */
public interface ModuleDao {

	void addModule(String name);

	Map getModuleById(int id);
}
