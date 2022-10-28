package com.jojo.recovery.service.impl;

import com.jojo.recovery.model.msg.*;
import com.jojo.recovery.service.VxEventService;
import org.springframework.stereotype.Service;

@Service
public class VxEventServiceImpl implements VxEventService {

    @Override
    public MessageBase subscribeHandle(EventMsgBase evenMsgBase) {
        System.out.println("订阅");
        ToTextMsg textMsg = new ToTextMsg();
        init(textMsg,evenMsgBase);
        if (evenMsgBase.getTicket() == null)
            textMsg.setContent("个人订阅");
        else
            textMsg.setContent(evenMsgBase.getTicket() + "\n推荐订阅");
        return textMsg;
    }

    @Override
    public MessageBase unsubscribeHandle(EventMsgBase evenMsgBase) {
        ToTextMsg textMsg = new ToTextMsg();
        init(textMsg,evenMsgBase);
        textMsg.setContent("取消订阅");
        return textMsg;
    }

    @Override
    public MessageBase ViewHandle(EventViewMsg evenViewMsg) {
        ToTextMsg textMsg = new ToTextMsg();
        init(textMsg,evenViewMsg);
        textMsg.setContent("点击view");
        return textMsg;
    }

    @Override
    public MessageBase clickHandle(EventMenuClickMsg clickMsg) {
        if (clickMsg.getEventKey().equals("news")) {
            ToImgAndTextMsg imgAndTextMsg = new ToImgAndTextMsg();
            init(imgAndTextMsg,clickMsg);
            imgAndTextMsg.setArticleCount("3");
            ToNewsItem toNewsItem = new ToNewsItem();
            toNewsItem.setTitle("这是第一张");
            toNewsItem.setDescription("1111111111111");
            toNewsItem.setPicUrl("http://boho.image.alimmdn.com/loading.png?t=1520057811554");
            toNewsItem.setUrl("boenfu.cn");
            imgAndTextMsg.add(toNewsItem);
            toNewsItem = new ToNewsItem();
            toNewsItem.setTitle("这是第二张");
            toNewsItem.setDescription("222222222222");
            toNewsItem.setPicUrl("http://boho.image.alimmdn.com/index/171222.png?t=1518670741568");
            toNewsItem.setUrl("boenfu.cn");
            imgAndTextMsg.add(toNewsItem);
            toNewsItem = new ToNewsItem();
            toNewsItem.setTitle("这是第三张");
            toNewsItem.setDescription("3333333333333333");
            toNewsItem.setPicUrl("http://boho.image.alimmdn.com/blog/nodedy/2.jpg?t=1520738563710");
            toNewsItem.setUrl("boenfu.cn");
            imgAndTextMsg.add(toNewsItem);
            return imgAndTextMsg;
        }else {
            ToTextMsg textMsg = new ToTextMsg();
            init(textMsg,clickMsg);
            textMsg.setContent("点击事件");
            return textMsg;
        }
    }

    @Override
    public MessageBase scanHandle(EventScanMsg scanMsg) {
        ToTextMsg toTextMsg = new ToTextMsg();
        init(toTextMsg,scanMsg);
        toTextMsg.setContent("用户扫描了生成的二维码" + scanMsg.getTicket());
        return toTextMsg;
    }

    private void init(MessageBase base, MessageBase textMsg){
        base.setToUserName(textMsg.getFromUserName());
        base.setFromUserName(textMsg.getToUserName());
    }
}
