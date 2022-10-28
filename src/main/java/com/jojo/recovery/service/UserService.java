package com.jojo.recovery.service;

import com.jojo.recovery.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 13:39
 * @Description
 * @Version 1.0
 */
public interface UserService {
    int insert(User user);

    int update(User user);

    int delete(int id);

    User getRecord(int id);

    User getUser(User user);

    List<User> getList(User user);

    User getRecordByPhone(String phone);
}
