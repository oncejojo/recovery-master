package com.jojo.recovery.common.enums;

public enum VxConfigEnum {
    APPID("wxf0317710603c558d"),//
    APPSECRET("1a5783c86b1083e7ce9060ecc35c3b85"),  //
    ROOTURLS("https://api.weixin.qq.com/"),
    ROOTURL("http://api.weixin.qq.com/"),
    MYTOKEN("weixin");   // 验证token
    VxConfigEnum(String val){
        this.val = val;
    }
    public String val;
}
