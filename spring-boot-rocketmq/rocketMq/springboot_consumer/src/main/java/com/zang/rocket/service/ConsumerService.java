package com.zang.rocket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Zhang Qiang
 * @date 2019/12/4 16:44
 */
@Slf4j
@Service
public class ConsumerService {

    public String readMessage(String msg){
        log.info("【读取消息服务】：{}", msg);
        return msg;
    }

}
