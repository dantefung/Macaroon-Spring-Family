package com.dantefung.springbootmvc.sign;

import com.dantefung.springbootmvc.annotation.RequireSign;
import com.dantefung.springbootmvc.config.SignConfiguration;
import com.dantefung.springbootmvc.constant.Constant;
import com.dantefung.springbootmvc.exception.SignException;
import com.dantefung.springbootmvc.utils.RSAEncrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 验证签名拦截器
 *
 * @author zhengxiaobin 2019年12月23日
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "configuration.swith.sign.enabled", havingValue = "true", matchIfMissing = false)
public class CheckSignInterceptor implements HandlerInterceptor {

    private static final long REQUEST_TIME_LIMIT = 600;// 十分钟 单位秒

    @Autowired(required = false)
    private SignConfig signConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info(">>>>>>>>>>>>>>>>>开始验签处理:"+request.getClass().getName());
        boolean needCheckSign = false;// 是否需要验证签名
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(RequireSign.class) || handlerMethod.getBean().getClass().getAnnotation(RequireSign.class) != null) {
                needCheckSign = true;
            }
        }
        RpcContext.put(SignConfiguration.KEY_NEED_SIGN, String.valueOf(needCheckSign));
        if (!needCheckSign) {
            log.info("{} 不需要验证签名", request.getRequestURI());
            return true;
        }
        String appId = request.getHeader(Constant.APPSYSID_HTTP_HEADER_KEY);
        if (StringUtils.isBlank(appId)) {
            throw new SignException("X-OS-KERNEL-APPSYSID不能为空");
        }
        //获取对应的公钥
        String publicKey = signConfig.getPublicKey(appId);
        RpcContext.put(SignConfiguration.KEY_APP_ID, appId);
        RpcContext.put(SignConfiguration.KEY_PUBLIC_KEY, publicKey);
        if(!StringUtils.isNumeric(request.getHeader(Constant.TIMESTAMP_HTTP_HEADER_KEY))){
            throw new SignException("X-OS-KERNEL-TIMESTAMP请求头错误");
        }
        long timestamp = Long.parseLong(request.getHeader(Constant.TIMESTAMP_HTTP_HEADER_KEY));
        if ((System.currentTimeMillis() / 1000 - timestamp) >= REQUEST_TIME_LIMIT) {
            // 请求过时
            log.warn("请求过时");
            throw new SignException("请求过时");
        }
        String token = request.getHeader(Constant.TOKEN_HTTP_HEADER_KEY);
        if (StringUtils.isBlank(token)) {
            throw new SignException("X-OS-KERNEL-TOKEN不能为空");
        }
        String method = request.getMethod();
        if (method.equalsIgnoreCase(RequestMethod.GET.name())) {
            checkGetSign(request);
            return true;
        } else if (method.equalsIgnoreCase(RequestMethod.POST.name())) {
            if (request.getContentType().contains(cn.hutool.http.ContentType.FORM_URLENCODED.toString())) {
                checkPostFormSign(request);
            } else if (request.getContentType().contains(cn.hutool.http.ContentType.JSON.toString())) {
                checkPostJsonSign(request);
            }
            return true;
        } else {
            // 跳过签名验证
            log.info("跳过签名验证");
            return true;
        }
    }

    private void checkGetSign(HttpServletRequest request)  {
        Enumeration<String> enumerations = request.getParameterNames();
        List<String> keys = new ArrayList<>();
        while (enumerations.hasMoreElements()) {
            keys.add(enumerations.nextElement());
        }
        // 字母升序排列
        keys.sort((String key1, String key2) -> key1.compareTo(key2));
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append("&").append(key).append("=").append(request.getParameter(key));
        }
        if (!keys.isEmpty()) {
            sb.deleteCharAt(0);
        }
        sb.append(request.getHeader(Constant.APPSYSID_HTTP_HEADER_KEY))
                .append(request.getHeader(Constant.TOKEN_HTTP_HEADER_KEY))
                .append(request.getHeader(Constant.TIMESTAMP_HTTP_HEADER_KEY));
        //
        String sign = request.getHeader(Constant.SIGN_HTTP_HEADER_KEY);
        String publicKey = RpcContext.get(SignConfiguration.KEY_PUBLIC_KEY);
        if (!RSAEncrypt.verifyByPublicKey(sb.toString().getBytes(), publicKey, sign)) {
            // 验签失败
            throw new SignException("签名验证失败");
        }

    }

    private void checkPostFormSign(HttpServletRequest request)  {
        checkGetSign(request);
    }

    private void checkPostJsonSign(HttpServletRequest request) throws IOException {
        Enumeration<String> enumerations = request.getParameterNames();
        List<String> keys = new ArrayList<>();
        while (enumerations.hasMoreElements()) {
            keys.add(enumerations.nextElement());
        }
        // 字母升序排列
        keys.sort((String key1, String key2) -> key1.compareTo(key2));
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            sb.append("&").append(key).append("=").append(request.getParameter(key));
        }
        if (!keys.isEmpty()) {
            sb.deleteCharAt(0);
        }
        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        sb.append(body);
        sb.append(request.getHeader(Constant.APPSYSID_HTTP_HEADER_KEY))
                .append(request.getHeader(Constant.TOKEN_HTTP_HEADER_KEY))
                .append(request.getHeader(Constant.TIMESTAMP_HTTP_HEADER_KEY));
        //
        String sign = request.getHeader(Constant.SIGN_HTTP_HEADER_KEY);
        String publicKey = RpcContext.get(SignConfiguration.KEY_PUBLIC_KEY);
        if (!RSAEncrypt.verifyByPublicKey(sb.toString().getBytes(), publicKey, sign)) {
            // 验签失败
            throw new SignException("签名验证失败");
        }
    }

}

