package com.bigcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.CommonBean;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ImageGlideUtils;
import com.bigcity.utils.ScreenSizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ================================================
 * name:            MyActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/30
 * description：我的界面activity
 * history：
 * ===================================================
 */

public class MyActivity extends BaseActivity {
    @BindView(R.id.mtb_act_my)
    MTopBarView mtbActMy;
    @BindView(R.id.iv_act_my_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_act_my_nicheng)
    TextView tvNiCheng;
    @BindView(R.id.tv_act_my_qianming)
    TextView tvQianMing;
    @BindView(R.id.tv_act_my_tiezishu)
    TextView tvTieZiShu;
    @BindView(R.id.tv_act_my_pinglunshu)
    TextView tvPingLunShu;
    @BindView(R.id.lv_act_my)
    ListView lvActMy;
    @BindView(R.id.tv_act_my_sixin)
    TextView tvSiXin;
    private List<CommonBean> list;

    @Override
    public int getLayoutResId() {
        return R.layout.act_my;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {
        list=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CommonBean bean=new CommonBean();
            bean.setTitle("这是标题");
            bean.setContent("iefrhfnklsfnskdfh;fjknfkvfnsdkfweiafkvnksdaisdfjeiw;fd" +
                    "fjknfkvfnsdkfweiafkvnksdaisdfjeiw" +
                    "fjknfkvfnsdkfweiafkvnksdaisdfjeiwfjknfkvfnsdkfweiafkvnksdaisdfjeiw" +
                    "fjknfkvfnsdkfweiafkvnksdaisdfjeiwfjknfkvfnsdkfweiafkvnksdaisdfjeiwfjknfkvfnsdkfweiafkvnksdaisdfjeiw" +
                    "fjknfkvfnsdkfweiafkvnksdaisdfjeiwfjknfkvfnsdkfweiafkvnksdaisdfjeiwkxnfdksf");
            bean.setHuifushu("23");
            bean.setLiulanshu("56");
            bean.setImaId(R.drawable.act_my_icon);
            bean.setImaId1(R.drawable.icon_temp1);
            bean.setImaId2(R.drawable.icon_temp2);
            bean.setImaId3(R.drawable.icon_temp3);
            list.add(bean);

        }
    }

    @Override
    public void initView() {
        ImageGlideUtils.loadCircularImage(ivIcon, R.drawable.act_my_icon);

        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbActMy.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbActMy.setLayoutParams(params);

        CommonLVAdapter adapter=new CommonLVAdapter(this,list);
        lvActMy.setAdapter(adapter);
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
