package org.yzh.protocol.t808;

import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 * 该消息2019版本已删除
 */
@Message(JT808.信息点播菜单设置)
public class T8303 extends JTMessage {

    /** @see org.yzh.protocol.commons.Action */
    @Field(length = 1, desc = "设置类型")
    private int type;
    @Field(lengthSize = 1, desc = "信息项")
    private List<Info> infos;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Info> getInfos() {
        return infos;
    }

    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    public void addInfo(int type, String name) {
        if (infos == null)
            infos = new ArrayList<>(2);
        infos.add(new Info(type, name));
    }

    public static class Info {
        @Field(length = 1, desc = "信息类型")
        private int type;
        @Field(lengthSize = 2, desc = "信息名称")
        private String name;

        public Info() {
        }

        public Info(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(40);
            sb.append("{type=").append(type);
            sb.append(",name=").append(name);
            sb.append('}');
            return sb.toString();
        }
    }
}