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

/**
 * 消息定义
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public abstract class FieldMetadata extends BeanMetadata {
    private static Logger log = LoggerFactory.getLogger(FieldMetadata.class.getSimpleName());
    protected DataType dataType;
    protected Method readMethod;
    protected Method writeMethod;

    protected int version;
    protected int index;
    protected String lengthName;
    protected Method lengthMethod;
    protected String desc;

    public FieldMetadata(Class typeClass) {
        super(typeClass);
    }

    public static FieldMetadata newInstance(Class typeClass, Method readMethod, Method writeMethod, int version, Field field) {
        FieldMetadata fieldMetadata;
        switch (field.type()) {
            case BYTE:
                fieldMetadata = new BYTEToInt(typeClass);
                break;
            case WORD:
                fieldMetadata = new WORDToInt(typeClass);
                break;
            case DWORD:
                if (typeClass.isAssignableFrom(Long.class) || typeClass.isAssignableFrom(Long.TYPE))
                    fieldMetadata = new DWORDToLong(typeClass);
                else
                    fieldMetadata = new DWORDToInt(typeClass);
                break;
            case BCD8421:
                if (typeClass.isAssignableFrom(LocalDateTime.class))
                    fieldMetadata = new BCDToDateTime(typeClass);
                else
                    fieldMetadata = new BCDToString(typeClass);
                break;
            case BYTES:
                if (typeClass.isAssignableFrom(String.class))
                    fieldMetadata = new ToString(typeClass, field.pad(), Charset.forName(field.charset()));
                else if (typeClass.isAssignableFrom(ByteBuffer.class))
                    fieldMetadata = new ToByteBuffer(typeClass);
                else
                    fieldMetadata = new ToBytes(typeClass);
                break;
            case STRING:
                fieldMetadata = new ToString(typeClass, field.pad(), Charset.forName(field.charset()));
                break;
            case OBJ:
                fieldMetadata = new ToObj(typeClass);
                break;
            case LIST:
                fieldMetadata = new ToList((Class<?>) ((ParameterizedType) readMethod.getGenericReturnType()).getActualTypeArguments()[0]);
                break;
            default:
                throw new RuntimeException("不支持的类型转换");
        }

        fieldMetadata.readMethod = readMethod;
        fieldMetadata.writeMethod = writeMethod;
        fieldMetadata.version = version;
        fieldMetadata.index = field.index();
        fieldMetadata.dataType = field.type();
        fieldMetadata.desc = field.desc();
        if (!field.lengthName().equals(""))
            fieldMetadata.lengthName = field.lengthName();
        if (field.length() > -1)
            fieldMetadata.length = field.length();
        else
            fieldMetadata.length = field.type().length;
        return fieldMetadata;
    }

    public abstract void write(ByteBuf buf, Object value);

    public abstract Object read(ByteBuf buf, int length);

    public String getLengthName() {
        return lengthName;
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

    public static class BYTEToInt extends FieldMetadata {
        public BYTEToInt(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            return (int) buf.readUnsignedByte();
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            buf.writeByte((int) value);
        }
    }

    public static class WORDToInt extends FieldMetadata {
        public WORDToInt(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            return buf.readUnsignedShort();
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            buf.writeShort((int) value);
        }
    }

    public static class DWORDToInt extends FieldMetadata {
        public DWORDToInt(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            return (int) buf.readUnsignedInt();
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            buf.writeInt((int) value);
        }
    }

    public static class DWORDToLong extends FieldMetadata {
        public DWORDToLong(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            return buf.readUnsignedInt();
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            buf.writeInt(((Long) value).intValue());
        }
    }

    public static class BCDToString extends FieldMetadata {
        public BCDToString(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return Bcd.leftTrim(Bcd.toStr(bytes), '0');
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            buf.writeBytes(Bcd.fromStr(Bcd.leftPad((String) value, length * 2, '0')));
        }
    }

    public static class BCDToDateTime extends FieldMetadata {
        public BCDToDateTime(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return Bcd.toDateTime(bytes);
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            buf.writeBytes(Bcd.fromDateTime((LocalDateTime) value));
        }
    }


    public static class ToByteBuffer extends FieldMetadata {
        public ToByteBuffer(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            return buf.nioBuffer(buf.readerIndex(), length);
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            ByteBuffer byteBuffer = (ByteBuffer) value;
            if (length > 0)
                byteBuffer.position(byteBuffer.limit() - length);
            buf.writeBytes(byteBuffer);
        }
    }

    public static class ToBytes extends FieldMetadata {
        public ToBytes(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            if (length == -1)
                length = buf.readableBytes();
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return bytes;
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            if (length < 0)
                buf.writeBytes((byte[]) value);
            else
                buf.writeBytes((byte[]) value, 0, length);
        }
    }

    public static class ToString extends FieldMetadata {
        private Charset charset;
        private byte pad;

        public ToString(Class typeClass, byte pad, Charset charset) {
            super(typeClass);
            this.pad = pad;
            this.charset = charset;
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            if (length == -1) {
                return buf.readCharSequence(buf.readableBytes(), this.charset).toString().trim();
            } else {
                byte[] bytes = new byte[length];
                buf.readBytes(bytes);
                for (int i = 0; i < bytes.length; i++) {
                    if (bytes[i] != this.pad)
                        return new String(bytes, i, bytes.length - i, this.charset);
                }
                return new String(bytes, this.charset);
            }
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            byte[] bytes = ((String) value).getBytes(charset);
            int srcLen = bytes.length;
            if (length > 0) {
                bytes = Bcd.checkRepair(bytes, length);
                if (srcLen > bytes.length)
                    log.warn("数据长度超出限制[{}],数据长度{},目标长度{}", value, srcLen, bytes.length);
            }
            buf.writeBytes(bytes);
        }
    }

    public static class ToObj extends FieldMetadata {
        public ToObj(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
            if (length == -1)
                length = buf.readableBytes();
            return decode(buf.readSlice(length));
        }

        @Override
        public void write(ByteBuf buf, Object value) {
            if (value != null)
                encode(buf, value);
        }
    }

    public static class ToList extends FieldMetadata {
        public ToList(Class typeClass) {
            super(typeClass);
        }

        @Override
        public Object read(ByteBuf buf, int length) {
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

        @Override
        public void write(ByteBuf buf, Object value) {
            if (value != null)
                encode(buf, (List) value);
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
                ", length=" + length +
                ", lengthMethod=" + lengthMethod +
                '}';
    }
}