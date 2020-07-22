package org.yzh.web.jt.t808;

import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.web.jt.common.JT808;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Type(JT808.CAN总线数据上传)
public class T0705 extends AbstractBody {

    private Integer total;
    private String dateTime;
    private List<Item> list;

    @Property(index = 0, type = DataType.WORD, desc = "数据项个数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 2, type = DataType.BCD8421, length = 5, desc = "CAN 总线数据接收时间")
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Property(index = 7, type = DataType.BCD8421, length = 5, desc = "CAN 总线数据项")
    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    public static class Item {
        private byte[] id;
        private byte[] data;

        @Property(index = 0, type = DataType.BYTES, length = 4, desc = "CAN ID")
        public byte[] getId() {
            return id;
        }

        public void setId(byte[] id) {
            this.id = id;
        }

        @Property(index = 4, type = DataType.BYTES, length = 8, desc = "CAN DATA")
        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }
    }
}