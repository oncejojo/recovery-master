package com.jojo.recovery.service;

import com.jojo.recovery.model.Box;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:10
 * @Description
 * @Version 1.0
 */
public interface BoxService {
    int insert(Box box);

    int update(Box box);

    int delete(int id);

    Box getRecord(int id);

    List<Box> getList(Box box);

    Box getRecordByCode(String code);
}
