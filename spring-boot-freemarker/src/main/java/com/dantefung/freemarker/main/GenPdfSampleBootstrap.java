/*
 * Copyright (C), 2015-2020
 * FileName: GenPdfSampleBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/12/25 11:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/25 11:26   V1.0.0
 */
package com.dantefung.freemarker.main;

import com.dantefung.freemarker.util.PathUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @Title: GenPdfSampleBootstrap
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/25 11/26
 * @since JDK1.8
 */
public class GenPdfSampleBootstrap {

	private static final String DEST = PathUtil.getCurrentPath() + "/HelloWorld_CN.pdf";
	private static final String FONT = PathUtil.getCurrentPath() + "/static/simhei.ttf";

	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
		document.open();
		Font f1 = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		document.add(new Paragraph("hello world,深入理解计算机系统", f1));
		document.close();
		writer.close();
	}
}
