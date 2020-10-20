package org.yzh.protocol;

import org.yzh.framework.orm.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.jsatl12.*;

/**
 * JT/T 808协议单元测试数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class JSATL12Beans {

    private static final String UUID = "ad72131579e54be0b0f737cfc72c5db8";

    /** 2013版消息头 */
    public static JTMessage H2013(JTMessage message) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        if (type != null)
            header.setMessageId(type.value()[0]);
        header.setMobileNo("12345678901");
        header.setSerialNo((int) Short.MAX_VALUE);
        header.setEncryption(0);
        header.setReserved(false);
        message.setHeader(header);
        return message;
    }

    /** 2019版消息头 */
    public static JTMessage H2019(JTMessage message) {
        Header header = new Header();
        Message type = message.getClass().getAnnotation(Message.class);
        if (type != null)
            header.setMessageId(type.value()[0]);
        header.setVersionNo(1);
        header.setMobileNo("17299841738");
        header.setSerialNo(65535);
        header.setEncryption(0);
        header.setVersion(true);
        header.setReserved(false);
        message.setHeader(header);
        return message;
    }

    //报警附件信息消息
    public static T1210 T1210() {
        T1210 bean = new T1210();
        bean.setTerminalId("1234567");
        bean.setAlarmId(new AlarmId("qwe123", "200827111111", 1, 3, 1));
        bean.setAlarmNo(UUID);
        bean.setType(0);
        bean.setItems(null);
        return bean;
    }

    //文件信息上传/文件上传完成消息
    public static T1211 T1211() {
        T1211 bean = new T1211();
        bean.setName("YKJYsf3so");
        bean.setType(1);
        bean.setSize(1024);
        return bean;
    }

    //报警附件上传指令
    public static T9208 T9208() {
        T9208 bean = new T9208();
        bean.setIp("47.104.97.169");
        bean.setTcpPort(8202);
        bean.setUdpPort(8203);
        bean.setAlarmId(new AlarmId("qwe123", "200827111111", 1, 1, 1));
        bean.setAlarmNo(UUID);
        bean.setReserved(new byte[16]);
        return bean;
    }

    //文件上传完成消息
    public static T9212 T9212() {
        T9212 bean = new T9212();
        bean.setName("test123");
        bean.setType(0);
        bean.setResult(0);
        return bean;
    }
}