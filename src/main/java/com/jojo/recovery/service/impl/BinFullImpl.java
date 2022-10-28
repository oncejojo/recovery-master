package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.BinFullMapper;
import com.jojo.recovery.model.BinFull;
import com.jojo.recovery.service.BinFullService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/7/19 16:55
 * @Description
 * @Version 1.0
 */
@Service
public class BinFullImpl implements BinFullService {
    @Resource
    BinFullMapper binFullMapper;

    @Override
    public Integer insert(BinFull binFull) {
        return binFullMapper.insertSelective(binFull);
    }

    @Override
    public Integer update(BinFull binFull) {
        return binFullMapper.updateByPrimaryKeySelective(binFull);
    }

    @Override
    public List<BinFull> getList(BinFull binFull) {
        return binFullMapper.getList(binFull);
    }

    @Override
    public Integer updateFull(BinFull binFull) {
        return binFullMapper.updateFull(binFull);
    }

    @Override
    public BinFull getInfoByCodeNo(BinFull binFull) {
        return binFullMapper.getInfoByCodeNo(binFull);
    }

    @Override
    public List<BinFull> getFullList(Integer boxId) {
        return binFullMapper.getFullList(boxId);
    }

    @Override
    public Integer updateCode(Integer boxId, String code) {
        return binFullMapper.updateCode(boxId, code);
    }
}
