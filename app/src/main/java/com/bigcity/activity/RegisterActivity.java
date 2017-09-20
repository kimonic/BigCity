package com.bigcity.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.LoginInfoBmobBean;
import com.bigcity.bombtest.BombTestActivity;
import com.bigcity.utils.AppInfoUtils;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.StringUtils;
import com.bigcity.utils.TimeUtils;
import com.bigcity.utils.ToastUtils;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


/**
 * * ================================================
 * name:            RegisterActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/21
 * description：注册界面activity
 * history：
 * ===================================================
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.lv_register_close)
    LinearLayout lvRegisterClose;
    @BindView(R.id.tv_act_register_close)
    TextView tvClose;
    @BindView(R.id.et_act_register_username)
    EditText etUsername;
    @BindView(R.id.et_act_register_code)
    EditText etCode;
    @BindView(R.id.ll_act_register_man)
    LinearLayout llMan;
    @BindView(R.id.ll_act_register_woman)
    LinearLayout llWoman;
    @BindView(R.id.tv_act_register_register)
    TextView tvRegister;
    @BindView(R.id.tv_act_register_login)
    TextView tvLogin;

    /**
     * 性别
     */
    private String sex="";
    /**用户名,密码*/
    private String  userName,password;
    /**用户名是否已存在*/
    private boolean   exist=false;

    @Override
    public int getLayoutResId() {
        return R.layout.act_register;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_register_close:
                closeActivity();
                break;
            case R.id.ll_act_register_man://性别男
                llMan.setBackgroundColor(Color.WHITE);
                llWoman.setBackgroundColor(Color.TRANSPARENT);
                sex = "男";
                break;
            case R.id.ll_act_register_woman://性别女
                llWoman.setBackgroundColor(Color.WHITE);
                llMan.setBackgroundColor(Color.TRANSPARENT);
                sex = "女";
                break;
            case R.id.tv_act_register_register://进行注册
                if (check()){
                    checkUserNameExist();
                }
                break;
            case R.id.tv_act_register_login://打开登陆界面
                openActivity(LoginActivity.class);
                closeActivity();
                break;
//            case R.id.tv_act_register_register:break;
        }
    }
    /**调用接口注册*/
    private void register() {
        LoginInfoBmobBean bean=new LoginInfoBmobBean();

        bean.setAge("");
        bean.setAndroidVersion(AppInfoUtils.getPhoneSystemVersion());
        bean.setEmail("");

        bean.setPhoneNumber("");
        bean.setEmailCheck("0");
        bean.setPhoneCheck("0");

        bean.setUserName(userName);
        bean.setPassword(password);
        bean.setPhoneBrand(AppInfoUtils.getPhoneName()+"---"+AppInfoUtils.getPhoneVersion());

        bean.setSex(sex);
        bean.setState("1");
        bean.setRegisterTime(TimeUtils.getNowDateAll());

        bean.setEndLoginTime(TimeUtils.getNowDateAll());
        bean.setIconUrl("");



        bean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    ToastUtils.showToast(RegisterActivity.this, "注册成功");
                    openActivity(LoginActivity.class);
                    closeActivity();
                } else {
                    ToastUtils.showToast(RegisterActivity.this, "注册失败,请重试");
                }
            }
        });




    }

    /**检查用户名密码*/
    private boolean check() {
        userName=etUsername.getText().toString().trim();
        password=etCode.getText().toString().trim();
        if (userName.length()<3){
            ToastUtils.showToast(this,"用户名的长度不能少于3个字符,请重新输入!!");
            return false;
        }else if (password.length() < 6 || password.length() > 16 || !StringUtils.conformPasswordRule(password)) {
            ToastUtils.showToast(this, "密码必须是6-16位数字和字母的组合!!");
            return false;
        }else if ("".equals(sex)){
            ToastUtils.showToast(RegisterActivity.this, "请选择性别!");
            return false;
        }
        return true;

    }


    private void checkUserNameExist(){
        // TODO: 2017/9/15 验证用户名是否存在


        BmobQuery<LoginInfoBmobBean> query = new BmobQuery<>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("userName", userName);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<LoginInfoBmobBean>() {
            @Override
            public void done(List<LoginInfoBmobBean> object, BmobException e) {
                if (e == null) {
//                    ToastUtils.showToast(RegisterActivity.this, "查询成功：共" + object.size() + "条数据。");
                    if (object.size()==0){
                        register();
                    }else {
                        ToastUtils.showToast(RegisterActivity.this, "用户名已存在,请重新输入!");
                    }
                } else {
                    ToastUtils.showToast(RegisterActivity.this, "注册失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }


    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {
//        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) lvRegisterClose.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        lvRegisterClose.setLayoutParams(params);
    }

    @Override
    public void initListener() {
        tvClose.setOnClickListener(this);
        llMan.setOnClickListener(this);
        llWoman.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi() {

    }
}
