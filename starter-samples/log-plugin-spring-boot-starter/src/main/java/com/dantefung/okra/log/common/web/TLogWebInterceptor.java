package com.dantefung.okra.log.common.web;

import com.dantefung.okra.log.common.context.TLogWebCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web controller的拦截器
 *
 * @author Bryan.Zhang
 * @since 2020/9/11
 */
public class TLogWebInterceptor implements HandlerInterceptor {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TLogWebCommon.preHandle(request, response, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        TLogWebCommon.afterCompletion(request, response, handler);
    }
}
