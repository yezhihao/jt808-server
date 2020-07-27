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
@Message(JT808.车辆控制)
public class T8500 extends AbstractMessage<Header> {

    private Integer sign;

    @Field(index = 0, type = DataType.BYTE, desc = "控制标志")
    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public void buildSign(int[] signs) {
        int sign = 0;
        for (int b : signs) {
            sign |= 1 << b;
        }
        this.sign = sign;
    }
}