package com.jojo.recovery.service.impl;

import com.jojo.recovery.common.exception.JsonException;
import com.jojo.recovery.mapper.SysUserMapper;
import com.jojo.recovery.model.SysUser;
import com.jojo.recovery.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 14:05
 * @Description
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public int insert(SysUser sysUser) {
        return sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public int update(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }


    @Override
    public SysUser getRecord(int id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public SysUser loginAction(String username, String password) throws JsonException {
        SysUser sysUser = sysUserMapper.selectByName(username);
        return sysUser;
    }

    @Override
    public List<SysUser> getList(SysUser sysUser) {
        return sysUserMapper.getList(sysUser);
    }

    @Override
    public Integer delete(int id) {
        return sysUserMapper.updateDeleted(id);
    }

    @Override
    public SysUser getRecordByName(String username) {
        return sysUserMapper.selectByName(username);
    }
}
