package com.dantefung.springbootstatemachine.config;

import com.dantefung.springbootstatemachine.enums.OrderStatus;
import com.dantefung.springbootstatemachine.enums.OrderStatusChangeEvent;
import com.dantefung.springbootstatemachine.listener.OrderPersistStateChangeListener;
import com.dantefung.springbootstatemachine.listener.PersistStateMachineHandler;
import com.dantefung.springbootstatemachine.service.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;

@Configuration
public class OrderPersistHandlerConfig {

    @Autowired
    private StateMachine<OrderStatus, OrderStatusChangeEvent> stateMachine;


    @Bean
    public OrderStateService persist() {
        PersistStateMachineHandler handler = persistStateMachineHandler();
        handler.addPersistStateChangeListener(persistStateChangeListener());
        return new OrderStateService(handler);
    }

    @Bean
    public PersistStateMachineHandler persistStateMachineHandler() {
        return new PersistStateMachineHandler(stateMachine);
    }

    @Bean
    public OrderPersistStateChangeListener persistStateChangeListener(){
        return new OrderPersistStateChangeListener();
    }


}
