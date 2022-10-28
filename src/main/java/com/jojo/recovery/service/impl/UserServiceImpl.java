package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.UserMapper;
import com.jojo.recovery.model.User;
import com.jojo.recovery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 13:42
 * @Description
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public int insert(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int delete(int id) {
        return userMapper.delete(id);
    }

    @Override
    public User getRecord(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getUser(User user) {
        return userMapper.getUser(user);
    }

    @Override
    public List<User> getList(User user) {
        return userMapper.getList(user);
    }

    @Override
    public User getRecordByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }
}
