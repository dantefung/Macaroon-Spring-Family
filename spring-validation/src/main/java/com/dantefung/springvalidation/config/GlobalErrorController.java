package com.dantefung.springvalidation.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 处理统一异常处理器无法处理的异常 包括filter、404
 * @author zhengxiaobin
 * 2020年5月13日
 */
@RestController
@ConditionalOnProperty(value = "configuration.swith.error.enabled", havingValue = "true", matchIfMissing = true)
public class GlobalErrorController extends BasicErrorController {

	public static final String ERROR_CODE = "error_code";
	public GlobalErrorController() {
        super(new DefaultErrorAttributes(), new ErrorProperties());
    }
	@Override
    @RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        HttpStatus status = getStatus(request);
        String value = null;
        Map<String,Object> map = new HashMap<>();
        map.put("code",NumberUtils.isCreatable(value)?NumberUtils.toInt(value, 500):body.get("status"));
        map.put("message",body.get("message"));
        map.put("timestamp",((Date)body.get("timestamp")).getTime());
        map.put("success", false);
        map.put("path", body.get("path"));
        map.put("error", body.get("error"));
        return new ResponseEntity<>(map, HttpStatus.OK);

    }
}
