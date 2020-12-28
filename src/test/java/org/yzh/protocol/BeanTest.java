package org.yzh.protocol;

import com.google.gson.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.codec.MultiPacketDecoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JT/T *协议单元测试类
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class BeanTest {

    public static final JTMessageDecoder decoder = new MultiPacketDecoder("org.yzh.protocol");

    public static final JTMessageEncoder encoder = new JTMessageEncoder("org.yzh.protocol");

    private static boolean show = true;

    private static void println(String hex1) {
        if (show)
            System.out.println(hex1);
    }

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            .setPrettyPrinting()
            .create();

    public static void selfCheck(String hex1) {
        println(hex1);

        JTMessage bean1 = transform(hex1);
        String json1 = gson.toJson(bean1);
        println(json1);

        String hex2 = transform(bean1);
        println(hex2);

        JTMessage bean2 = transform(hex2);
        String json2 = gson.toJson(bean2);
        println(json2);

        System.out.println();
        assertEquals(hex1, hex2, "hex not equals");
        assertEquals(json1, json2, "object not equals");
    }

    public static void selfCheck(JTMessage bean) {
        String hex1 = transform(bean);
        println("hex1 " + hex1);

        JTMessage bean1 = transform(hex1);
        String json1 = gson.toJson(bean1);
        println("json1 " + json1);

        String hex2 = transform(bean1);
        println("hex2 " + hex2);

        JTMessage bean2 = transform(hex2);
        String json2 = gson.toJson(bean2);
        println("json2 " + json2);

        assertEquals(hex1, hex2, "hex not equals");
        assertEquals(json1, json2, "object not equals");
    }

    public static JTMessage transform(String hex) {
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        JTMessage bean = decoder.decode(buf);
        return bean;
    }

    public static String transform(JTMessage bean) {
        ByteBuf buf = encoder.encode(bean);
        String hex = ByteBufUtil.hexDump(buf);
        buf.release();
        return hex;
    }
}