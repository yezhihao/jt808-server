package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.Charsets;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.提问下发)
public class T8302 extends AbstractMessage<Header> {

    private int sign;
    private int contentLen;
    private String content;
    private List<Option> options;

    public void buildSign(int[] signs) {
        int sign = 0;
        for (int b : signs)
            sign |= 1 << b;
        this.sign = sign;
    }

    @Field(index = 0, type = DataType.BYTE, desc = "标志")
    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    @Field(index = 1, type = DataType.BYTE, desc = "问题内容长度")
    public int getContentLen() {
        return contentLen;
    }

    public void setContentLen(int contentLen) {
        this.contentLen = contentLen;
    }

    @Field(index = 2, lengthName = "contentLen", type = DataType.STRING, desc = "问题")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.contentLen = content.getBytes(Charsets.GBK).length;
        this.content = content;
    }

    @Field(index = 2, indexOffsetName = "content", type = DataType.LIST, desc = "候选答案列表")
    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public static class Option {

        private int id;
        private int length;
        private String content;

        public Option() {
        }

        public Option(int id, String content) {
            this.id = id;
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }

        @Field(index = 0, type = DataType.BYTE, desc = "答案ID")
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Field(index = 1, type = DataType.WORD, desc = "答案内容长度")
        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        @Field(index = 3, type = DataType.STRING, lengthName = "length", desc = "答案内容")
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }
    }
}