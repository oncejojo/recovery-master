package com.jojo.recovery.controller;

import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.Order;
import com.jojo.recovery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JoJo
 * @Date 2022/3/21 13:49
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/info")
    public AjaxResult getRecord(int id) {
        Order order = orderService.getRecord(id);
        if (order == null) {
            return AjaxResult.error(JsonResultEnum.ORDER_NULL);
        }
        return AjaxResult.successData(order);
    }

    @PostMapping("/update")
    public AjaxResult updateRecord(Order order) {
        Order record = orderService.getRecord(order.getId());
        if (record == null) {
            return AjaxResult.error(JsonResultEnum.ORDER_NULL);
        }
        orderService.update(order);
        return AjaxResult.success();
    }

    @PostMapping("/delete")
    public AjaxResult deleteRecord(int id) {
        Order order = orderService.getRecord(id);
        if (order == null) {
            return AjaxResult.error(JsonResultEnum.ORDER_NULL);
        }
        orderService.deleted(id);
        return AjaxResult.success();
    }
}
