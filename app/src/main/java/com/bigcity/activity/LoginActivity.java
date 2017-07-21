package com.bigcity.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.utils.ScreenSizeUtils;

import butterknife.BindView;

/**
 * * ================================================
 * name:            LoginActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/18
 * description：登陆界面activity
 * history：
 * ===================================================
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.lv_act_login)
    LinearLayout lvActLogin;

    @Override
    public int getLayoutResId() {
        return R.layout.act_login;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {
        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvActLogin.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        lvActLogin.setLayoutParams(params);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }




}
