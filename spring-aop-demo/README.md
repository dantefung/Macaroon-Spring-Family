# Spring AOP + SPEL 
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

# Spring AOP + Annotation做审计日志
@SysLog: 用于记录系统所需的日志信息，其中content参数的内容中可以结合@SysLogType使用\
@SysLogType:标明入参的变量名，用于aop提取变量的值

## 案例：
```
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

```
# 调用链
文档地址: [ProjectTree](https://github.com/yueshutong/ProjectTree)

Project Tree不仅增加了对分布式接口的监控，还增加了pointcut表示式。

### 访问ProjectTree

启动你的项目，首先访问你项目的某个接口，使其执行被监控的方法。然后访问`localhost:8080/tree/project.html`查看网页。

#### 接口说明

| 接口                         | 说明                 |
| ---------------------------- | -------------------- |
| /tree/project.html         | 返回完整调用链视图     |
| /tree/project.html?all=1    | 返回全部方法视图     |
| /projecttree/all        | JSON形式的返回结果   |
| /projecttree/{methodId}      | 对某一方法的JSON结果 |
