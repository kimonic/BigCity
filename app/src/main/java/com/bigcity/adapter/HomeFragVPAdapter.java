package com.bigcity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * * ====================================================================
 * name:            HomeFragVPAdapter
 * guide:          WelcomeActivity-->GuideActivity--->HomeActivity
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/10
 * description：主页activity-->viewpager fragmentadapter
 * history：
 * * ====================================================================
 */

public class HomeFragVPAdapter extends FragmentPagerAdapter {
    private List<Fragment>  list;

    public HomeFragVPAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list=list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
