package com.jojo.recovery.service;

import com.jojo.recovery.model.Balance;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:25
 * @Description
 * @Version 1.0
 */
public interface BalanceService {
    int insert(Balance balance);

    int update(Balance balance);

    int delete(int id);

    Balance getRecord(int id);

    List<Balance> getList(Balance balance);
}
