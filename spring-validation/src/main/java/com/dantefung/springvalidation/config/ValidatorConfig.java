package com.dantefung.springvalidation.config;

import com.dantefung.springvalidation.utils.LocaleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


@Configuration
@ConfigurationProperties(prefix = "spring.validator")
public class ValidatorConfig {

	@Autowired
	private MessageSource messageSource;

	//private String failFast = "true";

	private String failFast = "false";

	public String getFailFast() {
		return failFast;
	}

	public void setFailFast(String failFast) {
		this.failFast = failFast;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
		factoryBean.setValidationMessageSource(messageSource);
		factoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", failFast);
		return factoryBean;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}

	public class MyLocaleResolver implements LocaleResolver {

		@Override
		public Locale resolveLocale(HttpServletRequest request) {
			//此处可以根据项目需要修改代码，比如使用session或cookie
			String lang = getLocaleFromCookies(request);
			Locale locale = new Locale(lang);
			return locale;
		}

		@Override
		public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

		}

		private String getLocaleFromCookies(HttpServletRequest request) {
			Cookie[] cookies = request.getCookies();

			if (cookies == null) {
				return null;
			}

			for (int i = 0; i < cookies.length; i++) {
				if (LocaleUtil.KEY_LANG.equals(cookies[i].getName())) {
					return cookies[i].getValue();
				}
			}
			return null;
		}

	}
}

