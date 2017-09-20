package com.bigcity.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.LoginInfoBmobBean;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.SharedPreferencesUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * * ================================================
 * name:            LoginActivityC
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/18
 * description：登陆界面activity,不跳转直接结束
 * history：
 * ===================================================
 */

public class LoginActivityC extends BaseActivity {
    @BindView(R.id.lv_act_login)
    LinearLayout lvActLogin;
    @BindView(R.id.tv_act_login_close)
    TextView tvClose;
    @BindView(R.id.et_act_login_username)
    EditText etUsername;
    @BindView(R.id.et_act_login_code)
    EditText etCode;
    @BindView(R.id.tv_act_login_login)
    TextView tvLogin;
    @BindView(R.id.tv_act_login_register)
    TextView tvRegister;

    private  SharedPreferencesUtils  sp;

    @Override
    public int getLayoutResId() {
        return R.layout.act_login;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_login_close:
                closeActivity();
                break;
            case R.id.tv_act_login_login://登陆
                login();
                break;
            case R.id.tv_act_login_register://打开注册界面
                openActivity(RegisterActivity.class);
                closeActivity();
                break;
        }
    }

    private void login() {

        BmobQuery<LoginInfoBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("userName", etUsername.getText().toString().trim());
        query.addWhereEqualTo("password", etCode.getText().toString().trim());
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<LoginInfoBmobBean>() {
            @Override
            public void done(List<LoginInfoBmobBean> object, BmobException e) {
                if (e == null) {
                    //                    ToastUtils.showToast(RegisterActivity.this, "查询成功：共" + object.size() + "条数据。");
                    if (object.size() == 0) {
                        ToastUtils.showToast(LoginActivityC.this, "用户名或密码错误,请重新输入!");
                        //无查询数据
                    } else {
                        //有查询数据
                        Gson gson = new Gson();
                        sp.put("loginInfo",gson.toJson(object.get(0), LoginInfoBmobBean.class));
                        sp.put("date", TimeUtils.getStringDateShortN());
                        ToastUtils.showToast(LoginActivityC.this, "登陆成功!");
                        closeActivity();
                    }
                } else {
                    ToastUtils.showToast(LoginActivityC.this, "登陆失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

    @Override
    public void initDataFromIntent() {
        sp=new SharedPreferencesUtils(this,"loginInfo");
    }

    @Override
    public void initView() {
//        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvActLogin.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        lvActLogin.setLayoutParams(params);
    }

    @Override
    public void initListener() {
        tvClose.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi() {

    }



}
