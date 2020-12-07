package com.dantefung.springbootmvc.resolver;

import com.dantefung.springbootmvc.annotation.LoginUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.UUID;

@Component
public class TokenInfoHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterType().isAssignableFrom(TokenInfo.class) && parameter.hasParameterAnnotation(LoginUser.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                 NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
	  TokenInfo tokenInfo = new TokenInfo();
	  tokenInfo.setUserId("1");
	  tokenInfo.setToken(UUID.randomUUID().toString());
	  return tokenInfo;
  }
}
