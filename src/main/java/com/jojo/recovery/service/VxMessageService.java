package com.jojo.recovery.service;

import com.jojo.recovery.model.msg.*;

public interface VxMessageService {
    MessageBase textHandle(FromTextMsg fromTextMsg);

    MessageBase imageHandle(FromImgMsg fromImgMsg);

    MessageBase voiceHandle(FromVoiceMsg fromVoiceMsg);

    MessageBase videoHandle(FromMp4ShortMsg fromMp4ShortMsg);

    /**
     * 小视频消息
     * @param mp4ShortMsg
     * @return
     */
    MessageBase shortVideoHandle(FromMp4ShortMsg mp4ShortMsg);

    MessageBase linkHandle(FromLinkMsg fromLinkMsg);

    MessageBase locationHandle(FromLocationMsg fromLocationMsg);


}
