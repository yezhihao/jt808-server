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
    public AbstractMessage notSupported(AbstractMessage<?> request, Session session) {
        log.warn(">>>>>>>>>>未识别的消息{},{}", session, request);

        AbstractHeader header = request.getHeader();
        T0001 response = new T0001(session.nextSerialNo(), header.getClientId());
        response.setSerialNo(header.getSerialNo());
        response.setReplyId(header.getMessageId());
        response.setResultCode(T0001.NotSupport);

        log.info("<<<<<<<<<<未识别的消息{},{}", session, response);
        return response;
    }


    /** 调用之后，返回值为void的 */
    @Override
    public AbstractMessage successful(AbstractMessage<?> request, Session session) {
        log.info(">>>>>>>>>>消息请求成功{},{}", session, request);

        AbstractHeader header = request.getHeader();
        T0001 response = new T0001(session.nextSerialNo(), header.getClientId());
        response.setSerialNo(header.getSerialNo());
        response.setReplyId(header.getMessageId());
        response.setResultCode(T0001.Success);

        log.info("<<<<<<<<<<通用应答消息{},{}", session, response);
        return response;
    }

    /** 调用之后抛出异常的 */
    @Override
    public AbstractMessage exceptional(AbstractMessage<?> request, Session session, Exception ex) {
        log.warn(">>>>>>>>>>消息处理异常{},{}", session, request);

        AbstractHeader header = request.getHeader();
        T0001 response = new T0001(session.nextSerialNo(), header.getClientId());
        response.setSerialNo(header.getSerialNo());
        response.setReplyId(header.getMessageId());
        response.setResultCode(T0001.Failure);

        log.info("<<<<<<<<<<异常处理应答{},{}", session, response);
        return response;
    }

    /** 调用之前 */
    @Override
    public boolean beforeHandle(AbstractMessage<?> request, Session session) {
        int messageId = request.getMessageId();
        if (messageId == JT808.终端注册 || messageId == JT808.终端鉴权)
            return true;
        if (messageId == JT808.位置信息汇报)
            session.setSnapshot(request);

        if (!session.isRegistered()) {
            log.warn(">>>>>>>>>>未注册的设备{},{}", session, request);
            return true;
        }
        return true;
    }

    /** 调用之后 */
    @Override
    public void afterHandle(AbstractMessage<?> request, AbstractMessage<?> response, Session session) {
        log.info(">>>>>>>>>>消息请求成功{},{}", session, request);
        log.info("<<<<<<<<<<应答消息{},{}", session, response);
    }
}