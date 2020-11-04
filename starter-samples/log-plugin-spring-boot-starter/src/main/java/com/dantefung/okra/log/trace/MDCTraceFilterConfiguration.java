package com.dantefung.okra.log.trace;

import com.dantefung.okra.log.filter.MDCTraceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class MDCTraceFilterConfiguration {

	@Bean
	public FilterRegistrationBean registerAuthFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new MDCTraceFilter());
		registration.addUrlPatterns("/*");
		registration.setName("MDCTraceFilter");
		registration.setOrder(-1);  //值越小，Filter越靠前。
		return registration;
	}
}
