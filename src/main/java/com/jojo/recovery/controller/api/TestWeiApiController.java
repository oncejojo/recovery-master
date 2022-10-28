package com.jojo.recovery.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author JoJo
 * @Date 2022/10/27 23:23
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/wxmsg")
@Slf4j
public class TestWeiApiController {
    private static final long serialVersionUID = 1L;

    public static final String TOKEN = "weixin";

    @GetMapping("/weixin")
    protected void doget(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        System.out.println(signature + timestamp + nonce);

        PrintWriter out = response.getWriter();
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (checkSignature(signature, timestamp, nonce).equals(signature)) {
            out.write(echostr);
            System.out.println("微信服务验证成功！" + echostr);
        } else {
            out.print(echostr);
            System.out.println("微信服务验证失败！" + echostr);
        }
        out.flush();
        out.close();
    }

    public static String checkSignature(String signature, String timestamp, String nonce) {
        String[] src = {TOKEN, timestamp, nonce};
        List<String> list = Arrays.asList(src);
        Collections.sort(list);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        return SHA1(sb.toString());

    }

    /**
     * @param decript
     * @return
     */
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}

