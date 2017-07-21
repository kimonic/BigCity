package com.bigcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.utils.ScreenSizeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * * ================================================
 * name:            RegisterActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/21
 * description：注册界面activity
 * history：
 * ===================================================
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.lv_register_close)
    LinearLayout lvRegisterClose;

    @Override
    public int getLayoutResId() {
        return R.layout.act_register;
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvRegisterClose.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        lvRegisterClose.setLayoutParams(params);
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
