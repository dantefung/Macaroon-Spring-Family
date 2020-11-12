package com.dantefung.beans;

/**
 *  描述bean的bean,搜集元数据<br>
 *  <strong>作用:</strong><br>
 * <p>“bean的实例化，bean的依赖装配，bean的初始化，bean的方法调用，bean的销毁回收”。<br><br>
 *
 * Spring为什么能够准确无误的完成这波对bean的操作呢？答案很简单，就是：<br><br>
 *
 * “Spring掌握了有关bean的足够多的信息”。<br><br>
 *
 * Spring通过bean定义的概念收集到了bean的全部信息。<br><br>
 *
 * 首先是统一了编程模型，只要是围绕Spring的开发，包括框架自身的开发，最后大都转化为bean定义的注册。<br><br>
 *
 * <strong>统一编程模型:</strong><br>
 *
 *     既然Spring能把业务开发人员写的业务类作为bean来对待（即注册bean定义），<br>
 *     难道就不能把用来处理这些业务类的“系统类”（即Spring自身的类）也当作bean来对待吗？<br>
 *	   答案是完全可以，而且实际也是这样做的。<br><br>
 *
 *     就是业务开发人员可以使用@Component/@Bean注册bean定义，使用@Autowired装配bean依赖。框架开发人员在开发Spring框架时也可以这样使用。<br>
 *     这样就模式统一、写法统一、思维统一，当然很爽了。
 *
 * </p>
 *
 * @Author: DANTE FUNG
 * @Date: 2020/11/11 15:46
 * @since JDK 1.8
 */
public interface BeanDefinition {

	String SCOPE_SINGLETON = "singleton";
	String SCOPE_PROTOTYPE = "prototype";
	String SCOPE_DEFAULT = "";

	boolean isSingleton();

	boolean isPrototype();

	String getScope();

	void setScope(String scope);

	String getBeanClassName();
}