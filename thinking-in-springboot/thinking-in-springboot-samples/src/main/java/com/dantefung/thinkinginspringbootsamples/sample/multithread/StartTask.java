package com.dantefung.thinkinginspringbootsamples.sample.multithread;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class StartTask   {

    //定义在构造方法完毕后，执行这个初始化方法
    @PostConstruct
    public  void init(){
		List<String> list = Lists.newArrayList("monitor1","monitor2","monitor3");
        log.info("监控任务的总Task数：{}",list.size());
        for(int i=0;i < list.size();i++) {
            MoniotrTask moniotrTask=   ApplicationContextProvider.getBean("mTask", MoniotrTask.class);
            moniotrTask.start();
            log.info("第{}个监控task: {}启动 !",(i+1),list.get(i));
        }

    }


}
