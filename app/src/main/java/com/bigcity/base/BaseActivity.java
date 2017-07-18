package com.bigcity.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lzy.imagepicker.view.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * * ================================================
 * name:            BaseActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：     activity基类
 * history：
 * ===================================================
 *
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseMethod,View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSystemBarTint();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏显示
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);//禁止截屏
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
        initDataFromIntent();
        initView();
        initListener();
        initDataFromInternet();

    }
    /**设置加载的布局*/
    public abstract int getLayoutResId();


    /** 设置状态栏颜色改变状态栏颜色 */
    protected int setStatusBarColor() {
        return 0;
    }

    /** 子类可以重写决定是否使用透明状态栏 */
    protected boolean translucentStatusBar() {
        return false;
    }

    /** 设置状态栏颜色 */
    protected void initSystemBarTint() {
        if (setStatusBarColor()!=0){
            Window window = getWindow();
            if (translucentStatusBar()) {
                // 设置状态栏全透明
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                }
            }
            // 沉浸式状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//21
                //5.0以上使用原生方法
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(setStatusBarColor());//直接设置状态栏的颜色
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//19
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(setStatusBarColor());//直接设置状态栏的颜色
            }
        }


    }

    /**启动下一个activity*/
    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }
    /**启动下一个activity*/
    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(this, toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }

    /**关闭activity*/
    protected void closeActivity() {
        this.finish();
    }
}
