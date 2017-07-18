package com.bigcity.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import java.util.List;

/**
 * * ================================================
 * name:            EncryptionLockUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/12
 * description： 手势加密解密工具类
 * history：
 * ===================================================
 */
public class EncryptionLockUtils {

    /**手势密码加密*/
    @SuppressLint("HardwareIds")
    public static String  convertEncryption(Context context,List<Integer> list){
        String password=convertList(list);
        String ANDROID_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return MD5Utils.md5(password+ANDROID_ID);
    }

    /**
     * 将list转化为字符串
     */
    public static String convertList(List<Integer> list) {
        String temp = "";
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != -1) {
                temp += list.get(i);
            }
        }
        return temp;
    }

}
