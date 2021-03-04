package com.dantefung.beans.factory.support;


public class GenericBeanDefinition extends AbstractBeanDefinition {

	private String id;
	private Object bean;
	private String beanClassName;
	private boolean singleton = true;
	private boolean prototype = false;
	private String scope = SCOPE_DEFAULT;

	public GenericBeanDefinition(String id, String beanClassName) {

		this.id = id;
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public String getBeanClassName() {
		return this.beanClassName;
	}

	public boolean isSingleton() {
		return this.singleton;
	}

	public boolean isPrototype() {
		return this.prototype;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
		this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
		this.prototype = SCOPE_PROTOTYPE.equals(scope);

	}

	public Object getBean() {
		return bean;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

}