package org.yzh.protocol.codec;

import io.github.yezhihao.netmc.codec.MessageDecoder;
import io.github.yezhihao.netmc.codec.MessageEncoder;
import io.github.yezhihao.netmc.session.Session;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.web.endpoint.LoggingPusher;

/**
 * JT消息编解码适配器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageAdapter implements MessageEncoder<JTMessage>, MessageDecoder<JTMessage> {

    private static final Logger log = LoggerFactory.getLogger(JTMessageAdapter.class.getSimpleName());

    private JTMessageEncoder messageEncoder;

    private JTMessageDecoder messageDecoder;

    @Autowired
    private LoggingPusher loggingPusher;

    public JTMessageAdapter(JTMessageEncoder messageEncoder, JTMessageDecoder messageDecoder) {
        this.messageEncoder = messageEncoder;
        this.messageDecoder = messageDecoder;
    }

    public ByteBuf encode(JTMessage message, Session session) {
        ByteBuf output = messageEncoder.encode(message, session);
        if (log.isInfoEnabled()) {
            String hexDump = ByteBufUtil.hexDump(output);
            log.info(">>>>>session={},payload={}", session, hexDump);
            loggingPusher.info(message, hexDump);
        } else {
            loggingPusher.info(message, ByteBufUtil.hexDump(output));
        }
        return output;
    }

    @Override
    public JTMessage decode(ByteBuf input, Session session) {
        JTMessage message = messageDecoder.decode(input, session);
        if (message != null) {
            if (message.isVerified()) {
                if (log.isInfoEnabled()) {
                    String hexDump = ByteBufUtil.hexDump(input, 0, input.writerIndex());
                    log.info("<<<<<session={},payload={}", session, hexDump);
                    loggingPusher.info(message, hexDump);
                } else {
                    loggingPusher.info(message, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
                }
            } else {
                log.error("<<<<<校验码错误session={},payload={}", session, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
            }
        }
        return message;
    }
}
