package org.yzh.web.endpoint;

import io.github.yezhihao.netmc.core.HandlerInterceptor;
import io.github.yezhihao.netmc.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0001;
import org.yzh.web.model.enums.SessionKey;

public class JTHandlerInterceptor implements HandlerInterceptor<JTMessage> {

    private static final Logger log = LoggerFactory.getLogger(JTHandlerInterceptor.class.getSimpleName());

    /** 未找到对应的Handle */
    @Override
    public JTMessage notSupported(JTMessage request, Session session) {
        Header header = new Header(JT808.平台通用应答);
        header.setSerialNo(session.nextSerialNo());
        header.copyBy(request.getHeader());

        T0001 response = new T0001();
        response.setHeader(header);
        response.setResponseSerialNo(request.getSerialNo());
        response.setResponseMessageId(request.getMessageId());
        response.setResultCode(T0001.NotSupport);

        log.info("{}\n<<<<-未识别的消息{}\n>>>>-{}", session, request, response);
        return response;
    }


    /** 调用之后，返回值为void的 */
    @Override
    public JTMessage successful(JTMessage request, Session session) {
        Header header = new Header(JT808.平台通用应答);
        header.setSerialNo(session.nextSerialNo());
        header.copyBy(request.getHeader());

        T0001 response = new T0001();
        response.setHeader(header);
        response.setResponseSerialNo(request.getSerialNo());
        response.setResponseMessageId(request.getMessageId());
        response.setResultCode(T0001.Success);

        log.info("{}\n<<<<-{}\n>>>>-{}", session, request, response);
        return response;
    }

    /** 调用之后抛出异常的 */
    @Override
    public JTMessage exceptional(JTMessage request, Session session, Exception e) {
        Header header = new Header(JT808.平台通用应答);
        header.setSerialNo(session.nextSerialNo());
        header.copyBy(request.getHeader());

        T0001 response = new T0001();
        response.setHeader(header);
        response.setResponseSerialNo(request.getSerialNo());
        response.setResponseMessageId(request.getMessageId());
        response.setResultCode(T0001.Failure);

        log.warn(session + "\n<<<<-" + request + "\n>>>>-" + response + '\n', e);
        return response;
    }

    /** 调用之前 */
    @Override
    public boolean beforeHandle(JTMessage request, Session session) {
        Header header = request.getHeader();
        if (header != null) {
            int messageId = header.getMessageId();
            if (messageId == JT808.终端注册 || messageId == JT808.终端鉴权)
                return true;
            if (messageId == JT808.位置信息汇报) {
                request.transform();
                session.setAttribute(SessionKey.Snapshot, request);
            }
        }
        if (!session.isRegistered()) {
            log.info("{}未注册的设备\n<<<<-{}", session, request);
            return true;
        }
        return true;
    }

    /** 调用之后 */
    @Override
    public void afterHandle(JTMessage request, JTMessage response, Session session) {
        if (response != null) {
            Header header = response.getHeader();
            if (header == null) {
                header = new Header().copyBy(request.getHeader());
                header.setSerialNo(session.nextSerialNo());
                response.setHeader(header);
            }

            if (header.getMessageId() == 0) {
                header.setMessageId(response.reflectMessageId());
            }
        }
        log.info("{}\n<<<<-{}\n>>>>-{}", session, request, response);
    }
}