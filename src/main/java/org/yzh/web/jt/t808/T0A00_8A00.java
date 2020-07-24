package org.yzh.web.jt.t808;

import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.common.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message({JT808.平台RSA公钥, JT808.终端RSA公钥})
public class T0A00_8A00 extends AbstractMessage<Header> {

    private Integer e;
    private byte[] n;

    public T0A00_8A00() {
    }

    public T0A00_8A00(Integer e, byte[] n) {
        this.e = e;
        this.n = n;
    }

    @Field(index = 0, type = DataType.DWORD, desc = "RSA公钥{e,n}中的e")
    public Integer getE() {
        return e;
    }

    public void setE(Integer e) {
        this.e = e;
    }

    @Field(index = 4, type = DataType.BYTES, length = 128, desc = "RSA公钥{e,n}中的n")
    public byte[] getN() {
        return n;
    }

    public void setN(byte[] n) {
        this.n = n;
    }
}