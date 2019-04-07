package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.common.MessageId;
import org.yzh.web.jt808.dto.basics.Header;

@Type({MessageId.平台RSA公钥, MessageId.终端RSA公钥})
public class RSAPack extends PackageData<Header> {

    private Long e;
    private byte[] n;

    public RSAPack() {
    }

    public RSAPack(Long e, byte[] n) {
        this.e = e;
        this.n = n;
    }

    @Property(index = 0, type = DataType.DWORD, desc = "RSA公钥{e,n}中的e")
    public Long getE() {
        return e;
    }

    public void setE(Long e) {
        this.e = e;
    }

    @Property(index = 4, type = DataType.BYTES, length = 128, desc = "RSA公钥{e,n}中的n")
    public byte[] getN() {
        return n;
    }

    public void setN(byte[] n) {
        this.n = n;
    }
}