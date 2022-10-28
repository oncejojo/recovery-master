package com.jojo.recovery.service.impl;

import com.jojo.recovery.mapper.BannerMapper;
import com.jojo.recovery.model.Banner;
import com.jojo.recovery.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/4/27 13:53
 * @Description
 * @Version 1.0
 */
@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Override
    public int insert(Banner banner) {
        return bannerMapper.insertSelective(banner);
    }

    @Override
    public int update(Banner banner) {
        return bannerMapper.updateByPrimaryKeySelective(banner);
    }

    @Override
    public int delete(int id) {
        return bannerMapper.delete(id);
    }

    @Override
    public List<Banner> getList() {
        return bannerMapper.getList();
    }

    @Override
    public Banner getRecord(int id) {
        return bannerMapper.selectByPrimaryKey(id);
    }
}
