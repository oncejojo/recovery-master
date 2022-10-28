package com.jojo.recovery.mapper;

import com.jojo.recovery.model.Rank;

import java.util.List;

public interface RankMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank record);

    int insertSelective(Rank record);

    Rank selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank record);

    int updateByPrimaryKey(Rank record);

    Rank selectByUser(int userId);

    List<Rank> getList();

}