package com.dantefung.springbootstatemachine.controller;

import com.dantefung.springbootstatemachine.enums.OrderStatusChangeEvent;
import com.dantefung.springbootstatemachine.service.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderStateService orderStateService;

    /**
     * 列出所有的订单列表
     *
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET})
    public ResponseEntity orders() {
        String orders = orderStateService.listDbEntries();
        return new ResponseEntity(orders, HttpStatus.OK);

    }


    /**
     * 通过触发一个事件，改变一个订单的状态
     * @param orderId
     * @param event
     * @return
     */
    @RequestMapping(value = "/{orderId}", method = {RequestMethod.POST})
    public ResponseEntity processOrderState(@PathVariable("orderId") Integer orderId, @RequestParam("event") OrderStatusChangeEvent event) {
        Boolean result = orderStateService.change(orderId, event);
        return new ResponseEntity(result, HttpStatus.OK);
    }

}
