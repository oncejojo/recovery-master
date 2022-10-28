package com.jojo.recovery.service;

import com.jojo.recovery.common.exception.JsonException;
import com.jojo.recovery.model.SysUser;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:05
 * @Description
 * @Version 1.0
 */
public interface SysUserService {
    int insert(SysUser sysUser);

    int update(SysUser sysUser);

    SysUser getRecord(int id);

    SysUser loginAction(String username, String password) throws JsonException;

    List<SysUser> getList(SysUser sysUser);

    Integer delete(int id);

    SysUser getRecordByName(String username);
}
