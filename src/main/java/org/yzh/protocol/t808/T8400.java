package org.yzh.protocol.t808;

import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.AbstractMessage;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;

/**
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
@Message(JT808.电话回拨)
public class T8400 extends AbstractMessage<Header> {

    /** 普通通话 */
    public static final int Normal = 0;
    /** 监听 */
    public static final int Listen = 1;

    private Integer type;
    private String content;

    @Field(index = 0, type = DataType.BYTE, desc = "标志")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Field(index = 1, type = DataType.STRING, length = 20, desc = "文本信息")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}