/*
 * Copyright (C), 2015-2020
 * FileName: ConvertHtml2PdfBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/12/25 14:15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/25 14:15   V1.0.0
 */
package com.dantefung.freemarker.main;

import com.dantefung.freemarker.util.PathUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @Title: ConvertHtml2PdfBootstrap
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/25 14/15
 * @since JDK1.8
 */
public class ConvertHtml2PdfBootstrap {

	private static final String DEST = PathUtil.getCurrentPath() + "/HelloWorld_CN_HTML.pdf";
	private static final String HTML = PathUtil.getCurrentPath() + "/templates/template.html";
	private static final String FONT = PathUtil.getCurrentPath() + "/static/simhei.ttf";

	public static void main(String[] args) throws IOException, DocumentException {
		XMLWorkerHelperHtmlToPDF();
	}

	private static void XMLWorkerHelperHtmlToPDF() throws DocumentException, IOException {
		// step 1
		Document document = new Document(PageSize.A2);
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
		// step 3
		document.open();
		document.addAuthor("pdf作者");
		document.addCreator("pdf创建者");
		document.addSubject("pdf主题");
		document.addCreationDate();
		document.addTitle("pdf标题,可在html中指定title");
		// step 4
		XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
		fontImp.register(FONT);
		// HTML文件生成PDF
		XMLWorkerHelper xmlWorkerHelper = XMLWorkerHelper.getInstance();
		// HTML字符串生成PDF
		//xmlWorkerHelper.parseXHtml(writer, document, new FileInputStream(HTML), null, Charset.forName("UTF-8"), fontImp);
		StringBuffer sb = new StringBuffer();
		BufferedReader br = new BufferedReader(new FileReader(new File(HTML)));
		String line = "";
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		String htmlString = sb.toString();
		xmlWorkerHelper.parseXHtml(writer, document, new ByteArrayInputStream(htmlString.getBytes("UTF-8")), null, Charset.forName("UTF-8"), fontImp);
		System.out.println(htmlString);
		// step 5
		document.close();
	}
}
