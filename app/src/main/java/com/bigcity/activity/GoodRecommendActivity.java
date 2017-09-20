package com.bigcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.CommonBean;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ================================================
 * name:            GoodRecommendActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/30
 * description：好物推荐界面activity
 * history：
 * ===================================================
 */


public class GoodRecommendActivity extends BaseActivity {
    @BindView(R.id.mtb_act_goodrecommend)
    MTopBarView mtbGoodrecommend;
    @BindView(R.id.lv_act_goodrecommend)
    ListView lvGoodrecommend;

    private List<CommonBean>  list;

    @Override
    public int getLayoutResId() {
        return R.layout.act_goodrecommend;
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
        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbGoodrecommend.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbGoodrecommend.setLayoutParams(params);

        CommonLVAdapter adapter=new CommonLVAdapter(this,list);
        lvGoodrecommend.setAdapter(adapter);



    }

    @Override
    public void initListener() {
        mtbGoodrecommend.getLeftTV().setOnClickListener(new View.OnClickListener() {
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
    public void loadInternetDataToUi() {

    }

}
