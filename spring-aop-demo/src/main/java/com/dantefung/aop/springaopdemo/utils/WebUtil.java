package com.dantefung.aop.springaopdemo.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Miscellaneous utilities for web applications.
 *
 * @author L.cm
 */
@Slf4j
@UtilityClass
public class WebUtil extends org.springframework.web.util.WebUtils {

	/**
	 * 判断是否ajax请求
	 * spring ajax 返回含有 ResponseBody 或者 RestController注解
	 *
	 * @param handlerMethod HandlerMethod
	 * @return 是否ajax请求
	 */
	public static boolean isBody(HandlerMethod handlerMethod) {
		ResponseBody responseBody = ClassUtil.getAnnotation(handlerMethod, ResponseBody.class);
		return responseBody != null;
	}

	/**
	 * 读取cookie
	 *
	 * @param name cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(String name) {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		return getCookieVal(request, name);
	}

	/**
	 * 读取cookie
	 *
	 * @param request HttpServletRequest
	 * @param name    cookie name
	 * @return cookie value
	 */
	@Nullable
	public static String getCookieVal(HttpServletRequest request, String name) {
		Cookie cookie = getCookie(request, name);
		return cookie != null ? cookie.getValue() : null;
	}

	/**
	 * 清除 某个指定的cookie
	 *
	 * @param response HttpServletResponse
	 * @param key      cookie key
	 */
	public static void removeCookie(HttpServletResponse response, String key) {
		setCookie(response, key, null, 0);
	}

	/**
	 * 设置cookie
	 *
	 * @param response        HttpServletResponse
	 * @param name            cookie name
	 * @param value           cookie value
	 * @param maxAgeInSeconds maxage
	 */
	public static void setCookie(HttpServletResponse response, String name, @Nullable String value, int maxAgeInSeconds) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath(StringPool.SLASH);
		cookie.setMaxAge(maxAgeInSeconds);
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}

	/**
	 * 获取 HttpServletRequest
	 *
	 * @return {HttpServletRequest}
	 */
	@Nullable
	public static HttpServletRequest getRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
			.map(x -> (ServletRequestAttributes) x)
			.map(ServletRequestAttributes::getRequest)
			.orElse(null);
	}

	/**
	 * 获取 HttpServletResponse
	 *
	 * @return {HttpServletResponse}
	 */
	@Nullable
	public static HttpServletResponse getResponse() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
			.map(x -> (ServletRequestAttributes) x)
			.map(ServletRequestAttributes::getResponse)
			.orElse(null);
	}

	/**
	 * 获取ip
	 *
	 * @return {String}
	 */
	@Nullable
	public static String getIP() {
		return Optional.ofNullable(WebUtil.getRequest())
			.map(WebUtil::getIP)
			.orElse(null);
	}

	private static final String[] IP_HEADER_NAMES = new String[]{
		"x-forwarded-for",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP",
		"HTTP_CLIENT_IP",
		"HTTP_X_FORWARDED_FOR"
	};

	private static final Predicate<String> IP_PREDICATE = (ip) -> StringUtil.isBlank(ip) || StringPool.UNKNOWN.equalsIgnoreCase(ip);

	/**
	 * 获取ip
	 *
	 * @param request HttpServletRequest
	 * @return {String}
	 */
	@Nullable
	public static String getIP(@Nullable HttpServletRequest request) {
		if (request == null) {
			return StringPool.EMPTY;
		}
		String ip = null;
		for (String ipHeader : IP_HEADER_NAMES) {
			ip = request.getHeader(ipHeader);
			if (!IP_PREDICATE.test(ip)) {
				break;
			}
		}
		if (IP_PREDICATE.test(ip)) {
			ip = request.getRemoteAddr();
		}
		return StringUtil.isBlank(ip) ? null : StringUtil.splitTrim(ip, StringPool.COMMA)[0];
	}


	/**
	 * 返回json
	 *
	 * @param response HttpServletResponse
	 * @param result   结果对象
	 */
	public static void renderJson(HttpServletResponse response, Object result) {
		renderJson(response, result, MediaType.APPLICATION_JSON_UTF8_VALUE);
	}

	/**
	 * 返回json
	 *
	 * @param response HttpServletResponse
	 * @param result   结果对象
	 * @param contentType   contentType
	 */
	public static void renderJson(HttpServletResponse response, Object result, String contentType) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		try (PrintWriter out = response.getWriter()) {
			out.append(JsonUtil.obj2Json(result));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

}