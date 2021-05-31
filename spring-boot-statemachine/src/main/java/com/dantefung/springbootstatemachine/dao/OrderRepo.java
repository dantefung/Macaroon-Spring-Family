package com.dantefung.springbootstatemachine.dao;

import com.dantefung.springbootstatemachine.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {


    Order findByOrderId(Integer order);
}
