package com.bigcity.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * * ================================================
 * name:            AppInfoUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/11
 * description：   app信息相关工具类
 * history：
 *
 * ---getVersionCode(Context context)--------获取App版本码---对应app->gradle中的versionCode
 * ---getAppName(Context context)---------获取应用程序名称--对应AndroidManifest中的android:label="@string/app_name"
 * ---getVersionName(Context context)---获取应用程序版本名称信息--对应app  gradle中的versionName
 * ---
 * ===================================================
 */

public class AppInfoUtils {
    /**
     * 获取App版本码---对应app  gradle中的versionCode
     *
     * @param context 上下文
     * @return App版本码  gradle中的versionCode
     */
    public static int getVersionCode(Context context) {
        if (isSpace(context.getPackageName())) return -1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 判断字符串为null或者是否全部为空白字符,换行符,空格符等
     */
    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取应用程序名称--对应AndroidManifest中的android:label="@string/app_name"
     */
    public static String getAppName(Context context) {
        if (isSpace(context.getPackageName())) return "无法获取到应用名称";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "无法获取到应用名称";
    }

    /**
     * 获取应用程序版本名称信息--对应app  gradle中的versionName
     *
     * @param context 上下文
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        if (isSpace(context.getPackageName())) return "无法获取到应用名称";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "无法获取到应用名称";
    }

    /**
     * 判断app是否为debug版本
     */
    public static boolean isApkDebugable(Context context) {
        ApplicationInfo info = context.getApplicationInfo();
        return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}
