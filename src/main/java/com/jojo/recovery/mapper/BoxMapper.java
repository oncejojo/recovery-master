package com.jojo.recovery.mapper;

import com.jojo.recovery.model.Box;

import java.util.List;

public interface BoxMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Box record);

    int insertSelective(Box record);

    Box selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Box record);

    int updateByPrimaryKey(Box record);

    List<Box> getList(Box box);

    int delete(int id);

    Box getRecordByCode(String code);
}