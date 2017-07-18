package com.bigcity.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * * ==================================================
 * name:            ToastUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：   toast显示工具类
 * history：
 * * ==================================================
 *
 */


public class ToastUtils {

    /**使用资源id展示*/
    public static void showToast(Context context,int resId){
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
    }

    /**使用字符串展示*/
    public static void showToast(Context context,String content){
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
