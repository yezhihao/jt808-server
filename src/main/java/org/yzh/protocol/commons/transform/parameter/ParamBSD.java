package org.yzh.protocol.commons.transform.parameter;

import io.netty.buffer.ByteBuf;

/**
 * 盲区监测系统参数
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class ParamBSD {

    public static final int id = 0xF367;

    public static int id() {
        return id;
    }

    public int 后方接近报警时间阈值;
    public int 侧后方接近报警时间阈值;

    public ParamBSD() {
    }


    public static class Schema implements io.github.yezhihao.protostar.Schema<ParamBSD> {

        public static final Schema INSTANCE = new Schema();

        private Schema() {
        }

        @Override
        public ParamBSD readFrom(ByteBuf input) {
            ParamBSD message = new ParamBSD();
            message.后方接近报警时间阈值 = input.readByte();
            message.侧后方接近报警时间阈值 = input.readByte();

            return message;
        }

        @Override
        public void writeTo(ByteBuf output, ParamBSD message) {
            output.writeByte(message.后方接近报警时间阈值);
            output.writeByte(message.侧后方接近报警时间阈值);
        }
    }
}
