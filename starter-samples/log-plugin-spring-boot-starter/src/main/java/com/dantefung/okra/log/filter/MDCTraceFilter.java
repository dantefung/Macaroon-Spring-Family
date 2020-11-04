package com.dantefung.okra.log.filter;

import com.dantefung.okra.log.util.TraceIdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Slf4j
public class MDCTraceFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		initTraceId((HttpServletRequest) request);
		try {
			chain.doFilter(request, response);
		} finally {
			// 由于tomcat线程重用，记得清空
			TraceIdUtil.clearTraceId();
		}
	}

	@Override
	public void destroy() {

	}

	/**
	 * traceId初始化
	 */
	private void initTraceId(HttpServletRequest request) {
		//尝试获取http请求中的traceId
		String traceId = request.getHeader(TraceIdUtil.X_FORWARD_TRACEID_HEADER);

		//如果当前traceId为空或者为默认traceId，则生成新的traceId
		if (StringUtils.isEmpty(traceId) || TraceIdUtil.defaultTraceId(traceId)) {
			traceId = TraceIdUtil.genTraceId();
		}
		//设置traceId
		TraceIdUtil.setTraceId(traceId);
		log.info(">>>>>>>>>>>>>>> initTraceId: {}", traceId);
	}

}