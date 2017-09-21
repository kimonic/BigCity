package com.bigcity.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * * ===============================================================
 * name:             PageInfoBmobBean
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/18
 * description：   页面分页信息查询bean
 * history：
 * *==================================================================
 */

public class PageInfoBmobBean extends BmobObject {

    /**分页页面名称*/
    private String  name;
    /**分页页面类型*/
    private String  type;
    /**发布日期,年月日*/
    private  String  date;
    /**当前日期信息总条数*/
    private  int  total;
    /**当前类型信息总条数*/
    private  int  totalNum=0;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
