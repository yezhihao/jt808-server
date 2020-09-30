package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yzh.framework.commons.transform.Bcd;
import org.yzh.framework.orm.annotation.Field;
import org.yzh.framework.orm.model.DataType;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.yzh.framework.orm.model.DataType.*;

/**
 * 消息定义
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class FieldMetadata extends BeanMetadata {
    private static Logger log = LoggerFactory.getLogger(FieldMetadata.class.getSimpleName());
    protected DataType dataType;
    protected Method readMethod;
    protected Method writeMethod;

    protected int index;
    protected String desc;
    protected Charset charset;
    protected byte pad;
    protected int size;
    protected boolean isLong;
    protected boolean isString;
    protected boolean isDateTime;
    protected boolean isByteBuffer;
    protected Field field;
    protected int version;
    protected Method lengthMethod;

    public FieldMetadata(Field field, int version, Class classType, Method readMethod, Method writeMethod) {
        super(classType);
        this.field = field;
        this.version = version;
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;

        this.index = field.index();
        this.dataType = field.type();
        this.charset = Charset.forName(field.charset());
        this.pad = field.pad();
        this.desc = field.desc();
        if (field.length() > -1)
            this.length = field.length();
        else
            this.length = field.type().length;
        if (dataType == DWORD)
            this.isLong = classType.isAssignableFrom(Long.class) || classType.isAssignableFrom(Long.TYPE);
        else
            this.isLong = false;

        if (dataType == BCD8421)
            this.isDateTime = classType.isAssignableFrom(LocalDateTime.class);
        else
            this.isDateTime = false;

        if (dataType == BYTES) {
            this.isString = classType.isAssignableFrom(String.class);
            this.isByteBuffer = classType.isAssignableFrom(ByteBuffer.class);
        } else {
            this.isString = false;
            this.isByteBuffer = false;
        }

        if (dataType == LIST) {
            this.typeClass = (Class<?>) ((ParameterizedType) readMethod.getGenericReturnType()).getActualTypeArguments()[0];
            this.length = -1;
        }
    }

    public Integer getLength(Object obj) {
        if (lengthMethod == null)
            return length;
        try {
            return (Integer) lengthMethod.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object read(ByteBuf buf, int length) {
        DataType type = this.dataType;
        if (DWORD == type) {
            if (this.isLong)
                return buf.readUnsignedInt();
            return (int) buf.readUnsignedInt();
        }
        if (WORD == type) {
            return buf.readUnsignedShort();
        }
        if (BYTE == type) {
            return (int) buf.readUnsignedByte();
        }

        if (length == -1)
            length = buf.readableBytes();

        if (OBJ == type) {
            return decode(buf.readSlice(length));
        }
        if (LIST == type) {
            if (length <= 0)
                return null;
            List list = new ArrayList();
//            ByteBuf slice = buf.readSlice(length);
            while (buf.isReadable()) {
                Object obj = decode(buf);
                if (obj == null) break;
                list.add(obj);
            }
            return list;
        }

        if (STRING == type) {
            return buf.readCharSequence(length, this.charset).toString().trim();
        }

        if (BCD8421 == type) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            if (this.isDateTime)
                return Bcd.toDateTime(bytes);
            return Bcd.leftTrim(Bcd.toStr(bytes), '0');
        }
        if (this.isByteBuffer) {
            return buf.nioBuffer(buf.readerIndex(), length);
        }

        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        if (this.isString) {
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] != this.pad)
                    return new String(bytes, i, bytes.length - i, this.charset);
            }
            return new String(bytes, this.charset);
        }
        return bytes;
    }


    public void write(ByteBuf buf, FieldMetadata field, Object value) {
        int length = field.length;

        switch (field.dataType) {
            case BYTE:
                buf.writeByte((int) value);
                break;
            case WORD:
                buf.writeShort((int) value);
                break;
            case DWORD:
                if (field.isLong)
                    buf.writeInt(((Long) value).intValue());
                else
                    buf.writeInt((int) value);
                break;
            case BYTES:
                if (field.isString) {
                    byte[] bytes = ((String) value).getBytes(field.charset);
                    int srcLen = bytes.length;
                    if (length > 0) {
                        bytes = Bcd.checkRepair(bytes, length);
                        if (srcLen > bytes.length)
                            log.warn("数据长度超出限制[{}]原始长度{},目标长度{},[{}]", value, srcLen, bytes.length);
                    }
                    buf.writeBytes(bytes);
                } else if (field.isByteBuffer) {
                    ByteBuffer byteBuffer = (ByteBuffer) value;
                    if (length > 0)
                        byteBuffer.position(byteBuffer.limit() - length);
                    buf.writeBytes(byteBuffer);

                } else {
                    if (length < 0) buf.writeBytes((byte[]) value);
                    else buf.writeBytes((byte[]) value, 0, length);
                }
                break;
            case BCD8421:
                if (field.isDateTime)
                    buf.writeBytes(Bcd.fromDateTime((LocalDateTime) value));
                else
                    buf.writeBytes(Bcd.fromStr(Bcd.leftPad((String) value, length * 2, '0')));
                break;
            case STRING:
                byte[] bytes = ((String) value).getBytes(field.charset);
                int srcLen = bytes.length;
                if (length > 0) {
                    bytes = Bcd.checkRepair(bytes, length);
                    if (srcLen > bytes.length)
                        log.warn("数据长度超出限制[{}],数据长度{},目标长度{}", value, srcLen, bytes.length);
                }
                buf.writeBytes(bytes);
                break;
            case OBJ:
                buf.writeBytes(encode(value));
                break;
            case LIST:
                if (value != null)
                    buf.writeBytes(encode((List) value));
                break;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "desc='" + desc + '\'' +
                ", typeClass=" + typeClass +
                ", readMethod=" + readMethod +
                ", writeMethod=" + writeMethod +
                ", dataType=" + dataType +
                ", charset=" + charset +
                ", pad=" + pad +
                ", length=" + length +
                ", lengthMethod=" + lengthMethod +
                '}';
    }
}
