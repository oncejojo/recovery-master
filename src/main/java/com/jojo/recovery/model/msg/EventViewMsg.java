package com.jojo.recovery.model.msg;

/**
 * 点击view按钮事件
 * @Author: wugui
 * @Date 2018-7-18 16:34
 */
public class EventViewMsg extends EventMsgBase {
    private String MenuId;

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
