package com.dantefung.springbootstatemachine;

import com.dantefung.springbootstatemachine.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

public class OrderServiceImplTest extends Tester{
 
    @Autowired
    private OrderService orderService;
 
    @Test
    public void testMultThread(){
        orderService.create();
        orderService.create();
 
        orderService.pay(1);
 
        new Thread(()->{
            orderService.deliver(1);
            orderService.receive(1);
        }).start();
 
        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);
 
        System.out.println(orderService.getOrders());
    }
}