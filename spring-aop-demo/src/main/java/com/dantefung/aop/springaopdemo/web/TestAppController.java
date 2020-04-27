/*
 * Copyright (C), 2015-2018
 * FileName: TestAppController
 * Author:   DANTE FUNG
 * Date:     2020/4/27 14:46
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/27 14:46   V1.0.0
 */
package com.dantefung.aop.springaopdemo.web;

import com.dantefung.aop.springaopdemo.annotation.AppControllerMapping;
import com.dantefung.aop.springaopdemo.annotation.LogAppSysEvent;
import com.dantefung.aop.springaopdemo.annotation.ResultKey;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Title: TestAppController
 * @Description:
 * @author DANTE FUNG
 * @date 2020/4/27 14:46
 */
@AppControllerMapping("/test/app")
public class TestAppController {

	private static final String APP_SYS_ID = "xxxxxx";
	private static final String EVENT_CODE = "yyyyyy";
	private static final String REMARK = "emememememememe.....";

	/**
	 * http://localhost:8080/test/app/logRecord?id=xxxx&userCode=123456789
	 * @param id
	 * @param userCode
	 * @return
	 */
	@GetMapping("/logRecord")
	@LogAppSysEvent(appsysid = APP_SYS_ID, eventcode = EVENT_CODE, remark = REMARK)
	public String logRecord(String id,String userCode) {
		return "ok!";
	}

	@GetMapping("/retWrapperTest")
	@ResultKey
	public String retWrapperTest() {
		return "retWrapperTest success!";
	}
}
