package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.Bit;
import org.yzh.protocol.commons.JT808;

import java.util.List;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@Message(JT808.提问下发)
public class T8302 extends JTMessage {

    @Field(index = 0, type = DataType.BYTE, desc = "标志：" +
            " [0]紧急" +
            " [1]保留" +
            " [2]终端显示器显示" +
            " [3]终端 TTS 播读" +
            " [4]广告屏显示" +
            " [5]0.中心导航信息|1.CAN故障码信息" +
            " [6~7]保留")
    private int sign;
    @Field(index = 1, type = DataType.STRING, lengthSize = 1, desc = "问题")
    private String content;
    @Field(index = 2, type = DataType.LIST, desc = "候选答案列表")
    private List<Option> options;

    public T8302() {
    }

    public T8302(String content, int... sign) {
        this.content = content;
        this.sign = Bit.writeInt(sign);
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public static class Option {

        @Field(index = 0, type = DataType.BYTE, desc = "答案ID")
        private int id;
        @Field(index = 1, type = DataType.STRING, lengthSize = 2, desc = "答案内容")
        private String content;

        public Option() {
        }

        public Option(int id, String content) {
            this.id = id;
            this.content = content;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(40);
            sb.append("{id=").append(id);
            sb.append(",content=").append(content);
            sb.append('}');
            return sb.toString();
        }
    }
}