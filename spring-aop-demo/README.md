## 简介
Spring内功心法 AOP + SPEL 组合之秘技。

基于Spring Aop 和 Spring el表达式的注解权限判断。

本项目的重点内容是让大家重视起Spring el的强大。

### 功能强大
1. 支持用户角色和权限表达式的判断，注解表达式支持 and or not 等复杂判断。
2. 支持方法参数作为注解表达式参数。
3. 支持Spring bean作为注解表达式判断。

### 示例代码
```java
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
 * @ 表示读取Spring Bean
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
```

### 链接
[Spring el 文档](https://docs.spring.io/spring/docs/4.3.16.RELEASE/spring-framework-reference/htmlsingle/#expressions-operators-logical)