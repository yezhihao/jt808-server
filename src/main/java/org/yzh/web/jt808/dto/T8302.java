package org.yzh.web.jt808.dto;

import org.yzh.framework.enums.DataType;
import org.yzh.framework.orm.annotation.Property;
import org.yzh.framework.orm.annotation.Type;
import org.yzh.framework.orm.model.AbstractBody;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt808.common.JT808;

import java.util.List;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt808-server
 */
@Type(JT808.提问下发)
public class T8302 extends AbstractBody {

    private Integer sign;
    private Integer contentLen;
    private String content;
    private List<Option> options;

    public void buildSign(int[] signs) {
        int sign = 0;
        for (int b : signs)
            sign |= 1 << b;
        this.sign = sign;
    }

    @Property(index = 0, type = DataType.BYTE, desc = "标志")
    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    @Property(index = 1, type = DataType.BYTE, desc = "问题内容长度")
    public Integer getContentLen() {
        return contentLen;
    }

    public void setContentLen(Integer contentLen) {
        this.contentLen = contentLen;
    }

    @Property(index = 2, lengthName = "contentLen", type = DataType.STRING, desc = "问题")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.contentLen = content.getBytes(Charsets.GBK).length;
        this.content = content;
    }

    @Property(index = 2, indexOffsetName = "content", type = DataType.LIST, desc = "候选答案列表")
    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Type
    public static class Option {

        private Integer id;
        private Integer length;
        private String content;

        public Option() {
        }

        public Option(Integer id, String content) {
            this.id = id;
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }

        @Property(index = 0, type = DataType.BYTE, desc = "答案ID")
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Property(index = 1, type = DataType.WORD, desc = "答案内容长度")
        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        @Property(index = 3, type = DataType.STRING, lengthName = "length", desc = "答案内容")
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
            this.length = content.getBytes(Charsets.GBK).length;
        }
    }
}