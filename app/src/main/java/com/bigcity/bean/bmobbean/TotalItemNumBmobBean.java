package com.bigcity.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * * ===============================================================
 * name:             TotalItemNumBmobBean
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/21
 * description：   每一个分类item的总数
 * history：
 * *==================================================================
 */

public class TotalItemNumBmobBean extends BmobObject {
    /**类型*/
    private  String  type="1";
    /**名称*/
    private  String  name="";
    /**总数*/
    private int  total=0;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
