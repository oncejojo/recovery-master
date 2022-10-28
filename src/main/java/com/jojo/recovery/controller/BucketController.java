package com.jojo.recovery.controller;

import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.model.Bucket;
import com.jojo.recovery.service.BucketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author JoJo
 * @Date 2022/7/20 17:02
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/bucket")
public class BucketController {
    @Resource
    BucketService bucketService;

    @GetMapping("/info")
    public AjaxResult getInfo(Integer id) {
        Bucket bucket = bucketService.getInfo(id);
        return AjaxResult.successData(bucket);
    }

    @PostMapping("/update")
    public AjaxResult update(Bucket bucket) {
        bucketService.update(bucket);
        return AjaxResult.success();
    }
}
