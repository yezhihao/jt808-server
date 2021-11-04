package org.yzh.protocol;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.commons.util.IOUtils;
import org.yzh.commons.util.StrUtils;

import java.io.File;

/**
 * JT/T HEX单元测试类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class TestHex {

    @Test
    public void testHex() {
        IOUtils.foreach(new File("target/test-classes/test_data/JT808.txt"), hex -> {
            if (StrUtils.isNotBlank(hex)) {
                BeanTest.selfCheck(hex);
            }
            return true;
        });
    }

    @Test
    public void testSubpackage() {
        IOUtils.foreach(new File("target/test-classes/test_data/JT1078.txt"), hex -> {
            if (StrUtils.isNotBlank(hex)) {
                JTMessage message = BeanTest.decoder.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
                if (message != null)
                    System.out.println(BeanTest.gson.toJson(message));
            }
            return true;
        });
    }
}