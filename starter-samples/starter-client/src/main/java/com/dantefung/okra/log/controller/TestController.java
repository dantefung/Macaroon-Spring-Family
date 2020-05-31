package com.dantefung.okra.log.controller;

import com.dantefung.okra.log.annontation.SysLog;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

	@SysLog("title is ok!")
	@RequestMapping("/ok")
	public String ok() {
		return "ok!";
	}
}
