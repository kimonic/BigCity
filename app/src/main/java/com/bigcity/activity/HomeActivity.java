package com.bigcity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.adapter.HomeFragVPAdapter;
import com.bigcity.base.BaseActivity;
import com.bigcity.fragment.DiscoverFragment;
import com.bigcity.fragment.HomeFragment;
import com.bigcity.fragment.MessageFragment;
import com.bigcity.fragment.MyFragment;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.tv_act_home1)
    TextView tvActHome1;
    @BindView(R.id.tv_act_home2)
    TextView tvActHome2;
    @BindView(R.id.tv_act_home3)
    TextView tvActHome3;
    @BindView(R.id.tv_act_home4)
    TextView tvActHome4;

    private List<Fragment> list;
    private List<TextView> listL;
    private int imaRes[] = new int[]{R.drawable.act_home_cafeunsel,
            R.drawable.act_home_maopaounsel,
            R.drawable.ic_act_home_zhinanzhenunsel
            , R.drawable.ic_act_home_myunsel
    };

    @Override
    public int getLayoutResId() {
        return R.layout.act_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_act_home1:
                vpActHome.setCurrentItem(0);
                setButtonStyle(0);
                break;
            case R.id.ll_act_home2:
                vpActHome.setCurrentItem(1);
                setButtonStyle(1);
                break;
            case R.id.ll_act_home3:
                vpActHome.setCurrentItem(2);
                setButtonStyle(2);
                break;
            case R.id.ll_act_home4:
                vpActHome.setCurrentItem(3);
                setButtonStyle(3);
                break;
        }

    }

    @Override
    public void initDataFromIntent() {

        list = new ArrayList<>();
        HomeFragment fragment1 = new HomeFragment();
        MessageFragment fragment2 = new MessageFragment();
        DiscoverFragment fragment3 = new DiscoverFragment();
        MyFragment fragment4 = new MyFragment();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);


        listL = new ArrayList<>();
        listL.add(tvActHome1);
        listL.add(tvActHome2);
        listL.add(tvActHome3);
        listL.add(tvActHome4);

    }

    @Override
    public void initView() {
        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbActHome.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbActHome.setLayoutParams(params);

        HomeFragVPAdapter adapter = new HomeFragVPAdapter(getSupportFragmentManager(), list);
        vpActHome.setAdapter(adapter);
        vpActHome.setOffscreenPageLimit(4);


    }

    @Override
    public void initListener() {
        vpActHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mtbActHome.setCenterTitle(R.string.dachengxiaoshi);
                        mtbActHome.getRightTV().setVisibility(View.VISIBLE);
                        setButtonStyle(0);
                        break;
                    case 1:
                        mtbActHome.setCenterTitle(R.string.xiaoxi);
                        mtbActHome.getRightTV().setVisibility(View.VISIBLE);
                        setButtonStyle(1);
                        break;
                    case 2:
                        mtbActHome.setCenterTitle(R.string.frag_discover_title);
                        mtbActHome.getRightTV().setVisibility(View.GONE);
                        setButtonStyle(2);
                        break;
                    case 3:
                        mtbActHome.setCenterTitle(R.string.my);
                        mtbActHome.getRightTV().setVisibility(View.VISIBLE);
                        setButtonStyle(3);
                        break;
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        llActHome1.setOnClickListener(this);
        llActHome2.setOnClickListener(this);
        llActHome3.setOnClickListener(this);
        llActHome4.setOnClickListener(this);

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }

    private void setButtonStyle(int position) {
        switch (position) {
            case 0:
                tvActHome1.setBackgroundResource(R.drawable.act_home_cafesel);
                break;
            case 1:
                tvActHome2.setBackgroundResource(R.drawable.act_home_maopaosel);
                break;
            case 2:
                tvActHome3.setBackgroundResource(R.drawable.act_home_zhinanzhensel);
                break;
            case 3:
                tvActHome4.setBackgroundResource(R.drawable.act_home_mysel);
                break;
        }
        for (int i = 0; i < listL.size(); i++) {
            if (position != i) {
                listL.get(i).setBackgroundResource(imaRes[i]);
            }
        }
    }



}
