/*
 * Copyright (C), 2015-2018
 * FileName: WeightConverter
 * Author:   DANTE FUNG
 * Date:     2020/7/1 17:09
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/1 17:09   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.bean;

import org.springframework.core.convert.converter.Converter;

/**
 * @Title: WeightConverter
 * @Description:
 * @author DANTE FUNG
 * @date 2020/7/1 17:09
 */
public class WeightConverter implements Converter<String,Weight> {
	@Override
	public Weight convert(String source) {
		return new Weight(source);
	}
}
