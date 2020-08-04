package org.yzh.web.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0001;

public class JTHandlerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JTHandlerInterceptor.class.getSimpleName());

    /** 未找到对应的Handle */
    @Override
    public void notFoundHandle(AbstractMessage<?> request, Session session) throws Exception {
        log.warn(">>>>>>未找到对应的Handel，{},{}", session, request);

        AbstractHeader header = request.getHeader();
        T0001 response = new T0001(session.nextSerialNo(), header.getTerminalId());
        response.setSerialNo(header.getSerialNo());
        response.setReplyId(header.getMessageId());
        response.setResultCode(T0001.NotSupport);

        log.info("<<<<<<未找到对应的Handel，{},{}", session, response);
        session.writeObject(response);
    }


    /** 调用之后，返回值为void的 */
    @Override
    public void afterHandle(AbstractMessage<?> request, Session session) throws Exception {
        log.info(">>>>>>消息请求成功，{},{}", session, request);

        AbstractHeader header = request.getHeader();
        if (header.getMessageId() != JT808.终端通用应答) {
            T0001 response = new T0001(session.nextSerialNo(), header.getTerminalId());
            response.setSerialNo(header.getSerialNo());
            response.setReplyId(header.getMessageId());
            response.setResultCode(T0001.Success);

            log.info("<<<<<<通用应答消息，{},{}", session, response);
            session.writeObject(response);
        }
    }

    /** 调用之后抛出异常的 */
    @Override
    public void afterThrow(AbstractMessage<?> request, Session session, Exception ex) {
        log.warn(">>>>>>消息处理异常，{},{}", session, request);

        AbstractHeader header = request.getHeader();
        T0001 response = new T0001(session.nextSerialNo(), header.getTerminalId());
        response.setSerialNo(header.getSerialNo());
        response.setReplyId(header.getMessageId());
        response.setResultCode(T0001.Failure);

        log.info("<<<<<<异常处理应答，{},{}", session, response);
        session.writeObject(response);
    }

    /** 超出队列或线程处理能力的 */
    @Override
    public void queueOverflow(AbstractMessage<?> request, Session session) {
        log.warn(">>>>>>队列负载过大，{},{}", session, request);

        AbstractHeader header = request.getHeader();
        T0001 response = new T0001(session.nextSerialNo(), header.getTerminalId());
        response.setSerialNo(header.getSerialNo());
        response.setReplyId(header.getMessageId());
        response.setResultCode(T0001.Failure);

        session.writeObject(response);
    }

    /** 调用之前 */
    @Override
    public boolean beforeHandle(AbstractMessage<?> request, Session session) throws Exception {
        int messageId = request.getHeader().getMessageId();
        if (messageId == JT808.终端注册 || messageId == JT808.终端鉴权)
            return true;
        if (!session.isRegistered()) {
            log.warn(">>>>>>未注册的设备，{},{}", session, request);
            return true;
        }
        return true;
    }

    /** 调用之后 */
    @Override
    public void afterHandle(AbstractMessage<?> request, AbstractMessage<?> response, Session session) throws Exception {
        log.info(">>>>>>消息请求成功，{},{}", session, request);
        log.info("<<<<<<应答消息，{},{}", session, response);
    }
}