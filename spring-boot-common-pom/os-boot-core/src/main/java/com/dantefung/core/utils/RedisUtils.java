/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.dantefung.core.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, Object> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**  默认过期时长，单位：秒 */
    private long defaultExpire = 60 * 60 * 24L;
    /**  不设置过期时长 */
    private long notExpire = -1;
    // 设置日志格式为“yyyy-MM-dd HH:mm:ss”，否则Gson默认时序列化的输出结果可能不能预期（eg. 有时有逗号有时没有）
    private final Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public void set(String key, Object value, long expire){
        valueOperations.set(key, toJson(value));
        if(expire != notExpire){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value){
        set(key, value, defaultExpire);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = (String)valueOperations.get(key);
        if(expire != notExpire){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, notExpire);
    }

    public String get(String key, long expire) {
        String value = (String)valueOperations.get(key);
        if(expire != notExpire){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, notExpire);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void deleteByPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if(keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

    public long inc(String key) {
        return incBy(key,1);
    }

    public long incBy(String key, long step) {
        return redisTemplate.opsForValue().increment(key, step);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object){
        if(object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String){
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz){
        return gson.fromJson(json, clazz);
    }
}
