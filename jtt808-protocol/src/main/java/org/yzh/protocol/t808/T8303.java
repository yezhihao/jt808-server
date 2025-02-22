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
 * 该消息2019版本已删除
 */
@ToString
@Data
@Accessors(chain = true)
@Message(JT808.信息点播菜单设置)
public class T8303 extends JTMessage {

    /** @see org.yzh.protocol.commons.Action */
    @Field(length = 1, desc = "设置类型")
    private int type;
    @Field(totalUnit = 1, desc = "信息项")
    private List<Info> infos;

    public void addInfo(int type, String name) {
        if (infos == null)
            infos = new ArrayList<>(2);
        infos.add(new Info().setType(type).setName(name));
    }

    @ToString
    @Data
    @Accessors(chain = true)
    public static class Info {
        @Field(length = 1, desc = "信息类型")
        private int type;
        @Field(lengthUnit = 2, desc = "信息名称")
        private String name;
    }
}