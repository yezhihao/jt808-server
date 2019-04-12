package org.yzh.jt808.codec;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.apache.commons.codec.binary.Hex;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.web.config.Charsets;

public class Test {

    public static void main(String[] args) throws Exception {
        System.out.println(Hex.encodeHexString("JS5-HD-105V".getBytes(Charsets.GBK)));
        System.out.println(Hex.encodeHexString("T17051604-V705061".getBytes(Charsets.GBK)));

        System.out.println(new String(Hex.decodeHex("073731363033"), Charsets.GBK));
        System.out.println(new String(Hex.decodeHex("4a53352d4c000000000000000000000000000000"), Charsets.GBK));
        System.out.println(new String(Hex.decodeHex("8401"), Charsets.GBK));

        System.out.println(Bcd.encode8421String(Hex.decodeHex("3689860225131650397533")));
        System.out.println(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump("8401")).readUnsignedShort());
    }
}