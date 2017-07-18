package com.bigcity.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.bigcity.R;
import com.bigcity.utils.EncryptionLockUtils;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * * ==================================================
 * name:            MLockView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：   锁屏view
 * history：
 * * ==================================================
 */

public class MLockView extends View {

    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 圆之间的距离dp
     */
    private final static int distanceDP = 50;

    /**
     * 圆之间的距离px
     */
    private int distancePX;
    /**
     * 上下左右外边距px
     */
    private final static int PADDING = 100;
    /**
     * 圆的半径
     */
    private int radius;
    /**
     * 圆心的xy坐标
     */
    private int circleX[] = new int[3];
    private int circleY[] = new int[3];

    /**
     * 空心圆画笔,阴影圆画笔,内圈圆画笔,直线画笔
     */
    private Paint hollowPaint, shadePaint, innerPaint, linePaint;

    /**
     * 当前点击的xy坐标点
     */
    private int currentX, currentY;

    /**
     * 圆圈所在矩形集合
     */
    private List<Rect> list;
    /**
     * 矩形集合赋值开关
     */
    private boolean rectOpen = true;
    /**
     * 当前点所在圆的位置
     */
    private int position = -1;


    /**
     * 当前已绘制点的集合
     */
    private List<Integer> positionSet;
    /**
     * 允许错误的输入次数
     */
    private int inputCount = 4;
    /**
     * 消除最后的尾线
     */
    private boolean tail = true;
    /**
     * 手势密码验证是否成功回调
     */
    private OnLockInputListener listener;

    /**
     * 是否绘制手势密码开关
     */
    private boolean openOrCloseDraw = true;

    /**
     * 设置的密码
     */

    private String password;
    private boolean flag=false;

    /**
     * 设置手势密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOpenOrCloseDraw() {
        return openOrCloseDraw;
    }

    public void setOpenOrCloseDraw(boolean openOrCloseDraw) {
        this.openOrCloseDraw = openOrCloseDraw;
    }

    public MLockView(Context context) {
        this(context, null, 0);
    }

    public MLockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(23)
    public MLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        screenWidth = ScreenSizeUtils.getScreenWidth(getContext());
        distancePX = distanceDP * ScreenSizeUtils.getDensity(getContext());
        hollowPaint = initPaint("#FE7B20", 1, 5);
        shadePaint = initPaint("#A6D3D8", 2, 0);
        innerPaint = initPaint("#3EACC5", 2, 0);
        linePaint = initPaint("#1898BF", 2, 20);

        positionSet = new ArrayList<>();
        positionSet.add(-1);
        list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Rect rect = new Rect();
            list.add(rect);
        }
    }

    /**
     * 初始化画笔
     */
    private Paint initPaint(String color, int style, int strokeWidth) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(true);
        switch (style) {
            case 1:
                paint.setStyle(Paint.Style.STROKE);//空心
                break;
            case 2:
                paint.setStyle(Paint.Style.FILL);//实心
                break;
        }
        if (strokeWidth != 0) {
            paint.setStrokeWidth(strokeWidth);
        }

        return paint;

    }

    @Override
    protected void onDraw(Canvas canvas) {


        radius = (getWidth() - PADDING * 2 - distancePX * 2) / 6;
        //中间圆的圆心坐标
        int circleX1 = getWidth() / 2 - 2 * radius - distancePX;
        int circleX2 = getWidth() / 2;
        int circleX3 = getWidth() / 2 + 2 * radius + distancePX;
        circleX[0] = circleX1;
        circleX[1] = circleX2;
        circleX[2] = circleX3;


        int circleY1 = PADDING + radius;
        int circleY2 = PADDING + radius * 3 + distancePX;
        int circleY3 = PADDING + radius * 5 + distancePX * 2;
        circleY[0] = circleY1;
        circleY[1] = circleY2;
        circleY[2] = circleY3;
        int temp = 0;
        for (int i = 0; i < 3; i++) {//绘制圆圈
            for (int j = 0; j < 3; j++) {
                canvas.drawCircle(circleX[i], circleY[j], radius, hollowPaint);
                if (rectOpen) {
                    list.get(temp).left = circleX[i] - radius;
                    list.get(temp).top = circleY[j] - radius;
                    list.get(temp).right = circleX[i] + radius;
                    list.get(temp).bottom = circleY[j] + radius;
                    temp++;
                }
            }
        }
        rectOpen = false;

        if (openOrCloseDraw) {
            for (int i = 0; i < list.size(); i++) {//判断点是否在圆内
                if (list.get(i).contains(currentX, currentY)) {
                    position = i;
                    break;
                }
            }


            if (!positionSet.contains(position)) {//如果当前圆已处于选中绘制状态
                positionSet.add(position);
            }

            for (int i = 0; i < positionSet.size(); i++) {//画内圆及阴影圆
                if (positionSet.get(i) != -1) {
                    canvas.drawCircle(circleX[positionSet.get(i) / 3], circleY[positionSet.get(i) % 3], radius - 5, shadePaint);
                    canvas.drawCircle(circleX[positionSet.get(i) / 3], circleY[positionSet.get(i) % 3], radius / 2, innerPaint);
                }
            }

            for (int i = 0; i < positionSet.size(); i++) {//画圆心连线
                if (positionSet.get(i) != -1 && i < positionSet.size() - 1) {
                    canvas.drawLine(circleX[positionSet.get(i) / 3], circleY[positionSet.get(i) % 3],
                            circleX[positionSet.get(i + 1) / 3], circleY[positionSet.get(i + 1) % 3], linePaint);
                }
            }
            if (positionSet.size() > 1 && positionSet.size() < 10 && tail) {
                canvas.drawLine(circleX[positionSet.get(positionSet.size() - 1) / 3], circleY[positionSet.get(positionSet.size() - 1) % 3],
                        currentX, currentY, linePaint);
            }

            if (flag){
                openOrCloseDraw=false;
            }


        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int newSize = Math.min(width, height);

        if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {
            if (newSize > screenWidth) {
                setMeasuredDimension(screenWidth, screenWidth);
            } else {
                setMeasuredDimension(newSize, newSize);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (openOrCloseDraw) {
            tail = true;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN://按下
                    break;
                case MotionEvent.ACTION_MOVE://移动
                    currentX = (int) event.getX();
                    currentY = (int) event.getY();
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP://抬起
                    tail = false;
                    invalidate();

                    if (listener != null) {
                        listener.drawFinish(positionSet);
                    }
                    if (password != null) {
                        checkInput();
                    }


                    break;
            }
        }
        return true;
    }

    /**
     * 检测手势绘制是否正确
     */
    private void checkInput() {

        String inputPassword = EncryptionLockUtils.convertEncryption(getContext(), positionSet);

        if (password.equals(inputPassword)) {
            flag=true;
            ToastUtils.showToast(getContext(), R.string.mlockview_sucess);
            if (listener != null) {
                listener.sucess();
            }
        } else {
            if (inputCount > 0) {
                Toast.makeText(getContext(), "输入错误,您还有" + inputCount + "次机会", Toast.LENGTH_SHORT).show();
                afreshDraw();
                inputCount--;
            } else {
                Toast.makeText(getContext(), R.string.errormoretimes, Toast.LENGTH_SHORT).show();
                afreshDraw();
                openOrCloseDraw = false;
                inputCount = 4;
                if (listener != null) {
                    listener.errorMoreTime();
                }
            }

        }


    }

    /**
     * 再次绘制时刷新界面
     */
    public void afreshDraw() {
        positionSet.clear();
        positionSet.add(-1);
        currentX = 0;
        currentY = 0;
        position = -1;
        invalidate();
    }

    public interface OnLockInputListener {
        /**
         * 错误次数过多之后执行的操作
         */
        void errorMoreTime();

        /**
         * 绘制完成监听,参数为绘制点位置集合
         */
        void drawFinish(List<Integer> positionSet);

        /**
         * 绘制手势密码成功
         */
        void sucess();
    }

    /**
     * 获得手势密码的位置集合,注意第一个值-1
     */
    public List<Integer> getPositionSet() {
        return positionSet;
    }

    /**
     * 设置手势密码的位置集合,注意第一个值-1
     */
    public void setPositionSet(List<Integer> positionSet) {
        this.positionSet = positionSet;
    }

    /**
     * 取得lockview的监听器
     */
    public OnLockInputListener getLockInputListener() {
        return listener;
    }

    /**
     * 设置lockview的监听器
     */
    public void setLockInputListener(OnLockInputListener listener) {
        this.listener = listener;
    }
}
