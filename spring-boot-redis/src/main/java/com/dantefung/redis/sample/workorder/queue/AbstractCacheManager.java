package com.dantefung.redis.sample.workorder.queue;

import com.alibaba.fastjson.JSONObject;
import com.dantefung.redis.plugin.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Slf4j
public abstract class AbstractCacheManager {

    final int MAX_RETRIES = 3;

    @Autowired
	RedisService redisService;

    /**
     * 重试操作
     * @param retries 重试次数
     * @param context 上下文
     * @param call 重试动作
     */
    public <T> void retry(int retries, T context, Function<Integer,Boolean> call){
        boolean done = false;
        int retry = 1;
        do {
            try {
                done = call.apply(retry);
                log.info("[retry] context={},retry={},done={}", JSONObject.toJSON(context),retry,done);
                retry ++;
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (Exception e) {
                log.error("[retry] 异常 ctx={}", JSONObject.toJSON(context),e);
                retry ++;
            }
        }while (retry <= retries && !done);
    }
}