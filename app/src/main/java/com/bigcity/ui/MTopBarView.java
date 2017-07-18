package com.bigcity.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;


/**
 * * ================================================
 * name:            MTopBarView
 * guide:          WelcomeActivity-->GuideActivity--->HomeActivity
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/10
 * description：  顶部标题栏自定义控件
 * history：
 * ===================================================
 */

public class MTopBarView extends LinearLayout {

    private TextView leftTV;
    private TextView rightTV;
    private TextView centerTV;


    private AttributeSet attrs;


    public MTopBarView(Context context) {
        this(context, null, 0);
    }

    public MTopBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MTopBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs=attrs;
        initView();
    }

    @TargetApi(23)
    public MTopBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attrs=attrs;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.view_mtopbar, this);
        leftTV = (TextView) view.findViewById(R.id.tv_mtopbar_left);
        centerTV = (TextView) view.findViewById(R.id.tv_mtopbar_center);
        rightTV = (TextView) view.findViewById(R.id.tv_mtopbar_right);

        if (attrs!=null){
            TypedArray a=getContext().obtainStyledAttributes(attrs,R.styleable.MTopBarView);

            String leftText=a.getString(R.styleable.MTopBarView_leftText);
            String rightText=a.getString(R.styleable.MTopBarView_rightText);
            String centerText=a.getString(R.styleable.MTopBarView_centerText);

            Drawable leftImage=a.getDrawable(R.styleable.MTopBarView_leftImage);
            Drawable rightImage=a.getDrawable(R.styleable.MTopBarView_rightImage);


            a.recycle();

            leftTV.setText(leftText);
            rightTV.setText(rightText);
            centerTV.setText(centerText);

            if (leftImage!=null){
                leftTV.setBackground(leftImage);
            }
            if (rightImage!=null){
                rightTV.setBackground(rightImage);
            }

        }



    }

    /**
     * 设置中间标题
     */
    public void setCenterTitle(int resId) {
        centerTV.setText(resId);
    }

    /**
     * 获得左方控件
     */
    public TextView getLeftTV() {
        return leftTV;
    }

    /**
     * 获得右方控件
     */
    public TextView getRightTV() {
        return rightTV;
    }
}
