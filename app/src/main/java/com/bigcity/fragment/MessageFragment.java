package com.bigcity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.adapter.MessageFragLvAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;
import com.bigcity.utils.ImageGlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * * ================================================
 * name:            MessageFragment
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/8
 * description：  首页界面activity第二个fragment--我的
 * history：
 * ===================================================
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.iv_frag_message_icon)
    ImageView ivFragMessageIcon;
    @BindView(R.id.iv_frag_message_messagecount)
    ImageView ivFragMessageMessageCount;
    @BindView(R.id.tv_frag_message_arrowright)
    TextView tvFragMessageArrowRight;
    @BindView(R.id.lv_frag_message)
    ListView lvFragMessage;
    private List<CommonBean>  list;

    @Override
    public int layoutRes() {
        return R.layout.frag_message;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

        list=new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            CommonBean bean=new CommonBean();
            bean.setTitle("哈哈哈");
            bean.setHuifushu("22:10");
            bean.setContent("哈eejofiowefjdfgIEI;EFGIEKFNDSFIDS经过艘攻击多少v你的开发商垫付哈哈");
            bean.setImaId(R.drawable.act_my_icon);
            list.add(bean);
        }
    }

    @Override
    public void initView() {

        ImageGlideUtils.loadCircularImage(ivFragMessageIcon, R.drawable.act_my_icon);
        MessageFragLvAdapter adapter=new MessageFragLvAdapter(getActivity(),list);
        lvFragMessage.setAdapter(adapter);



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
