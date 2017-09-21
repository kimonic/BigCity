package com.bigcity.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bombtest.BombTestActivity;
import com.bigcity.datamanage.BlogBmobBeanAddActivity;

import butterknife.BindView;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * * ================================================
 * name:            PreviewActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/11
 * description：预览界面activity
 * history：
 * ===================================================
 */

public class PreviewActivity extends BaseActivity {
    @BindView(R.id.bt_act_preview)
    Button btActPreview;
    @BindView(R.id.bt_act_preview1)
    Button btActPreview1;
    @BindView(R.id.bt_act_preview2)
    Button btActPreview2;
    @BindView(R.id.bt_act_preview3)
    Button btActPreview3;
    @BindView(R.id.bt_act_preview4)
    Button btActPreview4;
    @BindView(R.id.bt_act_preview5)
    Button btActPreview5;
    @BindView(R.id.bt_act_preview6)
    Button btActPreview6;
    @BindView(R.id.bt_act_preview7)
    Button btActPreview7;
    @BindView(R.id.bt_act_preview8)
    Button btActPreview8;
    @BindView(R.id.bt_act_preview9)
    Button btActPreview9;
    @BindView(R.id.bt_act_preview10)
    Button btActPreview10;
 @BindView(R.id.bt_act_preview11)
    Button btActPreview11;

    @Override
    public int getLayoutResId() {
        return R.layout.act_preview;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_act_preview:
                openActivity(LoginActivity.class);
                break;
            case R.id.bt_act_preview1:
                openActivity(RegisterActivity.class);
                break;
            case R.id.bt_act_preview2:
                openActivity(FeedbackActivity.class);
                break;
            case R.id.bt_act_preview3:
                openActivity(MyActivity.class);
                break;
            case R.id.bt_act_preview4:
                openActivity(GoodRecommendActivity.class);
                break;
            case R.id.bt_act_preview5:
                openActivity(SharkItOffActivity.class);
                break;
            case R.id.bt_act_preview6:
                openActivity(HomeActivity.class);
                break;
            case R.id.bt_act_preview7:
                openActivity(RelatedMeActivity.class);
                break;
            case R.id.bt_act_preview8:
                openActivity(AddDataListActivity.class);
                break;
            case R.id.bt_act_preview9:
                showShare();
                break;
            case R.id.bt_act_preview10:
                openActivity(BombTestActivity.class);
                break;
            case R.id.bt_act_preview11:
                openActivity(BlogBmobBeanAddActivity.class);
                break;
        }
    }

    @Override
    public void initDataFromIntent() {
//

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        btActPreview.setOnClickListener(this);
        btActPreview1.setOnClickListener(this);
        btActPreview2.setOnClickListener(this);
        btActPreview3.setOnClickListener(this);
        btActPreview4.setOnClickListener(this);
        btActPreview5.setOnClickListener(this);
        btActPreview6.setOnClickListener(this);
        btActPreview7.setOnClickListener(this);
        btActPreview8.setOnClickListener(this);
        btActPreview9.setOnClickListener(this);
        btActPreview10.setOnClickListener(this);
        btActPreview11.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
//关闭sso授权
        oks.disableSSOWhenAuthorize();

// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
// titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("https://juejin.im/welcome/android");
// text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }


}
