package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@Message({JT808.平台RSA公钥, JT808.终端RSA公钥})
public class T0A00_8A00 extends JTMessage {

    @Field(length = 4, desc = "RSA公钥{e,n}中的e")
    private int e;
    @Field(length = 128, desc = "RSA公钥{e,n}中的n")
    private byte[] n;

    public T0A00_8A00() {
    }

    public T0A00_8A00(int e, byte[] n) {
        this.e = e;
        this.n = n;
    }

    public int getE() {
        return e;
    }

    public void setE(int e) {
        this.e = e;
    }

    public byte[] getN() {
        return n;
    }

    public void setN(byte[] n) {
        this.n = n;
    }
}