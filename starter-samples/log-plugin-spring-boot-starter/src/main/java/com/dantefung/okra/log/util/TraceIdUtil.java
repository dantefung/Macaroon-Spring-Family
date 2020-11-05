package com.dantefung.okra.log.util;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * <p>traceId工具类</P>
 *
 * @author DANTE FUNG
 */
public class TraceIdUtil {

    public static final String TRACE_ID = "traceId";

    /**
     * 当traceId为空时，显示的traceId。随意。
     */
    private static final String DEFAULT_TRACE_ID = "0";

	/**
	 * 当别的系统调用本系统的时候可以在请求头添加traceId
	 * 格式:
	 *  X-FORWARD-TRACEID: 系统代号-ed88a87132134292bf059091922b5c62
	 *
	 *  系统代号可以是系统ID
	 */
	public static final String X_FORWARD_TRACEID_HEADER = "X-FORWARD-TRACEID";


	/**
     * 设置traceId
     */
    public static void setTraceId(String traceId) {
        //如果参数为空，则设置默认traceId
        traceId = StringUtils.isEmpty(traceId) ? DEFAULT_TRACE_ID : traceId;
        //将traceId放到MDC中
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 获取traceId
     */
    public static String getTraceId() {
        //获取
        String traceId = MDC.get(TRACE_ID);
        //如果traceId为空，则返回默认值
        return StringUtils.isEmpty(traceId) ? DEFAULT_TRACE_ID : traceId;
    }

    /**
     * 判断traceId为默认值
     */
    public static Boolean defaultTraceId(String traceId) {
        return DEFAULT_TRACE_ID.equals(traceId);
    }

    /**
     * 生成traceId
     */
    public static String genTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

	/**
	 * 清除traceId
	 */
	public static void clearTraceId() {
		MDC.remove(TRACE_ID);
	}
}

