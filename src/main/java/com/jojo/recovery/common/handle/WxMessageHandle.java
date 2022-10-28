package com.jojo.recovery.common.handle;

import com.jojo.recovery.common.enums.VxEventEnum;
import com.jojo.recovery.common.enums.VxMsgEnum;
import com.jojo.recovery.model.msg.*;
import com.jojo.recovery.service.VxEventService;
import com.jojo.recovery.service.VxMessageService;
import com.jojo.recovery.utils.XmlOrBean;
import lombok.extern.slf4j.Slf4j;
import org.jacoco.agent.rt.internal_035b120.core.internal.flow.IFrame;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class WxMessageHandle {
    @Resource
    VxMessageService vxMessageService;
    @Resource
    VxEventService vxEventService;

    public String getMessage(HttpServletRequest request) {
        Object sendMsg = XmlOrBean.xmlToBean(request);
        MessageBase reply = null;
        if (sendMsg instanceof MessageBase){

        } else if (sendMsg instanceof EventMsgBase) {

        } else {
            return "success";
        }

        return XmlOrBean.messageToXml(reply);
    }



}
