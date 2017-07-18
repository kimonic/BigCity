package com.bigcity.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/10.
 *
 */

public abstract class BaseFragment extends Fragment implements BaseMethod, View.OnClickListener {
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutRes(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initDataFromIntent();
        initView();
        initListener();
        initDataFromInternet();
        return view;
    }
    /**设置fragment的布局资源id*/
    public abstract int layoutRes();


    /**
     * 启动下一个activity
     */
    protected void openActivity(Class<? extends BaseActivity> toActivity) {
        openActivity(toActivity, null);
    }

    /**
     * 启动下一个activity
     */
    protected void openActivity(Class<? extends BaseActivity> toActivity, Bundle parameter) {
        Intent intent = new Intent(getActivity(), toActivity);
        if (parameter != null) {
            intent.putExtras(parameter);
        }
        startActivity(intent);
    }

    /**
     * 关闭activity
     */
    protected void closeActivity() {
        getActivity().finish();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
