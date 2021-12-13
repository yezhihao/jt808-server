package org.yzh.protocol;

import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import org.yzh.protocol.basics.JTMessage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * JT/T HEX单元测试类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class TestHex {

    @Test
    public void testHex() throws Exception {
        try (BufferedReader reader = reader("target/test-classes/JT808.txt")) {
            reader.lines().filter(hex -> !hex.isEmpty()).forEach(hex -> BeanTest.selfCheck(hex));
        }
    }

    @Test
    public void testSubpackage() throws Exception {
        try (BufferedReader reader = reader("target/test-classes/JT1078.txt")) {
            reader.lines().filter(hex -> !hex.isEmpty()).forEach(hex -> {
                JTMessage message = BeanTest.coder.decode(Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex)));
                if (message != null)
                    System.out.println(BeanTest.gson.toJson(message));
            });
        }
    }

    public static BufferedReader reader(String path) throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
    }
}