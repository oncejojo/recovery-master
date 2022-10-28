package com.jojo.recovery.service.impl;

import com.jojo.recovery.model.msg.*;
import com.jojo.recovery.service.VxMessageService;
import org.springframework.stereotype.Service;

@Service
public class VxMessageServiceImpl implements VxMessageService {
    /**
     * 文本消息
     * @param textMsg
     * @return
     */
    @Override
    public MessageBase textHandle(FromTextMsg textMsg) {
        ToTextMsg textMsg1 = new ToTextMsg();
        init(textMsg1,textMsg);
        textMsg1.setContent("文本消息");
        return textMsg1;
    }

    /**
     * 图片消息
     * @param imgMsg
     * @return
     */
    @Override
    public MessageBase imageHandle(FromImgMsg imgMsg) {
        ToImgMsg imgMsg1 = new ToImgMsg();
        init(imgMsg1,imgMsg);
        imgMsg1.setMediaId(imgMsg.getMediaId());
        return imgMsg1;
    }

    @Override
    public MessageBase voiceHandle(FromVoiceMsg voiceMsg) {
        System.out.println(voiceMsg);
        ToVoiceMsg textMsg = new ToVoiceMsg();
        init(textMsg,voiceMsg);
        textMsg.setMediaId(voiceMsg.getMediaId());
        return textMsg;
    }

    @Override
    public MessageBase videoHandle(FromMp4ShortMsg mp4ShortMsg) {
        ToMp4Msg textMsg = new ToMp4Msg();
        init(textMsg,mp4ShortMsg);
        textMsg.setMediaId(mp4ShortMsg.getMediaId());
        textMsg.setTitle("MP4");
        textMsg.setDescription("MP4消息");
        return textMsg;
    }

    @Override
    public MessageBase shortVideoHandle(FromMp4ShortMsg mp4ShortMsg) {
        ToTextMsg textMsg = new ToTextMsg();
        init(textMsg,mp4ShortMsg);
        textMsg.setContent("小视频消息");
        return textMsg;
    }


    @Override
    public MessageBase locationHandle(FromLocationMsg locationMsg) {
        ToTextMsg textMsg = new ToTextMsg();
        init(textMsg,locationMsg);
        textMsg.setContent("位置消息" +"\n" + locationMsg);
        return textMsg;
    }

    @Override
    public MessageBase linkHandle(FromLinkMsg locationMsg) {
        ToTextMsg textMsg = new ToTextMsg();
        init(textMsg,locationMsg);
        textMsg.setContent("链接消息");
        return textMsg;
    }

    private void init(MessageBase base, MessageBase textMsg){
        base.setToUserName(textMsg.getFromUserName());
        base.setFromUserName(textMsg.getToUserName());
    }
}
