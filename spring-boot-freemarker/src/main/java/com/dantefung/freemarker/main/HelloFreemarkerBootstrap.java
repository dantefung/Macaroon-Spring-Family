/*
 * Copyright (C), 2015-2020
 * FileName: HelloFreemarkerBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/12/24 15:04
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/24 15:04   V1.0.0
 */
package com.dantefung.freemarker.main;

import com.dantefung.freemarker.util.PathUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: HelloFreemarkerBootstrap
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/24 15/04
 * @since JDK1.8
 */
public class HelloFreemarkerBootstrap {

	private static final String DEST =
			PathUtil.getCurrentPath() + File.separator + "HelloWorld_CN_HTML_FREEMARKER_FS.pdf";
	private static final String TEMPLATES_DIR = "templates";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String FONT =
			PathUtil.getCurrentPath() + File.separator + "static" + File.separator + "simhei.ttf";
	private static final String LOGO_PATH = "file://" + PathUtil.getCurrentPath() + "static/";

	public static void main(String[] args) throws Exception {
		System.out.println(PathUtil.getCurrentPath());
		Configuration freemarkerCfg = new Configuration(Configuration.VERSION_2_3_30);
		// 设置Freemarker目录
		freemarkerCfg
				.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath() + File.separator + TEMPLATES_DIR));
		freemarkerCfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		// 获取模板文件
		Template template = freemarkerCfg.getTemplate("template_freemarker_fs.html", DEFAULT_ENCODING);
		Writer out = new StringWriter();
		Map<String, Object> data = new HashMap();
		data.put("name", "Jack");
		//将合并后的数据和模板写入到流中，这里使用的字符流
		template.process(data, out);
		out.flush();
		String finalText = out.toString();
		System.out.println(finalText);
		createPdf(finalText, DEST);
	}

	public static void createPdf(String content, String dest) throws IOException, DocumentException, Exception {
		ITextRenderer render = new ITextRenderer();
		ITextFontResolver fontResolver = render.getFontResolver();
		fontResolver.addFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// 方式一/URL方式生成PDF
		// render.setDocument(url);
		// 方式二/HTML代码字符串方式生成PDF
		render.setDocumentFromString(content);
		//解决图片相对路径的问题
		render.getSharedContext().setBaseURL(LOGO_PATH);
		render.layout();
		render.createPDF(new FileOutputStream(dest));
	}
}
