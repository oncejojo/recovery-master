package com.jojo.recovery.service;

import com.jojo.recovery.model.BinFull;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/7/19 16:53
 * @Description
 * @Version 1.0
 */
public interface BinFullService {
    Integer insert(BinFull binFull);

    Integer update(BinFull binFull);

    List<BinFull> getList(BinFull binFull);

    Integer updateFull(BinFull binFull);

    BinFull getInfoByCodeNo(BinFull binFull);

    List<BinFull> getFullList(Integer boxId);

    Integer updateCode(Integer boxId, String code);
}
