package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.多媒体数据上传应答)
public class T8800 extends JTMessage {

    private int mediaId;
    private int total;
    private byte[] items;

    public T8800() {
    }

    /** >0，如收到全部数据包则没有后续字段 */
    @Field(index = 0, type = DataType.DWORD, desc = "多媒体ID")
    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "重传包总数")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 5, type = DataType.BYTES, desc = "重传包ID列表")
    public byte[] getItems() {
        return items;
    }

    public void setItems(byte[] items) {
        this.items = items;
        this.total = items.length;
    }
}