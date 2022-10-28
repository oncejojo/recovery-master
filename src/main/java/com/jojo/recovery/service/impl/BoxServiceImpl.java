package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.BoxMapper;
import com.jojo.recovery.model.Box;
import com.jojo.recovery.service.BoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:11
 * @Description
 * @Version 1.0
 */
@Service
public class BoxServiceImpl implements BoxService {
    @Autowired
    BoxMapper boxMapper;


    @Override
    public int insert(Box box) {
        return boxMapper.insertSelective(box);
    }

    @Override
    public int update(Box box) {
        return boxMapper.updateByPrimaryKeySelective(box);
    }

    @Override
    public int delete(int id) {
        return boxMapper.delete(id);
    }

    @Override
    public Box getRecord(int id) {
        return boxMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Box> getList(Box box) {
        return boxMapper.getList(box);
    }

    @Override
    public Box getRecordByCode(String code) {
        return boxMapper.getRecordByCode(code);
    }
}
