package org.yzh.framework.orm;

/**
 * 消息元数据
 *
 * @author zhihao.ye (1527621790@qq.com)
 * @home http://gitee.com/yezhihao/jt-server
 */
public class BeanMetadata {

    public final FieldMetadata[] fieldMetadataList;
    public final int length;

    public BeanMetadata(FieldMetadata[] fieldMetadataList, int length) {
        this.fieldMetadataList = fieldMetadataList;
        this.length = length;
    }
}
