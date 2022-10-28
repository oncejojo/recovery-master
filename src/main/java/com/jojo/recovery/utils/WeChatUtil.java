package com.jojo.recovery.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author JoJo
 * @Date 2021/12/6 1:19
 * @Description
 * @Version 1.0
 */

public class WeChatUtil {

    public static JSONObject getSessionKeyOrOpenId(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", "wx8294d8461af030e5");
        //小程序secret
        requestUrlParam.put("secret", "3f0e4a283470384d4c40b37c6fd177b5");
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpUtil.get(requestUrl, requestUrlParam);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject;
    }

    public static JSONObject getAccessToken() {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", "wx8294d8461af030e5");
        //小程序secret
        requestUrlParam.put("secret", "3f0e4a283470384d4c40b37c6fd177b5");
        //默认参数
        requestUrlParam.put("grant_type", "client_credential");
        //发送post请求读取调用微信接口获取accessToken
        String result = HttpUtil.get(requestUrl, requestUrlParam);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject;
    }

    public static JSONObject getQrcode(String token, Integer id) {
        String requestUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token;
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        requestUrlParam.put("scene", "id=1");
        requestUrlParam.put("page", "pages/index/index");
        requestUrlParam.put("check_path", false);
        requestUrlParam.put("env_version", "release");

        String result = HttpUtil.post(requestUrl, requestUrlParam);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        return jsonObject;
    }



    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONUtil.parseObj(result);
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String token = getAccessToken().get("access_token").toString();
        System.out.println("token:" + token);
        String buffer = getMiniQrcode("a=1",token);
        System.out.println("buffer" + buffer);
    }

    public static String getMiniQrcode(String sceneStr, String accessToken) {
        RestTemplate rest = new RestTemplate();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] result = null;
        String codeUrl = null;
        ResponseEntity<byte[]> entity = null;
        File file = null;
        try {
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken;
            Map<String, Object> param = new HashMap<>();
            param.put("scene", sceneStr);
            param.put("page", "pages/index/index");
            param.put("width", 430);
            param.put("is_hyaline", true);
            param.put("auto_color", false);
            Map<String, Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
//            LOG.info("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            entity = rest.exchange(url, HttpMethod.POST, requestEntity, byte[].class, new Object[0]);
//            LOG.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
            result = entity.getBody();
            System.out.println("result:" + result);

            inputStream = new ByteArrayInputStream(result);
            String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + sceneStr.split("=")[1] + ".png";
            file = new File("D:/fileUpload/"+ fileName);
//            file = new File("/root/source/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
            codeUrl = "https://jblhsx.ahwrxx.com/image/" + fileName;
        } catch (Exception e) {
            System.out.println("调用小程序生成微信永久小程序码URL接口异常" + e);
//            LOG.error("调用小程序生成微信永久小程序码URL接口异常",e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return codeUrl;
    }


}