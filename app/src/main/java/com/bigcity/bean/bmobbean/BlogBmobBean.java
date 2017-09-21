package com.bigcity.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * * ===============================================================
 * name:             BlogBmobBean
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/15
 * description：  展示blog信息的bean--通用型---列表内容
 * history：
 * *==================================================================
 */

public class BlogBmobBean extends BmobObject {

    /**头像url*/
    private String  iconUrl="";
    /**标题*/
    private String  title="";
    /**先期展示内容*/
    private  String   content="";
    /**配图1*/
    private String  imageUrl1="";
    /**配图2*/
    private String  imageUrl2="";
    /**配图3*/
    private String  imageUrl3="";
    /**浏览次数*/
    private String  previewCount="0";
    /**回复次数*/
    private String  replyCount="0";
    /**适用类型*/
    private String type;
    /**发布时间,年月日----分页查询条件*/
    private String  releaseTimeDate;
    /**发布时间,时分秒----分页查询条件*/
    private String  releaseTimeHour;
    /**唯一标识id--关联表使用*/
    private String   id;
    /**在某一天的id,当天的发表顺序,越小越早*/
    private int  dateId;
    /**该类中的总排序id*/
    private int numId=0;

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String replyCount) {
        this.replyCount = replyCount;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }

    public String getPreviewCount() {
        return previewCount;
    }

    public void setPreviewCount(String previewCount) {
        this.previewCount = previewCount;
    }



    public String getReleaseTimeDate() {
        return releaseTimeDate;
    }

    public void setReleaseTimeDate(String releaseTimeDate) {
        this.releaseTimeDate = releaseTimeDate;
    }

    public String getReleaseTimeHour() {
        return releaseTimeHour;
    }

    public void setReleaseTimeHour(String releaseTimeHour) {
        this.releaseTimeHour = releaseTimeHour;
    }
}
