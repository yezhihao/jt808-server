package org.yzh.protocol.t1078;

import org.yzh.framework.mvc.model.Response;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT1078;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT1078.文件上传完成通知)
public class T1206 extends JTMessage implements Response {

    private int serialNo;
    private int result;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "结果")
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
