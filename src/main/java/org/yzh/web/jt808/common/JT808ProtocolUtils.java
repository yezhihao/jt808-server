package org.yzh.web.jt808.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.BCD8421Operator;
import org.yzh.framework.commons.transform.BitOperator;

import java.io.ByteArrayOutputStream;

/**
 * JT808协议转义工具类
 * <p>
 * <pre>
 * 0x7d01 <====> 0x7d
 * 0x7d02 <====> 0x7e
 * </pre>
 */
public class JT808ProtocolUtils {
    private static final Logger log = LoggerFactory.getLogger(JT808ProtocolUtils.class);

    /**
     * 接收消息时转义<br>
     * <p>
     * <pre>
     * 0x7d01 <====> 0x7d
     * 0x7d02 <====> 0x7e
     * </pre>
     *
     * @param bs    要转义的字节数组
     * @param start 起始索引
     * @param end   结束索引
     * @return 转义后的字节数组
     * @throws Exception
     */
    public static byte[] doEscape4Receive(byte[] bs, int start, int end) throws Exception {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("doEscape4Receive error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes HeaderLength=" + bs.length + ")");
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            for (int i = 0; i < start; i++) {
                baos.write(bs[i]);
            }
            for (int i = start; i < end - 1; i++) {
                if (bs[i] == 0x7d && bs[i + 1] == 0x01) {
                    baos.write(0x7d);
                    i++;
                } else if (bs[i] == 0x7d && bs[i + 1] == 0x02) {
                    baos.write(0x7e);
                    i++;
                } else {
                    baos.write(bs[i]);
                }
            }
            for (int i = end - 1; i < bs.length; i++) {
                baos.write(bs[i]);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }

    /**
     * 发送消息时转义<br>
     * <p>
     * <pre>
     *  0x7e <====> 0x7d02
     * </pre>
     *
     * @param bs    要转义的字节数组
     * @param start 起始索引
     * @param end   结束索引
     * @return 转义后的字节数组
     * @throws Exception
     */
    public static byte[] doEscape4Send(byte[] bs, int start, int end) throws Exception {
        if (start < 0 || end > bs.length)
            throw new ArrayIndexOutOfBoundsException("doEscape4Send error : index out of bounds(start=" + start
                    + ",end=" + end + ",bytes HeaderLength=" + bs.length + ")");
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            for (int i = 0; i < start; i++) {
                baos.write(bs[i]);
            }
            for (int i = start; i < end; i++) {
                if (bs[i] == 0x7e) {
                    baos.write(0x7d);
                    baos.write(0x02);
                } else {
                    baos.write(bs[i]);
                }
            }
            for (int i = end; i < bs.length; i++) {
                baos.write(bs[i]);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
                baos = null;
            }
        }
    }

    public static int generateMsgBodyProps(int msgLen, int enctyptionType, boolean isSubPackage, int reversed_14_15) {
        // [ 0-9 ] 0000,0011,1111,1111(3FF)(消息体长度)
        // [10-12] 0001,1100,0000,0000(1C00)(加密类型)
        // [ 13_ ] 0010,0000,0000,0000(2000)(是否有子包)
        // [14-15] 1100,0000,0000,0000(C000)(保留位)
        if (msgLen >= 1024)
            log.warn("The max identity of msgLen is 1023, but {} .", msgLen);
        int subPkg = isSubPackage ? 1 : 0;
        int ret = (msgLen & 0x3FF) | ((enctyptionType << 10) & 0x1C00) | ((subPkg << 13) & 0x2000)
                | ((reversed_14_15 << 14) & 0xC000);
        return ret & 0xffff;
    }

    public static byte[] generateMsgHeader(String phone, int msgType, byte[] body, int msgBodyProps, int flowId)
            throws Exception {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            // 1. 消息ID word(16)
            baos.write(BitOperator.integerTo2Bytes(msgType));
            // 2. 消息体属性 word(16)
            baos.write(BitOperator.integerTo2Bytes(msgBodyProps));
            // 3. 终端手机号 bcd[6]
            baos.write(BCD8421Operator.string2Bcd(phone));
            // 4. 消息流水号 word(16),按发送顺序从 0 开始循环累加
            baos.write(BitOperator.integerTo2Bytes(flowId));
            // 消息包封装项 此处不予考虑
            return baos.toByteArray();
        } finally {
            if (baos != null) {
                baos.close();
            }
        }
    }


}