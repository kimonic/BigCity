package com.bigcity.utils;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * * ================================================
 * name:            ScreenSizeUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/11
 * description：  工具类 --屏幕尺寸相关
 * ---------------getScreenWidth(Context context)------获取屏幕宽度
 * ---------------getScreenHeight(Context context)------获取屏幕高度
 * ---------------getDensityDpi(Context context)------获取屏幕密度dpi--displayMetrics.density*160
 * ---------------getDensity(Context context)---------获取屏幕密度--displayMetrics.densityDpi/160
 * ---------------dip2px(Context context, float dpValue)----dp转化为px
 * ---------------sp2px(Context context, float spValue)------sp转化为px
 * ---------------getStatusHeight(Context context)-------获取状态栏的高度px值
 * history：
 * ===================================================
 */

public class ScreenSizeUtils {
    private ScreenSizeUtils() {
        throw new UnsupportedOperationException("不允许使用new构造实例!!");

    }

    /**
         * 获取屏幕的宽度px
         *
         * @param context 上下文
         * @return 屏幕宽px
         */
        public static int getScreenWidth(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);// 给白纸设置宽高
            return point.x;
        }

        /**
         * 获取屏幕的高度px
         *
         * @param context 上下文
         * @return 屏幕高px
         */
        public static int getScreenHeight(Context context) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);// 设置宽高
            return point.y;
        }

        /**
         * 获得屏幕密度
         *
         * @param context 上下文
         * @return displayMetrics.density*160
         */

        public static int getDensityDpi(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return displayMetrics.densityDpi;
        }

        /**
         * @param context 上下文
         * @return displayMetrics.densityDpi/160
         */
        public static int getDensity(Context context) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            return (int) displayMetrics.density;
        }

        /**.
         * dp转化为px
         * @param context  上下文
         * @param dpValue  dp值
         * @return  像素值px
         */

        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * sp转化为px
         * @param context  上下文
         * @param spValue  sp值
         * @return  像素值px
         */
        public static int sp2px(Context context, float spValue) {
            final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }

    /**
     * 获取状态栏的高度px值
     * @param context  上下文
     * @return    状态栏高度--单位px
     */
    public static int getStatusHeight(Context context){
            int statusBarHeight = -1;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
            return statusBarHeight;
        }

}
