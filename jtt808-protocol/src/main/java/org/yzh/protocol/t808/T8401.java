package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.设置电话本)
public class T8401 extends JTMessage {

    /** @see org.yzh.protocol.commons.Action */
    @Field(length = 1, desc = "类型")
    private int type;
    @Field(totalUnit = 1, desc = "联系人项")
    private List<Contact> contacts;

    public void addContact(Contact contact) {
        if (contacts == null)
            contacts = new ArrayList<>(2);
        contacts.add(contact);
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Contact {
        @Field(length = 1, desc = "标志")
        private int sign;
        @Field(lengthUnit = 1, desc = "电话号码")
        private String phone;
        @Field(lengthUnit = 1, desc = "联系人")
        private String name;
    }
}