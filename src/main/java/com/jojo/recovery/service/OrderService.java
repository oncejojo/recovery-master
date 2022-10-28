package com.jojo.recovery.service;

import com.jojo.recovery.model.Order;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/5/17 10:16
 * @Description
 * @Version 1.0
 */
public interface OrderService {
    int insert(Order order);

    int update(Order order);

    Order getRecord(int id);

    List<Order> getList(Order order);

    int deleted(int id);


}
