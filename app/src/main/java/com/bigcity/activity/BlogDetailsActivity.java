package com.bigcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ===============================================================
 * name:             BlogDetailsActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/22
 * description： 帖子详情activity
 * history：
 * *==================================================================
 */

public class BlogDetailsActivity extends BaseActivity {
    @BindView(R.id.mtb_act_blogdetails)
    MTopBarView mtb;
    @BindView(R.id.tv_act_blogdetails_title)
    TextView tvTitle;
    @BindView(R.id.tv_act_blogdetails_time)
    TextView tvTime;
    @BindView(R.id.tv_act_blogdetails_author)
    TextView tvAuthor;
    @BindView(R.id.tv_act_blogdetails_content)
    TextView tvContent;
    @BindView(R.id.iv_act_blogdetails_image)
    ImageView ivImage;
    @BindView(R.id.lv_act_blogdetails)
    ListView lv;

    @Override
    public int getLayoutResId() {
        return R.layout.act_blogdetails;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {
        //        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtb.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtb.setLayoutParams(params);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi() {

    }


}
