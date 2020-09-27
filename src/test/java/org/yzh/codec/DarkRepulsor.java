package org.yzh.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.yzh.framework.orm.FieldMetadata;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.jsatl12.DataPacket;

import java.nio.ByteBuffer;

/**
 * 编码分析
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class DarkRepulsor extends JTMessageEncoder {

    private static final DarkRepulsor darkRepulsor = new DarkRepulsor("org.yzh.protocol.jsatl12");

    public DarkRepulsor(String basePackage) {
        super(basePackage);
    }

    public static void main(String[] args) {
        DataPacket dataPacket = new DataPacket();
        dataPacket.setFlag(0x30316364);
        dataPacket.setName("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWX");
        dataPacket.setOffset(0);
        byte[] data = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        dataPacket.setLength(data.length);
        dataPacket.setData(ByteBuffer.wrap(data));

        ByteBuf byteBuf = darkRepulsor.encode(dataPacket, 0);
        System.out.println();
        System.out.println(ByteBufUtil.hexDump(byteBuf));
    }

    @Override
    public void write(ByteBuf buf, FieldMetadata fieldMetadata, Object value, int version) {
        int before = buf.writerIndex();
        super.write(buf, fieldMetadata, value, version);
        int after = buf.writerIndex();

        String hex = ByteBufUtil.hexDump(buf, before, after - before);
        System.out.println(fieldMetadata.index + "\t" + hex + "\t" + fieldMetadata.desc + "\t" + value);
    }
}