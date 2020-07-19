package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.annotation.Type;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.AbstractBody;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt808.common.MessageId;

import java.util.ArrayList;
import java.util.List;

@Type(MessageId.设置电话本)
public class T8401 extends AbstractBody {

    /** 清空 */
    public static final int Clean = 0;
    /** 更新（删除终端中已有全部联系人并追加消息中的联系人） */
    public static final int Update = 1;
    /** 追加 */
    public static final int Append = 2;
    /** 修改 */
    public static final int Modify = 3;

    private Integer type;
    private Integer total;
    private List<Item> list;

    @Property(index = 0, type = DataType.BYTE, desc = "类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "总数")
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Property(index = 2, type = DataType.LIST, desc = "联系人列表")
    public List<Item> getList() {
        return list;
    }

    public void setList(List<Item> list) {
        this.list = list;
    }

    public void add(Item item) {
        if (list == null)
            list = new ArrayList();
        list.add(item);
        total = list.size();
    }

    public static class Item {
        private Integer sign;

        private Integer phoneLength;

        private String phone;

        private Integer nameLength;

        private String name;

        public Item() {
        }

        public Item(Integer sign, String phone, String name) {
            this.sign = sign;
            this.setPhone(phone);
            this.setName(name);
        }

        @Property(index = 0, type = DataType.BYTE, desc = "标志")
        public Integer getSign() {
            return sign;
        }

        public void setSign(Integer sign) {
            this.sign = sign;
        }

        @Property(index = 1, type = DataType.BYTE, desc = "电话号码长度")
        public Integer getPhoneLength() {
            if (phoneLength == null)
                phoneLength = phone.getBytes(Charsets.GBK).length;
            return phoneLength;
        }

        public void setPhoneLength(Integer phoneLength) {
            this.phoneLength = phoneLength;
        }

        @Property(index = 2, type = DataType.STRING, lengthName = "phoneLength", desc = "电话号码")
        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
            this.phoneLength = phone.getBytes(Charsets.GBK).length;
        }

        @Property(index = 2, indexOffsetName = "phoneLength", type = DataType.BYTE, desc = "联系人长度")
        public Integer getNameLength() {
            if (nameLength == null)
                nameLength = name.getBytes(Charsets.GBK).length;
            return nameLength;
        }

        public void setNameLength(Integer nameLength) {
            this.nameLength = nameLength;
        }

        @Property(index = 3, indexOffsetName = "phoneLength", type = DataType.STRING, lengthName = "nameLength", desc = "联系人")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
            this.nameLength = name.getBytes(Charsets.GBK).length;
        }
    }
}