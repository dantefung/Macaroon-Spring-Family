package com.dantefung.springvalidation.filter;

import com.dantefung.springvalidation.utils.LocaleUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //无需初始化方法
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        // 得到用户个人相关的信息（用户的语言）
        fillUserLang((HttpServletRequest) request);

        try {
            chain.doFilter(request, response);
        } finally {
            // 由于tomcat线程重用，记得清空
            clearAllUserLang();
        }
    }

    private void fillUserLang(HttpServletRequest request) {

        // 语言信息
        String locale = getLocaleFromCookies(request);

        // 放入到threadlocal，同一个线程任何地方都可以拿出来
        if (locale != null && !"".equalsIgnoreCase(locale)) {
            LocaleUtil.setLocale(locale);
        }
    }

    private void clearAllUserLang() {
        LocaleUtil.clearAllUserLang();
    }

    private String getLocaleFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (int i = 0; i < cookies.length; i++) {
            if (LocaleUtil.KEY_LANG.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    @Override
    public void destroy() {
        //无需销毁方法
    }

}
