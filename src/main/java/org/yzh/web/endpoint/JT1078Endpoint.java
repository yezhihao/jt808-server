package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.core.annotation.Endpoint;
import io.github.yezhihao.netmc.core.annotation.Mapping;
import io.github.yezhihao.netmc.session.MessageManager;
import io.github.yezhihao.netmc.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.t1078.T1003;
import org.yzh.protocol.t1078.T1005;
import org.yzh.protocol.t1078.T1205;
import org.yzh.protocol.t1078.T1206;
import org.yzh.protocol.t808.T0001;

import static org.yzh.protocol.commons.JT1078.*;

@Endpoint
@Component
public class JT1078Endpoint {

    @Autowired
    private MessageManager messageManager;

    @Mapping(types = 终端上传音视频属性, desc = "终端上传音视频属性")
    public void 终端上传音视频属性(T1003 message, Session session) {
        messageManager.response(message);
    }

    @Mapping(types = 终端上传乘客流量, desc = "终端上传乘客流量")
    public void 终端上传乘客流量(T1005 message, Session session) {

    }

    @Mapping(types = 终端上传音视频资源列表, desc = "终端上传音视频资源列表")
    public T0001 终端上传音视频资源列表(T1205 message, Session session) {
        messageManager.response(message);

        Header header = message.getHeader();
        T0001 result = new T0001(session.nextSerialNo(), header.getMobileNo());
        result.setSerialNo(message.getSerialNo());
        result.setReplyId(header.getMessageId());
        result.setResultCode(T0001.Success);
        return result;
    }

    @Mapping(types = 文件上传完成通知, desc = "文件上传完成通知")
    public void 文件上传完成通知(T1206 message, Session session) {
    }
}