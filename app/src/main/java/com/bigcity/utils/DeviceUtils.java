package com.bigcity.utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.File;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * * ================================================
 * name:            DeviceUtils
 * guide:
 * author：          DeviceUtils
 * version：          1.0
 * date：            2017/7/12
 * description： 设备信息相关工具类
 * history：
 *
 * -----------------isDeviceRooted()----判断设备是否root
 * -----------------getSDKVersion()----获取设备系统Android版本号
 * -----------------getAndroidID(Context context)----获取设备AndroidID
 * -----------------getMacAddress(Context context)---获取设备MAC地址
 * ----private------getMacAddressByWifiInfo(Context context)---通过WiFi信息获取设备MAC地址
 * ----private------getMacAddressByNetworkInterface()----通过网络接口获取设备MAC地址
 * -----------------getManufacturer()-----获取设备厂商
 * -----------------getModel()-----获取设备型号
 * -----------------getIMET(Context context)-----获取设备IMET
 * ===================================================
 */
public class DeviceUtils {

    private DeviceUtils() {
        throw new UnsupportedOperationException("不允许使用new构造实例!!");
    }

    /**
     * 判断设备是否root
     *
     * @return the boolean{@code true}: 是  {@code false}: 否
     */
    public static boolean isDeviceRooted() {
        String su = "su";
        String[] locations = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/", "/system/bin/failsafe/",
                "/data/local/xbin/", "/data/local/bin/", "/data/local/"};
        for (String location : locations) {
            if (new File(location + su).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取设备系统Android版本号
     *
     * @return 设备系统版本号
     */
    public static int getSDKVersion() {
        return Build.VERSION.SDK_INT;
    }


    /**
     * 获取设备AndroidID
     *
     * @return AndroidID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    public static String getMacAddress(Context context) {
        String macAddress = getMacAddressByWifiInfo(context);
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        macAddress = getMacAddressByNetworkInterface();
        if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }
        /*macAddress = getMacAddressByFile();
        /*if (!"02:00:00:00:00:00".equals(macAddress)) {
            return macAddress;
        }*/
        return "please open wifi";
    }

    /**
     * 通过WiFi信息获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return MAC地址
     */
    @SuppressLint("HardwareIds")
    private static String getMacAddressByWifiInfo(Context context) {
        try {
            WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifi != null) {
                WifiInfo info = wifi.getConnectionInfo();
                if (info != null) return info.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 通过网络接口获取设备MAC地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return MAC地址
     */
    private static String getMacAddressByNetworkInterface() {
        try {
            List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni : nis) {
                if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
                byte[] macBytes = ni.getHardwareAddress();
                if (macBytes != null && macBytes.length > 0) {
                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02x:", b));
                    }
                    return res1.deleteCharAt(res1.length() - 1).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    /**
     * 获取设备厂商
     * <p>如Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取设备型号
     * <p>如MI2SC</p>
     *
     * @return 设备型号
     */
    public static String getModel() {
        String model = Build.MODEL;
        if (model != null) {
            model = model.trim().replaceAll("\\s*", "");
        } else {
            model = "";
        }
        return model;
    }

    /**获取设备IMEI*/
    @SuppressLint("HardwareIds")
    public static String getIMET(Context context){
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(TELEPHONY_SERVICE);
        return TelephonyMgr.getDeviceId();
//        return TelephonyMgr.getSubscriberId();
//        return TelephonyMgr.getLine1Number();
    }




}