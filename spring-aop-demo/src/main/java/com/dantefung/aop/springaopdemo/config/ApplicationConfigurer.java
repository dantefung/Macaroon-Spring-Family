package com.dantefung.aop.springaopdemo.config;

import com.dantefung.aop.springaopdemo.core.interceptor.HandlerMethodReturnValueHandlerWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnProperty(
		value="configuration.swith.request-handler",
		matchIfMissing = true)
public class ApplicationConfigurer implements InitializingBean {
	@Autowired
	private RequestMappingHandlerAdapter adapter;

	public void afterPropertiesSet() throws Exception {
		decorateAdapter();
	}

	// 篡改默认的RequestMappingHandlerAdapter
	private void decorateAdapter() {
		List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();// 这是unmodifiableList
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(returnValueHandlers);
		this.decorateHandlers(handlers);
		adapter.setReturnValueHandlers(handlers);
	}

	// 替换掉RequestResponseBodyMethodProcessor的实现
	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler instanceof RequestResponseBodyMethodProcessor) {
				HandlerMethodReturnValueHandlerWrapper decorator = new HandlerMethodReturnValueHandlerWrapper(handler);
				int index = handlers.indexOf(handler);
				handlers.set(index, decorator);
				break;
			}
		}
	}
}
