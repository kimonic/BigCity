package com.bigcity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigcity.R;
import com.bigcity.activity.BlogDetailsActivity;
import com.bigcity.activity.PublishedTopicsActivity;
import com.bigcity.adapter.HomeChildFragLvAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.bean.bmobbean.DateSearchBmobBean;
import com.bigcity.bean.bmobbean.PageInfoBmobBean;
import com.bigcity.bean.bmobbean.TotalItemNumBmobBean;
import com.bigcity.utils.DialogUtils;
import com.bigcity.utils.SharedPreferencesUtils;
import com.bigcity.utils.StringUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;
import com.mob.tools.MobLog;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
    private AlertDialog dialog;
    private SharedPreferencesUtils save;

    /**
     * 当前日期当前页面的item的总条数
     */
    private int totalItem;
    /**
     * 当前日期分页计数
     */
    private int itemNum = 0;
    /**
     * 日期计数
     */
    private int dateNum = 0;

    /**
     * 要加载的数据类型
     */
    private int type = 1;
    /**
     * 页面名称
     */
    private String pageName;
    /**
     * 日期字符串
     */
    private String dateC;
    /**
     * 日期集合
     */
    private String[] collection;
    private HomeChildFragLvAdapter adapter;
    private String yearM;
    private String monthM;


    @Override
    public int layoutRes() {
        return R.layout.frag_homechild;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initDataFromIntent() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", 1);
        }
        getPageName();
        save = new SharedPreferencesUtils(getActivity(), pageName);

        listBlog = new ArrayList<>();

//        yearM = TimeUtils.getCurrentYearStr();
//        monthM = TimeUtils.getCurrentMonthStr();

    }

    @Override
    public void initView() {

        dialog = DialogUtils.showProgreessDialog(getActivity(), getResources().getString(R.string.zaicidianjijtcbgym));
        //刷新加载
        srl.setPrimaryColorsId(R.color.themeColor);
        srl.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        srl.setRefreshFooter(new BallPulseFooter(getActivity()));
        srl.setEnableAutoLoadmore(false);
    }

    @Override
    public void initListener() {
        //下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                itemNum = 0;
                initDataFromInternet();
                listBlog.clear();
            }
        });
        //上拉加载
        srl.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getItemInfo();
            }
        });

        lvFragHomeChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), BlogDetailsActivity.class);
                intent.putExtra("id",listBlog.get(position).getId());
                intent.putExtra("title",listBlog.get(position).getTitle());
                intent.putExtra("imageurl1",listBlog.get(position).getImageUrl1());
                intent.putExtra("imageurl2",listBlog.get(position).getImageUrl2());
                intent.putExtra("imageurl3",listBlog.get(position).getImageUrl3());
                startActivity(intent);
            }
        });

    }

    @Override
    public void initDataFromInternet() {
        dialog.show();
        BmobQuery<TotalItemNumBmobBean> query = new BmobQuery<>();
        query.addWhereEqualTo("name", pageName);
        query.addWhereEqualTo("type", "" + type);
        query.setLimit(10);
        query.findObjects(new FindListener<TotalItemNumBmobBean>() {
            @Override
            public void done(List<TotalItemNumBmobBean> object, BmobException e) {

                dialog.dismiss();
                if (e == null) {
                    if (object.size() == 0) {
                        //无查询数据
                        dialog.dismiss();
                        ToastUtils.showToast(getActivity(), R.string.dangqianyemianmyyxsdxx);
                    } else {
                        //有查询数据
                        totalItem = object.get(0).getTotal();
                        getItemInfo();
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(getActivity(), "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }

    @Override
    public void loadInternetDataToUi() {
        orderItem();
        if (adapter == null) {
            adapter = new HomeChildFragLvAdapter(getActivity(), listBlog);
            lvFragHomeChild.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();//刷新适配器
            finishRL();
        }

    }

    /**
     * 结束上拉加载与下拉刷新
     */
    private void finishRL() {

        if (srl.isRefreshing()) {
            srl.finishRefresh();
        }
        if (!srl.isLoadmoreFinished()) {
            srl.finishLoadmore();
        }
    }


    /**
     * 获得item的具体展示信息
     */
    private void getItemInfo() {
        //--------------------------------------------------------------------------------------------
        //50-30*2=-10
        if (totalItem - 30 * itemNum > 0) {//当前日期未查询完时
            BmobQuery<BlogBmobBean> query = new BmobQuery<>();
            query.addWhereEqualTo("type", "" + type);//类型
            query.addWhereLessThanOrEqualTo("numId", totalItem - 30 * itemNum);//20--上限范围
            query.addWhereGreaterThanOrEqualTo("numId", totalItem - 30 * (itemNum + 1));//-10--下限范围
            itemNum++;
            //返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(50);
            //执行查询方法
            query.findObjects(new FindListener<BlogBmobBean>() {
                @Override
                public void done(List<BlogBmobBean> object, BmobException e) {
                    dialog.dismiss();
                    if (e == null) {
                        if (object.size() != 0) {
                            //有查询数据
                            listBlog.addAll(object);
                            loadInternetDataToUi();
                        } else {
                            //无查询数据
                            finishRL();
                            ToastUtils.showToast(getActivity(), R.string.dangqianyemianmyyxsdxx);
                        }
                    } else {
                        finishRL();
                        ToastUtils.showToast(getActivity(), "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
        } else {
            finishRL();
            ToastUtils.showToast(getActivity(), R.string.dangqianyemianmyyxsdxx);
        }


    }
    /**对item进行排序*/
    private void orderItem() {
        if (listBlog.size()>0){
            for (int i = 0; i < listBlog.size(); i++) {
                for (int j = i+1; j < listBlog.size(); j++) {
                    if (listBlog.get(i).getNumId()<listBlog.get(j).getNumId()){
                        BlogBmobBean bean=listBlog.get(i);
                        listBlog.remove(i);
                        listBlog.add(i,listBlog.get(j-1));
                        listBlog.remove(j);
                        listBlog.add(j,bean);
                    }
                }
            }
        }
    }


    /**
     * 获取当前列表的名称
     */
    private void getPageName() {
        switch (type) {
            case 1:
                pageName = "huzhuwenda";
                break;
            case 2:
                pageName = "kaixinyike";
                break;
            case 3:
                pageName = "jiaojiaopengyou";
                break;
            case 4:
                pageName = "jingchenggonglve";
                break;
        }
    }


}
