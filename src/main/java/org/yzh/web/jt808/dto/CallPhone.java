package org.yzh.web.jt808.dto;

import org.yzh.framework.annotation.Property;
import org.yzh.framework.enums.DataType;
import org.yzh.framework.message.PackageData;
import org.yzh.web.jt808.dto.basics.Header;

/**
 * 电话回拨
 */
public class CallPhone extends PackageData<Header> {

    //普通通话
    public static final int Normal = 0;
    //监听
    public static final int Listen = 1;

    private Integer type;

    private String content;

    @Property(index = 0, type = DataType.BYTE, desc = "标志")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    @Property(index = 1, type = DataType.STRING, length = 20, desc = "文本信息")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}