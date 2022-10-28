package com.jojo.recovery.service;

import com.jojo.recovery.model.Rank;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/5/19 14:34
 * @Description
 * @Version 1.0
 */
public interface RankService {
    int insert(Rank rank);

    int update(Rank rank);

    List<Rank> getList();

    Rank getInfo(int userId);
}
