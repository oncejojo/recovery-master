package com.jojo.recovery.utils;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;

/**
 * @Author JoJo
 * @Data 2021/11/11$ 16:45$
 * @Description
 * @Param $
 * @Return $
 */
public class MessageUtil {
    public static com.aliyun.dysmsapi20170525.Client createClient(String phone, String message) throws Exception {

        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId("LTAI5tQZCYYz5Wc2pJyQ2UBo")
                // 您的AccessKey Secret
                .setAccessKeySecret("pWQ9Gy0DjpI2ouKz8G31RQuEoWX8t1");
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        com.aliyun.dysmsapi20170525.Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName("金宝莱回收")
                .setTemplateCode("SMS_248740443")
                .setTemplateParam(message)
                .setSmsUpExtendCode("90999")
                .setOutId("abcdefgh");
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
        System.out.println(client);
        return new com.aliyun.dysmsapi20170525.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        String verifyCode = "安徽芜湖外包园"+"-"+"金属箱";

        String message = "{\"name\":\"" + verifyCode + "\"}";
        MessageUtil.createClient("17355367349",message);

    }


}
