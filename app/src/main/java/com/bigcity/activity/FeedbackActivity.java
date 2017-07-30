package com.bigcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ================================================
 * name:            FeedbackActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/26
 * description：意见反馈界面activity
 * history：
 * ===================================================
 */

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.mtb_act_feedback)
    MTopBarView mtbFeedback;

    @Override
    public int getLayoutResId() {
        return R.layout.act_feedback;
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbFeedback.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbFeedback.setLayoutParams(params);
    }

    @Override
    public void initListener() {
            mtbFeedback.getLeftTV().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeActivity();
                }
            });
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }


}
