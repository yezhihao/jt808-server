package org.yzh.framework.orm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 消息元数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class BeanMetadata<T> {
    private static Logger log = LoggerFactory.getLogger(BeanMetadata.class.getSimpleName());
    protected Class<T> typeClass;
    protected FieldMetadata[] fieldMetadataList;
    protected int length;

    public BeanMetadata(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    public BeanMetadata(Class<T> typeClass, FieldMetadata[] fieldMetadataList) {
        this.typeClass = typeClass;
        this.fieldMetadataList = fieldMetadataList;

        FieldMetadata lastField = fieldMetadataList[fieldMetadataList.length - 1];
        int lastIndex = lastField.index;
        int lastLength = lastField.length < 0 ? 4 : lastField.length;
        this.length = lastIndex + lastLength;
    }

    public FieldMetadata[] getFieldMetadataList() {
        return fieldMetadataList;
    }

    public void setFieldMetadataList(FieldMetadata[] fieldMetadataList) {
        this.fieldMetadataList = fieldMetadataList;
    }

    public <T> T decode(ByteBuf buf) {
        T result = null;
        boolean isEmpty = true;//防止死循环
        try {
            result = (T) typeClass.newInstance();
            if (this.fieldMetadataList == null)
                return null;

            for (FieldMetadata field : this.fieldMetadataList) {
                Integer length = field.getLength(result);

                if (!buf.isReadable(length))
                    break;
                Object value = field.read(buf, length);
                field.writeMethod.invoke(result, value);
                isEmpty = false;
            }
        } catch (Exception e) {
            log.error("解码异常：" + typeClass.getName(), e);
        }
        if (isEmpty)
            return null;
        return result;
    }

    public ByteBuf encode(Object obj) {
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(length, 2048);
        try {
            for (FieldMetadata field : fieldMetadataList) {
                Object value = field.readMethod.invoke(obj);
                if (value != null)
                    field.write(buf, field, value);
            }
        } catch (Exception e) {
            log.error("获取对象值失败", e);
        }
        return buf;
    }

    public ByteBuf encode(List<Object> list) {
        int size = list.size();
        if (size == 0)
            return Unpooled.EMPTY_BUFFER;

        int length = 128;
        if (this.length > 0) {
            length = this.length;
        }

        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(length * size);
        try {
            for (Object obj : list) {
                for (FieldMetadata field : fieldMetadataList) {
                    Object value = field.readMethod.invoke(obj);
                    if (value != null)
                        field.write(buf, field, value);
                }
            }
        } catch (Exception e) {
            log.error("获取对象值失败", e);
        }
        return buf;
    }
}