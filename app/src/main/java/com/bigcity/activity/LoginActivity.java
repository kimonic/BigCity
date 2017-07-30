package com.bigcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.utils.ScreenSizeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.tv_act_login_close)
    TextView tvClose;

    @Override
    public int getLayoutResId() {
        return R.layout.act_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_act_login_close:
                closeActivity();
                break;
        }
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
        tvClose.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }



}
