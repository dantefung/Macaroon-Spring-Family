package com.dantefung.springbootmvc.sign;

import com.dantefung.springbootmvc.config.SignConfiguration;
import com.dantefung.springbootmvc.constant.Constant;
import com.dantefung.springbootmvc.utils.RSAEncrypt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
@ConditionalOnProperty(value = "configuration.swith.sign.enabled", havingValue = "true", matchIfMissing = false)
public class HeaderModifierAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private SignConfig signConfig;

	//这个方法用于判断是否需要调用beforeBodyWrite方法
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    // 这时响应内容未写入到response.outputStream
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        HttpServletResponse httpResponse = ((ServletServerHttpResponse) response).getServletResponse();
        boolean needSign = Boolean.parseBoolean(RpcContext.get(SignConfiguration.KEY_NEED_SIGN, "false"));
        if (!needSign) {
            // 不需要签名
            return body;
        }
        String responseBody = null;
        if(body.getClass()==String.class){
            responseBody = (String) body;
        }else {
            ObjectMapper objectMapper = context.getBean(ObjectMapper.class);
            try {
                responseBody = objectMapper.writeValueAsString(body);
            } catch (JsonProcessingException e) {
                log.error("", e);
            }
        }

        log.info("响应 | {}",responseBody);
        try {
            String privateKey = signConfig.getPrivateKeyString();
            log.info("获取私钥 | {}",privateKey);
            String sign = RSAEncrypt.signByPrivateKey(responseBody.getBytes(), privateKey);
            httpResponse.addHeader(Constant.SIGN_HTTP_HEADER_KEY, sign);
        } catch (Exception e) {
            log.error("", e);
        }

        return body;

    }

}
