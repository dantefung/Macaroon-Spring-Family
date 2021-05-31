package com.dantefung.springbootstatemachine.listener;

import com.dantefung.springbootstatemachine.dao.OrderRepo;
import com.dantefung.springbootstatemachine.entity.Order;
import com.dantefung.springbootstatemachine.enums.OrderStatus;
import com.dantefung.springbootstatemachine.enums.OrderStatusChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

public class OrderPersistStateChangeListener implements PersistStateMachineHandler.PersistStateChangeListener {


    @Autowired
    private OrderRepo repo;


    @Override
    public void onPersist(State<OrderStatus, OrderStatusChangeEvent> state, Message<OrderStatusChangeEvent> message,
                          Transition<OrderStatus, OrderStatusChangeEvent> transition, StateMachine<OrderStatus, OrderStatusChangeEvent> stateMachine) {
        if (message != null && message.getHeaders().containsKey("order")) {
            Integer order = message.getHeaders().get("order", Integer.class);
            Order o = repo.findByOrderId(order);
            OrderStatus status = state.getId();
            o.setStatus(status);
            repo.save(o);

        }
    }
}