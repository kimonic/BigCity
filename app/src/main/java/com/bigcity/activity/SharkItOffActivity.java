package com.bigcity.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.ui.MTopBarView;
import com.bigcity.utils.ScreenSizeUtils;
import com.bigcity.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * * ================================================
 * name:            SharkItOffActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/8/7
 * description：摇一摇界面activity
 * history：
 * ===================================================
 */

public class SharkItOffActivity extends BaseActivity implements SensorEventListener {
    @BindView(R.id.mtb_act_sharkitoff)
    MTopBarView mtbActSharkItOff;
    @BindView(R.id.tv_act_sharkitoff)
    TextView tvActSharkItOff;

    /**传感器管理类*/
    private SensorManager manager;
    /**传感器--具体类型*/
    private Sensor sensor;
    /**手机震动类*/
    private Vibrator vibrator;
    /**音效播放类*/
    private SoundPool soundPool;
    /**音效文件id*/
    private int myAudio;
    /**传感器类型*/
    private int type;
    /**是否已触发震动*/
    private boolean isShake = false;


    @Override
    public int getLayoutResId() {
        return R.layout.act_sharkitoff;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_sharkitoff:
                showAnimation();
                break;
        }

    }

    @Override
    public void initDataFromIntent() {

        /**
         *SoundPool源码中的构造方法方法体
         *
         * @param maxStreams 最多可以容纳多少个音频
         * @param streamType 指定的声音类型，通过AudioManager类提供的常量进行指定
         * @param srcQuality 指定音频的质量，默认为0
         * @return a SoundPool object, or null if creation failed
         */
        //初始化soundPool
        soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        myAudio = soundPool.load(this, R.raw.weichat_audio, 1);
        //获取震动服务
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);


    }


    @Override
    protected void onStart() {
        super.onStart();
        //        //获取 SensorManager 负责管理传感器
        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (manager != null) {
            //            //获取加速度传感器
            sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (sensor != null) {
                //注册传感器监听
                manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {

        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (manager != null) {
            manager.unregisterListener(this);
        }
        super.onPause();
    }

    @Override
    public void initView() {
//        /**设置沉浸式状态栏*/
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mtbActSharkItOff.getLayoutParams();
        params.setMargins(0, ScreenSizeUtils.getStatusHeight(this), 0, 0);
        mtbActSharkItOff.setLayoutParams(params);


    }

    @Override
    public void initListener() {
        tvActSharkItOff.setOnClickListener(this);
        mtbActSharkItOff.getLeftTV().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }

    private void showAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvActSharkItOff, "rotation", 0f, 60f, -60f, 60f, -60f, 0);
        animator.setDuration(1000);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ToastUtils.showToast(SharkItOffActivity.this, "哈哈哈哈哈哈!");
                isShake = false;

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    //传感器监听
    @Override
    public void onSensorChanged(SensorEvent event) {
        type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];
            Log.e("TAG", "onSensorChanged: ---------------------???" + x);
            Log.e("TAG", "onSensorChanged: ---------------------???" + y);
            Log.e("TAG", "onSensorChanged: ---------------------???" + z);
            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math.abs(z) > 17) && !isShake) {
                isShake = true;
                soundPool.play(myAudio, 1, 1, 0, 0, 1);
                vibrator.vibrate(500);
                showAnimation();
            }

        }
//
//        if (type == Sensor.TYPE_ACCELEROMETER) {
//
//            if ((Math.abs(x) > 17 || Math.abs(y) > 17 || Math
//                    .abs(z) > 17) && !isShake) {
//                isShake = true;
//                // TODO: 2016/10/19 实现摇动逻辑, 摇动后进行震动
//                Thread thread = new Thread() {
//                    @Override
//                    public void run() {
//
//
//                        super.run();
//                        try {
//                            Log.d(TAG, "onSensorChanged: 摇动");
//
//                            //开始震动 发出提示音 展示动画效果
//                            mHandler.obtainMessage(START_SHAKE).sendToTarget();
//                            Thread.sleep(500);
//                            //再来一次震动提示
//                            mHandler.obtainMessage(AGAIN_SHAKE).sendToTarget();
//                            Thread.sleep(500);
//                            mHandler.obtainMessage(END_SHAKE).sendToTarget();
//
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                thread.start();
//            }
//        }

    }
    //传感器监听

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
