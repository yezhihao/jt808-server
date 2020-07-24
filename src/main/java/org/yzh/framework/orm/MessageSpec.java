package org.yzh.framework.orm;

/**
 * 消息定义
 */
public class MessageSpec {

    public final FieldSpec[] fieldSpecs;
    public final int length;

    public MessageSpec(FieldSpec[] fieldSpecs, int length) {
        this.fieldSpecs = fieldSpecs;
        this.length = length;
    }
}
