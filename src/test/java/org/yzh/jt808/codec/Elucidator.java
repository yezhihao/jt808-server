package org.yzh.jt808.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.framework.annotation.Property;
import org.yzh.framework.message.PackageData;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt808.codec.JT808MessageDecoder;
import org.yzh.web.jt808.dto.PositionReport;
import org.yzh.web.jt808.dto.basics.Header;

import java.beans.PropertyDescriptor;
import java.nio.charset.Charset;

/**
 * 阐释者
 *
 * @author zhihao.ye (yezhihaoo@gmail.com)
 */
public class Elucidator extends JT808MessageDecoder {

    private static final Elucidator elucidator = new Elucidator(Charsets.GBK);

    public Elucidator(Charset charset) {
        super(charset);
    }

    public static void main(String[] args) {
        Class<? extends PackageData> clazz = PositionReport.class;
        String hex = "0200002d010000000000007b000000070000000600000001000000020003000400051904061915541206000000000000110100e3040000000bfe";

        System.out.println(hex);
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        Header header = elucidator.decodeHeader(buf);
        System.out.println();
        ByteBuf slice = buf.slice(header.getHeaderLength(), header.getBodyLength());
        PackageData<Header> body = elucidator.decodeBody(slice, clazz);
        body.setHeader(header);
    }

    @Override
    public Object read(ByteBuf buf, Property prop, int length, PropertyDescriptor pd) {
        buf.markReaderIndex();
        System.out.print("\n" + prop.index() + "\t" + ByteBufUtil.hexDump(buf.readSlice(length)) + "\t\t" + prop.desc() + "\t");
        buf.resetReaderIndex();

        Object value = super.read(buf, prop, length, pd);
        System.out.print(value);
        return value;
    }
}