/*
 * Copyright (C), 2015-2020
 * FileName: TestController
 * Author:   DANTE FUNG
 * Date:     2020/12/24 14:32
 * Description: test controller
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/24 14:32   V1.0.0
 */
package com.dantefung.freemarker.controller;

import com.dantefung.freemarker.util.FreemarkerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title: TestController
 * @Description: test controller
 * @author DANTE FUNG
 * @date 2020/12/24 14/32
 * @since JDK1.8
 */
@Controller
@RequestMapping
public class TestController {

	@GetMapping
	public String index(Model model) {
		Map map = new LinkedHashMap<>();
		for (int i = 0; i < 5; i++) {
			map.put("key" + i, "value" + i);
		}
		model.addAttribute("list", Arrays.asList("string1", "string2", "string3", "string4", "string5", "string6"));
		model.addAttribute("map", map);
		model.addAttribute("name", "   htTps://wWw.zHyD.mE   ");
		model.addAttribute("htmlText", "<span style=\"color: red;font-size: 16px;\">html内容</span>");
		model.addAttribute("num", 123.012);
		model.addAttribute("null", null);
		model.addAttribute("dateObj", new Date());
		model.addAttribute("bol", true);
		return "index";
	}

	@RequestMapping("/createHtml")
	@ResponseBody
	public String createHtml(Model model){
		Map map = new LinkedHashMap<>();
		for (int i = 0; i < 5; i++) {
			map.put("key" + i, "value" + i);
		}
		model.addAttribute("list", Arrays.asList("string1", "string2", "string3", "string4", "string5", "string6"));
		model.addAttribute("map", map);
		model.addAttribute("name", "   htTps://wWw.zHyD.mE   ");
		model.addAttribute("htmlText", "<span style=\"color: red;font-size: 16px;\">html内容</span>");
		model.addAttribute("num", 123.012);
		model.addAttribute("null", null);
		model.addAttribute("dateObj", new Date());
		model.addAttribute("bol", true);
		return FreemarkerUtil.parseTpl("index", model.asMap());
	}


	@GetMapping("/hello")
	public ModelAndView helloView() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", "Joke");
		mv.setViewName("template_freemarker");
		return mv;
	}

	@GetMapping("/hello2")
	public ModelAndView hello2View() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", "Mike");
		mv.setViewName("template_freemarker_fs");
		return mv;
	}

}
