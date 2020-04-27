package com.dantefung.aop.springaopdemo.core.interceptor;

import com.dantefung.aop.springaopdemo.annotation.ApiMethodMapping;
import com.dantefung.aop.springaopdemo.annotation.ResultKey;
import com.dantefung.aop.springaopdemo.core.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;

/**
 * 为返回值包装Result
 */
public class HandlerMethodReturnValueHandlerWrapper implements HandlerMethodReturnValueHandler {

	private HandlerMethodReturnValueHandler target;

	public HandlerMethodReturnValueHandlerWrapper(HandlerMethodReturnValueHandler target) {
		this.target = target;
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		return target.supportsReturnType(returnType);
	}

	/**
	 * 针对非api请求自动封装返回值
	 * @param returnValue
	 * @param returnType
	 * @param mavContainer
	 * @param webRequest
	 * @throws Exception
	 */
	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
				NativeWebRequest webRequest) throws Exception {
		Object resultInfo = returnValue;
		//不处理来自Api的请求
		if(!returnType.hasMethodAnnotation(ApiMethodMapping.class)){
			/**** 处理app请求 ****/
			//注解@ResultKey的返回值
			if(returnType.hasMethodAnnotation(ResultKey.class)){
				ResultKey resultKey = returnType.getMethodAnnotation(ResultKey.class);
				if(StringUtils.isNotBlank(resultKey.value())){
					HashMap returnData = new HashMap();
					returnData.put(resultKey.value(),returnValue);
					resultInfo = returnData;
				}
			}
			//处理非Result返回
			if (!returnType.getParameterType().isAssignableFrom(Result.class)) {
				resultInfo = Result.ok(resultInfo);
			}
		}
		target.handleReturnValue(resultInfo, returnType, mavContainer, webRequest);
	}
}
