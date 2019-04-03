package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.config.Charsets;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 信息服务
 */
public class Information extends PackageData<Header> {

    private Integer type;

    private Integer length;

    private String content;

    @Property(index = 0, type = DataType.BYTE, desc = "标志")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Property(index = 1, type = DataType.WORD, desc = "信息长度")
    public Integer getLength() {
        if (length == null)
            this.length = content.getBytes(Charsets.GBK).length;
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Property(index = 3, type = DataType.STRING, lengthName = "length", desc = "文本信息")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.length = content.getBytes(Charsets.GBK).length;
    }
}