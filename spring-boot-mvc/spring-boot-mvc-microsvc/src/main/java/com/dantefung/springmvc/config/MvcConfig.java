package com.dantefung.springmvc.config;

import com.dantefung.springbootmvc.sign.CheckSignInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Autowired
	private CheckSignInterceptor checkSignInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 配置拦截路径
		registry.addInterceptor(checkSignInterceptor)
				// 开放给会员部的接口
				.addPathPatterns("/test/**");
				/** 其他开放给第三方的接口 .addPathPatterns("/xxx/**");**/
	}

}
