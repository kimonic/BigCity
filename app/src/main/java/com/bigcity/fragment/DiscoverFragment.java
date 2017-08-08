package com.bigcity.fragment;

import android.view.View;
import android.widget.ListView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;
import com.bigcity.ui.MTopBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * * ================================================
 * name:            DiscoverFragment
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：  首页界面activity第三个fragment--发现
 * history：
 * ===================================================
 */

public class DiscoverFragment extends BaseFragment {
    @BindView(R.id.mtb_frag_discover)
    MTopBarView mtbFragDiscover;
    @BindView(R.id.lv_frag_discover)
    ListView lvFragDiscover;
    private List<CommonBean> list;

    @Override
    public int layoutRes() {
        return R.layout.frag_discover;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            CommonBean bean = new CommonBean();
            bean.setTitle("这是标题");

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
        CommonLVAdapter adapter = new CommonLVAdapter(getActivity(), list);
        lvFragDiscover.setAdapter(adapter);
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
