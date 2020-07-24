package org.yzh.web.endpoint;

import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.mvc.annotation.Mapping;
import org.yzh.framework.session.Session;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.t1078.T1205;
import org.yzh.web.jt.t808.T0001;

import static org.yzh.web.jt.common.JT1078.终端上传音视频资源列表;
import static org.yzh.web.jt.common.JT808.平台通用应答;

@Endpoint
@Component
public class JT1078Endpoint {

    @Mapping(types = 终端上传音视频资源列表, desc = "终端上传音视频资源列表")
    public T0001 终端上传音视频资源列表(T1205 message, Session session) {
        Header header = message.getHeader();
        String mobileNo = header.getMobileNo();

        T0001 result = new T0001(终端上传音视频资源列表, message.getSerialNo(), T0001.Success);
        result.setHeader(new Header(平台通用应答, session.currentFlowId(), header.getMobileNo()));
        return result;
    }
}