/*
 * Copyright (C), 2015-2020
 * FileName: User
 * Author:   DANTE FUNG
 * Date:     2020/12/25 17:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/25 17:15   V1.0.0
 */
package com.dantefung.freemarker.entity;

import lombok.Data;

/**
 * @Title: User
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/25 17/15
 * @since JDK1.8
 */
@Data
public class User {

	private String dept;

	private String position;

	private String name;
	/**
	 * base64格式
	 */
	private String photo;
}
