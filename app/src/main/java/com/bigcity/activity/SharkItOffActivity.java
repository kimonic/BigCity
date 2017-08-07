package com.bigcity.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ================================================
 * name:            SharkItOffActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/7
 * description：摇一摇界面activity
 * history：
 * ===================================================
 */

public class SharkItOffActivity extends BaseActivity {
    @BindView(R.id.mtb_act_sharkitoff)
    MTopBarView mtbActSharkItOff;
    @BindView(R.id.tv_act_sharkitoff)
    TextView tvActSharkItOff;

    @Override
    public int getLayoutResId() {
        return R.layout.act_sharkitoff;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_act_sharkitoff:
                showAnimation();
                break;
        }

    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {
        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbActSharkItOff.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbActSharkItOff.setLayoutParams(params);

        mtbActSharkItOff.getLeftTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    @Override
    public void initListener() {
        tvActSharkItOff.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }

    private void showAnimation(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvActSharkItOff, "rotation", 0f, 30f, -30f,30f,-30f,0);
        animator.setDuration(1000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ToastUtils.showToast(SharkItOffActivity.this,"哈哈哈哈哈哈!");

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


}
