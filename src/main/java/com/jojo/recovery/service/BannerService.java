package com.jojo.recovery.service;

import com.jojo.recovery.model.Banner;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 13:51
 * @Description
 * @Version 1.0
 */
public interface BannerService {
    int insert(Banner banner);

    int update(Banner banner);

    int delete(int id);

    List<Banner> getList();

    Banner getRecord(int id);
}
