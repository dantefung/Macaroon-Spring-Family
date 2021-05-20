package com.dantefung.core.http;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @Title: HttpUtilsTest* @Description: ${todo}* @author DANTE FUNG
 * @date 2021/05/20 18/27
 * @since JDK1.8
 */
public class HttpUtilsTest {

	@Test
	public void getParams() {
		String queryStr = "userId=1&name=iBit程序猿&password=123";
		Map<String, String> params = HttpUtils.getParams(queryStr);
		assertEquals("1", params.get("userId"));
		assertEquals("xxxx", params.get("name"));
		assertEquals("123", params.get("password"));
	}

	@Test
	public void getQueryString() {
		Map<String, String> params = new LinkedHashMap<String, String>() {{
			put("userId", "1");
			put("name", "yyyy");
			put("password", "123");
		}};
		assertEquals("userId=1&name=yyyy&password=123", HttpUtils.getQueryString(params, null));
		assertEquals("userId=1&name=yyyy&password=**", HttpUtils.getQueryString(params, Collections.singletonList("password")));
	}
}
