package com.bigcity.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * * ===============================================================
 * name:             BlogDetailsBmobBean
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/15
 * description：
 * history：
 * *==================================================================
 */

public class BlogDetailsBmobBean extends BmobObject {
    /**唯一标识id--关联表使用*/
    private String   id;
    /**文章作者*/
    private String   author;
    /**文章内容*/
    private String  content;
    /**展示图片地址*/
    private String  imageUrl;
    /**发表时间,年月日时分*/
    private  String  publishTime;

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
