package org.yzh.protocol.t808;

import io.github.yezhihao.netmc.core.model.Response;
import io.github.yezhihao.protostar.DataType;
import io.github.yezhihao.protostar.annotation.Convert;
import io.github.yezhihao.protostar.annotation.Field;
import io.github.yezhihao.protostar.annotation.Message;
import org.yzh.protocol.basics.JTMessage;
import org.yzh.protocol.commons.JT808;
import org.yzh.protocol.commons.transform.ParameterConverter;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
@Message(JT808.查询终端参数应答)
public class T0104 extends JTMessage implements Response {

    private int responseSerialNo;
    private int total;
    private Map<Integer, Object> parameters;

    @Field(index = 0, type = DataType.WORD, desc = "应答流水号")
    public int getResponseSerialNo() {
        return responseSerialNo;
    }

    public void setResponseSerialNo(int responseSerialNo) {
        this.responseSerialNo = responseSerialNo;
    }

    @Field(index = 2, type = DataType.BYTE, desc = "应答参数个数")
    public int getTotal() {
        if (parameters != null)
            return parameters.size();
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Convert(converter = ParameterConverter.class)
    @Field(index = 3, type = DataType.MAP, desc = "参数项列表")
    public Map<Integer, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Integer, Object> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(Integer key, Object value) {
        if (parameters == null)
            parameters = new TreeMap();
        parameters.put(key, value);
    }
}