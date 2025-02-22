package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.core.HandlerInterceptor;
import io.github.yezhihao.netmc.session.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0001;
import org.yzh.protocol.t808.T0200;
import org.yzh.web.model.entity.DeviceDO;
import org.yzh.web.model.enums.SessionKey;
import org.yzh.web.model.vo.T0200Ext;

@Slf4j
@Component
public class JTHandlerInterceptor implements HandlerInterceptor<JTMessage> {

    /** 未找到对应的Handle */
    @Override
    public JTMessage notSupported(JTMessage request, Session session) {
        T0001 response = new T0001();
        response.copyBy(request);
        response.setMessageId(JT808.平台通用应答);
        response.setSerialNo(session.nextSerialNo());

        response.setResponseSerialNo(request.getSerialNo());
        response.setResponseMessageId(request.getMessageId());
        response.setResultCode(T0001.NotSupport);

        log.info("{}\n<<<<-未识别的消息{}\n>>>>-{}", session, request, response);
        return response;
    }


    /** 调用之后，返回值为void的 */
    @Override
    public JTMessage successful(JTMessage request, Session session) {
        T0001 response = new T0001();
        response.copyBy(request);
        response.setMessageId(JT808.平台通用应答);
        response.setSerialNo(session.nextSerialNo());

        response.setResponseSerialNo(request.getSerialNo());
        response.setResponseMessageId(request.getMessageId());
        response.setResultCode(T0001.Success);

//        log.info("{}\n<<<<-{}\n>>>>-{}", session, request, response);
        return response;
    }

    /** 调用之后抛出异常的 */
    @Override
    public JTMessage exceptional(JTMessage request, Session session, Throwable e) {
        T0001 response = new T0001();
        response.copyBy(request);
        response.setMessageId(JT808.平台通用应答);
        response.setSerialNo(session.nextSerialNo());

        response.setResponseSerialNo(request.getSerialNo());
        response.setResponseMessageId(request.getMessageId());
        response.setResultCode(T0001.Failure);

        log.warn(session + "\n<<<<-" + request + "\n>>>>-" + response + '\n', e);
        return response;
    }

    /** 调用之前 */
    @Override
    public boolean beforeHandle(JTMessage request, Session session) {
        int messageId = request.getMessageId();
        if (messageId == JT808.终端注册 || messageId == JT808.终端鉴权)
            return true;
        if (!session.isRegistered()) {
            log.warn("{}未注册的设备<<<<-{}", session, request);
//            return false;//忽略该消息
        }

        DeviceDO device = session.getAttribute(SessionKey.Device);
        if (messageId == JT808.位置信息汇报) {
            T0200 t0200 = (T0200) request;
            if (t0200.getDeviceTime() == null) {
                return false;//忽略没有时间的消息
            }
            request.setExtData(new T0200Ext(t0200));
            return true;
        }
        return true;
    }

    /** 调用之后 */
    @Override
    public void afterHandle(JTMessage request, JTMessage response, Session session) {
        if (response != null) {
            response.copyBy(request);
            response.setSerialNo(session.nextSerialNo());

            if (response.getMessageId() == 0) {
                response.setMessageId(response.reflectMessageId());
            }
        }
//        log.info("{}\n<<<<-{}\n>>>>-{}", session, request, response);
    }
}