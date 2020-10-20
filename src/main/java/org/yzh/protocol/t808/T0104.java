package org.yzh.protocol.t808;

import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.annotation.Message;
import org.yzh.framework.mvc.model.AbstractMessage;
import org.yzh.framework.orm.model.DataType;
import org.yzh.framework.mvc.model.Response;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.basics.Header;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.TerminalParameterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询终端参数应答)
public class T0104 extends AbstractMessage<Header> implements Response {

    private int serialNo;
    private int total;
    private List<BytesParameter> bytesParameters;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "应答参数个数")
    public int getTotal() {
        if (bytesParameters != null)
            return bytesParameters.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Field(index = 3, type = DataType.LIST, desc = "参数项列表")
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