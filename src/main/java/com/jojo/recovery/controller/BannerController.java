package com.jojo.recovery.controller;

import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.Banner;
import com.jojo.recovery.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JoJo
 * @Date 2022/3/21 13:44
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @GetMapping("/info")
    public AjaxResult dataInfo(int id) {
        Banner banner = bannerService.getRecord(id);
        if (banner == null) {
            return AjaxResult.error(JsonResultEnum.TARGET_NULL);
        }
        return AjaxResult.successData(banner);
    }

    @PostMapping("/insert")
    public AjaxResult insertBanner(Banner banner) {
        bannerService.insert(banner);
        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult updateBanner(Banner banner) {
        Banner record = bannerService.getRecord(banner.getId());
        if (record == null) {
            return AjaxResult.error(JsonResultEnum.TARGET_NULL);
        }
        bannerService.update(banner);
        return AjaxResult.success();
    }

    @PostMapping("/delete")
    public AjaxResult deleteBanner(int id) {
        Banner banner = bannerService.getRecord(id);
        if (banner == null) {
            return AjaxResult.error(JsonResultEnum.TARGET_NULL);
        }
        bannerService.delete(id);
        return AjaxResult.success();
    }
}
