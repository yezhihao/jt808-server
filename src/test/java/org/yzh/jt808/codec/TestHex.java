package org.yzh.jt808.codec;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.commons.FileUtils;
import org.yzh.web.commons.JsonUtils;

import java.io.File;

/**
 * JT/T 808协议单元测试类
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class TestHex {

    @Test
    public void testHex() throws Exception {
        FileUtils.foreach(new File("target/test-classes/test_data/hex.txt"), hex -> {
            hex = hex.substring(2, hex.length() - 2);
            TestBeans.selfCheck(hex);
            return true;
        });
    }

    @Test
    public void testSubpackage() throws Exception {
        FileUtils.foreach(new File("target/test-classes/test_data/T1205.txt"), hex -> {
            if (StringUtils.isBlank(hex))
                return false;
            hex = hex.substring(2, hex.length() - 2);
            AbstractMessage message = TestBeans.decoder.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
            System.out.println(JsonUtils.toJson(message));
            return true;
        });
    }
}