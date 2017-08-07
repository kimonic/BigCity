package com.bigcity.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.bigcity.R;
import com.bigcity.adapter.HomeFragVPAdapter;
import com.bigcity.base.BaseActivity;
import com.bigcity.fragment.HomeFragment;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * * ================================================
 * name:            HomeActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/7
 * description：  首页界面activity
 * history：
 * ===================================================
 */

public class HomeActivity extends BaseActivity {


    @BindView(R.id.mtb_act_home)
    MTopBarView mtbActHome;
    @BindView(R.id.vp_act_home)
    ViewPager vpActHome;
    @BindView(R.id.ll_act_home1)
    LinearLayout llActHome1;
    @BindView(R.id.ll_act_home2)
    LinearLayout llActHome2;
    @BindView(R.id.ll_act_home3)
    LinearLayout llActHome3;
    @BindView(R.id.ll_act_home4)
    LinearLayout llActHome4;

    private List<Fragment>  list;

    @Override
    public int getLayoutResId() {
        return R.layout.act_home;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

        list=new ArrayList<>();
        HomeFragment fragment1=new HomeFragment();
        HomeFragment fragment2=new HomeFragment();
        HomeFragment fragment3=new HomeFragment();
        HomeFragment fragment4=new HomeFragment();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);

    }

    @Override
    public void initView() {
        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbActHome.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbActHome.setLayoutParams(params);

        HomeFragVPAdapter adapter=new HomeFragVPAdapter(getSupportFragmentManager(),list);
        vpActHome.setAdapter(adapter);
        vpActHome.setOffscreenPageLimit(4);


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
