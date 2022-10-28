package com.jojo.recovery.service;

import com.jojo.recovery.model.msg.*;

public interface VxEventService {
    /**
     * 订阅事件
     * @param evenMsgBase
     * @return
     */
    MessageBase subscribeHandle(EventMsgBase evenMsgBase);

    /**
     * 取消订阅事件
     * @param eventMsgBase
     * @return
     */
    MessageBase unsubscribeHandle(EventMsgBase eventMsgBase);

    /**
     * 点击view事件
     * @param eventViewMsg
     * @return
     */
    MessageBase ViewHandle(EventViewMsg eventViewMsg);
    /**
     * 点击事件
     * @param clickMsg
     * @return
     */
    MessageBase clickHandle(EventMenuClickMsg clickMsg);

    /**
     * 扫描二维码事件
     * @param scanMsg
     * @return
     */
    MessageBase scanHandle(EventScanMsg scanMsg);
}
