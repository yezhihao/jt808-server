package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.TerminalParameterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.设置终端参数)
public class T8103 extends JTMessage {

    private int total;
    private List<BytesParameter> bytesParameters;

    public T8103() {
    }

    public T8103(String mobileNo) {
        super(new Header(mobileNo, JT808.设置终端参数));
    }

    @Field(index = 0, type = DataType.BYTE, desc = "参数总数")
    public int getTotal() {
        if (bytesParameters != null)
            return bytesParameters.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 1, type = DataType.LIST, desc = "参数项列表")
    public List<BytesParameter> getBytesParameters() {
        return bytesParameters;
    }

    public void setBytesParameters(List<BytesParameter> bytesParameters) {
        this.bytesParameters = bytesParameters;
    }

    public void addParameter(BytesParameter bytesParameter) {
        if (bytesParameters == null)
            bytesParameters = new ArrayList<>();
        bytesParameters.add(bytesParameter);
    }

    public Map<Integer, String> getParameters() {
        return TerminalParameterUtils.transform(bytesParameters);
    }

    public void setParameters(Map<Integer, String> parameters) {
        this.bytesParameters = TerminalParameterUtils.transform(parameters);
    }
}