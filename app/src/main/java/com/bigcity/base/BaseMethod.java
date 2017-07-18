package com.bigcity.base;

/**
 * * ================================================
 * name:            BaseActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/7
 * description：     基本方法接口
 * history：
 * ===================================================
 *
 */

public interface BaseMethod {


    /**初始化来自intent的数据 */
    void initDataFromIntent();

    /**初始化UI控件方法 */
    void initView();

    /** 初始化事件监听方法 */
    void initListener();

    /**初始化来自internet的数据 */
    void initDataFromInternet();

    /** 加载网络数据到ui界面*/
    void LoadInternetDataToUi();



}
