package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.BucketMapper;
import com.jojo.recovery.model.Bucket;
import com.jojo.recovery.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/7/1 10:14
 * @Description
 * @Version 1.0
 */
@Service
public class BucketServiceImpl implements BucketService {
    @Autowired
    BucketMapper bucketMapper;

    @Override
    public int insert(Bucket bucket) {
        return bucketMapper.insertSelective(bucket);
    }

    @Override
    public int update(Bucket bucket) {
        return bucketMapper.updateByPrimaryKeySelective(bucket);
    }

    @Override
    public Bucket getInfo(Integer id) {
        return bucketMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Bucket> getList() {
        return bucketMapper.getList();
    }
}
