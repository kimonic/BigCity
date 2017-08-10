package com.bigcity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ImageGlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * * ================================================
 * name:            MyFragment
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：  首页界面activity第四个fragment--我的
 * history：
 * ===================================================
 */
public class MyFragment extends BaseFragment {

    @BindView(R.id.iv_frag_my_icon)
    ImageView ivFragMyIcon;
    @BindView(R.id.tv_frag_my_nicheng)
    TextView tvFragMyNiCheng;
    @BindView(R.id.tv_frag_my_qianming)
    TextView tvFragMyQianMing;
    @BindView(R.id.tv_frag_my_tiezishu)
    TextView tvFragMyTieZiShu;
    @BindView(R.id.tv_frag_my_pinglunshu)
    TextView tvFragMyPingLunShu;
    @BindView(R.id.lv_frag_my)
    ListView lvFragMy;
    Unbinder unbinder;
    private List<CommonBean> list;

    @Override
    public int layoutRes() {
        return R.layout.frag_my;
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
        ImageGlideUtils.loadCircularImage(ivFragMyIcon, R.drawable.act_my_icon);
        CommonLVAdapter adapter=new CommonLVAdapter(getActivity(),list);
        lvFragMy.setAdapter(adapter);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
