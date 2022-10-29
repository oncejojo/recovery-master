package com.jojo.recovery.common.handle;

import com.jojo.recovery.common.enums.VxEventEnum;
import com.jojo.recovery.common.enums.VxMsgEnum;
import com.jojo.recovery.model.msg.*;
import com.jojo.recovery.service.VxEventService;
import com.jojo.recovery.service.VxMessageService;
import com.jojo.recovery.utils.XmlOrBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class WxMessageHandle {
    @Resource
    VxMessageService vMessageService;
    @Resource
    VxEventService vEventService;

    /**
     * @param request
     * @return
     * @desc 消息处理
     */
    public String messageHandle(HttpServletRequest request) {
        Object userMsg = XmlOrBean.xmlToBean(request);
        MessageBase replyMsg = null;
        if (userMsg instanceof FromMsgBase) {
            if (userMsg instanceof FromTextMsg) {
                replyMsg = vMessageService.textHandle((FromTextMsg) userMsg);
            } else if (userMsg instanceof FromImgMsg) {
                replyMsg = vMessageService.imageHandle((FromImgMsg) userMsg);
            } else if (userMsg instanceof FromMp4ShortMsg) {
                FromMp4ShortMsg mp4ShortMsg = (FromMp4ShortMsg) userMsg;
                if (mp4ShortMsg.getMsgType().equals(VxMsgEnum.VIDEO.val)) {
                    replyMsg = vMessageService.videoHandle((FromMp4ShortMsg) userMsg);
                } else {
                    replyMsg = vMessageService.shortVideoHandle((FromMp4ShortMsg) userMsg);
                }
            } else if (userMsg instanceof FromVoiceMsg) {
                replyMsg = vMessageService.voiceHandle((FromVoiceMsg) userMsg);
            } else if (userMsg instanceof FromLinkMsg) {
                replyMsg = vMessageService.linkHandle((FromLinkMsg) userMsg);
            } else if (userMsg instanceof FromLocationMsg) {
                replyMsg = vMessageService.locationHandle((FromLocationMsg) userMsg);
            }
        } else if (userMsg instanceof EventMsgBase){
            if (userMsg instanceof EventMenuClickMsg) {
                replyMsg = vEventService.clickHandle((EventMenuClickMsg) userMsg);
            } else if (userMsg instanceof EventScanMsg) {
                replyMsg = vEventService.scanHandle((EventScanMsg) userMsg);
            } else if (userMsg instanceof EventSubMsg) {
                EventSubMsg subMsg = (EventSubMsg) userMsg;
                if (subMsg.getEvent().equals(VxEventEnum.SUBSCRIBE.val)){
                    replyMsg = vEventService.subscribeHandle((EventMsgBase) userMsg);
                } else {
                    replyMsg = vEventService.unsubscribeHandle((EventMsgBase) userMsg);
                }
            }
        } else {
            return "success";
        }

        return XmlOrBean.messageToXml(replyMsg);
    }



}
