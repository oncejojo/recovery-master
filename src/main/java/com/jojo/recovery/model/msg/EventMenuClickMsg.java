package com.jojo.recovery.model.msg;

/**
 * 菜单点击事件实体
 * @Author: wugui
 * @Date 2018-7-18 10:29
 */
public class EventMenuClickMsg extends EventMsgBase {
    private String EventKey; // 事件KEY值，与自定义菜单接口中KEY值对应

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
}
