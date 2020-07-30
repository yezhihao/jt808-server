package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.Charsets;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.信息服务)
public class T8304 extends AbstractMessage<Header> {

    private Integer type;
    private Integer length;
    private String content;

    public T8304() {
    }

    public T8304(String mobileNo) {
        super(new Header(mobileNo, JT808.信息服务));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "信息类型")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.WORD, desc = "信息长度")
    public Integer getLength() {
        if (length == null)
            this.length = content.getBytes(Charsets.GBK).length;
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Field(index = 3, type = DataType.STRING, lengthName = "length", desc = "文本信息")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.length = content.getBytes(Charsets.GBK).length;
    }
}