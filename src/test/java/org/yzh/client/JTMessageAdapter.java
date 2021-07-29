package org.yzh.client;

import io.github.yezhihao.netmc.codec.MessageDecoder;
import io.github.yezhihao.netmc.codec.MessageEncoder;
import io.github.yezhihao.netmc.session.Session;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;

/**
 * JT消息编解码适配器
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageAdapter implements MessageEncoder<JTMessage>, MessageDecoder<JTMessage> {

    private static final Logger log = LoggerFactory.getLogger(JTMessageAdapter.class.getSimpleName());

    private JTMessageEncoder messageEncoder;

    private JTMessageDecoder messageDecoder;

    public JTMessageAdapter(JTMessageEncoder messageEncoder, JTMessageDecoder messageDecoder) {
        this.messageEncoder = messageEncoder;
        this.messageDecoder = messageDecoder;
    }

    public ByteBuf encode(JTMessage message, Session session) {
        ByteBuf output = messageEncoder.encode(message, session);
        return output;
    }

    @Override
    public JTMessage decode(ByteBuf input, Session session) {
        JTMessage message = messageDecoder.decode(input, session);
        return message;
    }
}
