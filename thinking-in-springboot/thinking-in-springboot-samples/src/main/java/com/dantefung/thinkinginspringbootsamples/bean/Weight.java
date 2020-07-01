/*
 * Copyright (C), 2015-2018
 * FileName: Weight
 * Author:   DANTE FUNG
 * Date:     2020/7/1 17:07
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/1 17:07   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.bean;

import lombok.Setter;
import lombok.ToString;

/**
 * @Title: Weight
 * @Description:
 * @author DANTE FUNG
 * @date 2020/7/1 17:07
 */
@ToString
public class Weight {

	public Weight(String name) {
		this.name = name;
	}

	@Setter
	private String name;
}
