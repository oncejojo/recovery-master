package com.jojo.recovery.mapper;

import com.jojo.recovery.model.BinFull;

import java.util.List;

public interface BinFullMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BinFull record);

    int insertSelective(BinFull record);

    BinFull selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BinFull record);

    int updateByPrimaryKey(BinFull record);

    List<BinFull> getList(BinFull binFull);

    List<BinFull> getFullList(Integer boxId);

    Integer updateFull(BinFull binFull);

    BinFull getInfoByCodeNo(BinFull binFull);

    Integer updateCode(Integer boxId, String code);
}