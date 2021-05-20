package com.zang.rocket.controller;

import com.zang.rocket.service.LocalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhang Qiang
 * @date 2019/12/4 16:22
 */
@Slf4j
@RestController
public class ProducerController {

    @Autowired
    LocalService localService;

    @GetMapping("/product/send/{s}")
    public String sendSyncMessage(@PathVariable("s")String s){
        return localService.sentSyncMessage(s);
    }
    @GetMapping("/product/send")
    public String sendSyncMessage(){
        return localService.sentSyncMessage("匿名消息");
    }

}
