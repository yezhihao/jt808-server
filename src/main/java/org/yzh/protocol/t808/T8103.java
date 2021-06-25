package org.yzh.protocol.t808;

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
@Message(JT808.设置终端参数)
public class T8103 extends JTMessage {

    @Field(index = 0, type = DataType.BYTE, desc = "参数总数")
    private int total;
    @Convert(converter = ParameterConverter.class)
    @Field(index = 1, type = DataType.MAP, desc = "参数项列表")
    private Map<Integer, Object> parameters;

    public T8103() {
    }

    public T8103(Map<Integer, Object> parameters) {
        this.parameters = parameters;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Map<Integer, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Integer, Object> parameters) {
        this.parameters = parameters;
        this.total = parameters.size();
    }

    public void addParameter(Integer key, Object value) {
        if (parameters == null)
            parameters = new TreeMap();
        parameters.put(key, value);
        total = parameters.size();
    }
}