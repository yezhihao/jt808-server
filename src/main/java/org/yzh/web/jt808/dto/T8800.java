package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(MessageId.多媒体数据上传应答)
public class T8800 extends AbstractBody {

    private Integer mediaId;
    private Integer packageTotal;
    private byte[] idList;

    public T8800() {
    }

    /** >0，如收到全部数据包则没有后续字段 */
    @Property(index = 0, type = DataType.DWORD, desc = "多媒体ID")
    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    @Property(index = 4, type = DataType.BYTE, desc = "重传包总数")
    public Integer getPackageTotal() {
        return packageTotal;
    }

    public void setPackageTotal(Integer packageTotal) {
        this.packageTotal = packageTotal;
    }

    @Property(index = 5, type = DataType.BYTES, desc = "重传包ID列表")
    public byte[] getIdList() {
        return idList;
    }

    public void setIdList(byte[] idList) {
        this.idList = idList;
    }
}