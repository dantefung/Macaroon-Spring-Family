package com.dantefung.aop.springaopdemo.advice;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServletWrapper extends DispatcherServlet {
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest httpRequest = new HttpRequestWrapper(request);

        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(httpRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        super.doDispatch(httpRequest, response);

    }
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception{
        super.doService(request,response);
        //清除数据
        LogContext.remove();
    }
}
