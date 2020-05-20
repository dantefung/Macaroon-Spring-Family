/*
 * Copyright (C), 2015-2018
 * FileName: EmbedTomcatSimulateSample
 * Author:   DANTE FUNG
 * Date:     2020/5/19 17:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/5/19 17:40   V1.0.0
 */
package com.dantefung.springmvc.samples;

import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

/**
 * @Title: EmbedTomcatSimulateSample
 * @Description:
 * @author DANTE FUNG
 * @date 2020/5/19 17:40
 */
public class EmbedTomcatSimulateSample {

	public static void main(String[] args) {
		ServletWebServerApplicationContext applicationContext = new AnnotationConfigServletWebServerApplicationContext();
		applicationContext.refresh();
	}

}
