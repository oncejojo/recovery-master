package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.OrderMapper;
import com.jojo.recovery.model.Order;
import com.jojo.recovery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/5/17 10:18
 * @Description
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderMapper orderMapper;


    @Override
    public int insert(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Override
    public int update(Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order getRecord(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Order> getList(Order order) {
        return orderMapper.getList(order);
    }

    @Override
    public int deleted(int id) {
        return orderMapper.deleted(id);
    }


}
