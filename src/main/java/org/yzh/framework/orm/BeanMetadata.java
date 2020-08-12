package org.yzh.framework.orm;

/**
 * 消息元数据
 * @author yezhihao
 * @home https://gitee.com/yezhihao/jt808-server
 */
public class BeanMetadata {

    public final FieldMetadata[] fieldMetadataList;
    public final int length;

    public BeanMetadata(FieldMetadata[] fieldMetadataList, int length) {
        this.fieldMetadataList = fieldMetadataList;
        this.length = length;
    }
}
