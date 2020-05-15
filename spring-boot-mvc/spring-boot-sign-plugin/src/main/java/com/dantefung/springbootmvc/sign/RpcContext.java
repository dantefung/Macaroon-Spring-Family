package com.dantefung.springbootmvc.sign;

import java.util.HashMap;
import java.util.Map;

/**
 * RPC上下文环境变量
 */
public class RpcContext {

	private RpcContext() {}
	public static final String RPC_HEADER_PREFIX = "X-OS-BOOT-RPC-";

	public static final String RPC_UID = "uid";

	private static final ThreadLocal<Map<String,String>> contextHolder =ThreadLocal.withInitial(HashMap<String, String>::new);
	
	public static void put(String key, String value) {
		contextHolder.get().put(key, value);
	}

	public static String get(String key) {
		return contextHolder.get().get(key);
	}
	public static String get(String key,String defaultValue) {
		return contextHolder.get().getOrDefault(key,defaultValue);
	}
	public static Map<String,String> getAll(){
		return contextHolder.get();
	}
	
	public static void putAll(Map<String,String> attributes) {
		contextHolder.set(attributes);
	}
	public static void clearAll() {
		contextHolder.get().clear();
	}
}
