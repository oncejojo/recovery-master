package com.jojo.recovery.mapper;

import com.jojo.recovery.model.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int delete(int id);

    User getUser(User user);

    List<User> getList(User user);

    User getUserByPhone(String phone);
}