package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.jt808.common.MessageId;

@Type(MessageId.查询指定终端参数)
public class T8106 extends AbstractBody {

    private Integer total;
    private byte[] ids;

    @Property(index = 0, type = DataType.BYTE, desc = "参数总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 1, type = DataType.BYTES, desc = "参数ID列表")
    public byte[] getIds() {
        return ids;
    }

    public void setIds(byte[] ids) {
        this.ids = ids;
        this.total = ids.length;
    }
}