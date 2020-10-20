package org.yzh.protocol;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.yzh.framework.commons.ClassUtils;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.codec.JTMessageDecoder;
import org.yzh.protocol.codec.JTMessageEncoder;
import org.yzh.protocol.codec.MultiPacketDecoder;
import org.yzh.web.commons.RandomUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

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

    public static final Gson gson = new Gson();

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
        assertEquals("hex not equals", hex1, hex2);
        assertEquals("object not equals", json1, json2);
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

        assertEquals("hex not equals", hex1, hex2);
        assertEquals("object not equals", json1, json2);
    }

    public static JTMessage transform(String hex) {
        ByteBuf buf = Unpooled.wrappedBuffer(ByteBufUtil.decodeHexDump(hex));
        JTMessage bean = decoder.decode(buf);
        return bean;
    }

    public static String transform(JTMessage bean) {
        ByteBuf buf = encoder.encode(bean);
        String hex = ByteBufUtil.hexDump(buf);
        return hex;
    }

    public static void main(String[] args) throws Exception {
        List<Class<?>> classList = ClassUtils.getClassList("org.yzh.protocol.t1078");
        for (Class<?> aClass : classList) {
            String simpleName = aClass.getSimpleName();
            BeanInfo beanInfo = Introspector.getBeanInfo(aClass);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            String ps = "";
            for (PropertyDescriptor p : propertyDescriptors) {
                Method writeMethod = p.getWriteMethod();
                if (writeMethod == null)
                    continue;
                Object val = genBaseData(p.getPropertyType());
                String name = writeMethod.getName();
                ps += "\t\tbean." + name + "(" + val + ");\n";
            }
            System.out.println("    //xxxx\n" +
                    "    public static " + simpleName + " " + simpleName + "() {\n" +
                    "        " + simpleName + " bean = new " + simpleName + "();\n" +
                    ps +
                    "        return bean;\n" +
                    "    }");
        }
    }

    private static <T> Object genBaseData(Class<T> clazz) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (clazz.isAssignableFrom(Boolean.class) || clazz.isAssignableFrom(Boolean.TYPE))
            return random.nextBoolean();
        if (clazz.isAssignableFrom(Byte.class) || clazz.isAssignableFrom(Byte.TYPE))
            return (byte) random.nextInt(0, 127);
        if (clazz.isAssignableFrom(Short.class) || clazz.isAssignableFrom(Short.TYPE))
            return (short) random.nextInt(0, 127);
        if (clazz.isAssignableFrom(Integer.class) || clazz.isAssignableFrom(Integer.TYPE))
            return random.nextInt(0, 127);
        if (clazz.isAssignableFrom(Long.class) || clazz.isAssignableFrom(Long.TYPE))
            return random.nextLong(0, 127);
        if (clazz.isAssignableFrom(Float.class) || clazz.isAssignableFrom(Float.TYPE))
            return random.nextFloat();
        if (clazz.isAssignableFrom(Double.class) || clazz.isAssignableFrom(Double.TYPE))
            return random.nextDouble();
        if (clazz.isAssignableFrom(Character.class) || clazz.isAssignableFrom(Character.TYPE))
            return RandomUtils.nextString(1).charAt(0);
        if (clazz.isAssignableFrom(String.class))
            return "\"" + RandomUtils.nextString(9) + "\"";
        if (clazz.isAssignableFrom(Date.class))
            return new Date();
        return null;
    }
}