/*
 * Copyright (C), 2015-2020
 * FileName: ConvertHtml2ImgBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/12/25 15:01
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/25 15:01   V1.0.0
 */
package com.dantefung.freemarker.main;

import com.dantefung.freemarker.util.PathUtil;
import gui.ava.html.Html2Image;
import gui.ava.html.renderer.ImageRenderer;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @Title: ConvertHtml2ImgBootstrap
 * @Description:
 * @author DANTE FUNG
 * @date 2020/12/25 15/01
 * @since JDK1.8
 */
public class ConvertHtml2ImgBootstrap {

	public static final String DEST = PathUtil.getCurrentPath() + "/test.png";

	public static void main(String[] args) throws FileNotFoundException {
		String html = "<div style=\"width: 500px; height: 500px;\"><img src=\"http://car0.autoimg.cn/upload/spec/8090/u_20101129100303187264.jpg\" width=\"350\" height=\"233\"><a href=\"#\" style=\"color: red;\">testImage</a></div>";
		Html2Image html2Image = Html2Image.fromHtml(html.toString());
		// 这边如果设置false,图片不会自动根据内容设置长宽默认长1024 ,宽768
		ImageRenderer imageRenderer = html2Image.getImageRenderer();
		imageRenderer.saveImage(new File(DEST));
	}



}
