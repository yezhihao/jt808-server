package org.yzh.web.config;

import io.github.yezhihao.netmc.codec.MessageDecoder;
import io.github.yezhihao.netmc.codec.MessageEncoder;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.MultiVersionSchemaManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.web.endpoint.LoggingPusher;

/**
 * JT消息编解码适配器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageAdapter implements MessageEncoder<JTMessage>, MessageDecoder<JTMessage> {

    private static final Logger log = LoggerFactory.getLogger(JTMessageAdapter.class.getSimpleName());

    private final JTMessageEncoder messageEncoder;

    private final JTMessageDecoder messageDecoder;

    @Autowired
    private LoggingPusher loggingPusher;

    public JTMessageAdapter(String... basePackages) {
        this(new MultiVersionSchemaManager(basePackages));
    }

    public JTMessageAdapter(MultiVersionSchemaManager schemaManager) {
        this.messageEncoder = new JTMessageEncoder(schemaManager);
        this.messageDecoder = new JTMessageDecoder(schemaManager);
    }

    public JTMessageAdapter(JTMessageEncoder messageEncoder, JTMessageDecoder messageDecoder) {
        this.messageEncoder = messageEncoder;
        this.messageDecoder = messageDecoder;
    }

    public ByteBuf encode(JTMessage message, Session session) {
        ByteBuf output = messageEncoder.encode(message);
        if (log.isInfoEnabled())
            log.info(">>>>>session={},payload={}", session, ByteBufUtil.hexDump(output));
        loggingPusher.send(message, output);
        return output;
    }

    @Override
    public JTMessage decode(ByteBuf input, Session session) {
        if (log.isInfoEnabled())
            log.info("<<<<<session={},payload={}", session, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
        JTMessage message = messageDecoder.decode(input);
        if (message != null) {
            message.setSession(session);
            if (!message.isVerified())
                log.error("<<<<<校验码错误session={},payload={}", session, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
            loggingPusher.send(message, input);
        }
        return message;
    }
}
