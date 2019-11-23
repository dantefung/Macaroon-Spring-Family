package com.dantefung.aop.springaopdemo.web;

import com.dantefung.aop.springaopdemo.annotation.PreAuthorize;
import com.dantefung.aop.springaopdemo.annotation.SysLog;
import com.dantefung.aop.springaopdemo.annotation.SysLogType;
import com.dantefung.aop.springaopdemo.security.SecurityUser;
import com.dantefung.aop.springaopdemo.service.SysLogTestService;
import com.dantefung.aop.springaopdemo.utils.SpringContextUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * Spring el 文档地址：https://docs.spring.io/spring/docs/4.3.16.RELEASE/spring-framework-reference/htmlsingle/#expressions-operators-logical
 * </p>
 *
 * @author L.cm
 */
@RestController
public class TestController {

	/**
	 * 模拟登录
	 */
	@GetMapping
	public Object login(HttpSession session) {
		SecurityUser user = new SecurityUser();
		user.setName("Dreamlu");
		user.addRole("test");
		user.addPermission("get:role:list");
		session.setAttribute("user", user);
		return "登录成功请访问：\n http://localhost:8080/permission?test=123";
	}

	/**
	 * @ 表示读取Bean
	 * # 读取表达式参数，此处为Aop中设置的方法参数
	 * @sec 详见 SecurityBean
	 * <p>
	 * http://localhost:8080/permission?test=123
	 * </p>
	 */
	@GetMapping("permission")
	@PreAuthorize("hasPermission('get:role:list') and @sec.hasPermission(#test)")
	public Object hasPermission(String test) {
		return "test=" + test;
	}

	/**
	 * 权限判断函数请查看 SecurityFun
	 *
	 * http://localhost:8080/role?test=test&test1=admin
	 */
	@GetMapping("role")
	@PreAuthorize("hasRole(#test, #test1)")
	public Object hasRole(String test, String test1) {
		return "test=" + test + ", test1=" + test1;
	}

	/**
	 * 访问: http://localhost:8080/testSysLogAnnotation
	 *
	 * 控制台期望输出:
	 * >>>>>>>>>>>>>{"id":null,"userId":null,"opeatorType":"会员续费","operatorContent":"客户\"测试公司1\"续费了一年","operatorTime":"2019-11-23 17:36:51"}
	 *
	 *
	 * 演示service层使用@SysLog注解
	 * @return
	 */
	@GetMapping("testSysLogAnnotation")
	public Object testSysLogAnnotation() {
		SysLogTestService sysLogTestService = SpringContextUtils.getApplicationContext().getBean(SysLogTestService.class);
		sysLogTestService.saveRenewalLog("测试公司1");
		return "test!";
	}

	/**
	 * 演示service层使用@SysLog注解
	 *
	 * 访问: http://localhost:8080/doBiz
	 *
	 * 控制台期望输出:
	 * >>>>>>>>>>>>>{"id":null,"userId":null,"opeatorType":"会员续费","operatorContent":"客户\"测试公司2\"续费了一年","operatorTime":"2019-11-23 17:36:51"}
	 *
	 * @return
	 */
	@GetMapping("doBiz")
	public Object doBiz() {
		SysLogTestService sysLogTestService = SpringContextUtils.getApplicationContext().getBean(SysLogTestService.class);
		sysLogTestService.saveRenewalLog("测试公司2");
		return "test!";
	}

	/**
	 * 访问:http://localhost:8080/doLogAction?count=1&phone=13244445555
	 * 控制台期望输出:
	 *
	 *  >>>>>>>>>>>>>{"id":null,"userId":null,"opeatorType":"订单模块","operatorContent":"用户下了\"1\"张订单,联系人电话为\"13244445555\"","operatorTime":"2019-11-23 17:35:30"}
	 *
	 * 演示Controller层使用@SysLog注解
	 * @param count
	 * @return
	 */
	@GetMapping("doLogAction")
	@SysLog(type = "订单模块", content = "用户下了{count}张订单,联系人电话为{phone}", projectId = "2")
	public Object doLogAction(@SysLogType("count") String count, @SysLogType("phone")String phone) {
		return count;
	}
}
