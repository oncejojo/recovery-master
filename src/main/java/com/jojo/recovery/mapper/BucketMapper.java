package com.jojo.recovery.mapper;

import com.jojo.recovery.model.Bucket;

import java.util.List;

public interface BucketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bucket record);

    int insertSelective(Bucket record);

    Bucket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bucket record);

    int updateByPrimaryKey(Bucket record);

    List<Bucket> getList();
}