package com.dantefung.springvalidation.config;

import com.dantefung.springvalidation.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(Exception.class)
	public R defaultExceptionHandler(HttpServletRequest req, Exception e) {
		e.printStackTrace();
		return R.error(e.getMessage());
	}

	@ExceptionHandler(value= {MethodArgumentNotValidException.class , BindException.class})
	public R handleVaildException(Exception e){
    	log.error(ExceptionUtils.getMessage(e));
		BindingResult bindingResult = null;
		if (e instanceof MethodArgumentNotValidException) {
			bindingResult = ((MethodArgumentNotValidException)e).getBindingResult();
		} else if (e instanceof BindException) {
			bindingResult = ((BindException)e).getBindingResult();
		}
		Map<String,String> errorMap = new HashMap<>(16);
		bindingResult.getFieldErrors().forEach((fieldError)->
				errorMap.put(fieldError.getField(),fieldError.getDefaultMessage())
		);
		log.info(errorMap.toString());
		return R.error().put("data", errorMap);
	}

}
