package com.bigcity.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bigcity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * * ================================================
 * name:            NaviButtonView
 * guide:          WelcomeActivity-->GuideActivity--->HomeActivity
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/10
 * description：  底部导航条自定义控件
 * history：
 * mark:  必须设置count(按钮数量),imagsUnSel(未选中资源图片id数组)
 * -------magsSel(选中资源图片id数组),text(按钮描述文本,与顶部bar同名)
 * -------colorSel(选中时文本颜色),colorUnSel(未选中时文本颜色)
 *
 * -------app:imagesSelArray="@array/imagesSelR"
 * -------app:imagesUnSelArray="@array/imagesUnSelR"
 * -------app:textArray="@array/textIdR"
 * -------app:count="4"
 * -------app:colorSel="@color/colorPrimary"
 * -------app:colorUnSel="@color/black"
 * ===================================================
 */

public class NaviButtonView extends LinearLayout implements View.OnClickListener {
    /**
     * 按钮数量
     */
    private int count = 4;
    /**
     * 未选中资源图片id
     */
    private int imagsUnSel[];
    /**
     * 选中资源图片id
     */
    private int imagsSel[];
    /**
     * 文本数组
     */
    private int text[];
    /**
     * 按钮集合
     */
    private List<View> list;
    private List<ImageView> listImag;
    private List<TextView> listText;

    /**
     * 选中时文本颜色
     */
    private int colorSel;
    private int colorUnSel;

    /**
     * tag
     */
    private static final String VIEWTAG = "navibuttonview";

    /**
     * 关联viewpager
     */
    private ViewPager viewPager;
    /**
     * 关联MTopBarView
     */
    private MTopBarView mTopBarView;


    private AttributeSet attrs;

    public void setmTopBarView(MTopBarView mTopBarView) {
        this.mTopBarView = mTopBarView;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setSelStyle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public NaviButtonView(Context context) {
        this(context, null, 0);
    }

    public NaviButtonView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NaviButtonView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attrs = attrs;
        initView();
    }

    @TargetApi(23)
    public NaviButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.attrs = attrs;
        initView();
    }

    private void initView() {


        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NaviButtonView);
        count = a.getInt(R.styleable.NaviButtonView_count, 0);
        colorSel = a.getColor(R.styleable.NaviButtonView_colorSel, 0);
        colorUnSel = a.getColor(R.styleable.NaviButtonView_colorUnSel, 0);

        imagsSel = new int[count];
        imagsUnSel = new int[count];
        text = new int[count];
        TypedArray b = getContext().getResources().obtainTypedArray
                (a.getResourceId(R.styleable.NaviButtonView_imagesSelArray, 0));
        TypedArray c = getContext().getResources().obtainTypedArray
                (a.getResourceId(R.styleable.NaviButtonView_imagesUnSelArray, 0));
        TypedArray d = getContext().getResources().obtainTypedArray
                (a.getResourceId(R.styleable.NaviButtonView_textArray, 0));
        for (int i = 0; i < count; i++) {
            imagsSel[i] = b.getResourceId(i, 0);
            imagsUnSel[i] = c.getResourceId(i, 0);
            text[i] = d.getResourceId(i, 0);
        }
        b.recycle();
        c.recycle();
        d.recycle();
        a.recycle();


        list = new ArrayList<>();
        listImag = new ArrayList<>();
        listText = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (int i = 0; i < count; i++) {
            View view = inflater.inflate(R.layout.view_navibutton, null);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            view.setLayoutParams(params);
            view.setTag(VIEWTAG + i);
            this.addView(view);
            view.setOnClickListener(this);
            list.add(view);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_navibutton);
            listImag.add(imageView);
            TextView textView = (TextView) view.findViewById(R.id.tv_navibutton);
            listText.add(textView);
            if (i == 0) {
                imageView.setImageResource(imagsSel[i]);
                textView.setTextColor(colorSel);
            } else {
                imageView.setImageResource(imagsUnSel[i]);
                textView.setTextColor(colorUnSel);
            }
            textView.setText(text[i]);

        }
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < count; i++) {
            if ((VIEWTAG + i).equals(view.getTag())) {
                setSelStyle(i);
                if (viewPager != null) {
                    viewPager.setCurrentItem(i);
                }
                break;
            }
        }
    }

    /**
     * 设置选中时的状态
     */
    private void setSelStyle(int position) {
        for (int i = 0; i < count; i++) {
            if (position == i) {
                listImag.get(i).setImageResource(imagsSel[i]);
                listText.get(i).setTextColor(colorSel);
                if (mTopBarView != null) {
                    mTopBarView.setCenterTitle(text[i]);
                }
            } else {
                listImag.get(i).setImageResource(imagsUnSel[i]);
                listText.get(i).setTextColor(colorUnSel);
            }
        }
    }
}
