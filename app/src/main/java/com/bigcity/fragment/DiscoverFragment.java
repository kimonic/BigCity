package com.bigcity.fragment;

import android.view.View;
import android.widget.ListView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.adapter.DiscoverFragLvAdapter;
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
            CommonBean bean = new CommonBean();
            bean.setTitle("漂流瓶");
            bean.setImaId1(R.drawable.frag_discover_piaoliuping);
            list.add(bean);

        CommonBean bean1 = new CommonBean();
        bean1.setTitle("摇一摇");
        bean1.setImaId1(R.drawable.frag_discover_yaoyiyao);
        list.add(bean1);


        CommonBean bean2= new CommonBean();
        bean2.setTitle("好物推荐");
        bean2.setImaId1(R.drawable.frag_discover_piaoliuping);
        list.add(bean2);

    }

    @Override
    public void initView() {
        DiscoverFragLvAdapter adapter = new DiscoverFragLvAdapter(getActivity(), list);
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
