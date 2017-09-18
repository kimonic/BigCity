package com.bigcity.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * * ===============================================================
 * name:             DateSearchBmobBean
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/18
 * description：    有帖子的日期查询bean,在应用启动时默认请求一次,请求当前月份的有帖子的日期
 * history：
 * *==================================================================
 */

public class DateSearchBmobBean extends BmobObject {


    /**分页页面名称*/
    private String  name;
    /**年份*/
    private  String  year;
    /**月份*/
    private  String  month;
    /**年月日的字符串*/
    private  String  ysdCollection;
    /**当权月份是否有帖子,0---没有,1---有*/
    private  String  hasBlog="0";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHasBlog() {
        return hasBlog;
    }

    public void setHasBlog(String hasBlog) {
        this.hasBlog = hasBlog;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYsdCollection() {
        return ysdCollection;
    }

    public void setYsdCollection(String ysdCollection) {
        this.ysdCollection = ysdCollection;
    }
}
