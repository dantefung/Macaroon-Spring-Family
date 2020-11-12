/*
 * Copyright (C), 2015-2020
 * FileName: SpringControllerValidateBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/11/12 15:14
 * Description: 在 Spring controller 方法参数中使用 valid 或 validated 注解标注待校验参数
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/12 15:14   V1.0.0
 */
package com.dantefung.springvalidation.bootstrap;

import com.dantefung.springvalidation.annotation.ListValue;
import com.dantefung.springvalidation.utils.LocaleUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * @Title: SpringControllerValidateBootstrap
 * @Description: 在 Spring controller 方法参数中使用 valid 或 validated 注解标注待校验参数
 * @author DANTE FUNG
 * @date 2020/11/12 15/14
 * @since JDK1.8
 */
@Slf4j
@RestController
@SpringBootApplication(scanBasePackages = {"com.dantefung.*"})
public class SpringControllerValidateBootstrap extends SpringBootServletInitializer
		implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication
				.run(SpringControllerValidateBootstrap.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = StringUtils.defaultString(env.getProperty("server.port"), "8086");
		String applicationName = env.getProperty("spring.application.name");
		log.info("\n----------------------------------------------------------\n\t" + "Application " + applicationName
				+ " is running! Access URLs:\n\t" + "Local: \t\thttp://localhost:" + port + "/\n\t"
				+ "External: \thttp://" + ip + ":" + port + "/\n\t" + "swagger-ui: \thttp://" + ip + ":" + port
				+ "/swagger-ui/index.html\n\t" + "Doc: \t\thttp://" + ip + ":" + port + "/doc.html\n\t");
	}

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		factory.setPort(8086);
	}

	/**
	 * @Description: 访问http://localhost:8086/validate?username=&password=123&key=,可看到校验效果！
	 * @param userModel:
     * @param bindingResult:
	 * @Author: DANTE FUNG
	 * @Date: 2020/11/12 16:26
	 * @since JDK 1.8
	 * @return: java.lang.String
	 */
	@RequestMapping("validate")
	public String validate(@Validated UserModel userModel, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			fieldErrors.forEach(fieldError -> {
				//日志打印不符合校验的字段名和错误提示
				log.error("error field is : {} ,message is : {}", fieldError.getField(),
						fieldError.getDefaultMessage());
			});
			for (int i = 0; i < fieldErrors.size(); i++) {
				//控制台打印不符合校验的字段名和错误提示
				System.out.println(
						"error field is :" + fieldErrors.get(i).getField() + ",message is :" + fieldErrors.get(i)
								.getDefaultMessage());
			}

		}
		return "validated ! print by BindingResult.";
	}

	@RequestMapping("valid")
	public String valid(@Validated UserModel userModel) {
		return "valid!";
	}

	@RequestMapping("i18n")
	public String i18n() {
		String message = LocaleUtil.getMessage("NotBlank.userModel.key", "[测试国际化]");
		System.out.println(message);
		return message;
	}

	@Data
	static class UserModel {
		@NotNull(message = "username can not be null")
		@Pattern(regexp = "[a-zA-Z0-9_]{5,10}", message = "username is illegal")
		private String username;
		@Size(min = 5, max = 10, message = "password's length is illegal")
		private String password;
		@NotBlank(message = "{NotBlank.userModel.key}")
		private String key;
		@ListValue(message = "{ListValue.userModel.gender}", vals = {1, 2})
		private Integer gender;
	}

}
