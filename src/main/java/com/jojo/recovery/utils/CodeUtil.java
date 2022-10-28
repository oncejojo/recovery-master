package com.jojo.recovery.utils;

import java.util.Random;

/**
 * @Author JoJo
 * @Data 2021/9/9$ 11:26$
 * @Description
 * @Param $
 * @Return $
 */
public class CodeUtil {

    /**
     * desc 生成6位字母数字码数字不超过4个
     * @return
     */
    public static String generateCode() {
        String charList = "ABCDEFGHIJKLMNPQRSTUVWXY";
        String numList = "0123456789";
        String rev = "";
        int maxNumCount = 4;
        int length = 6;
        Random f = new Random();
        for (int i = 0; i < length; i++) {
            if (f.nextBoolean() && maxNumCount > 0) {
                maxNumCount--;
                rev += numList.charAt(f.nextInt(numList.length()));
            } else {
                rev += charList.charAt(f.nextInt(charList.length()));
            }
        }
        return rev;
    }
}
