package com.jojo.recovery.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.model.BinFull;
import com.jojo.recovery.model.Box;
import com.jojo.recovery.model.Bucket;
import com.jojo.recovery.model.Full;
import com.jojo.recovery.service.BinFullService;
import com.jojo.recovery.service.BoxService;
import com.jojo.recovery.service.BucketService;
import com.jojo.recovery.utils.MessageUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/7/19 10:28
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/box")
public class BoxApiController {
    @Resource
    BoxService boxService;
    @Resource
    BinFullService binFullService;
    @Resource
    BucketService bucketService;

    @PostMapping("/check")
    public AjaxResult checkBox(Box box){
        List<Box> boxList = boxService.getList(box);
        if (boxList.size()==0){
            return AjaxResult.error("箱体不存在");
        }
        return AjaxResult.success("箱体认证成功");
    }

    @PostMapping("/full")
    public AjaxResult updateBinFull(Full full) throws Exception {
        Box box = boxService.getRecordByCode(full.getCode());
        if (box == null) {
            return AjaxResult.error("箱体不存在");
        }
        List<BinFull> binFulls = JSONObject.parseArray(full.getMessage(), BinFull.class);
        // 箱体满溢
        for (BinFull binFull : binFulls){
            Bucket bucket = bucketService.getInfo(binFull.getNo());

            BigDecimal weight = binFull.getWeight();
            if (weight.compareTo(new BigDecimal(0.00)) == -1) {
                binFull.setWeight(weight.multiply(new BigDecimal(-1)));
            }
            if(binFull.getFullValue() == 1){
                String verifyCode = box.getName() + "-" +bucket.getName();
                String message = "{\"name\":\"" + verifyCode + "\"}";


                MessageUtil.createClient("15550939567", message);
            }
            binFull.setDevCode(full.getCode());
            binFull.setBoxId(box.getId());
            binFull.setUpdateTime(new Date());
            binFullService.updateFull(binFull);

        }

        return AjaxResult.success();
    }
}
