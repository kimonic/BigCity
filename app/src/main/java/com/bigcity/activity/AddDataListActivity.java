package com.bigcity.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.bean.bmobbean.TotalItemNumBmobBean;
import com.bigcity.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * * ===============================================================
 * name:             AddDataListActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/21
 * description：  新建数据表使用
 * history：
 * *==================================================================
 */

public class AddDataListActivity extends BaseActivity {
    @BindView(R.id.bt_act_adddatalist)
    Button btActAdddatalist;

    @Override
    public int getLayoutResId() {
        return R.layout.act_adddatalist;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_act_adddatalist:
                addTotalItemNumBmobBean();
                break;
//            case R.id.: break;
//            case R.id.: break;
//            case R.id.: break;
//            case R.id.: break;
//            case R.id.: break;
        }
    }

    private void addTotalItemNumBmobBean() {
        TotalItemNumBmobBean bean = new TotalItemNumBmobBean();
        bean.setType("1");
        bean.setName("huzhuwenda");
        bean.setTotal(0);
        bean.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    //数据保存成功
                    ToastUtils.showToast(AddDataListActivity.this, "数据保存成功");

                } else {
                    //数据保存失败
                    ToastUtils.showToast(AddDataListActivity.this, "数据保存失败");
                }
            }
        });
    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        btActAdddatalist.setOnClickListener(this);
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void loadInternetDataToUi() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
