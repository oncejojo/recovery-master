package com.jojo.recovery.utils;

import com.jojo.recovery.model.Order;
import com.jojo.recovery.model.SysUser;
import com.jojo.recovery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 后台功能所用工具类
 */
public class AdminUtil {


    public static SysUser getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (SysUser) session.getAttribute("admin");
    }

    public static String orderInsert(Order order){
        try {
            RestTemplate template = new RestTemplate();
            String url = "https://jbl.ahwrxx.com/api/order/insert";
            // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
            MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
            paramMap.add("userId", order.getUserId());
            paramMap.add("weight", order.getWeight());
            paramMap.add("money", order.getMoney());
            // 1、使用postForObject请求接口
            String result = template.postForObject(url, paramMap, String.class);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "请求失败";
        }
    }
}
