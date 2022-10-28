package com.jojo.recovery.controller;

import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.common.enums.StatusEnum;
import com.jojo.recovery.model.Balance;
import com.jojo.recovery.model.User;
import com.jojo.recovery.service.BalanceService;
import com.jojo.recovery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @Author JoJo
 * @Date 2022/5/23 15:06
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/balance")
public class BalanceController {
    @Autowired
    BalanceService balanceService;
    @Autowired
    UserService userService;

    @PostMapping("/update")
    public AjaxResult update(Balance balance) {
        Balance record = balanceService.getRecord(balance.getId());
        if (record == null ){
            return AjaxResult.error(JsonResultEnum.TARGET_NULL);
        }
        balanceService.update(balance);
//        撤销提现返回金额
        if(Objects.equals(balance.getStatus(), StatusEnum.STATUS_CONFIRM.getCode())) {
            User user = userService.getRecord(record.getUserId());
            if (user != null) {
                user.setMoney(user.getMoney().add(record.getMoney()));
                userService.update(user);
            }
        }
        return AjaxResult.success();
    }
}
