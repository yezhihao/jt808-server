package org.yzh.framework.orm;

/**
 * 消息元数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class BeanMetadata<T> {

    public final Class<? extends T> typeClass;
    public final FieldMetadata[] fieldMetadataList;
    public final int length;

    public BeanMetadata(Class<? extends T> typeClass, FieldMetadata[] fieldMetadataList) {
        this.typeClass = typeClass;
        this.fieldMetadataList = fieldMetadataList;

        FieldMetadata lastField = fieldMetadataList[fieldMetadataList.length - 1];
        int lastIndex = lastField.index;
        int lastLength = lastField.length < 0 ? 4 : lastField.length;
        this.length = lastIndex + lastLength;
    }
}