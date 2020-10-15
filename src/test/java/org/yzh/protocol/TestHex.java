package org.yzh.protocol;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.commons.FileUtils;
import org.yzh.web.commons.JsonUtils;

import java.io.File;

/**
 * JT/T HEX单元测试类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class TestHex {

    @Test
    public void testHex() throws Exception {
        FileUtils.foreach(new File("target/test-classes/test_data/JT808.txt"), hex -> {
            if (StringUtils.isNotBlank(hex)) {
                if (hex.startsWith("7e"))
                    hex = hex.substring(2, hex.length() - 2);
                BeanTest.selfCheck(hex);
            }
            return true;
        });
    }

    @Test
    public void testSubpackage() throws Exception {
        FileUtils.foreach(new File("target/test-classes/test_data/JT1078.txt"), hex -> {
            if (StringUtils.isBlank(hex))
                return false;
            hex = hex.substring(2, hex.length() - 2);
            AbstractMessage message = BeanTest.decoder.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
            System.out.println(JsonUtils.toJson(message));
            return true;
        });
    }
}