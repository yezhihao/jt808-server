package org.yzh.protocol;

import com.google.gson.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JT/T *协议单元测试类
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class BeanTest {

    public static final JTMessageAdapter coder = new JTMessageAdapter("org.yzh.protocol");

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE)))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
            .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src, type, context) -> new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_TIME)))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, type, context) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_TIME))
            .registerTypeAdapter(ByteBuf.class, (JsonSerializer<ByteBuf>) (src, type, context) -> new JsonPrimitive(ByteBufUtil.hexDump(src)))
            .registerTypeAdapter(ByteBuf.class, (JsonDeserializer<ByteBuf>) (json, type, context) -> Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(json.getAsJsonPrimitive().getAsString())))
//            .registerTypeAdapter(byte[].class, (JsonSerializer<byte[]>) (src, type, context) -> new JsonPrimitive(ByteBufUtil.hexDump(src)))
//            .registerTypeAdapter(byte[].class, (JsonDeserializer<byte[]>) (json, type, context) -> ByteBufUtil.decodeHexDump(json.getAsJsonPrimitive().getAsString()))
            .setPrettyPrinting()
            .create();

    private static void println(String hex) {
        System.out.println(hex);
    }

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
        JTMessage bean = coder.decode(buf);
        return bean;
    }

    public static String transform(JTMessage bean) {
        ByteBuf buf = coder.encode(bean);
        String hex = ByteBufUtil.hexDump(buf);
        buf.release();
        return hex;
    }
}