package org.yzh.web.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yzh.framework.mvc.HandlerInterceptor;
import org.yzh.framework.mvc.annotation.Endpoint;
import org.yzh.framework.orm.model.AbstractHeader;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.session.Session;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.t808.T0001;

import static org.yzh.protocol.commons.JT808.平台通用应答;

@Endpoint
@Component
public class JTHandlerInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(JTHandlerInterceptor.class.getSimpleName());

    /** 未找到对应的Handle */
    @Override
    public void notFoundHandle(AbstractMessage<?> request, Session session) throws Exception {
        log.warn("=============>未找到对应的Handel，{},{}", session, request);
        AbstractHeader header = request.getHeader();
        T0001 result = new T0001(header.getMessageId(), header.getSerialNo(), T0001.NotSupport);
        result.setHeader(new Header(平台通用应答, session.currentFlowId(), header.getTerminalId()));
        session.getChannel().writeAndFlush(result);
    }


    /** 调用之后，返回值为void的 */
    @Override
    public void afterHandle(AbstractMessage<?> request, Session session) throws Exception {
        log.warn("=============>通用应答消息，{},{}", session, request);
        AbstractHeader header = request.getHeader();
        if (JT808.终端通用应答 != header.getMessageId()) {
            T0001 result = new T0001(header.getMessageId(), header.getSerialNo(), T0001.Success);
            result.setHeader(new Header(平台通用应答, session.currentFlowId(), header.getTerminalId()));
            session.getChannel().writeAndFlush(result);
        }
    }

    /** 调用之后抛出异常的 */
    @Override
    public void afterThrow(AbstractMessage<?> request, Session session, Exception ex) {
        log.warn("=============>异常处理返回，{},{}", session, request);
        AbstractHeader header = request.getHeader();
        T0001 result = new T0001(header.getMessageId(), header.getSerialNo(), T0001.Fial);
        result.setHeader(new Header(平台通用应答, session.currentFlowId(), header.getTerminalId()));
        session.getChannel().writeAndFlush(result);
    }

    /** 超出队列或线程处理能力的 */
    @Override
    public void queueOverflow(AbstractMessage<?> request, Session session) {
        log.warn("=============>队列负载过大，{},{}", session, request);
        AbstractHeader header = request.getHeader();
        T0001 result = new T0001(header.getMessageId(), header.getSerialNo(), T0001.Fial);
        result.setHeader(new Header(平台通用应答, session.currentFlowId(), header.getTerminalId()));
        session.getChannel().writeAndFlush(result);
    }

    /** 调用之前 */
    @Override
    public boolean beforeHandle(AbstractMessage<?> request, Session session) throws Exception {
        log.info("=============>新消息{},{}", session, request);
        if (session.isAuthenticated())
            return true;
        return true;
    }

    /** 调用之后 */
    @Override
    public void afterHandle(AbstractMessage<?> request, AbstractMessage<?> response) throws Exception {
    }
}