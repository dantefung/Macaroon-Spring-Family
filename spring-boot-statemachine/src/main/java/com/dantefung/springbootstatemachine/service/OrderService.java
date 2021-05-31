package com.dantefung.springbootstatemachine.service;

import com.dantefung.springbootstatemachine.entity.Order;

import java.util.Map;

/**
 * @Title: OrderService
 * @Description:
 * @author DANTE FUNG
 * @date 2021/05/31 18/01
 * @since JDK1.8
 */
public interface OrderService {

	Order create();

	Order pay(int id);

	Order deliver(int id);

	Order receive(int id);

	Map<Integer, Order> getOrders();
}
