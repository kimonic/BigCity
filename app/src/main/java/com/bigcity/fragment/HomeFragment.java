package com.bigcity.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.adapter.HomeFragVPAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * * ================================================
 * name:            HomeFragment
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/7
 * description：  首页界面activity第一个fragment
 * history：
 * ===================================================
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.tv_frag_home_1)
    TextView tvFragHome1;
    @BindView(R.id.tv_frag_home_2)
    TextView tvFragHome2;
    @BindView(R.id.tv_frag_home_3)
    TextView tvFragHome3;
    @BindView(R.id.tv_frag_home_4)
    TextView tvFragHome4;
    @BindView(R.id.vp_frag_home)
    ViewPager viewPager;

    private List<Fragment> list;
    private List<TextView> listTv;


    @Override
    public int layoutRes() {
        return R.layout.frag_home;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_frag_home_1:
                viewPager.setCurrentItem(0);
                setBtnStyle(0);
                break;
            case R.id.tv_frag_home_2:
                viewPager.setCurrentItem(1);
                setBtnStyle(1);
                break;
            case R.id.tv_frag_home_3:
                viewPager.setCurrentItem(2);
                setBtnStyle(2);
                break;
            case R.id.tv_frag_home_4:
                viewPager.setCurrentItem(3);
                setBtnStyle(3);
                break;
//                 case R.id.:break;
//                 case R.id.:break;
        }

    }

    @Override
    public void initDataFromIntent() {
        list = new ArrayList<>();
        HomeChildFragment fragment1 = new HomeChildFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("type", "1");
        fragment1.setArguments(bundle1);


        HomeChildFragment fragment2 = new HomeChildFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("type", "2");
        fragment2.setArguments(bundle2);


        HomeChildFragment fragment3 = new HomeChildFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putString("type", "3");
        fragment3.setArguments(bundle3);

        HomeChildFragment fragment4 = new HomeChildFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putString("type", "4");
        fragment4.setArguments(bundle4);


        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);


        listTv = new ArrayList<>();
        listTv.add(tvFragHome1);
        listTv.add(tvFragHome2);
        listTv.add(tvFragHome3);
        listTv.add(tvFragHome4);

    }

    @Override
    public void initView() {
        HomeFragVPAdapter adapter = new HomeFragVPAdapter(getChildFragmentManager(), list);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void initListener() {
        tvFragHome1.setOnClickListener(this);
        tvFragHome2.setOnClickListener(this);
        tvFragHome3.setOnClickListener(this);
        tvFragHome4.setOnClickListener(this);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setBtnStyle(position);

            }
        });

    }

    private void setBtnStyle(int position) {

        for (int i = 0; i < listTv.size(); i++) {
            if (i == position) {
                listTv.get(i).setBackgroundColor(Color.RED);
            } else {
                listTv.get(i).setBackgroundColor(Color.TRANSPARENT);
            }
        }

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }


}
