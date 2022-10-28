package com.jojo.recovery.mapper;

import com.jojo.recovery.model.Balance;

import java.util.List;

public interface BalanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Balance record);

    int insertSelective(Balance record);

    Balance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Balance record);

    int updateByPrimaryKey(Balance record);

    List<Balance> getList(Balance balance);

    int delete(int id);
}