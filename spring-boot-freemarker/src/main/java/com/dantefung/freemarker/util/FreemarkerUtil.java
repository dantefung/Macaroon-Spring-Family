/*
 * Copyright (C), 2015-2020
 * FileName: FreemarkerUtil
 * Author:   DANTE FUNG
 * Date:     2020/12/24 16:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/24 16:24   V1.0.0
 */
package com.dantefung.freemarker.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @Title: FreemarkerUtil
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/24 16/24
 * @since JDK1.8
 */
public class FreemarkerUtil {

	public static String parseTpl(String viewName, Map<String, Object> params) {
		Configuration cfg = SpringContextHolder.getBean(Configuration.class);
		String html = null;
		Template t = null;
		try {
			t = cfg.getTemplate(viewName + ".html");
			html = FreeMarkerTemplateUtils.processTemplateIntoString(t, params);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		return html;
	}
}
