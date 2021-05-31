package com.dantefung.springbootstatemachine.launch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 项目启动事件通知
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class StartedEventListener {

	@Async
	@Order(Ordered.LOWEST_PRECEDENCE - 1)
	@EventListener(WebServerInitializedEvent.class)
	public void afterStart(WebServerInitializedEvent event) throws UnknownHostException {
		WebServerApplicationContext context = event.getApplicationContext();
		Environment environment = context.getEnvironment();
		String appName = environment.getRequiredProperty("spring.application.name");
		String ip = InetAddress.getLocalHost().getHostAddress();
		int localPort = event.getWebServer().getPort();
		String profile = StringUtils.arrayToCommaDelimitedString(environment.getActiveProfiles());
		System.err.printf("---[%s]---启动完成，当前使用的端口:[%d]，环境变量:[%s]---%n", appName, localPort, profile);
		// 如果有 swagger，打印开发阶段的 swagger ui 地址
		if (ClassUtils.isPresent("springfox.documentation.spring.web.plugins.Docket", null)) {
			log.info("\n----------------------------------------------------------\n\t" +
					"Application local-consumer is running! Access URLs:\n\t" +
					"Local: \t\thttp://localhost:" + localPort + "/\n\t" +
					"External: \thttp://" + ip + ":" + localPort + "/\n\t" +
					"swagger-ui3.0: \thttp://" + ip + ":" + localPort + "/swagger-ui/index.html\n\t" +
					"Doc: \t\thttp://" + ip + ":" + localPort + "/doc.html\n\t");
		} else {
			System.out.printf("http://localhost:%s%n", localPort);
		}
	}

}