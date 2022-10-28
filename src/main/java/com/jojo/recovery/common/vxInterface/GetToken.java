package com.jojo.recovery.common.vxInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jojo.recovery.common.enums.VxConfigEnum;
import com.jojo.recovery.utils.HttpInterface;

public class GetToken {
    private static String Token = "11_Rwf_vNCLoGB2NyalxsEyyMLs67MKWRrTAVob8S8TMFb1leloqt1MLWZwCGkJ1F_3NfGRlyzA5QgSabfbF4Y__vK8SytTeE_WVwUZgFF8Sen41EGo0TJi14iKIVuqFWYutgNUxEsrmZbY3EwuORSaAJAADE";
    public static String getToken(){
        if (Token != null)
            return Token;
        synchronized (GetToken.class){
            if (Token == null){
                String url = VxConfigEnum.ROOTURLS.val + "cgi-bin/token?grant_type=client_credential&appid="+ VxConfigEnum.APPID.val+"&secret="+ VxConfigEnum.APPSECRET.val;
                JSONObject json = JSON.parseObject(HttpInterface.doGet(url));
                Token = json.getString("access_token");
                System.out.println(Token);
            }
        }
        return Token;
    }
    public static void setTokenNull(){
        Token = null;
    }

    public static void main(String[] args) {
        System.out.println(GetToken.getToken());
    }
}
