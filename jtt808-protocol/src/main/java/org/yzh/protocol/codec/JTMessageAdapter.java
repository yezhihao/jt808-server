package org.yzh.protocol.codec;

import io.github.yezhihao.netmc.codec.MessageDecoder;
import io.github.yezhihao.netmc.codec.MessageEncoder;
import io.github.yezhihao.netmc.session.Session;
import io.github.yezhihao.protostar.SchemaManager;
import io.github.yezhihao.protostar.util.Explain;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.protocol.basics.JTMessage;

/**
 * JT消息编解码适配器
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class JTMessageAdapter implements MessageEncoder<JTMessage>, MessageDecoder<JTMessage> {

    protected static final Logger log = LoggerFactory.getLogger(JTMessageAdapter.class);

    private final JTMessageEncoder messageEncoder;

    private final JTMessageDecoder messageDecoder;

    public JTMessageAdapter(String... basePackages) {
        this(new SchemaManager(basePackages));
    }

    public JTMessageAdapter(SchemaManager schemaManager) {
        this(new JTMessageEncoder(schemaManager), new MultiPacketDecoder(schemaManager, new MultiPacketListener(20)));
    }

    public JTMessageAdapter(JTMessageEncoder messageEncoder, JTMessageDecoder messageDecoder) {
        this.messageEncoder = messageEncoder;
        this.messageDecoder = messageDecoder;
    }

    public ByteBuf encode(JTMessage message, Explain explain) {
        return messageEncoder.encode(message, explain);
    }

    public JTMessage decode(ByteBuf input, Explain explain) {
        return messageDecoder.decode(input, explain);
    }

    public ByteBuf encode(JTMessage message) {
        return messageEncoder.encode(message);
    }

    public JTMessage decode(ByteBuf input) {
        return messageDecoder.decode(input);
    }

    @Override
    public ByteBuf encode(JTMessage message, Session session) {
        ByteBuf output = messageEncoder.encode(message);
        encodeLog(session, message, output);
        return output;
    }

    @Override
    public JTMessage decode(ByteBuf input, Session session) {
        JTMessage message = messageDecoder.decode(input);
        if (message != null)
            message.setSession(session);
        decodeLog(session, message, input);
        return message;
    }

    public void encodeLog(Session session, JTMessage message, ByteBuf output) {
        if (log.isInfoEnabled())
            log.info("{}\n>>>>>-{},hex[{}]", session, message, ByteBufUtil.hexDump(output));
    }

    public void decodeLog(Session session, JTMessage message, ByteBuf input) {
        if (log.isInfoEnabled())
            log.info("{}\n<<<<<-{},hex[{}]", session, message, ByteBufUtil.hexDump(input, 0, input.writerIndex()));
    }
}