package org.yzh.protocol.commons.transform.parameter;

import io.github.yezhihao.protostar.Schema;
import io.github.yezhihao.protostar.annotation.Field;
import io.netty.buffer.ByteBuf;

/**
 * 盲区监测系统参数
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class ParamBSD {

    public static final Integer key = 0xF367;

    public static final Schema<ParamBSD> SCHEMA = new ParamBSDSchema();

    @Field(desc = "后方接近报警时间阈值")
    private byte p0;
    @Field(desc = "侧后方接近报警时间阈值")
    private byte p1;

    public ParamBSD() {
    }

    public byte getP0() {
        return p0;
    }

    public void setP0(byte p0) {
        this.p0 = p0;
    }

    public byte getP1() {
        return p1;
    }

    public void setP1(byte p1) {
        this.p1 = p1;
    }

    private static class ParamBSDSchema implements Schema<ParamBSD> {

        private ParamBSDSchema() {
        }

        @Override
        public ParamBSD readFrom(ByteBuf input) {
            ParamBSD message = new ParamBSD();
            message.p0 = input.readByte();
            message.p1 = input.readByte();
            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamBSD message) {
            output.writeByte(message.p0);
            output.writeByte(message.p1);
        }
    }
}
