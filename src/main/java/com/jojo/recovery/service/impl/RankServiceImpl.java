package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.RankMapper;
import com.jojo.recovery.model.Rank;
import com.jojo.recovery.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/5/19 14:35
 * @Description
 * @Version 1.0
 */
@Service
public class RankServiceImpl implements RankService {
    @Autowired
    RankMapper rankMapper;

    @Override
    public int insert(Rank rank) {
        Rank record = rankMapper.selectByUser(rank.getUserId());
        rank.setNumber(rank.getNumber() + 1);

        if (record == null) {
            return rankMapper.insertSelective(rank);
        }else {
            return rankMapper.updateByPrimaryKeySelective(rank);
        }

    }

    @Override
    public int update(Rank rank) {
        return rankMapper.updateByPrimaryKeySelective(rank);
    }

    @Override
    public List<Rank> getList() {
        return rankMapper.getList();
    }

    @Override
    public Rank getInfo(int userId) {
        return rankMapper.selectByUser(userId);
    }
}
