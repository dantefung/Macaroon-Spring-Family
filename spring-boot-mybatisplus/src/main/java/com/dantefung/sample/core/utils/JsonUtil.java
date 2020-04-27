package com.dantefung.sample.core.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * JSON 工具
 * @author 丁伟
 * @version 1.0
 * @date 2017年9月4日 上午1:25:32
 */
@Slf4j
public abstract class JsonUtil {

	private JsonUtil(){
		//default
	}
	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	@SuppressWarnings("unchecked")
	public static final Map<String, Object> json2Map(String json) {
		if (json == null) {
			return null;
		}

		try {
			return objectMapper.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String map2Json(Map<String,Object> map) {
		return obj2Json(map);
	}

	public static final <T> T json2Obj(String content, Class<T> clazz) {
		if (StringUtils.isBlank(content)) {
			return null;
		}
		try {
			return objectMapper.readValue(content, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static String obj2Json(Object obj) {
		if (obj == null) {
			return null;
		}

		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
	}


    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数.
     * @param jsonString
     * @param javaType
     * @return
     */
    public static <T> T fromJson(String jsonString, JavaType javaType) {
        try {
            return objectMapper.readValue(jsonString, javaType);
        } catch (Exception e) {
			throw new RuntimeException(e.getMessage());
        }
    }
}
