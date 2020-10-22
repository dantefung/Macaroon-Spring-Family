/*
 * Copyright (C), 2015-2018
 * FileName: JsonValidator
 * Author:   DANTE FUNG
 * Date:     2020/10/22 22:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/10/22 22:33   V1.0.0
 */
package com.dantefung.troutertree.common.utils;

import com.dantefung.core.utils.JsonUtil;
import lombok.experimental.UtilityClass;

/**
 * @Title: JsonValidator
 * @Description:
 * @author DANTE FUNG
 * @date 2020/10/22 22:33
 */
@UtilityClass
public class JsonValidator {

	/**
	 * 判断是JsonObject
	 * @param obj
	 * @return
	 */
	public static boolean isJsonObject(Object obj){
		String content = obj.toString();
		try {
			JsonUtil.json2Obj(content, Object.class);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
