package com.bigcity.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * * ===============================================================
 * name:             CommentBmobBean
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/25
 * description：   详情评论列表
 * history：
 * *==================================================================
 */

public class CommentBmobBean extends BmobObject {
    /**帖子唯一标识id*/
    private  String  id;
    /**回复人--当前登陆账号昵称*/
    private  String  name;
    /**评论用户的头像url*/
    private  String  iconUrl;
    /**点赞数*/
    private  String  admire="0";
    /**评论的回复数*/
    private  String  replyNum="0";
    /**评论时间*/
    private  String  time;
    /**评论下的追加评论,name-name-content格式*/
    private  String  addComment;
    /**评论内容*/
    private  String  content;
    /**排序时间*/
    private  long  orderTime;
    /**点赞用户id集合*/
    private  String  userIdCollection;

    public String getUserIdCollection() {
        return userIdCollection;
    }

    public void setUserIdCollection(String userIdCollection) {
        this.userIdCollection = userIdCollection;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getReplyNum() {
        return replyNum;
    }

    public void setReplyNum(String replyNum) {
        this.replyNum = replyNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdmire() {
        return admire;
    }

    public void setAdmire(String admire) {
        this.admire = admire;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddComment() {
        return addComment;
    }

    public void setAddComment(String addComment) {
        this.addComment = addComment;
    }
}





































