package com.bigcity.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bigcity.R;
import com.bigcity.adapter.RelatedMeActLvAdapter;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.CommonBean;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * * ================================================
 * name:            RelatedMeActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：与我相关界面activity
 * history：
 * ===================================================
 */

public class RelatedMeActivity extends BaseActivity {
    @BindView(R.id.mtb_act_relatedme)
    MTopBarView mtbActRelatedMe;
    @BindView(R.id.lv_act_relatedme)
    ListView lvActRelatedMe;
    private ArrayList<CommonBean> list;

    @Override
    public int getLayoutResId() {
        return R.layout.act_relatedme;
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
            bean.setContent("iefrhfnklsfnskdfh;fjknfkvfnsdkfweiafkvnksdaisdfjeiw;fd");
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
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbActRelatedMe.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbActRelatedMe.setLayoutParams(params);

        RelatedMeActLvAdapter adapter=new RelatedMeActLvAdapter(this,list);
        lvActRelatedMe.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        mtbActRelatedMe.getLeftTV().setOnClickListener(new View.OnClickListener() {
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
