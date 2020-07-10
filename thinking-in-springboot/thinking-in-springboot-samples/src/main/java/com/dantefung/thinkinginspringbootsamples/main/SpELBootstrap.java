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
import com.dantefung.thinkinginspringbootsamples.bean.Society;
import com.dantefung.thinkinginspringbootsamples.expression.ApprovalConditionObject;
import com.dantefung.thinkinginspringbootsamples.expression.ConditionParser;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: SpELBootstrap
 * @Description: spring expression language
 * https://www.baeldung.com/spring-expression-language
 *
 * EvaluationContext
 *
 * EvaluationContext接口在解析一个涉及属性，方法，字段或者类型转换时使用。Spring提供了两种实现：
 *
 * （1）SimpleEvaluationContext：为不需要SpEL语言语法的完整范围的表达式类别公开一个基本SpEL语言功能和配置选项的子集，并且应该进行有意义的限制。示例包括但不限于数据绑定表达式和基于属性的过滤器。
 *
 * SimpleEvaluationContext旨在仅支持SpEL语言语法的子集。它排除了Java类型引用，构造函数和bean引用。它还要求您明确选择表达式中属性和方法的支持级别。默认情况下，create()静态工厂方法仅启用对属性的读访问权限。
 *
 * （2）StandardEvaluationContext：公开全套SpEL语言功能和配置选项。您可以使用它来指定默认根对象并配置每个可用的与评估相关的策略。
 *
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
		String expression = "level=='1' && requestType==1 && shiftCount>=1";
		ApprovalConditionObject.ApprovalCondition condition = ApprovalConditionObject.ApprovalCondition.builder()
				.level("1").requestType(1).shiftCount(1).build();

		boolean result = parser.caculate(expression, new ApprovalConditionObject(condition));
		Assert.isTrue(result, "表达式执行结果为true");
	}

	@Test
	public void test0() {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'Hello World'.concat('!')");
		String message = (String) exp.getValue();
		System.out.println(message);
		exp = parser.parseExpression("'Hello World'.bytes");
		System.out.println((byte[]) exp.getValue());

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
		Expression expression = parser.parseExpression(greetingExp, new TemplateParserContext());
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

	/**
	 * 创建实例
	 * 使用new可以直接在SpEL中创建实例，需要创建实例的类要通过全限定名进行访问，如：new java.util.Date()
	 */
	@Test
	public void test5() {
		SpelExpressionParser elParser = new SpelExpressionParser();
		String condition = "new java.util.Date()";
		Expression expression = elParser.parseExpression(condition);
		StandardEvaluationContext context = new StandardEvaluationContext();
		System.out.println(expression.getValue(context, Date.class));

	}

	/**
	 * 类型
	 * SpEL中可以使用特定的Java类型，经常用来访问Java类型中的静态属性或静态方法，需要用T()操作符进行声明。括号中需要包含类名的全限定名，也就是包名加上类名。
	 * 唯一例外的是，SpEL内置了java.lang包下的类声明，也就是说java.lang.String可以通过T(String)访问，而不需要使用全限定名。
	 *
	 * 两个例子： T(Math).random() 或 T(java.lang.Math).random()
	 *
	 */
	@Test
	public void test6() {
		SpelExpressionParser elParser = new SpelExpressionParser();
		// String condition = "T(java.lang.Math).random()";
		// 调用静态方法
		String condition = "T(com.dantefung.thinkinginspringbootsamples.bean.SecurityUser).DEFAULT_ROLE_NAME";
		Expression expression = elParser.parseExpression(condition);
		StandardEvaluationContext context = new StandardEvaluationContext();
		System.out.println(expression.getValue(context, Object.class));
	}

	/**
	 * 二元操作符
	 * SpEL中的二元操作符同Java中的二元操作符，包括了数学运算符、位运算符、关系运算符等等。
	 *
	 * 三元操作符
	 * SpEL的三元操作符主要是 ** if else then ** 操作符 condition ? true statement : false statement与Java中的一致。
	 */
	@Test
	public void test7() {
		SpelExpressionParser elParser = new SpelExpressionParser();
		//String condition = "1+2";
		//String condition = " true && false";
		String condition = "true ? 'a' : 'b' ";
		Expression expression = elParser.parseExpression(condition);
		StandardEvaluationContext context = new StandardEvaluationContext();
		System.out.println(expression.getValue(context, Object.class));
	}

	/**
	 * 安全访问符
	 * 同样借鉴自Groovy，在SpEL中引入了安全访问符Safe Navigator Operator——?.，
	 * 解决了很大问题。相信每个Javaer都遇到过NullPointException的运行时异常，
	 * 通常是对象还未实例化或者找不到对象，却访问对象属性造成的。
	 * 通过安全访问符就可以避免这样的问题。
	 *
	 * String expStr = "thisMayBeNull.?property"
	 * 这句表达式在求值的时候，不会因为thisMayBeNull是Null值而抛出NullPointException，
	 * 而是会简单的返回null。个人认为此处结合Elvis操作符，是一个很完善的处理方式。
	 *
	 * DANTE FUNG NOTE: freemark也有
	 *
	 */
	@Test
	public void test8() {
		SecurityUser user = new SecurityUser();
		user.putExtraParams("moon", "月亮");
		user.putExtraParams("sun", "太阳");
		user.setSecurityUsers(Lists.newArrayList(new SecurityUser(1L, "rose"), new SecurityUser(2L, "jack"),
				new SecurityUser(3L, "alice")));

		ExpressionParser elParser = new SpelExpressionParser();
		String condition = "name?.name";
		Expression expression = elParser.parseExpression(condition);
		// 初始化Sp el表达式上下文，并设置 SecurityFun
		StandardEvaluationContext context = new StandardEvaluationContext(user);
		System.out.println(expression.getValue(context, String.class));

		String aleks = elParser.parseExpression("name = 'Alexandar Seovic'").getValue(context, String.class);
		System.out.println(aleks);

		// Elvis operator  是三目运算符的简写，用于groovy语言中。通常，如果使用三目运算符，会重复一个变量两次
		String name = elParser.parseExpression("userId?:'Unknown'").getValue(context, String.class);
		System.out.println(name);

		// 集合选择（Collection Selection）
		// list
		StandardEvaluationContext securityUsersContext = new StandardEvaluationContext(user);
		SecurityUser securityUser = elParser.parseExpression("securityUsers.?[name=='rose']")
				.getValue(securityUsersContext, SecurityUser.class);
		System.out.println(securityUser);
		// map
		StandardEvaluationContext extraParamsContext = new StandardEvaluationContext(user);
		Map newMap = elParser.parseExpression("extraParams.?[value=='月亮']").getValue(extraParamsContext, Map.class);
		System.out.println(newMap);
		// ------------ START 待处理 --------------------------
		// 获取第一个
		//Map first = elParser.parseExpression("extraParams^[...]").getValue(extraParamsContext, Map.class);
		//System.out.println(first);
		// 获取最后一个
		//Map last = elParser.parseExpression("extraParams$[...]").getValue(extraParamsContext, Map.class);
		//System.out.println(last);
		// ------------ END 待处理 --------------------------
		// 集合投影（Collection Projection ）
		Society society = new Society();
		society.setName("alice");
		society.getMembers().add(new SecurityUser(1L, "rose"));
		society.getMembers().add(new SecurityUser(2L, "jack"));
		society.getMembers().add(new SecurityUser(3L, "ben"));
		StandardEvaluationContext societyContext = new StandardEvaluationContext(user);
		societyContext.setRootObject(society);
		societyContext.setVariable("s", society);

		List<SecurityUser> list0 = (List<SecurityUser>) elParser.parseExpression("#s.Members").getValue(societyContext);
		System.out.println(list0);

		Object o = elParser.parseExpression("#root").getValue(societyContext);
		System.out.println(o);

		List<String> l = (List<String>) elParser.parseExpression("#root.Members.![name]").getValue(societyContext);
		System.out.println(l);

		//支持数学运算
		List<Integer> l2 = (List<Integer>) elParser.parseExpression("#root.Members.![#this.userId * 30] ")
				.getValue(societyContext);
		System.out.println(l2);


		//支持+
		Map<String, String> m = (Map<String, String>) elParser
				.parseExpression("{name:#root.Members.![name],userId:#root.Members.![userId]}")
				.getValue(societyContext);
		System.out.println(m);
		List<String> m2 = (List<String>) elParser.parseExpression("#root.Members.!['{name:' + #this.name + '}']")
				.getValue(societyContext);
		System.out.println(m2);

		//支持投影为一个map
		List<Map<String, String>> m3 = (List<Map<String, String>>) elParser
				.parseExpression("#root.Members.![ {x:#this.name,y:#this.userId}]").getValue(societyContext);
		System.out.println(m3);

		// 表达式模板化（Expression templating ）
		String randomPhrase = elParser
				.parseExpression("random number is #{T(java.lang.Math).random()}", new TemplateParserContext())
				.getValue(String.class);
		System.out.println(randomPhrase);
	}

	/**
	 * SpelCompiler 编译器
	 *
	 * 编译模式	说明
	 * OFF	不启用编译模式（默认）。可在 spring.properties 中通过 spring.expression.compiler.mode 属性进行全局设置。
	 * MIXED	在混合模式下，随着时间的推移，表达式会从解释模式自动切换到编译模式。即前面使用解释模式，当调用次数达到某个阈值后，改为使用编译模式。
	 * IMMEDIATE	启用编译模式。实际内部在调用第一次之后，才会真正使用编译模式。
	 */
	@Test
	public void test9() {
		//Spel 解析配置器
		SpelParserConfiguration configuration = new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE,
				SpELBootstrap.class.getClassLoader());
		//解析器
		SpelExpressionParser parser=new SpelExpressionParser(configuration);
		//上下文
		EvaluationContext context=new StandardEvaluationContext(new SecurityUser(1L, "rose"));
		//表达式
		String expression="getName()";
		//解析表达式
		SpelExpression spelExpression=parser.parseRaw(expression);
		System.out.println(spelExpression.getValue(context));
	}

}
