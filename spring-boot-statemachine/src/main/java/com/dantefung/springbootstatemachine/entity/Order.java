package com.dantefung.springbootstatemachine.entity;

import com.dantefung.springbootstatemachine.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_test")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "order_id")
    private Integer orderId;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private OrderStatus status;


    public Order() {
    }

    public Order(Integer orderId, OrderStatus status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", status=" + status +
                '}';
    }
}
