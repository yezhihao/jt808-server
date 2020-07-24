package org.yzh.web.jt.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.web.jt.basics.Header;
import org.yzh.web.jt.common.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.多媒体数据上传应答)
public class T8800 extends AbstractMessage<Header> {

    private Integer mediaId;
    private Integer packageTotal;
    private byte[] idList;

    public T8800() {
    }

    /** >0，如收到全部数据包则没有后续字段 */
    @Field(index = 0, type = DataType.DWORD, desc = "多媒体ID")
    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    @Field(index = 4, type = DataType.BYTE, desc = "重传包总数")
    public Integer getPackageTotal() {
        return packageTotal;
    }

    public void setPackageTotal(Integer packageTotal) {
        this.packageTotal = packageTotal;
    }

    @Field(index = 5, type = DataType.BYTES, desc = "重传包ID列表")
    public byte[] getIdList() {
        return idList;
    }

    public void setIdList(byte[] idList) {
        this.idList = idList;
    }
}