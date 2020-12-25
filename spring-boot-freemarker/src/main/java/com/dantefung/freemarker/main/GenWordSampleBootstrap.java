/*
 * Copyright (C), 2015-2020
 * FileName: GenWordSampleBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/12/25 15:41
 * Description: Freemarker生成word文档
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/25 15:41   V1.0.0
 */
package com.dantefung.freemarker.main;

import com.dantefung.freemarker.entity.User;
import com.dantefung.freemarker.util.ImageUtil;
import com.dantefung.freemarker.util.PathUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: GenWordSampleBootstrap
 * @Description: Freemarker生成word文档
 * 参见: https://blog.csdn.net/weixin_44516305/article/details/88049964
 * @author DANTE FUNG
 * @date 2020/12/25 15/41
 * @since JDK1.8
 */
public class GenWordSampleBootstrap {

	public static void main(String[] args) throws Exception {
		generateWord(getWordData(), PathUtil.getCurrentPath() + "/User.doc");
	}

	/**
	 * 使用FreeMarker自动生成Word文档
	 * @param dataMap   生成Word文档所需要的数据
	 * @param fileName  生成Word文档的全路径名称
	 */
	public static void generateWord(Map<String, Object> dataMap, String fileName) throws Exception {
		// 设置FreeMarker的版本和编码格式
		Configuration configuration = new Configuration(new Version("2.3.28"));
		configuration.setDefaultEncoding("UTF-8");

		// 设置FreeMarker生成Word文档所需要的模板的路径
		configuration.setDirectoryForTemplateLoading(new File(PathUtil.getCurrentPath() + "/templates"));
		// 设置FreeMarker生成Word文档所需要的模板
		Template t = configuration.getTemplate("ot_apply_tpl.xml", "UTF-8");
		// 创建一个Word文档的输出流
		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)), "UTF-8"));
		//FreeMarker使用Word模板和数据生成Word文档
		t.process(dataMap, out);
		out.flush();
		out.close();
	}

	/**
	 * 获取生成Word文档所需要的数据
	 */
	private static Map<String, Object> getWordData() {
		/*
		 * 创建一个Map对象，将Word文档需要的数据都保存到该Map对象中
		 */
		Map<String, Object> dataMap = new HashMap<>();

		/*
		 * 直接在map里保存一个用户的各项信息
		 * 该用户信息用于Word文档中FreeMarker普通文本处理
		 * 模板文档占位符${name}中的name即指定使用这里的name属性的值"用户1"替换
		 */
		dataMap.put("dept", "大数据部");
		dataMap.put("name", "张三");
		dataMap.put("position", "总经理");

		/**
		 * 将用户的各项信息封装成对象，然后将对象保存在map中，
		 * 该用户对象用于Word文档中FreeMarker表格和图片处理
		 * 模板文档占位符${userObj.name}中的userObj即指定使用这里的userObj属性的值(即user2对象)替换
		 */
		User user2 = new User();
		user2.setName("用户2");
		user2.setDept("说逗谈唱部");
		user2.setPosition("中二主任");
		// 使用FreeMarker在Word文档中生成图片时，需要将图片的内容转换成Base64编码的字符串
		user2.setPhoto(ImageUtil.getImageBase64String(PathUtil.getCurrentPath() + "/static/logo.png"));
		dataMap.put("userObj", user2);

		/*
		 * 将多个用户对象封装成List集合，然后将集合保存在map中
		 * 该用户集合用于Word文档中FreeMarker表单处理
		 * 模板文档中使用<#list userList as user>循环遍历集合，即指定使用这里的userList属性的值(即userList集合)替换
		 */
		List<User> userList = new ArrayList<>();
		User user3 = new User();
		user3.setName("用户3");
		user3.setDept("说逗谈唱部3");
		user3.setPosition("中二主任3");
		User user4 = new User();
		user4.setName("用户4");
		user4.setDept("说逗谈唱部4");
		user4.setPosition("中二主任4");
		userList.add(user3);
		userList.add(user4);
		dataMap.put("userList", userList);

		return dataMap;
	}
}
