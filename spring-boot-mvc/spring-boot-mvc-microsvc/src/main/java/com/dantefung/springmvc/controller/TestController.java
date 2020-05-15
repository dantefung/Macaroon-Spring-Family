/*
 * Copyright (C), 2015-2018
 * FileName: TestController
 * Author:   DANTE FUNG
 * Date:     2020/5/8 13:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/5/8 13:50   V1.0.0
 */
package com.dantefung.springmvc.controller;

import com.dantefung.springbootmvc.annotation.RequireSign;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: TestController
 * @Description:
 * @author DANTE FUNG
 * @date 2020/5/8 13:50
 */
@RestController
@RequestMapping("/test")
public class TestController {

	@PostMapping("/ok")
	@RequireSign
	public Map ok() {
		Map map = new HashMap();
		map.put("code","200");
		map.put("success", true);
		map.put("result", null);
		return map;
	}
}
