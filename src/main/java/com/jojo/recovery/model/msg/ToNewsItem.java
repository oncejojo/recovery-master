package com.jojo.recovery.model.msg;

/**
 * 图文消息每一项
 * @Author: wugui
 * @Date 2018-7-19 16:03
 */
public class ToNewsItem {
    private String Title;  // 图文消息标题
    private String Description;  // 图文消息描述
    private String PicUrl;  // 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
    private String Url;  // 点击图文消息跳转链接

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
