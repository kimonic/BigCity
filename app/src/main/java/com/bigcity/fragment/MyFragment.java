package com.bigcity.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.activity.LoginActivity;
import com.bigcity.activity.LoginActivityC;
import com.bigcity.adapter.CommonLVAdapter;
import com.bigcity.base.BaseFragment;
import com.bigcity.bean.CommonBean;
import com.bigcity.bean.bmobbean.LoginInfoBmobBean;
import com.bigcity.utils.HeightUtils;
import com.bigcity.utils.ImageGlideUtils;
import com.bigcity.utils.SharedPreferencesUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
     @BindView(R.id.tv_frag_my_sex)
    TextView tvFragMySex;
    @BindView(R.id.lv_frag_my)
    ListView lvFragMy;
    Unbinder unbinder;
    private List<CommonBean> list;

    private SharedPreferencesUtils sp;
    private LoginInfoBmobBean bean;

    @Override
    public int layoutRes() {
        return R.layout.frag_my;
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

        sp = new SharedPreferencesUtils(getActivity(), "loginInfo");
    }

    @Override
    public void initView() {
        ImageGlideUtils.loadCircularImage(ivFragMyIcon, R.drawable.act_my_icon);
        CommonLVAdapter adapter = new CommonLVAdapter(getActivity(), list);
        lvFragMy.setAdapter(adapter);
//        HeightUtils.setListviewHeight(lvFragMy);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDataFromInternet() {

        String userInfo = sp.getString("loginInfo", "");
        String date = sp.getString("date", "");
        if (!userInfo.equals("")) {
            Gson gson = new Gson();
            bean = gson.fromJson(userInfo, LoginInfoBmobBean.class);
            if (!date.equals(TimeUtils.getStringDateShortN())) {
                BmobQuery<LoginInfoBmobBean> query = new BmobQuery<>();
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("userName", bean.getUserName());
                query.addWhereEqualTo("password", bean.getPassword());
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(10);
                //执行查询方法
                query.findObjects(new FindListener<LoginInfoBmobBean>() {
                    @Override
                    public void done(List<LoginInfoBmobBean> object, BmobException e) {
                        if (e == null) {
                            if (object.size() == 0) {
                                //无查询数据
                                ToastUtils.showToast(getActivity(), R.string.qingxiandenglu);
                                openActivity(LoginActivity.class);
                            } else {
                                //有查询数据
                                bean = object.get(0);
                                loadInternetDataToUi();
                            }
                        } else {
                            ToastUtils.showToast(getActivity(), "查询失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
            }else {
                loadInternetDataToUi();
            }

        } else {
            //无查询数据
            ToastUtils.showToast(getActivity(), R.string.qingxiandenglu);
            Intent intent = new Intent(getActivity(), LoginActivityC.class);
            startActivityForResult(intent, 1);
//            openActivity(LoginActivityC.class);
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            initDataFromInternet();
            Log.e("TAG", "onActivityResult: ?????????????????????");
        }

    }

    @Override
    public void loadInternetDataToUi() {
        if (bean!=null){
            if (bean.getIconUrl()!=null){
                ImageGlideUtils.loadCircularImage(ivFragMyIcon, bean.getIconUrl());
            }

            tvFragMyNiCheng.setText(bean.getUserName());

            if (bean.getSex().equals("男")){
                tvFragMySex.setBackgroundResource(R.drawable.act_login_men);
            }else {
                tvFragMySex.setBackgroundResource(R.drawable.act_login_women);
            }
            tvFragMyQianMing.setText(bean.getSelfIntroduction());
            tvFragMyTieZiShu.setText(bean.getTotalTopics());
            tvFragMyPingLunShu.setText(bean.getTotalReply());
        }


    }
    /**
     * String picPath = "sdcard/temp.jpg";
     BmobFile bmobFile = new BmobFile(new File(picPath));
     bmobFile.uploadblock(new UploadFileListener() {

    @Override
    public void done(BmobException e) {
    if(e==null){
    //bmobFile.getFileUrl()--返回的上传文件的完整地址
    toast("上传文件成功:" + bmobFile.getFileUrl());
    }else{
    toast("上传文件失败：" + e.getMessage());
    }

    }

    @Override
    public void onProgress(Integer value) {
    // 返回的上传进度（百分比）
    }
    });
     */


}
