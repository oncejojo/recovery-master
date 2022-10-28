package com.jojo.recovery.model.msg;

import com.jojo.recovery.common.enums.VxMsgEnum;

/**
 * 被动回复图片消息
 * @Author: wugui
 * @Date 2018-7-17 17:56
 */
public class ToImgMsg extends MessageBase {
    private ToBitch Image; //
    public ToImgMsg(){
        super.setMsgType(VxMsgEnum.IMAGE.val);
        Image = new ToBitch();
    }

    public String getMediaId() {
        return Image.getMediaId();
    }

    public void setMediaId(String mediaId) {
        Image.setMediaId(mediaId);
    }
}
