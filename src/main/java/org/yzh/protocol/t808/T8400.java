package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.电话回拨)
public class T8400 extends JTMessage {

    /** 普通通话 */
    public static final int Normal = 0;
    /** 监听 */
    public static final int Listen = 1;

    private int type;
    private String mobileNo;

    public T8400() {
    }

    public T8400(String clientId, int type, String mobileNo) {
        super(new Header(clientId, JT808.电话回拨));
        this.type = type;
        this.mobileNo = mobileNo;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "标志")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.STRING, length = 20, desc = "电话号码")
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}