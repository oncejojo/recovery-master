package com.jojo.recovery.service;

import com.jojo.recovery.model.Bucket;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/7/1 10:13
 * @Description
 * @Version 1.0
 */
public interface BucketService {
    int insert(Bucket bucket);

    int update(Bucket bucket);

    Bucket getInfo(Integer id);

    List<Bucket> getList();
}
