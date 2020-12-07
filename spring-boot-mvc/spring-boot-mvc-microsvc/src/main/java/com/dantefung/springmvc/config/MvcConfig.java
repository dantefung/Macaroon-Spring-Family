package com.dantefung.springmvc.config;

import com.dantefung.springbootmvc.resolver.TokenInfoHandlerMethodArgumentResolver;
import com.dantefung.springbootmvc.sign.CheckSignInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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

	/**
	 * 此处获取拦截器实例化对象，同理拦截器
	 * @return
	 */
	@Bean
	public TokenInfoHandlerMethodArgumentResolver getLoginUserHandlerMethodArgumentResolver(){
		return new TokenInfoHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
		argumentResolvers.add(getLoginUserHandlerMethodArgumentResolver());
	}

}
