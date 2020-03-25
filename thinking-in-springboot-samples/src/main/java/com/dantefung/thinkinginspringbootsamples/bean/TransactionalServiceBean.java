/*
 * Copyright (C), 2015-2018
 * FileName: TransactionalServiceBean
 * Author:   DANTE FUNG
 * Date:     2020/3/25 16:49
 * Description: @TransactionalService测试Bean
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/25 16:49   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.bean;

import com.dantefung.thinkinginspringbootsamples.annotation.TransactionalService;

/**
 * @Title: TransactionalServiceBean
 * @Description: @TransactionalService测试Bean
 * @author DANTE FUNG
 * @date 2020/3/25 16:49
 */
@TransactionalService(name = "transactionalServiceBean")
public class TransactionalServiceBean {

	public void save() {
		System.out.println("保存操作...");
	}
}
