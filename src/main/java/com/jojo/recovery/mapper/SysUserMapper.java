package com.jojo.recovery.mapper;

import com.jojo.recovery.model.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByName(String username);

    Integer updateDeleted(int id);

    List<SysUser> getList(SysUser sysUser);
}