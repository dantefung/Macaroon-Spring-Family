package com.dantefung.sample.interceptor.dataperm;

/**
 * @Title: DataPermInvocationHandler
 * @Description: 数据权限回调处理器
 * @author DANTE FUNG
 * @date 2021/04/26 14/58
 * @since JDK1.8
 */
public interface DataPermInvocationHandler {

	boolean support(String funcPermCode);

	default String handle(String[] idArr) {
		return "and 1=1";
	}

	String handle(String funPermCode);
}
