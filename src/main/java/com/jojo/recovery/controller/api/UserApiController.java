package com.jojo.recovery.controller.api;

import cn.hutool.json.JSONObject;
import com.jojo.recovery.common.domain.AjaxResult;
import com.jojo.recovery.common.enums.JsonResultEnum;
import com.jojo.recovery.model.User;
import com.jojo.recovery.service.UserService;
import com.jojo.recovery.utils.WeChatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JoJo
 * @Date 2022/5/26 13:08
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public AjaxResult wxLogin(String code) {
        JSONObject jsonObject = WeChatUtil.getSessionKeyOrOpenId(code);
        return AjaxResult.successData(jsonObject);
    }

    @GetMapping("/newUser")
    public AjaxResult newUser(String encryptedData, String sessionKey, String iv){
        JSONObject jsonObject = WeChatUtil.getUserInfo(encryptedData, sessionKey,iv);
        System.out.println(jsonObject);
        String phone = jsonObject.get("phoneNumber").toString();
        User record = userService.getRecordByPhone(phone);
        if (record == null) {
            record = new User();

            record.setPhone(phone);
            userService.insert(record);
        }

        return AjaxResult.successData(record);
    }

    @PostMapping("/update")
    public AjaxResult updateUser(User user) {
        userService.update(user);
        return AjaxResult.successData(user);
    }

    @GetMapping("/info")
    public AjaxResult getUserInfo(int id) {
        User user = userService.getRecord(id);
        if (user == null) {
            return AjaxResult.error(JsonResultEnum.USER_NULL);
        }
        return AjaxResult.successData(user);
    }
}
