/*
 * Copyright (C), 2015-2020
 * FileName: UserService
 * Author:   DANTE FUNG
 * Date:     2021/3/4 10:06 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/4 10:06 下午   V1.0.0
 */
package com.dantefung.aware;

import com.dantefung.beans.factory.BeanNameAware;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: UserService
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/04 22/06
 * @since JDK1.8
 */
@Slf4j
public class UserService implements BeanNameAware {

	private String name;

	@Override
	public void setBeanName(String name) {
		this.name = name;
		log.info("BeanNameAware -> name: {}", name);
	}


}
