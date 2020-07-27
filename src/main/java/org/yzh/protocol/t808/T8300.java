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
@Message(JT808.文本信息下发)
public class T8300 extends AbstractMessage<Header> {

    private Integer sign;

    private int[] signs;

    private String content;

    @Field(index = 0, type = DataType.BYTE, desc = "标志")
    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public int[] getSigns() {
        return signs;
    }

    public void setSigns(int[] signs) {
        int sign = 0;
        for (int b : signs) {
            sign |= 1 << b;
        }
        this.sign = sign;
        this.signs = signs;
    }

    @Field(index = 1, type = DataType.STRING, desc = "文本信息")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}