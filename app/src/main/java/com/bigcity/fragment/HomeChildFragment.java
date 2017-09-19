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
import com.bigcity.bean.bmobbean.DateSearchBmobBean;
import com.bigcity.bean.bmobbean.PageInfoBmobBean;
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

    }

    @Override
    public void initView() {

        dialog = DialogUtils.showProgreessDialog(getActivity(), getResources().getString(R.string.zaicidianjijtcbgym));
        //刷新加载
        srl.setPrimaryColorsId(R.color.themeColor);
        srl.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
        srl.setRefreshFooter(new BallPulseFooter(getActivity()));
    }

    @Override
    public void initListener() {
        //下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Log.e("TAG", "onRefresh: 下拉刷新1");
                itemNum = 0;
                dateNum = 0;
                listBlog.clear();
                String year = TimeUtils.getCurrentYearStr();
                String month = TimeUtils.getCurrentMonthStr();
                getDateInfo(year, month);
            }
        });
        //上拉加载
        srl.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getItemInfo();
            }
        });

    }

    @Override
    public void initDataFromInternet() {
        dialog.show();

        String year = TimeUtils.getCurrentYearStr();
        String month = TimeUtils.getCurrentMonthStr();

        if (!isCheck(year, month)) {
            getDateInfo(year, month);
        } else {//直接获取已存储的数据
            collection = handleDateC(save.getString("ysdCollection" + year + month, ""));//ysdCollection
            if (collection.length > 0) {
                getTotalItem(collection[0]);
            } else {
                if (!(StringUtils.string2Integer(year) > 2017)) {
                    getDateInfo(TimeUtils.getYearOfLastMonth(year, month), TimeUtils.getLastMonth(month));
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(getActivity(), R.string.dangqianyemianmyyxsdxx);
                }
            }

        }

    }

    @Override
    public void LoadInternetDataToUi() {
        Log.e("TAG", "onRefresh: 下拉刷新5");
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
     * 判断当天是否查询过日期
     *
     * @param year  年份
     * @param month 月份
     * @return true--需要查询,false---不需要查询
     */
    private boolean isCheck(String year, String month) {
        String date = save.getString("date", "");
        if (TimeUtils.getStringDateShortN().equals(date)) {//当天已查询过,不再进行查询
            String ysdCollection = save.getString("ysdCollection" + year + month, "");
            return !"".equals(ysdCollection);
        } else {
            return false;
        }


    }

    /**
     * 查询当前类目下指定年月有哪些日期有信息
     *
     * @param year  年份
     * @param month 月份
     */

    private void getDateInfo(String year, String month) {
        Log.e("TAG", "onRefresh: 下拉刷新2");
        final String y = year;
        final String m = month;
        //当前未查询过进行查询
        BmobQuery<DateSearchBmobBean> query = new BmobQuery<>();
        //查询的数据
        query.addWhereEqualTo("year", year);
        query.addWhereEqualTo("month", month);
        query.addWhereEqualTo("name", pageName);
        //执行查询方法
        query.findObjects(new FindListener<DateSearchBmobBean>() {
            @Override
            public void done(List<DateSearchBmobBean> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        //无查询数据
                        dialog.dismiss();
                        ToastUtils.showToast(getActivity(), R.string.dangqianyemianmyyxsdxx);
                    } else {
                        //有查询数据,保存查询数据的日期集合到本地
                        DateSearchBmobBean bean = object.get(0);
                        save.put("ysdCollection" + y + m, bean.getYsdCollection());//某年某月日期集合
                        save.put("date", TimeUtils.getStringDateShortN());//查询日期
                        collection = handleDateC(bean.getYsdCollection());
                        if (collection.length > 0) {
                            // TODO: 2017/9/19   具体日期的信息条数
                            getTotalItem(collection[0]);
                        } else {//查询至2017年1月是否有信息
                            if (!(StringUtils.string2Integer(y) > 2017)) {
                                getDateInfo(TimeUtils.getYearOfLastMonth(y, m), TimeUtils.getLastMonth(m));
                            } else {
                                dialog.dismiss();
                                ToastUtils.showToast(getActivity(), R.string.dangqianyemianmyyxsdxx);
                            }
                        }
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(getActivity(), "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }


    /**
     * 获取当前页面当前日期的的总item数
     */
    public void getTotalItem(String date) {
        Log.e("TAG", "onRefresh: 下拉刷新3");

        BmobQuery<PageInfoBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("type", "" + type);//类型
        query.addWhereEqualTo("date", date);//日期yyyymmdd
        query.addWhereEqualTo("name", "" + pageName);//页面名称
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<PageInfoBmobBean>() {
            @Override
            public void done(List<PageInfoBmobBean> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0) {
                        //无查询数据
                        dialog.dismiss();
                        ToastUtils.showToast(getActivity(), R.string.dangqianyemianmyyxsdxx);
                    } else {
                        //有查询数据
                        PageInfoBmobBean bean = object.get(0);
                        totalItem = bean.getTotal();//获得总条数
                        //根据总条数进行分页查询
                        getItemInfo();
                    }
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast(getActivity(), "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 获得item的具体展示信息
     */
    private void getItemInfo() {
        //50-30*2=-10
        Log.e("TAG", "onRefresh: 下拉刷新4");
        Log.e("TAG", "onRefresh: 上拉加载6"+itemNum);
        Log.e("TAG", "onRefresh: 上拉加载6"+dateNum);
        if (totalItem - 30 * itemNum > 0&&dateNum<collection.length) {//当前日期未查询完时
            Log.e("TAG", "onRefresh: 上拉加载1");

            BmobQuery<BlogBmobBean> query = new BmobQuery<>();
            query.addWhereEqualTo("type", "" + type);//类型
            query.addWhereEqualTo("releaseTimeDate", collection[dateNum]);//发布日期
            query.addWhereLessThanOrEqualTo("dateId", totalItem - 30 * itemNum);//20--上限范围
            query.addWhereGreaterThanOrEqualTo("dateId", totalItem - 30 * (itemNum + 1));//-10--下限范围
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
                            Log.e("TAG", "initDataFromInternet" + type + ": -------getItemInfo--------------" + listBlog.size());

                            LoadInternetDataToUi();
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
            Log.e("TAG", "onRefresh: 上拉加载2");
            itemNum = 0;
            dateNum++;
            if (dateNum< collection.length) {
                Log.e("TAG", "onRefresh: 上拉加载3");
                getTotalItem(collection[dateNum]);
            } else {
                Log.e("TAG", "onRefresh: 上拉加载4");
                finishRL();//终止刷新
            }

            Log.e("TAG", "onRefresh: 上拉加载5"+itemNum);
            Log.e("TAG", "onRefresh: 上拉加载5"+dateNum);


            // TODO: 2017/9/19 当前日期已展示完毕,查询前一天的信息
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
                pageName = "jignchenggonglve";
                break;
        }
    }


    /**
     * 处理日期集合字符串,日期倒序排列
     */
    private String[] handleDateC(String str) {
        String[] temp = str.split(",");
        for (int i = 0; i < temp.length; i++) {
            for (int j = i + 1; j < temp.length; j++) {
                String temp1;
                if (StringUtils.string2Integer(temp[i]) < StringUtils.string2Integer(temp[j])) {
                    temp1 = temp[i];
                    temp[i] = temp[j];
                    temp[j] = temp1;
                }
            }
        }
        return temp;
    }
}
