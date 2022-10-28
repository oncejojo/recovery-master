package com.jojo.recovery.controller;

import cn.hutool.json.JSONObject;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.BinFull;
import com.jojo.recovery.model.Box;
import com.jojo.recovery.service.BinFullService;
import com.jojo.recovery.service.BoxService;
import com.jojo.recovery.utils.WeChatUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/6/20 14:16
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/box")
public class BoxController {
    @Resource
    BoxService boxService;
    @Resource
    BinFullService binFullService;

    @PostMapping("/insert")
    public AjaxResult insert(Box box) {
        boxService.insert(box);
        BinFull binFull = new BinFull();
        for(int i = 1; i < 5; i++) {
            binFull.setBoxId(box.getId());
            binFull.setNo(i);
            binFull.setDevCode(box.getCode());
            binFullService.insert(binFull);
        }

        return AjaxResult.success();
    }

    @PostMapping("/update")
    public AjaxResult update(Box box) {
        Box record = boxService.getRecord(box.getId());
        if (record == null) {
            return AjaxResult.error(JsonResultEnum.TARGET_NULL);
        }
        boxService.update(box);
        if (!record.getCode().equals(box.getCode())){
            binFullService.updateCode(box.getId(),box.getCode());
        }
        return AjaxResult.success();
    }

    @GetMapping("/info")
    public AjaxResult getInfo(Integer id) {
        Box box = boxService.getRecord(id);
        if (box == null) {
            return AjaxResult.error("箱体不存在");
        }
        return AjaxResult.successData(box);
    }

    @PostMapping("/delete")
    public AjaxResult delete(Integer id) {
        boxService.delete(id);
        return AjaxResult.success();
    }

    @PostMapping("/code")
    public AjaxResult createCode(Integer id) {
        Box box = boxService.getRecord(id);
        if (box == null) {
            return AjaxResult.error("箱体不存在");
        }
        JSONObject token = WeChatUtil.getAccessToken();
        String sceneStr = "message=" + box.getName();
        String code = WeChatUtil.getMiniQrcode(sceneStr, token.get("access_token").toString());
        box.setCode(code);
        boxService.update(box);
        return AjaxResult.successData(box);
    }


}
