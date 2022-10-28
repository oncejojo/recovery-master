package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.BalanceMapper;
import com.jojo.recovery.model.Balance;
import com.jojo.recovery.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:25
 * @Description
 * @Version 1.0
 */
@Service
public class BalanceServiceImpl implements BalanceService {
    @Autowired
    BalanceMapper balanceMapper;

    @Override
    public int insert(Balance balance) {
        return balanceMapper.insertSelective(balance);
    }

    @Override
    public int update(Balance balance) {
        return balanceMapper.updateByPrimaryKeySelective(balance);
    }

    @Override
    public int delete(int id) {
        return balanceMapper.delete(id);
    }

    @Override
    public Balance getRecord(int id) {
        return balanceMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Balance> getList(Balance balance) {
        return balanceMapper.getList(balance);
    }
}
