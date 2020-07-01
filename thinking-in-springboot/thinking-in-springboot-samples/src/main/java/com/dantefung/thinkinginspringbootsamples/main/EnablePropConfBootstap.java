/*
 * Copyright (C), 2015-2018
 * FileName: EnableHelloWorldBootstap
 * Author:   DANTE FUNG
 * Date:     2020/3/26 20:15
 * Description: 标注@EnableHelloWorld到引导类EnableHelloWorldBootstrap
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/3/26 20:15   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.bean.MailPropertiesA;
import com.dantefung.thinkinginspringbootsamples.bean.MailPropertiesB;
import com.dantefung.thinkinginspringbootsamples.bean.MailPropertiesC;
import com.dantefung.thinkinginspringbootsamples.bean.MailPropertiesD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;

/**
 * @Title: EnablePropConfBootstap
 * @author DANTE FUNG
 * @date 2020-7-1 17:17:01
 */
@SpringBootApplication
public class EnablePropConfBootstap {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(EnablePropConfBootstap.class, args);

		Binder binder = Binder.get(context.getEnvironment());

		// 绑定简单配置
		MailPropertiesA foo = binder.bind("kaka.cream.mail-a", Bindable.of(MailPropertiesA.class)).get();
		System.out.println(foo.getName());

		MailPropertiesC fooc = binder.bind("kaka.cream.mail-c", Bindable.of(MailPropertiesC.class)).get();
		System.out.println(fooc.getName());

		MailPropertiesB foob = binder.bind("kaka.cream.mail-b", Bindable.of(MailPropertiesB.class)).get();
		System.out.println(foob.getName());

		MailPropertiesD food = binder.bind("kaka.cream.mail-d", Bindable.of(MailPropertiesD.class)).get();
		System.out.println(food.getStmpServers());
		System.out.println(food.getPauseBetweenMails());
		System.out.println(food.getMaxAttachementWeight());
		System.out.println(food.getMaxAttachementSize());
		System.out.println(food.getHostName());

	}
}
