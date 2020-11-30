package com.dantefung.springevent.notify;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import lombok.extern.slf4j.Slf4j;

/**
 * 本地bean方法調用 jvm://beanclassname/method?key1=&key2=
 * ps:这里的beanclass和method的定义有些受限 限制有：
 * 1、beanclass是完整的类路径（可以是interface），且作为一个springbean被spring容器管理
 * 2、method是public声明，且一个类里面不能同名，因为这里只用了methodName来识别方法
 */
@Slf4j
public class JvmNotifyHandler implements RetryCallback<Void, Exception> {

	private Map<String, String> params;

	private Object bean;

	private Method method;
	
	private PlatformTransactionManager transactionManager;
	

	public JvmNotifyHandler(ApplicationContext context, NotifyUri notifyUri) {
		String url = notifyUri.getUrl();
		this.params = notifyUri.getQuery();
		url = url.replace(NotifyUri.JVM_PROTOCOL, "");// 刪掉 jvm://
		String body = url.substring(0, url.indexOf('?'));
		String[] strs = body.split("/");
		try {
			Class<?> beanClass = Class.forName(strs[0]);
			String beanMethod = strs[1];
			this.bean = context.getBean(beanClass);
			Method[] methods = beanClass.isInterface() ? beanClass.getMethods() : beanClass.getDeclaredMethods();
			for (Method m : methods) {
				if (beanMethod.equals(m.getName())) {
					this.method = m;
				}
			}
		} catch (ClassNotFoundException e) {
			log.error("", e);
			throw new IllegalStateException(String.format("类%s未定义", strs[0]));
		}
		transactionManager=context.getBean(PlatformTransactionManager.class);
	}

	@Override
	public Void doWithRetry(RetryContext context) throws Exception {
		if(context.getLastThrowable()!=null) {
			log.error("",context.getLastThrowable());
		}
		log.info("第{}次 发起请求:{}", context.getRetryCount() + 1, bean.getClass());
		List<Object> methodParams = new ArrayList<>(this.params.size());
		for (Map.Entry<String, String> entry : this.params.entrySet()) {
			methodParams.add(entry.getValue());
		}
		TransactionStatus status =transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
		try {
			method.invoke(this.bean, methodParams.toArray());
			transactionManager.commit(status);
        } catch (Exception e) {
        	// 回滚
        	transactionManager.rollback(status);
        	throw e;
        }
		
		return null;
	}

}
