package com.dantefung.springevent.notify;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class NotifyUri implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9128830973613801192L;
	//lb://service-id/path?key1=&key2=
	public static final String LB_PROTOCOL = "lb://";
	//http://hostname/path?key1=&key2=
	public static final String HTTP_PROTOCOL = "http://";
	//https://hostname/path?key1=&key2=
	public static final String HTTPS_PROTOCOL = "https://";
	// jvm://com.dantefung.beanclass/method?key1=&key2=
	public static final String JVM_PROTOCOL = "jvm://";
	//mq://topic?key1=&key2= 未实现
	public static final String MQ_PROTOCOL = "mq://";
	/**
	 * 通信协议
	 */
	private NotifySchema schema;
	/**
	 * 带参数的url
	 */
	private String url;
	/**
	 * notifyUrl里的通知参数 参数有序
	 */
	private LinkedHashMap<String, String> query;
	/**
	 * 额外参数
	 */
	private Map<String,String> extras;
	
	public NotifyUri(String uri,Map<String,String> extras) {
		parse(uri);
		this.extras = extras;
	}
	private void parse(String uri) {
		this.url = uri;
		if (uri.startsWith(LB_PROTOCOL)) {
			// lb://service-id/path?key1=&key2=
			schema = NotifySchema.LOAD_BALANCE;
		} else if (uri.startsWith(HTTP_PROTOCOL)) {
			// http://hostname/path?key1=&key2=
			schema = NotifySchema.HTTP;
		} else if (uri.startsWith(HTTPS_PROTOCOL)) {
			// https://hostname/path?key1=&key2=
			schema = NotifySchema.HTTP;
		} else if (uri.startsWith(JVM_PROTOCOL)) {
			// jvm://beanclass/method?key1=&key2=
			schema = NotifySchema.JVM;
		} else if (uri.startsWith(MQ_PROTOCOL)) {
			// mq://topic?key1=&key2=
			schema = NotifySchema.MQ;
		} else {
			throw new RuntimeException("该协议未实现");
		}
		// 解析参数 放入query属性
		parseQuery(uri);

	}

	private void parseQuery(String uri) {
		query = new LinkedHashMap<>();
		int index = uri.indexOf('?');
		if (index > -1) {
			String str = uri.substring(index+1);
			String[] strs = str.split("&");
			for (String s : strs) {
				String[] temp = s.split("=");
				String key = temp[0];
				String value = temp[1];
				query.put(key, value);
			}
		}
	}

	
	public String buildGetUrl() {
		StringBuilder sb = new StringBuilder(url);
		if(this.url.indexOf('?')==-1) {
			sb.append("?");
		}else {
			sb.append("&");
		}
		for(Map.Entry<String, String> entry : extras.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		return sb.toString();
	}
	public NotifySchema getSchema() {
		return schema;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, String> getQuery() {
		return query;
	}
	public Map<String, String> getExtras() {
		return extras;
	}
	
	

}
