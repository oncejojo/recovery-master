package com.jojo.recovery.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.jojo.recovery.model.WeChatTemplateMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wx")
@Slf4j
public class TemplateMsgController {

    @RequestMapping("/template")
    public String sendTemplateMsg() {
        // openId代表一个唯一微信用户，即微信消息的接收人
        String openId = "o38P16HaJlgTdD06U_AkYmPao5_k";
        // 公众号的模板id(也有相应的接口可以查询到)
        String templateId = "Y3lZ1oD77xTQF0gmknT_FbwN0QpDwVR2ETsSgyGvfA8";
        // 微信的基础accessToken
        String accessToken = "57_LubK-8NKQc6C7jsLMxvdHaI0ju4x3-HPWEFhh7GKkw9fKbWhuxxoZyX4GaVIn6y4yO7RKfSlCyHdedKJlHUMZkd8457nKm0TOoaVkbzK1HCZ4g4gZdrmAGBylGBOZu9yxxxxxxxxxxxxxxxx";
        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;
        String navigateUrl = "https://www.jblwrxx.com";
        /**
         *  其他模板可以从模板库中自己添加
         * 模板ID
         * Y3lZ1oD77xTQF0gmknT_FbwN0QpDwVR2ETsSgyGvfA8
         * 开发者调用模板消息接口时需提供模板ID
         * 标题
         * 结果通知
         * 行业
         * IT
         * 详细内容
         * {{first.DATA}}
         * 订单号：{{keyword1.DATA}}
         * 品牌名称：{{keyword2.DATA}}
         * 金额：{{keyword3.DATA}}
         * 检测时间：{{keyword4.DATA}}
         * {{remark.DATA}}
         */
        Map<String, WeChatTemplateMsg> sendMsg = new HashMap<>();
        sendMsg.put("first", new WeChatTemplateMsg("结果通知"));
        sendMsg.put("keyword1", new WeChatTemplateMsg("orderId"));
        sendMsg.put("keyword2", new WeChatTemplateMsg("brandName"));
        sendMsg.put("keyword3", new WeChatTemplateMsg("price"));
        sendMsg.put("keyword4", new WeChatTemplateMsg("createTime"));
        sendMsg.put("remark", new WeChatTemplateMsg("点击查看详情"));
        Map<String, Object> sendBody = new HashMap<>();
        sendBody.put("touser", openId);
        sendBody.put("url", navigateUrl);
        sendBody.put("data", sendMsg);
        sendBody.put("template_id", templateId);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> entity = restTemplate.postForEntity(url, sendBody, String.class);
        JSONObject jsonObject = JSONObject.parseObject(entity.getBody());
        String code = jsonObject.getString("errcode");
        String msgId = jsonObject.getString("msgid");
        log.info("code:" + code + "msgId:" + msgId);

        return entity.getBody();
    }
}
