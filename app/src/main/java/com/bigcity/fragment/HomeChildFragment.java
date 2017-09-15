package com.bigcity.fragment;

import android.view.View;
import android.widget.ListView;

import com.bigcity.R;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.adapter.HomeChildFragLvAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;
import com.bigcity.bean.bmobbean.BlogBmobBean;
import com.bigcity.utils.ToastUtils;

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
    Unbinder unbinder;
    private List<CommonBean> list;
    private List<BlogBmobBean> listBlog;


    @Override
    public int layoutRes() {
        return R.layout.frag_homechild;
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
            //头像图片地址
            //http://www.pconline.com.cn/images/html/viewpic_pconline.htm?http:
            // img0.pconline.com.cn/pconline/1405/27/4840043_f689635f6e18bd3367803e941140462c1.jpg&channel=9261
            list.add(bean);



        }
    }

    @Override
    public void initView() {
        CommonLVAdapter adapter=new CommonLVAdapter(getActivity(),list);
        lvFragHomeChild.setAdapter(adapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataFromInternet() {
        BmobQuery<BlogBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("releaseTimeDate", "20170915");
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //执行查询方法
        query.findObjects(new FindListener<BlogBmobBean>() {
            @Override
            public void done(List<BlogBmobBean> object, BmobException e) {
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
