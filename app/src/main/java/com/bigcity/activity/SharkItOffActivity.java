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

import java.util.List;

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
    public void loadInternetDataToUi() {

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
/**
 *
 1.Android的三大类传感器

 Android传感器按大方向划分大致有这么三类传感器：
 动作（Motion）传感器、环境（Environmental）传感器、位置（Position）传感器。

 （1）动作传感器

 这类传感器在三个轴（x、y、z）上测量加速度和旋转角度。包括如下几个传感器：

 加速（accelerometer）传感器、
 重力（gravity）传感器、陀螺仪（gyroscope）传感器、旋转向量（rotational vector ）传感器
 下面来看一下传感器世界的坐标系：

 传感器世界的坐标系

 是不是已经有点感觉了。

 （2）环境传感器

 这类传感器可以测量不同环境的参数，例如，周围环境的空气温度和压强、光照强度和湿度。包括如下几个传感器：

 湿度（barometer）传感器、光线（photometer）传感器、温度（thermometer）传感器
 （3）位置传感器

 这类传感器可以测量设备的物理位置。包括如下几个传感器：

 方向（orientation）传感器、磁力（magnetometer）传感器
 了解后我们就开始进入传感器的编程工作了，
 接下来我们看一下Android为我们提供的传感器框架（Android sensor framework，简称ASF）。

 2.Android传感器框架

 Android SDK为我们提供了ASF，可以用来访问当前Android设备内置的传感器。
 ASF提供了很多类和接口，帮助我们完成各种与传感器有关的任务。例如：

 1）确定当前Android设备内置了哪些传感器。
 2）确定某一个传感器的技术指标。
 3）获取传感器传回来的数据，以及定义传感器回传数据的精度。
 4）注册和注销传感器事件监听器，这些监听器用于监听传感器的变化，
 通常从传感器回传的数据需要利用这些监听器完成。
 ASF允许我们访问很多传感器类型，这些传感器有一些是基于硬件的传感器，
 还有一些是基于软件的传感器。基于硬件的传感器就是直接以芯片形式嵌入到Android设备中，
 这些传感器直接从外部环境获取数据。基于软件的传感器并不是实际的硬件芯片，
 基于软件的传感器传回的数据本质上也来自于基于硬件的传感器，
 只是这些数据通常会经过二次加工。所以基于软件的传感器也可以称为虚拟（virtual）
 传感器或合成（synthetic）传感器。

 Android对每个设备的传感器都进行了抽象，其中SensorManger类用来控制传感器，
 Sensor用来描述具体的传感器，SensorEventListener用来监听传感器值的改变。

 （1）SensorManager类

 用于创建sensor service的实例。该类提供了很多用于访问和枚举传感器，
 注册和注销传感器监听器的方法。而且还提供了与传感器精度、扫描频率、校正有关的常量。

 （2）Sensor类

 Sensor类为我们提供了一些用于获取传感器技术参数的方法。如版本、类型、生产商等。例如所有传感器的TYPE类型如下：

 序号	传感器	Sensor类中定义的TYPE常量
 1	加速度传感器	TYPE_ACCELEROMETER
 2	温度传感器	TYPE_AMBIENT_TEMPERATURE
 3	陀螺仪传感器	TYPE_GYROSCOPE
 4	光线传感器	TYPE_LIGHT
 5	磁场传感器	TYPE_MAGNETIC_FIELD
 6	压力传感器	TYPE_PRESSURE
 7	临近传感器	TYPE_PROXIMITY
 8	湿度传感器	TYPE_RELATIVE_HUMIDITY
 9	方向传感器	TYPE_ORIENTATION
 10	重力传感器	TYPE_GRAVITY
 11	线性加速传感器	TYPE_LINEAR_ACCELERATION
 12	旋转向量传感器	TYPE_ROTATION_VECTOR
 注意：1-8是硬件传感器，9是软件传感器，其中方向传感器的数据来自重力和磁场传感器，10-12是硬件或软件传感器。
 /**
 * 小米4 传感器列表
 *accelerometer   加速度传感器
 *magnetometer    磁力传感器
 * magnetometer  uncalibrated   未校准的磁力传感器
 * gyroscope     陀螺仪
 * gyroscope   uncalibrated   未校准的陀螺仪
 * proximity  sensor        距离传感器
 * ambient light sensor     环境光源传感器
 * barometer  sensor     气压传感器
 * temperature  sensor   温度传感器
 * gravity        重力传感器
 * linear  accelerration   线性加速度传感器
 * rotation  vector     旋转矢量
 * step  detector     计步器
 * step counter      计步器
 * significant motion detector   重要的运动检测器
 * game rotation vector     游戏旋转矢量
 * geomagnetic  rotation  vector   地磁旋转矢量
 * orientation    取向传感器
 * AMD
 * RMD
 * basic gesture   基本手势
 * facing           面对
 * tilt             倾斜
 * pedometer     计步器
 * PEDESTRIAN-ACTIVITY-MONITOR
 * MOTION ACCEL   运动加速度
 *
 *
 *//**
 （3）SensorEvent类

 系统使用该类创建传感器事件对象。该对象可以提供与传感器事件有关的信息。
 传感器事件对象包括的信息有原始的传感器回传数据、传感器类型、数据的精度以及触发事件的时间。

 （4）SensorEventListener接口

 该接口包含两个回调方法，当传感器的回传值或精度发生变化时，系统会调用这两个回调方法。

 /**
 * 传感器精度变化时回调
 *//**
@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) {
}
    /**
     * 传感器数据变化时回调
     *//**
    @Override
    public void onSensorChanged(SensorEvent event) {
    }
        到了这里，我们就可以进行传感器开发工作了。

        3.获取传感器技术参数

        下来我们编写代码来获取一下自己手机的传感器技术参数。

        TextView tvSensors = (TextView) findViewById(R.id.tv_sensors);
//获取传感器SensorManager对象
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
        tvSensors.append(sensor.getName() + "\n");
        }
        先运行一下看看效果：

        获取传感器技术参数

        貌似我的手机传感器还不少，哈哈。注意此处必须用实体机测试哦。

        下来我们分别看一下动作传感器、环境传感器和位置传感器的组成及使用方法。

        4.动作传感器的组成及使用方法

        所有的动作传感器都会返回三个浮点数的值（通过长度为3的数组返回），
    但对于不同的传感器，这三个只是意义不同。例如，对于加速传感器，会返回三个坐标轴的数据。
    对于陀螺仪传感器，会返回三个坐标轴的旋转角速度。

        注意：动作传感器本身一般并不会用于监测设备的位置，
    关于设备的位置需要用其他类型的传感器进行监测，例如，磁场传感器。

        （1）加速度传感器

        加速度传感器需要结合重力传感器使用，以减少加速度受重力的影响。
    首先需要实现SensorEventListener接口，添加回调方法，
    然后获取传感器SensorManager对象，注册传感器，然后我们就可以监听传感器的变化了。示例代码如下：

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvAccelerometer;
    private SensorManager mSensorManager;
    private float[] gravity = new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        tvAccelerometer = (TextView) findViewById(R.id.tv_accelerometer);
        //获取传感器SensorManager对象
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    /**
     * 传感器精度变化时回调
     *//**
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    /**
     * 传感器数据变化时回调
     *//**
    @Override
    public void onSensorChanged(SensorEvent event) {
        //判断传感器类别
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: //加速度传感器
                final float alpha = (float) 0.8;
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                String accelerometer = "加速度传感器\n" + "x:"
                        + (event.values[0] - gravity[0]) + "\n" + "y:"
                        + (event.values[1] - gravity[1]) + "\n" + "z:"
                        + (event.values[2] - gravity[2]);
                tvAccelerometer.setText(accelerometer);
                //重力加速度9.81m/s^2，只受到重力作用的情况下，自由下落的加速度
                break;
            case Sensor.TYPE_GRAVITY://重力传感器
                gravity[0] = event.values[0];//单位m/s^2
                gravity[1] = event.values[1];
                gravity[2] = event.values[2];
                break;
            default:
                break;
        }
    }
    /**
     * 界面获取焦点，按钮可以点击时回调
     *//**
    protected void onResume() {
        super.onResume();
        //注册加速度传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//传感器TYPE类型
                SensorManager.SENSOR_DELAY_UI);//采集频率
        //注册重力传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_FASTEST);
    }
    /**
     * 暂停Activity，界面获取焦点，按钮可以点击时回调
     *//**
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
        我们将手机水平正面朝上放置于桌子上，看一下效果图：

        加速度传感器

        我们可以看到正值和负值，那什么情况是正值？什么情况是负值呢？

        设备沿x轴正方向推动，x轴加速度为正值。
        设备沿y轴正方向推动，y轴加速度为正值。
        如果沿z轴正方向推动，此时手机相对于桌子水平正面朝上放置，z轴加速度为正值。
    由底部朝着顶部以a m/s^2的加速度推动，那么z轴的加速度为a + 9.81，所以如果计算实际的加速度

    （抵消重力加速度），需要减9.81。
        5.位置传感器的组成及使用方法

        Android提供了磁场传感器和方向传感器用于确定设备的位置，
    还提供了测量设备正面到某一个邻近物体距离的传感器（邻近传感器）。

        邻近传感器在手机中很常见。像接听电话时手机屏幕灭屏就是使用的邻近传感器。
    方向传感器是基于软件的，该传感器的回传数据来自加速度传感器和磁场传感器。

        位置传感器对于确定设备在真实世界中的物理位置非常有用。例如，
    可以组合磁场传感器和加速度传感器测量设备相对于地磁北极的位置，
    还可以利用方向传感器确定当前设备相对于自身参照系的位置。

        磁场传感器和方向传感器都返回值3个值（SensorEvent.values），而邻近传感器只返回1个值。

        下面我们具体看一下他们的返回值：

        方向传感器：

        SensorEvent.values[0]：绕着Z轴旋转的角度。如果Y轴（正常拿手机的方向）
    正对着北方，该值是0，如果Y轴指向南方，改值是180，Y轴指向东方，该值是90，如果Y轴指向西方，该值是270。
        SensorEvent.values[1]：绕着X轴旋转的度数。当从Z轴正方向朝向Y轴正方向，
    改值为正值。反之，为负值。该值在180至-180之间变动。
        SensorEvent.values[2]：绕着Y轴旋转的度数。当从Z轴正方向朝向X轴正方向，
    改值为正值。反之，为负值。该值在180至-180之间变动。
        磁场传感器：

        SensorEvent.values[0]：沿着X轴的磁力(μT，millitesla）
        SensorEvent.values[1]：沿着Y轴的磁力(μT，millitesla）
        SensorEvent.values[2]：沿着Y轴的磁力(μT，millitesla）
        邻近传感器：

        SensorEvent.values[0]：手机正面距离邻近物理的距离（CM）
        （1）临近传感器

        这里以临近传感器作为示例工程实现一下，其他传感器实现大同小异。

public class SensorActivity extends AppCompatActivity implements SensorEventListener {
    private TextView tvProximity;
    private SensorManager mSensorManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensor);
        tvProximity = (TextView) findViewById(R.id.tv_proximity);
        //获取传感器SensorManager对象
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    /**
     * 传感器精度变化时回调
     *//**
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    /**
     * 传感器数据变化时回调
     *//**
    @Override
    public void onSensorChanged(SensorEvent event) {
        //判断传感器类别
        switch (event.sensor.getType()) {
            case Sensor.TYPE_PROXIMITY://临近传感器
                tvProximity.setText(String.valueOf(event.values[0]));
                break;
            default:
                break;
        }
    }
    /**
     * 界面获取焦点，按钮可以点击时回调
     *//**
    protected void onResume() {
        super.onResume();
        //注册临近传感器
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI);
    }
    /**
     * 暂停Activity，界面获取焦点，按钮可以点击时回调
     *//**
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

}
        运行程序，我间断的挡住临近传感器，看一下效果图：

        临近传感器

        0.0是我挡住临近传感器时候的值，8.0是我将手移开时的值。

        下面我们再来看一个比较叼的传感器，与自然息息相关。

        6.环境传感器的组成及使用方法

        Android提供了用于检测不同的外部环境的传感器。例如，可以检测周围空气的湿度、
    光线、空气的压强和温度，这些传感器都是基于硬件的传感器。除了光线传感器外，

    其他传感器在普通的Android设备中很少见。所以如果使用环境传感器，最
    好运行时对当前Android设备所支持的传感器进行检测。

        （1）环境传感器的返回值

        大多数动作传感器和位置传感器都返回多个值，而所有的环境传感器都只返回一个值：

        传感器	TYPE值	返回值	单位
        温度传感器	TYPE_AMBIENT_TEMPERATURE	event.values[0]	°C
        压力传感器	TYPE_PRESSURE	event.values[0]	hPa
        光线传感器	TYPE_LIGHT	event.values[0]	lx
        湿度传感器	TYPE_RELATIVE_HUMIDITY	event.values[0]	RH（%）
        注意：环境传感器返回的值很少受到杂音的干扰，而动作和位置传感器经常需要消除杂音的影响。
    例如，加速度传感器要消除重力对其回传值的影响。

        （2）光线传感器回传数据

//最强的光线强度（估计只有沙漠地带才能达到这个值）
public static final float LIGHT_SUNLIGHT_MAX = 120000.0f;
//万里无云时阳光直射的强度
public static final float LIGHT_SUNLIGHT = 110000.0f;
//有阳光，但被云彩抵消了部分光线时的强度
public static final float LIGHT_SHADE = 20000.0f;
//多云时的光线强度
public static final float LIGHT_OVERCAST = 10000.0f;
//太阳刚刚升起时（日出）的光线强度
public static final float LIGHT_SUNRISE = 400.0f;
//在阴雨天，没有太阳时的光线强度
public static final float LIGHT_CLOUDY = 100.0f;
//夜晚有月亮时的光线强度
public static final float LIGHT_FULLMOON = 0.25f;
//夜晚没有月亮时的光线强度（当然，也不能有路灯，就是漆黑一片）
public static final float LIGHT_NO_MOON = 0.001f;

        环境传感器的使用方法与动作、位置传感器大同小异，在次不再赘述。

        相信通过本篇的学习，大家的开发水准都会有一定的提高，而大家的提高是我最欣慰的事情了。
 */