/*
 * Copyright (C), 2015-2020
 * FileName: DB
 * Author:   DANTE FUNG
 * Date:     2021/1/9 12:23
 * Description: 模拟数据库
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/1/9 12:23   V1.0.0
 */
package com.dantefung.springbootcache.sample;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @Title: DB
 * @Description: 模拟数据库
 * @author DANTE FUNG
 * @date 2021/01/09 12/23
 * @since JDK1.8
 */
@Component
public class DB extends HashMap {

	public DB() {
		put("A", "A张三");
		put("B", "B张三");
		put("C", "C张三");
		put("D", "D张三");
		put("E", "E张三");
		put("F", "F张三");
		put("G", "G张三");
		put("H", "H张三");
		put("I", "I张三");
		put("J", "J张三");
	}


}
