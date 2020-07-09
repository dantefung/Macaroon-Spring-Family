/*
 * Copyright (C), 2015-2018
 * FileName: SpELBootstrap
 * Author:   DANTE FUNG
 * Date:     2020/7/9 13:59
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/7/9 13:59   V1.0.0
 */
package com.dantefung.thinkinginspringbootsamples.main;

import com.dantefung.thinkinginspringbootsamples.ThinkingInSpringbootSamplesApplication;
import com.dantefung.thinkinginspringbootsamples.bean.SecurityFun;
import com.dantefung.thinkinginspringbootsamples.bean.SecurityUser;
import com.dantefung.thinkinginspringbootsamples.expression.ApprovalConditionObject;
import com.dantefung.thinkinginspringbootsamples.expression.ConditionParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

/**
 * @Title: SpELBootstrap
 * @Description: spring expression
 * @author DANTE FUNG
 * @date 2020/7/9 13:59
 */
@SpringBootTest(classes = ThinkingInSpringbootSamplesApplication.class)
public class SpELBootstrap {

	@Autowired
	private ConditionParser parser;
	@Autowired
	private ApplicationContext applicationContext;

	@Test
	public void test() {
		//如果保存的表达式用#{}包裹，使用的时候要先进行清除
		String expression="level=='1' && requestType==1 && shiftCount>=1";
		ApprovalConditionObject.ApprovalCondition condition = ApprovalConditionObject.ApprovalCondition.builder().level("1").requestType(1).shiftCount(1).build();

		boolean result = parser.caculate(expression,new ApprovalConditionObject(condition));
		Assert.isTrue(result,"表达式执行结果为true");
	}

	@Test
	public void test0() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello World'.concat('!')");
		String message = (String) exp.getValue();
		System.out.println(message);
		exp = parser.parseExpression("'Hello World'.bytes");
		System.out.println( (byte[]) exp.getValue());

	}

	@Test
	public void test1() {
		// 1、创建一个模板表达式，所谓模板就是带字面量和表达式的字符串。其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
		String greetingExp = "Hello, #{ #user }";
		// 2、创建表达式解析器，SpEL框架创建了一个语言无关的处理框架，所以对于其他的表达式语言，完全可以创建不同的ExpressionParser。在这里我们学习的是SpEL所以使用SpelExpressionParser()
		ExpressionParser parser = new SpelExpressionParser();
		// 3、过evaluationContext.setVariable可以在上下文中设定变量。
		EvaluationContext context = new StandardEvaluationContext();
		context.setVariable("user", "Gangyou");
		/*
		 * 4、解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
		 * 如果不传入模板解析器上下文，指定表达式为模板，那么表达式字符串Hello, #{ #user }，解析器会首先去尝试解析Hello。
		 * 例子中的模板表达式，与'Hello, ' + #user是等价的。
		 */
		Expression expression = parser.parseExpression(greetingExp,
				new TemplateParserContext());
		// 5、使用Expression.getValue()获取表达式的值，这里传入了Evalution上下文，第二个参数是类型参数，表示返回值的类型。
		System.out.println(expression.getValue(context, String.class));

	}

	@Test
	public void test2() {

		SecurityUser user = new SecurityUser();
		user.setName("DANTE FUNG");
		user.addRole("test");
		user.addPermission("get:role:list");

		ExpressionParser elParser = new SpelExpressionParser();
		String condition = "hasPermission('get:role:list') and @sec.hasPermission(#test)";
		Expression expression = elParser.parseExpression(condition);
		// 初始化Sp el表达式上下文，并设置 SecurityFun
		StandardEvaluationContext context = new StandardEvaluationContext(new SecurityFun(user));
		// 设置表达式支持spring bean
		context.setBeanResolver(new BeanFactoryResolver(applicationContext));
		context.setVariable("test", "123");
		System.out.println(expression.getValue(context, Boolean.class));
	}

	/**
	 * 属性访问
	 */
	@Test
	public void test3() {

		SecurityUser user = new SecurityUser();
		user.setName("DANTE FUNG");
		user.addRole("test");
		user.addPermission("get:role:list");

		ExpressionParser elParser = new SpelExpressionParser();
		String condition = "name + ' ' + roles + ' ' + permissions";
		Expression expression = elParser.parseExpression(condition);
		// 初始化Sp el表达式上下文，并设置 SecurityFun
		StandardEvaluationContext context = new StandardEvaluationContext(user);
		// 设置表达式支持spring bean
		context.setBeanResolver(new BeanFactoryResolver(applicationContext));
		System.out.println(expression.getValue(context, String.class));
	}

	/**
	 * 数组、列表和Map
	 * 数组和列表都可以用过数字下标进行访问，比如list[0]。
	 *
	 * Map的访问就类似JavaScript的访问方式，使用key访问。
	 *
	 * 例如java代码map.put("name", "gangyou")其中的map对象，可以通过map['name']获取到字符串gangyou。
	 *
	 */
	@Test
	public void test4() {
		SecurityUser user = new SecurityUser();
		user.setName("DANTE FUNG");
		user.addRole("test1");
		user.addRole("test2");
		user.addPermission("get:role:list");
		user.putExtraParams("one", "1");
		user.putExtraParams("two", "2");
		user.putExtraParams("three", "3");

		ExpressionParser elParser = new SpelExpressionParser();
		String condition = "roles[0] + '-' + roles[1] + ' <==> ' + extraParams['two']";
		Expression expression = elParser.parseExpression(condition);
		// 初始化Sp el表达式上下文，并设置 SecurityFun
		StandardEvaluationContext context = new StandardEvaluationContext(user);
		System.out.println(expression.getValue(context, String.class));
	}
}
