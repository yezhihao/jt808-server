package org.yzh.protocol.commons.transform;

import org.yzh.framework.commons.transform.Bytes;
import org.yzh.framework.orm.model.DataType;
import org.yzh.protocol.basics.BytesParameter;
import org.yzh.protocol.commons.Charsets;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class TerminalParameterUtils {

    private static final Map<Integer, DataType> MAPPER;

    static {
        Map<Integer, DataType> map = new HashMap(ParameterType.values().length);
        Arrays.stream(ParameterType.values()).forEach(p -> map.put(p.id, p.type));
        MAPPER = Collections.unmodifiableMap(map);
    }


    public static Map<Integer, String> transform(List<BytesParameter> bytesParameters) {
        if (bytesParameters == null)
            return null;
        Map<Integer, String> result = new TreeMap<>();
        for (BytesParameter bytesParameter : bytesParameters) {
            Integer id = bytesParameter.getId();
            result.put(id, String.valueOf(toValue(id, bytesParameter.getValue())));
        }
        return result;
    }

    public static List<BytesParameter> transform(Map<Integer, String> parameters) {
        if (parameters == null)
            return null;
        List<BytesParameter> result = new ArrayList<>(parameters.size());
        for (Map.Entry<Integer, String> entry : parameters.entrySet()) {
            Integer id = entry.getKey();
            result.add(new BytesParameter(id, toBytes(id, entry.getValue())));
        }
        return result;
    }

    public static Object toValue(int id, byte[] bytes) {
        DataType dataType = MAPPER.get(id);
        if (dataType != null)
            switch (dataType) {
                case WORD:
                    return Bytes.getInt16(bytes, 0);
                case DWORD:
                    return Bytes.getInt32(bytes, 0);
                case BYTE:
                    return bytes[0];
                case BYTES:
                    return bytes;
            }
        return new String(bytes, Charset.forName("GBK"));
    }

    public static byte[] toBytes(int id, String value) {
        DataType dataType = MAPPER.get(id);
        if (dataType != null)
            switch (dataType) {
                case WORD:
                    return Bytes.setInt16(new byte[2], 0, Integer.parseInt(value));
                case DWORD:
                    return Bytes.setInt32(new byte[4], 0, Integer.parseInt(value));
                case BYTE:
                    return new byte[]{(byte) Integer.parseInt(value)};
                case STRING:
                    return value.getBytes(Charsets.GBK);
            }
        return null;
    }

    public static byte[] toBytes(int id, int value) {
        DataType dataType = MAPPER.get(id);
        if (dataType != null)
            switch (dataType) {
                case WORD:
                    return Bytes.setInt16(new byte[2], 0, value);
                case DWORD:
                    return Bytes.setInt32(new byte[4], 0, value);
                case BYTE:
                    return new byte[]{(byte) value};
            }
        return null;
    }
}