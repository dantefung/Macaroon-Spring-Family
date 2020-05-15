package com.dantefung.springbootmvc.sign;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatcherServletWrapper extends DispatcherServlet {

    /**
     *
     */
    private static final long serialVersionUID = 5932428961697375891L;

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new HttpRequestWrapper(request), new HttpResponseWrapper(response));

    }

}
