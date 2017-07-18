package com.bigcity.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.bigcity.utils.ScreenSizeUtils;


/**
 * * ==================================================
 * name:            DotView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：   viewpager底部导航小点
 * history：
 * * ==================================================
 *
 */

public class DotView extends View {

    /**关联的viewpager*/
    private ViewPager viewPager;
    /**控件的宽度*/
    private int width;
    /**小圆点的半径*/
    private int dotRadius=16;
    /**小圆点之间的间距*/
    private int dotDistance=20;
    /**绘制小圆点的画笔*/
    private Paint paintSel,paintUnSel;
    /**需要绘制的小圆的个数*/
    private int dotNum;
    /**当前小圆点的位置*/
    private int position=0;

    public void setPosition(int position) {
        this.position = position;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public DotView(Context context) {
        this(context, null, 0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    @TargetApi(21)
    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        width= ScreenSizeUtils.getScreenWidth(getContext());
        paintSel=initPaint(Color.parseColor("#FFFFFF"));
//        paintSel=initPaint(Color.parseColor("#000000"));
        paintUnSel=initPaint(Color.parseColor("#D0D0D0"));
    }
    /**初始化画笔*/
    private Paint initPaint(int color) {
        Paint paint=new Paint();
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (viewPager!=null){

            dotNum=viewPager.getAdapter().getCount()+1;
            int firstX=(getWidth()/2-(dotRadius*2*dotNum+dotDistance*(dotNum-1))/2)+dotRadius;
            int circleY=getHeight()/2;
            for (int i = 0; i < dotNum; i++) {
                canvas.drawCircle(firstX+i*(dotRadius*2+dotDistance),circleY,dotRadius,paintSel);
                if (i==position){
                    canvas.drawRect(firstX+position*(dotRadius*2+dotDistance),circleY-dotRadius,
                            firstX+(position+1)*(dotRadius*2+dotDistance),circleY+dotRadius,paintSel);

                }
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);

        int height=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(ScreenSizeUtils.getScreenWidth(getContext()),50);
        }else {
            setMeasuredDimension(width,50);
        }

//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
