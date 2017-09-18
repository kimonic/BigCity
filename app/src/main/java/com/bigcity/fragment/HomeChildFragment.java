package com.bigcity.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.adapter.HomeChildFragLvAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.utils.DialogUtils;
import com.bigcity.utils.ToastUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * * ===============================================================
 * name:             HomeChildFragment
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/15
 * description：  homefragment中互助问答,开心一刻,交交朋友,京城攻略通用fragment
 * history：
 * *==================================================================
 */

public class HomeChildFragment extends BaseFragment {
    @BindView(R.id.lv_frag_homechild)
    ListView lvFragHomeChild;
    @BindView(R.id.srl_frag_homechild)
    SmartRefreshLayout srl;

    Unbinder unbinder;
    private List<CommonBean> list;
    private List<BlogBmobBean> listBlog;
    private AlertDialog  dialog;

    /**要加载的数据类型*/
    private int type=1;


    @Override
    public int layoutRes() {
        return R.layout.frag_homechild;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

        Bundle bundle=getArguments();
        if (bundle!=null){
            type=bundle.getInt("type",1);
        }

    }

    @Override
    public void initView() {
//        CommonLVAdapter adapter=new CommonLVAdapter(getActivity(),list);
//        lvFragHomeChild.setAdapter(adapter);

        dialog= DialogUtils.showProgreessDialog(getActivity(),getResources().getString(R.string.zaicidianjijtcbgym));
        //刷新加载
        srl.setPrimaryColorsId(R.color.themeColor);
        srl.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        srl.setRefreshFooter(new BallPulseFooter(getActivity()));
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataFromInternet() {
        dialog.show();
        BmobQuery<BlogBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
//        query.addWhereEqualTo("releaseTimeDate", "20170915");
        Log.e("TAG", "initDataFromInternet: ---------------------"+type);
        query.addWhereEqualTo("type", ""+type);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<BlogBmobBean>() {
            @Override
            public void done(List<BlogBmobBean> object, BmobException e) {
                dialog.dismiss();
                if (e == null) {
                    //                    ToastUtils.showToast(RegisterActivity.this, "查询成功：共" + object.size() + "条数据。");
                    if (object.size()==0){
                        //无查询数据
                    }else {
                        //有查询数据
                        listBlog=object;
                        LoadInternetDataToUi();
                    }
                } else {
                    ToastUtils.showToast(getActivity(), "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void LoadInternetDataToUi() {
        HomeChildFragLvAdapter adapter=new HomeChildFragLvAdapter(getActivity(),listBlog);
        lvFragHomeChild.setAdapter(adapter);
    }

}
